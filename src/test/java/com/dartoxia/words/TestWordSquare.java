package com.dartoxia.words;

import com.google.common.collect.Lists;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bgazzard on 10/01/2017.
 */
public class TestWordSquare {

    @Test
    public void testSimpleComplete() {
        WordSquare ws = new WordSquare(3, 3);
        ws.setGrid(new char[][] {
                "abc".toCharArray(),
                "def".toCharArray(),
                "ghi".toCharArray()
        });

        ws.print();

        assertTrue(ws.isComplete(Lists.<char[]>newArrayList()));
        assertTrue(ws.isComplete(Lists.newArrayList("abc".toCharArray())));
        assertTrue(ws.isComplete(Lists.newArrayList("abc".toCharArray(), "hed".toCharArray())));
        assertTrue(ws.isComplete(Lists.newArrayList("abcfihg".toCharArray())));
        assertFalse(ws.isComplete(Lists.newArrayList("efh".toCharArray())));
    }
}
