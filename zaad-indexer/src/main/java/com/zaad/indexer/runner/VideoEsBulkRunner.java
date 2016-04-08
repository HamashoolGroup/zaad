package com.zaad.indexer.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaad.common.domain.Playlist;
import com.zaad.common.domain.Video;
import com.zaad.common.exception.ZaadDomainCreationException;
import com.zaad.common.util.ZaadLogger;
import com.zaad.common.util.ZaadOutputDirectoryManager;
import com.zaad.indexer.common.ZaadEsBulkRunner;
import com.zaad.indexer.common.util.ZaadLanguageUtil;
import com.zaad.indexer.sitemap.VideoSiteMapGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;

import java.io.File;
import java.util.*;

public class VideoEsBulkRunner extends ZaadEsBulkRunner {
    protected static final int BULK_SIZE = 1000;
    private static final String INDEX_NAME = "video";
    private static final String TYPE_NAME = "detail";

    private static Logger logger;

    static {
        logger = ZaadLogger.getLogger(VideoEsBulkRunner.class);
    }

    public static void main(String[] args) {
        VideoEsBulkRunner runner = new VideoEsBulkRunner();
        runner.bulk();
    }

    public VideoEsBulkRunner() {
    }

    public VideoEsBulkRunner(Client client) {
        this.client = client;
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

        BulkRequestBuilder bulRequestBuilder = this.client.prepareBulk();

        List<String> tutorPlaylistVideoIds = ZaadOutputDirectoryManager.getDataVideoIds();
        ObjectMapper mapper = new ObjectMapper();
        String line;
        Scanner scanner = null;
        String tutorId;
        String playlistId;
        String videoId;
        VideoSiteMapGenerator siteMapGenerator = new VideoSiteMapGenerator();
        Playlist playlist;
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
                    video = new Video(logger, lines, tutorId, playlistId, videoId);

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
                    bulRequestBuilder.add(client.prepareIndex(newIndexName, typeName, video.getZaadId())
                            .setSource(mapper.writeValueAsString(video))
                    );

                    siteMapGenerator.appendUrl(video);
                }

                if (bulRequestBuilder.numberOfActions() > BULK_SIZE) {
                    BulkResponse bulkResponse = bulRequestBuilder.execute().actionGet();

                    if (bulkResponse.hasFailures()) {
                        // TODO: do something
                    }

                    bulRequestBuilder = this.client.prepareBulk();
                }

            } catch (Exception e) {
                System.out.println(tutorPlaylistVideoId);
                e.printStackTrace();
            } finally {
                scanner.close();
            }


        }

        if (bulRequestBuilder.numberOfActions() > 0) {
            BulkResponse bulkResponse = bulRequestBuilder.execute().actionGet();

            if (bulkResponse.hasFailures()) {
                // TODO: do something
            }
        }

        siteMapGenerator.close();

        beforeExit(newIndexName, newIndexName, aliasName);
    }
}
