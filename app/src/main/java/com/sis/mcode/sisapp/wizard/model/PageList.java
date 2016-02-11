package com.sis.mcode.sisapp.wizard.model;

import java.util.ArrayList;

public class PageList extends ArrayList<Page> implements  PageTreeNode {

    public  PageList() {

    }

    public PageList(Page... pages) {
        for (Page page : pages) {
            add(page);
        }
    }

    @Override
    public Page findByKey(String key) {
        for (Page page : this) {
            Page found = page.findByKey(key);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    @Override
    public void flattenCurrentPageSequence(ArrayList<Page> dest) {
        for (Page childPage : this) {
            childPage.flattenCurrentPageSequence(dest);
        }
    }

}