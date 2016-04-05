package com.zaad.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zaad.common.domain.ZaadKeyword;

public class ZaadKeywordMapper {
	public static Map<String, ZaadKeyword> CATEGORIES = new HashMap<String, ZaadKeyword>();
	public static Map<String, ZaadKeyword> SECTIONS = new HashMap<String, ZaadKeyword>();
	public static Map<String, ZaadKeyword> TESTS = new HashMap<String, ZaadKeyword>();
	public static Map<String, ZaadKeyword> CONTINENTALS = new HashMap<String, ZaadKeyword>();
	public static Map<String, ZaadKeyword> LEVELS = new HashMap<String, ZaadKeyword>();
	public static Map<String, ZaadKeyword> TAGS = new HashMap<String, ZaadKeyword>();
	
	static {
		String name = null;
		String tag = null;
		String title = null;
		List<String> terms = null;
		
		// Topic ///////////////////////////////////////////////////////////
		// Everyday
		name = "everyday";
		tag = "everyday";
		title = "For Everyday";
		terms = new ArrayList<String>();
		terms.add("colloquial");
		terms.add("survival");
		terms.add("everyday");
		terms.add("informal");
		terms.add("casual");
		terms.add("etiquette");
		terms.add("telephone");
		terms.add("common english");
		CATEGORIES.put(name, new ZaadKeyword(name, tag, title, terms));
		
	
		// Culture & Tips
		name = "tip";
		tag = "culture&tips";
		title = "Culture & Tips";
		terms = new ArrayList<String>();
		terms.add("tip");
		terms.add("culture");
		terms.add("secret");
		terms.add("advice");
		CATEGORIES.put(name, new ZaadKeyword(name, tag, title, terms));
		
		
		// Business
		name = "business";
		tag = "business";
		title = "For Business";
		terms = new ArrayList<String>();
		terms.add("business");
		terms.add("job");
		terms.add("workplace");
		terms.add("public");
		terms.add("presentation");
		terms.add("debate");
		CATEGORIES.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// Job Interview
		name = "interview";
		tag = "job_interview";
		title = "Job Interview";
		terms = new ArrayList<String>();
		terms.add("interview");
		terms.add("resume");
		terms.add("job interview");
		CATEGORIES.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// Job Interview
		name = "travel";
		tag = "travel";
		title = "Travel";
		terms = new ArrayList<String>();
		terms.add("travel");
		terms.add("trip");
		CATEGORIES.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// motivational
		name = "motivational";
		tag = "motivational";
		title = "Motivational";
		terms = new ArrayList<String>();
		terms.add("motivational");
		terms.add("motivation");
		terms.add("challenging");
		terms.add("inspirational");
		CATEGORIES.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// slang
		name = "slang";
		tag = "slang";
		title = "Slang";
		terms = new ArrayList<String>();
		terms.add("slang");
		CATEGORIES.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// Methodology
		name = "methodology";
		tag = "methodology";
		title = "Methodology";
		terms = new ArrayList<String>();
		terms.add("method");
		terms.add("methodology");
		terms.add("technique");
		CATEGORIES.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// News
		name = "news";
		tag = "news";
		title = "News";
		terms = new ArrayList<String>();
		terms.add("news");
		terms.add("voa");
		CATEGORIES.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// exclusive: For Kids
		name = "kid";
		tag = "kids";
		title = "For Kids";
		terms = new ArrayList<String>();
		terms.add("kid");
		terms.add("child");
		terms.add("children");
		CATEGORIES.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// Section ///////////////////////////////////////////////////////////
		// speaking
		name = "speaking";
		tag = "speaking";
		title = "Speaking";
		terms = new ArrayList<String>();
		terms.add("speaking");
		terms.add("spoken");
		terms.add("conversation");
		terms.add("accent");
		terms.add("commnunication");
		terms.add("saying");
		terms.add("speech");
		terms.add("dialogue");
		terms.add("schwa");
		SECTIONS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// listening
		name = "listening";
		tag = "listening";
		title = "Listening";
		terms = new ArrayList<String>();
		terms.add("listen");
		terms.add("listening");
		terms.add("hearing");
		terms.add("understand");
		terms.add("understanding");
		SECTIONS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// writing
		name = "writing";
		tag = "writing";
		title = "Writing";
		terms = new ArrayList<String>();
		terms.add("write");
		terms.add("writing");
		terms.add("spelling");
		terms.add("email");
		SECTIONS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// reading
		name = "reading";
		tag = "reading";
		title = "Reading";
		terms = new ArrayList<String>();
		terms.add("reading");
		SECTIONS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// grammar
		name = "grammar";
		tag = "grammar";
		title = "Grammar";
		terms = new ArrayList<String>();
		terms.add("grammar");
		terms.add("tense");
		terms.add("auxiliary");
		terms.add("gerund");
		terms.add("preposition");
		terms.add("comparative");
		terms.add("superlative");
		terms.add("present perfect");
		terms.add("past participle");
		SECTIONS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// vocabulary
		name = "vocabulary";
		tag = "vocabulary";
		title = "Vocabulary";
		terms = new ArrayList<String>();
		terms.add("vocabulary");
		terms.add("phrasal");
		terms.add("phrase");
		terms.add("word");
		terms.add("jargon");
		terms.add("idiom");
		terms.add("verb");
		terms.add("expression");
		terms.add("synonym");
		terms.add("antonym");
		terms.add("plural");
		SECTIONS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// pronunciation
		name = "pronunciation";
		tag = "pronunciation";
		title = "Pronunciation";
		terms = new ArrayList<String>();
		terms.add("pronounce");
		terms.add("pronunciation");
		terms.add("accent");
		terms.add("intonation");
		SECTIONS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// Test ///////////////////////////////////////////////////////////
		// ielts
		name = "ielts";
		tag = "ielts";
		title = "IELTS";
		terms = new ArrayList<String>();
		terms.add("ielts");
		TESTS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// toeic
		name = "toeic";
		tag = "toeic";
		title = "TOEIC";
		terms = new ArrayList<String>();
		terms.add("toeic");
		TESTS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// toefl
		name = "toefl";
		tag = "toefl";
		title = "TOEFL";
		terms = new ArrayList<String>();
		terms.add("toefl");
		TESTS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		
		// Continentals ///////////////////////////////////////////////////////////
		// American
		name = "american";
		tag = "american";
		title = "American";
		terms = new ArrayList<String>();
		terms.add("american");
		CONTINENTALS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		
		// American
		name = "british";
		tag = "british";
		title = "British";
		terms = new ArrayList<String>();
		terms.add("british");
		CONTINENTALS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// australia
		name = "australia";
		tag = "australian";
		title = "Australian";
		terms = new ArrayList<String>();
		terms.add("australia");
		terms.add("australian");
		CONTINENTALS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// irish
		name = "irish";
		tag = "irish";
		title = "Irish";
		terms = new ArrayList<String>();
		terms.add("irish");
		terms.add("irish");
		CONTINENTALS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// india
		name = "india";
		tag = "india";
		title = "India";
		terms = new ArrayList<String>();
		terms.add("india");
		CONTINENTALS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// canada
		name = "canada";
		tag = "canada";
		title = "Canadian";
		terms = new ArrayList<String>();
		terms.add("canada");
		terms.add("canadian");
		CONTINENTALS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// DIFFICULTY ///////////////////////////////////////////////////////////
		// 1
		name = "beginner";
		tag = "beginner";
		title = "For Beginner";
		terms = new ArrayList<String>();
		terms.add("basic");
		terms.add("elementary");
		terms.add("beginner");
		LEVELS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// 2
		name = "intermediate";
		tag = "intermediate";
		title = "For Intermediate";
		terms = new ArrayList<String>();
		terms.add("intermediate");
		LEVELS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// 3
		name = "advanced";
		tag = "advanced";
		title = "For Advanced";
		terms = new ArrayList<String>();
		terms.add("advanced");
		LEVELS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		// TAGS ///////////////////////////////////////////////////////////
		TAGS.putAll(CATEGORIES);
		TAGS.putAll(SECTIONS);
		TAGS.putAll(TESTS);
		TAGS.putAll(CONTINENTALS);
		//TAGS.putAll(LEVELS);
		
		name = "song";
		tag = "song";
		title = "Song";
		terms = new ArrayList<String>();
		terms.add("song");
		TAGS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		name = "fun";
		tag = "fun";
		title = "Funny";
		terms = new ArrayList<String>();
		terms.add("fun");
		terms.add("funny");
		terms.add("commedy");
		terms.add("joke");
		TAGS.put(name, new ZaadKeyword(name, tag, title, terms));
		
		name = "mistake";
		tag = "mistake";
		title = "Common Mistake";
		terms = new ArrayList<String>();
		terms.add("mistake");
		TAGS.put(name, new ZaadKeyword(name, tag, title, terms));
	}
	
