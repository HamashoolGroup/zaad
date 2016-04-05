package com.zaad.common.domain;

import java.util.HashSet;
import java.util.Set;

public class ZaadTokenBox {
	private Set<String> categories;
	private Set<String> sections;
	private Set<String> tests;
	private Set<String> tags;
	private Set<String> continentals;
	private Set<String> levels;
	
	public ZaadTokenBox() {
		this.categories = new HashSet<String>();
		this.sections = new HashSet<String>();
		this.tests = new HashSet<String>();
		this.tags = new HashSet<String>();
		this.continentals = new HashSet<String>();
		this.levels = new HashSet<String>();
	}

	
	/**
	 * @return the categories
	 */
	public Set<String> getCategories() {
		return categories;
	}

	/**
	 * @param categories the categories to set
	 */
	public void setCategories(Set<String> categories) {
		this.categories = categories;
	}

	/**
	 * @return the tags
	 */
	public Set<String> getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	/**
	 * @return the continentals
	 */
	public Set<String> getContinentals() {
		return continentals;
	}

	/**
	 * @param continentals the continentals to set
	 */
	public void setContinentals(Set<String> continentals) {
		this.continentals = continentals;
	}

	/**
	 * @return the levels
	 */
	public Set<String> getLevels() {
		return levels;
	}


	/**
	 * @param levels the levels to set
	 */
	public void setLevels(Set<String> levels) {
		this.levels = levels;
	}

	/**
	 * @return the sections
	 */
	public Set<String> getSections() {
		return sections;
	}

	/**
	 * @param sections the sections to set
	 */
	public void setSections(Set<String> sections) {
		this.sections = sections;
	}
	
	/**
	 * @return the tests
	 */
	public Set<String> getTests() {
		return tests;
	}


	/**
	 * @param tests the tests to set
	 */
	public void setTests(Set<String> tests) {
		this.tests = tests;
	}


	public void addCategory(String category) {
		this.categories.add(category);
	}
	
	public void addTag(String tag) {
		this.tags.add(tag);
	}
	
	public void addContinental(String continental) {
		this.continentals.add(continental);
	}
	
	public void addLevel(String level) {
		this.levels.add(level);
	}
	
	public void addSection(String section) {
		this.sections.add(section);
	}

	public void addTest(String test) {
		this.tests.add(test);
	}
}

