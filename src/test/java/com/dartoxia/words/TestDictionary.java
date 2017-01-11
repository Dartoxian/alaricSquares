package com.dartoxia.words;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bgazzard on 10/01/2017.
 */
public class TestDictionary {

    @Test
    public void testDictionaryWithoutWords() {
        Dictionary d = new Dictionary(Lists.newArrayList(
                "abc".toCharArray()
        ));

        int size = 0;
        for (char[] word : d) {
            size++;
        }
        assertEquals(1, size);


        d = d.getDictionaryWithoutWords(Sets.newHashSet("abc".toCharArray()));
        size = 0;
        for (char[] word : d) {
            size++;
        }
        assertEquals(0, size);
    }

    @Test
    public void testDictionaryWithWords() {
        Dictionary d = new Dictionary(Lists.newArrayList(
                "abc".toCharArray(),
                "def".toCharArray()
        ));

        int size = 0;
        for (char[] word : d) {
            size++;
        }
        assertEquals(2, size);


        d = d.getDictionaryWithoutWords(Sets.newHashSet("abc".toCharArray()));
        size = 0;
        for (char[] word : d) {
            size++;
        }
        assertEquals(1, size);
    }

    @Test
    public void testLongestWord() {
        Dictionary d = new Dictionary(Lists.newArrayList(
                "abc".toCharArray(),
                "defg".toCharArray()
        ));

        assertEquals("defg", new String(d.getLongestWord()));
    }
}
