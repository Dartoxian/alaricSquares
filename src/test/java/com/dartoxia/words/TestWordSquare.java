package com.dartoxia.words;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

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

    @Test
    public void testAlaricSolutionValid() {
        WordSquare ws = new WordSquare(9, 7);
        ws.setGrid(new char[][] {
                "azetled".toCharArray(),
                "mthaumk".toCharArray(),
                "gilonpa".toCharArray(),
                "aspuapl".toCharArray(),
                "mmercha".toCharArray(),
                "panoimm".toCharArray(),
                "ixihpob".toCharArray(),
                "etocmed".toCharArray(),
                "barhoga".toCharArray()
        });

        ws.print();

        assertTrue(ws.isComplete(Dictionary.greekAlphabet));

        List<char[]> invalidDictionary = Lists.newArrayList(Dictionary.greekAlphabet);
        invalidDictionary.add("thiswordismissing".toCharArray());
        assertFalse(ws.isComplete(invalidDictionary));
    }

    @Test
    public void testAlaric2ndSolutionValid() {
        WordSquare ws = new WordSquare(7, 8);
        ws.setGrid(new char[][] {
                "ppammbda".toCharArray(),
                "aikyanus".toCharArray(),
                "epsilome".toCharArray(),
                "aukxphig".toCharArray(),
                "thactrca".toCharArray(),
                "zetoionl".toCharArray(),
                "*bledahp".toCharArray()
        });

        ws.print();

        assertTrue(ws.isComplete(Dictionary.greekAlphabet));

        List<char[]> invalidDictionary = Lists.newArrayList(Dictionary.greekAlphabet);
        invalidDictionary.add("thiswordismissing".toCharArray());
        assertFalse(ws.isComplete(invalidDictionary));
    }
}
