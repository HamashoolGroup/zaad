package com.zaad.common.parser;

import com.zaad.common.domain.ZaadTokenBox;
import com.zaad.common.util.ZaadKeywordMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * zaad 텍스트 분석기
 *
 * @author socurites, lks21c
 */
public class ZaadTextAnalyzer {
    private static final StandardAnalyzer standardAnalyzer = new StandardAnalyzer();

    /**
     * 금칙어
     */
    private static List<String> STOP_WORDS = new ArrayList<String>();

    /**
     * 복합단어
     */
    private static List<String> NON_SINGULAR_WORDS = new ArrayList<String>();

    /**
     * 최소 단어 길이
     */
    private static final int MIN_WORD_LENGTH = 2;

    static {
        NON_SINGULAR_WORDS.add("ielts");
        NON_SINGULAR_WORDS.add("news");
    }

    public static ZaadTokenBox analyze(String text) {
        ZaadTokenBox tokenBox = new ZaadTokenBox();
        if (!StringUtils.isEmpty(text)) {
            TokenStream tokenStream = null;
            try {
                tokenStream = standardAnalyzer.tokenStream(null, new StringReader(text));
                tokenStream.reset();

                String currToken = null;
                String prevToken = null;
                while (tokenStream.incrementToken()) {
                    currToken = tokenStream.getAttribute(CharTermAttribute.class).toString();

                    if (STOP_WORDS.contains(currToken)) {
                        continue;
                    }
                    if (currToken.length() < MIN_WORD_LENGTH) {
                        continue;
                    }
                    if (!isLetters(currToken)) {
                        continue;
                    }

                    if (isSingularVerb(currToken)) {
                        currToken = stripEnglishPlural(currToken);
                    }

                    addTokenToBox(currToken, tokenBox);
                    addTokenToBox(prevToken + " " + currToken, tokenBox);

                    prevToken = currToken;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    tokenStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return tokenBox;
    }

    /**
     * 영문자인지 검사
     *
     * @param token
     * @return
     */
    private static boolean isLetters(String token) {
        return token.matches("[a-zA-Z]+");
    }

    /**
     * 단일동사인지 검사
     *
     * @param token
     * @return
     */
    private static boolean isSingularVerb(String token) {
        if (NON_SINGULAR_WORDS.contains(token)) {
            return false;
        }

        if (token.endsWith("s")) {
            return true;
        }

        return false;
    }

    private static void addTokenToBox(String token, ZaadTokenBox tokenBox) {
        String tag = ZaadKeywordMapper.isCategory(token);
        if (tag != null) {
            tokenBox.addCategory(tag);
        }
        tag = ZaadKeywordMapper.isSection(token);
        if (tag != null) {
            tokenBox.addSection(tag);
        }
        tag = ZaadKeywordMapper.isTest(token);
        if (tag != null) {
            tokenBox.addTest(tag);
        }
        tag = ZaadKeywordMapper.isContinental(token);
        if (tag != null) {
            tokenBox.addContinental(tag);
        }
        tag = ZaadKeywordMapper.isLevel(token);
        if (tag != null) {
            tokenBox.addLevel(tag);
        }
        tag = ZaadKeywordMapper.isTag(token);
        if (tag != null) {
            tokenBox.addTag(tag);
        }
    }

    public static String stripEnglishPlural(String word) {
        // special cases
        if (word.equals("has") ||
                word.equals("was") ||
                word.equals("does") ||
                word.equals("goes") ||
                word.equals("dies") ||
                word.equals("yes") ||
                word.equals("gets") || // means too much in java/JSP
                word.equals("its")) {
            return word;
        }
        String newWord = word;
        if (word.endsWith("sses") ||
                word.endsWith("xes") ||
                word.endsWith("hes")) {
            // remove 'es'
            newWord = word.substring(0, word.length() - 2);
        } else if (word.endsWith("ies")) {
            // remove 'ies', replace with 'y'
            newWord = word.substring(0, word.length() - 3) + 'y';
        } else if (word.endsWith("s") &&
                !word.endsWith("ss") &&
                !word.endsWith("is") &&
                !word.endsWith("us") &&
                !word.endsWith("pos") &&
                !word.endsWith("ses")) {
            // remove 's'
            newWord = word.substring(0, word.length() - 1);
        }
        return newWord;
    }


    public static void main(String[] args) {
        System.out.println(analyze("job interview").getTags());
    }
}
