package com.zaad.indexer.sitemap;

import com.zaad.common.domain.Video;
import com.zaad.common.util.ZaadProperties;
import com.zaad.indexer.transport.ZaadEsTransportClientRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VideoSiteMapGenerator {
    private Logger logger = LoggerFactory.getLogger(VideoSiteMapGenerator.class);

    private static final String SITEMAP_FILE_NAME = "sitemap_video.xml";

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static final Date date = new Date();
    private static final String LAST_MOD = sdf.format(date) + "+09:00";

    private PrintWriter pw;

    public VideoSiteMapGenerator() throws FileNotFoundException {
        ZaadProperties.loadProperties(
                ZaadEsTransportClientRunner.class.getClassLoader().getResourceAsStream(
                        "zaad.properties"));

        File sitemapFile = new File(ZaadProperties.getAsString("sitemap.output.path") + "/" + SITEMAP_FILE_NAME);
        if (!sitemapFile.exists()) {
            logger.info("sitemap path = " + ZaadProperties.getAsString("sitemap.output.path") + "/" + SITEMAP_FILE_NAME);
            throw new FileNotFoundException("sitemapFile not found");
        }
        try {
            pw = new PrintWriter(sitemapFile);
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"");
            pw.println("    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
            pw.println("    xsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd\">");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void appendUrl(Video video) {
        if (this.pw == null) {
            throw new NullPointerException("pw is null");
        }
        this.pw.println("<url>");
        this.pw.println("    <loc>http://www.malsami.com/video/" + video.getVideoId() + "</loc>");
        this.pw.println("    <lastmod>" + LAST_MOD + "</lastmod>");
        this.pw.println("    <changefreq>weekly</changefreq>");
        this.pw.println("</url>");
    }

    public void close() {
        if (this.pw == null) {
            throw new NullPointerException("pw is null");
        }
        this.pw.println("</urlset>");
        this.pw.close();
    }

    public static void main(String[] args) {
        System.out.println(LAST_MOD);
    }
}
