package com.zaad.indexer.sitemap;

import com.zaad.common.domain.Tutor;
import com.zaad.common.util.ZaadProperties;
import com.zaad.indexer.transport.ZaadEsTransportClientRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TutorSitemapGenerator {
	private static final String SITEMAP_FILE_NAME = "sitemap_tutor.xml";
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	private static final Date date = new Date();
	private static final String LAST_MOD = sdf.format(date);
	
	private PrintWriter pw;
	
	public TutorSitemapGenerator() {
		ZaadProperties.loadProperties(
				ZaadEsTransportClientRunner.class.getClassLoader().getResourceAsStream(
						"zaad-t-ubuntu.properties"));
		
		File sitemapFile = new File(ZaadProperties.getAsString("sitemap.output.path") + "/" + SITEMAP_FILE_NAME);
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
	public void appendUrl(Tutor tutor) {
		this.pw.println("<url>");
		this.pw.println("    <loc>http://www.malsami.com/tutor/" + tutor.getTutorId() + "</loc>");
		this.pw.println("    <lastmod>" + LAST_MOD + "</lastmod>");
		this.pw.println("    <changefreq>weekly</changefreq>");
		this.pw.println("</url>");
	}
	
	public void close() {
		this.pw.println("</urlset>");
		this.pw.close();
	}
}
