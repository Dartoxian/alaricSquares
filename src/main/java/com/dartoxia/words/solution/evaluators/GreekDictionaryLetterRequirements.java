package com.dartoxia.words.solution.evaluators;

import com.dartoxia.words.solution.PartialSolution;
import com.dartoxia.words.solution.PartialSolutionEvaluator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * According to Alaric partial solutions should have these minimum letter thresholds
 *
 * Created by bgazzard on 11/01/2017.
 */
public class GreekDictionaryLetterRequirements implements PartialSolutionEvaluator {

    private static final Map<Character, Integer> characterToLowerBound = ImmutableMap.<Character, Integer>builder()
            .put('a', 4)
            .put('b', 1)
            .put('c', 1)
            .put('d', 1)
            .put('e', 2)
            .put('g', 1)
            .put('h', 2)
            .put('i', 1)
            .put('k', 1)
            .put('l', 1)
            .put('m', 2)
            .put('n', 1)
            .put('o', 2)
            .put('p', 3)
            .put('r', 1)
            .put('s', 1)
            .put('t', 2)
            .put('u', 1)
            .put('x', 1)
            .put('z', 1)
            .build();
    
    private static final Map<Character, Integer> characterToUpperBound = ImmutableMap.<Character, Integer>builder()
            .put('b', 2)
            .put('c', 2)
            .put('d', 2)
            .put('g', 3)
            .put('h', 5)
            .put('k', 1)
            .put('l', 4)
            .put('m', 5)
            .put('n', 3)
            .put('r', 2)
            .put('s', 3)
            .put('x', 1)
            .put('z', 1)
            .build();

    @Override
    public boolean couldWork(PartialSolution ps) {
        int opportunities = ps.getRemainingBlankSquares();
        // upper bounds are subset of lower bounds
        for (Character c: characterToLowerBound.keySet()) {
            int occ = ps.wordSquare.getNumberOf(c);
            if (characterToUpperBound.containsKey(c) && occ > characterToUpperBound.get(c)) {
                return false;
            }


        }

        return true;
    }
}
