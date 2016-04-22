package com.zaad.indexer.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaad.common.domain.Playlist;
import com.zaad.common.domain.Video;
import com.zaad.common.exception.ZaadDomainCreationException;
import com.zaad.common.util.ZaadOutputDirectoryManager;
import com.zaad.indexer.common.ZaadEsBulkRunner;
import com.zaad.indexer.common.util.ZaadLanguageUtil;
import com.zaad.indexer.sitemap.VideoSiteMapGenerator;
import com.zaad.indexer.transport.ZaadEsClient;
import com.zaad.indexer.transport.ZaadEsTransportClient;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author socurites, lks21c
 */
public class VideoEsBulkRunner extends ZaadEsBulkRunner {
    /**
     * 로거
     */
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TutorEsBulkRunner.class);

    private static final String INDEX_NAME = "video";
    private static final String TYPE_NAME = "detail";

    public static void main(String[] args) throws Exception {
        ZaadEsClient zaadEsClient = new ZaadEsTransportClient();
        VideoEsBulkRunner runner = new VideoEsBulkRunner(zaadEsClient);
        runner.bulk();
    }

    public VideoEsBulkRunner(ZaadEsClient zaadEsClient) {
        this.client = zaadEsClient;
    }

    @Override
    protected void init() {
        this.indexName = INDEX_NAME;
        this.typeName = TYPE_NAME;
        super.init();
    }

    @Override
    protected void bulk() throws FileNotFoundException, InterruptedException {
        init();

        ObjectMapper mapper = new ObjectMapper();
        VideoSiteMapGenerator siteMapGenerator = new VideoSiteMapGenerator();
        String line;
        Scanner scanner = null;
        String tutorId, playlistId, videoId;
        Playlist playlist;

        List<String> tutorPlaylistVideoIds = ZaadOutputDirectoryManager.getDataVideoIds();
        for (String tutorPlaylistVideoId : tutorPlaylistVideoIds) {
            tutorId = StringUtils.split(tutorPlaylistVideoId, "/")[0];
            playlistId = StringUtils.split(tutorPlaylistVideoId, "/")[1];
            videoId = StringUtils.split(tutorPlaylistVideoId, "/")[2];
            File videoDataFile = ZaadOutputDirectoryManager.getVideoDataFile(tutorId, playlistId, videoId);
            try {
                scanner = new Scanner(videoDataFile);

                List<String> lines = new ArrayList<>();
                while (scanner.hasNext()) {
                    line = scanner.nextLine();
                    if (line != null) {
                        lines.add(line);
                    }
                }

                Video video = null;
                try {
                    video = new Video(lines, tutorId, playlistId, videoId);

                    if (!ZaadLanguageUtil.isLangEnglish(video.getTitle())) {
                        video = null;
                        throw new ZaadDomainCreationException();
                    }

                    video.generateKeyTags(video.getTitle());
                    video.setTutor(TutorEsBulkRunner.readTutor(tutorId));

                    // voa news filtering
                    if (video.getTutor().getTutorName().toUpperCase().contains("VOA")) {
                        Set<String> newsCategory = new HashSet<String>();
                        newsCategory.add("news");
                        video.addCategories(newsCategory);
                        video.addTags(newsCategory);
                        video.setSections(null);
                        video.setTests(null);
                        video.setContinentals(null);
                        video.setLevels(null);
                    }

                    playlist = PlaylistEsBulkRunner.readPlaylist(tutorId + "/" + playlistId);

                    if (playlist == null) {
                        System.out.println("playlist null");
                        break;
                    }

                    video.setPlaylist(playlist);
                    video.addCategories(playlist.getCategories());
                    video.addSections(playlist.getSections());
                    video.addTests(playlist.getTests());
                    video.addTags(playlist.getTags());
                    video.addContinentals(playlist.getContinentals());
                    video.addLevels(playlist.getLevels());

                    video.calcRanking();
                } catch (ZaadDomainCreationException e) {
                    logger.error(e.getMessage() + " for: " + tutorPlaylistVideoId);
                }

                if (video != null) {
                    IndexRequestBuilder builder = this.client.getClient()
                            .prepareIndex(newIndexName, typeName) // 인덱스명 설정
                            .setId(video.getZaadId()) // 색인ID 설정
                            .setSource(mapper.writeValueAsString(mapper.writeValueAsString(video))); // 소스 설정
                    // 벌크 프로세서에 추가
                    processor.add(builder.request());
                    siteMapGenerator.appendUrl(video);
                }
            } catch (Exception e) {
                System.out.println(tutorPlaylistVideoId);
                e.printStackTrace();
            } finally {
                scanner.close();
            }
        }
        siteMapGenerator.close();

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
}