	private static String isTermCotained(Map<String, ZaadKeyword> items, String term) {
		Set<String> names = items.keySet();
		
		ZaadKeyword item = null;
		for ( String name : names ) {
			item = items.get(name);
			
			if ( item.getRelatedTerm().contains(term) ) {
				return name;
			}
		}

		return null;
	}
	
	public static String isCategory(String term) {
		return isTermCotained(CATEGORIES, term);
	}
	
	public static String isSection(String term) {
		return isTermCotained(SECTIONS, term);
	}
	
	public static String isTest(String term) {
		return isTermCotained(TESTS, term);
	}
	
	public static String isContinental(String term) {
		return isTermCotained(CONTINENTALS, term);
	}
	
	public static String isLevel(String term) {
		return isTermCotained(LEVELS, term);
	}
	
	public static String isTag(String term) {
		return isTermCotained(TAGS, term);
	}
	
	public static String getTitleOfCategory(String cat) {
		String title = null;
		
		if ( TAGS.containsKey(cat) ) {
			title = TAGS.get(cat).getTitle();
		} else if ( LEVELS.containsKey(cat) ) {
			title = LEVELS.get(cat).getTitle();
		}
		
		return title;
	}
	
	public static List<String> getTermsOfCategory(String cat) {
		if ( TAGS.containsKey(cat) ) {
			return TAGS.get(cat).getRelatedTerm();
		} else if ( LEVELS.containsKey(cat) ) {
			return LEVELS.get(cat).getRelatedTerm();
		}
		
		return null;
	}
}
