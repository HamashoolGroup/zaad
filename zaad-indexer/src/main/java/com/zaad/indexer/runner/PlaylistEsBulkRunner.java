package com.zaad.indexer.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaad.common.domain.Playlist;
import com.zaad.common.exception.ZaadDomainCreationException;
import com.zaad.common.util.ZaadOutputDirectoryManager;
import com.zaad.indexer.common.ZaadEsBulkRunner;
import com.zaad.indexer.common.util.ZaadLanguageUtil;
import com.zaad.indexer.transport.ZaadEsClient;
import com.zaad.indexer.transport.ZaadEsTransportClient;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

/**
 * @author socurites, lks21c
 */
public class PlaylistEsBulkRunner extends ZaadEsBulkRunner {
    /**
     * 로거
     */
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TutorEsBulkRunner.class);

    private static final String INDEX_NAME = "playlist";
    private static final String TYPE_NAME = "detail";

    public static void main(String[] args) throws InterruptedException {
        ZaadEsClient zaadEsClient = new ZaadEsTransportClient();
        PlaylistEsBulkRunner runner = new PlaylistEsBulkRunner(zaadEsClient);
        runner.bulk();
    }

    public PlaylistEsBulkRunner(ZaadEsClient zaadEsClient) {
        this.client = zaadEsClient;
    }

    @Override
    protected void init() {
        this.indexName = INDEX_NAME;
        this.typeName = TYPE_NAME;
        super.init();
    }

    @Override
    protected void bulk() throws InterruptedException {
        init();

        ObjectMapper mapper = new ObjectMapper();

        List<String> tutorPlaylistIds = ZaadOutputDirectoryManager.getDataPlaylistIds();
        for (String tutorPlaylistId : tutorPlaylistIds) {
            try {
                Playlist playlist = readPlaylist(tutorPlaylistId);

                if (playlist != null) {
                    IndexRequestBuilder builder = this.client.getClient()
                            .prepareIndex(newIndexName, typeName) // 인덱스명 설정
                            .setId(playlist.getPlaylistId()) // 색인ID 설정
                            .setSource(mapper.writeValueAsString(playlist)); // 소스 설정

                    // 벌크 프로세서에 추가
                    processor.add(builder.request());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // awaitClose시에 벌크 색인을 종료하기 전에 남아있는 색인 요청(BULK_SIZE보다 적은 요청)이 추가로 색인 실행
        // processor.awaitClose(..) 호출시 내부적으로 호출되므로 주석 처리함.
        //processor.flush();

        // 벌크 색인 종료를 기다림
        processor.close();

        /**
         * 1. alias 변경
         * 2. 색인 refresh
         */
        beforeExit(newIndexName, newIndexName, aliasName);
    }

    public static Playlist readPlaylist(String tutorPlaylistId) throws Exception {
        String line;
        Scanner scanner = null;
        String tutorId = StringUtils.split(tutorPlaylistId, "/")[0];
        String playlistId = StringUtils.split(tutorPlaylistId, "/")[1];
        ;
        File playlistDataFile = ZaadOutputDirectoryManager.getPlaylistDataFile(tutorId, playlistId);
        Playlist playlist = null;
        try {
            scanner = new Scanner(playlistDataFile);

            List<String> lines = new ArrayList<String>();
            while (scanner.hasNext()) {
                line = scanner.nextLine();
                if (line != null) {
                    lines.add(line);
                }
            }

            try {
                playlist = new Playlist(lines);

                if (!ZaadLanguageUtil.isLangEnglish(playlist.getTitle())) {
                    playlist = null;
                    throw new ZaadDomainCreationException();
                }

                playlist.setTutorId(tutorId);
                playlist.setPlaylistId(playlistId);
                playlist.generateKeyTags(playlist.getTitle());
                playlist.setTutor(TutorEsBulkRunner.readTutor(tutorId));

                // voa news filtering
                if (playlist.getTutor().getTutorName().toUpperCase().contains("VOA")) {
                    Set<String> newsCategory = new HashSet<String>();
                    newsCategory.add("news");
                    playlist.addCategories(newsCategory);
                    playlist.addTags(newsCategory);
                    playlist.setSections(null);
                    playlist.setTests(null);
                    playlist.setContinentals(null);
                    playlist.setLevels(null);
                }
            } catch (ZaadDomainCreationException e) {
                logger.error(e.getMessage() + " for: " + tutorPlaylistId);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            scanner.close();
        }

        return playlist;
    }
}
