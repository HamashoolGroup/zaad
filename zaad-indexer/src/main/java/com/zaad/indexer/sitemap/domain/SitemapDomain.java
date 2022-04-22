package com.zaad.indexer.sitemap.domain;

/**
 * @author socurites
 */
public class SitemapDomain {
	protected String loc;
	protected String changefreq = "weekly";
	protected String priority = "1.00";

	/**
	 * @return the loc
	 */
	public String getLoc() {
		return loc;
	}
	/**
	 * @param loc the loc to set
	 */
	public void setLoc(String loc) {
		this.loc = loc;
	}
	/**
	 * @return the changefreq
	 */
	public String getChangefreq() {
		return changefreq;
	}
	/**
	 * @param changefreq the changefreq to set
	 */
	public void setChangefreq(String changefreq) {
		this.changefreq = changefreq;
	}
	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}
}
