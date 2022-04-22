package com.zaad.common.domain;

import com.zaad.common.ZaadMetaDomain;

import java.util.List;

/**
 * zaad 키워드
 *
 * @author socurites, lks21c
 */
public class ZaadKeyword extends ZaadMetaDomain {
    /**
     * 이름
     */
    protected String name;

    /**
     * 태그
     */
    protected String tag;

    /**
     * 제목
     */
    protected String title;

    /**
     * 관련용어
     */
    protected List<String> relatedTerm;

    public ZaadKeyword(String name, String tag, String title, List<String> relatedTerm) {
        this.name = name;
        this.tag = tag;
        this.title = title;
        this.relatedTerm = relatedTerm;
    }

    /**
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the relatedTerm
     */
    public List<String> getRelatedTerm() {
        return relatedTerm;
    }

    /**
     * @param relatedTerm the relatedTerm to set
     */
    public void setRelatedTerm(List<String> relatedTerm) {
        this.relatedTerm = relatedTerm;
    }

    public void addTerm(String term) {
        this.relatedTerm.add(term);
    }
}
