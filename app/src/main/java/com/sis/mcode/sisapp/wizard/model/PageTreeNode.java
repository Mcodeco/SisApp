package com.sis.mcode.sisapp.wizard.model;

import java.util.ArrayList;

public interface PageTreeNode {
    public Page findByKey(String key);
    public void flattenCurrentPageSequence(ArrayList<Page> dest);
}