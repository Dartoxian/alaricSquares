package com.dartoxia.words.solution;

import com.dartoxia.words.Dictionary;
import com.dartoxia.words.WordSquare;
import com.google.common.collect.Lists;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by bgazzard on 10/01/2017.
 */
public class TestWordSquareStrategy {

    @Test
    public void testSimpleComplete() {
        WordSquare result = new WordSquareStrategy(3,3, Lists.newArrayList(
                "abc".toCharArray(),
                "cfi".toCharArray(),
                "ihg".toCharArray(),
                "ade".toCharArray()
        )).findSolution();

        assertTrue(result != null);
    }

    @Test
    public void testSimpleImpossible() {
        WordSquare result = new WordSquareStrategy(3,3, Lists.newArrayList(
                "abc".toCharArray(),
                "cfi".toCharArray(),
                "ihg".toCharArray(),
                "ade".toCharArray(),
                "lmn".toCharArray()
        )).findSolution();

        assertTrue(result == null);
    }

    //@Test
    public void testGreekAlphabet() {
        WordSquare result = new WordSquareStrategy(9,7, Dictionary.greekAlphabet).findSolution();

        assertTrue(result != null);
    }
}
