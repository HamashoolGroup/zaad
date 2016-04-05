package com.zaad.common;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.zaad.common.domain.ZaadTokenBox;
import com.zaad.common.exception.ZaadDomainCreationException;
import com.zaad.common.parser.ZaadTextAnalyzer;

public class ZaadDomain {
	protected Set<String> categories;
	protected Set<String> sections;
	protected Set<String> tests;
	protected Set<String> tags;
	protected Set<String> continentals;
	protected Set<String> levels;
	
	public ZaadDomain() {
		
	}
	
	public ZaadDomain(List<String> lines) {
		if ( lines == null || lines.size() < 1 ) {
			throw new ZaadDomainCreationException("zaad data is empty");
		}
	}
	
	public void generateKeyTags(String title) {
		if ( ! StringUtils.isEmpty(title) ) {
			ZaadTokenBox tokenBox = ZaadTextAnalyzer.analyze(title);
			this.categories = tokenBox.getCategories();
			this.sections = tokenBox.getSections();
			this.tests = tokenBox.getTests();
			this.tags = tokenBox.getTags();
			this.continentals = tokenBox.getContinentals();
			this.levels = tokenBox.getLevels();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (new ReflectionToStringBuilder(this) {
	         protected boolean accept(Field f) {
	             return super.accept(f) && !f.getName().equals("password");
	         }
	     }).toString();
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

	/**
	 * @param sections the sections to set
	 */
	public void setSections(Set<String> sections) {
		this.sections = sections;
	}
	
	public void addCategories(Set<String> categories) {
		if ( this.categories == null ) {
			this.categories = new HashSet<String>();
		}
		
		this.categories.addAll(categories);
	}
	
	public void addSections(Set<String> sections) {
		if ( this.sections == null ) {
			this.sections = new HashSet<String>();
		}
		
		this.sections.addAll(sections);
	}
	
	public void addTests(Set<String> tests) {
		if ( this.tests == null ) {
			this.tests = new HashSet<String>();
		}
		
		this.tests.addAll(tests);
	}
	
	public void addTags(Set<String> tags) {
		if ( this.tags == null ) {
			this.tags = new HashSet<String>();
		}
		
		this.tags.addAll(tags);
	}
	
	public void addContinentals(Set<String> continentals) {
		if ( this.continentals == null ) {
			this.continentals = new HashSet<String>();
		}
		
		this.continentals.addAll(continentals);
	}
	
	public void addLevels(Set<String> levels) {
		if ( this.levels == null ) {
			this.levels = new HashSet<String>();
		}
		
		this.levels.addAll(levels);
	}
}
