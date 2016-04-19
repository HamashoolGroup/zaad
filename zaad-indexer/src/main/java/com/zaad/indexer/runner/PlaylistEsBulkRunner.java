package com.zaad.indexer.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaad.common.domain.Playlist;
import com.zaad.common.exception.ZaadDomainCreationException;
import com.zaad.common.util.ZaadLogger;
import com.zaad.common.util.ZaadOutputDirectoryManager;
import com.zaad.indexer.common.ZaadEsBulkRunner;
import com.zaad.indexer.common.util.ZaadLanguageUtil;
import com.zaad.indexer.transport.ZaadEsClient;
import com.zaad.indexer.transport.ZaadEsTransportClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;

import java.io.File;
import java.util.*;

public class PlaylistEsBulkRunner extends ZaadEsBulkRunner {
    protected static final int BULK_SIZE = 1000;
    private static final String INDEX_NAME = "playlist";
    private static final String TYPE_NAME = "detail";

    private static Logger logger;

    static {
        logger = ZaadLogger.getLogger(PlaylistEsBulkRunner.class);
    }


    public static void main(String[] args) {
        ZaadEsClient zaadEsClient = new ZaadEsTransportClient();
        PlaylistEsBulkRunner runner = new PlaylistEsBulkRunner(zaadEsClient);
        runner.bulk();
    }

    public PlaylistEsBulkRunner() {

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
    protected void bulk() {
        init();

        BulkRequestBuilder bulRequestBuilder = this.client.getClient().prepareBulk();

        List<String> tutorPlaylistIds = ZaadOutputDirectoryManager.getDataPlaylistIds();
        ObjectMapper mapper = new ObjectMapper();
        for (String tutorPlaylistId : tutorPlaylistIds) {
            try {
                Playlist playlist = readPlaylist(tutorPlaylistId);

                if (playlist != null) {
                    bulRequestBuilder.add(client.getClient().prepareIndex(newIndexName, typeName, playlist.getPlaylistId())
                            .setSource(mapper.writeValueAsString(playlist))
                    );
                }

                if (bulRequestBuilder.numberOfActions() > BULK_SIZE) {
                    BulkResponse bulkResponse = bulRequestBuilder.execute().actionGet();

                    if (bulkResponse.hasFailures()) {
                        // TODO: do something
                    }

                    bulRequestBuilder = this.client.getClient().prepareBulk();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        if (bulRequestBuilder.numberOfActions() > 0) {
            BulkResponse bulkResponse = bulRequestBuilder.execute().actionGet();

            if (bulkResponse.hasFailures()) {
                // TODO: do something
            }
        }

        beforeExit(newIndexName, newIndexName, aliasName);
    }

    public static Playlist readPlaylist(String tutorPlaylistId) throws Exception {
        String line = null;
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
