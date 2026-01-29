package com.xht.framework.core.domain;

/**
 *
 * @author xht
 **/
public class TestR {
    public static void main(String[] args) {
        R<String> build1 = R.ok().build(null);
        R<TestR> build2 = R.ok().build(null);
    }
}
