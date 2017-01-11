package com.dartoxia.words.solution.evaluators;

import com.dartoxia.words.solution.PartialSolution;
import com.dartoxia.words.solution.PartialSolutionEvaluator;
import com.google.common.collect.Sets;

/**
 * Created by bgazzard on 11/01/2017.
 */
public class TooManyDistinctCharactersEvaluator implements PartialSolutionEvaluator {

    public static final PartialSolutionEvaluator INSTANCE = new TooManyDistinctCharactersEvaluator();

    @Override
    public boolean couldWork(PartialSolution ps) {
        return ps.getRemainingBlankSquares() >= Sets.difference(ps.remainingDictionary.getDistinctCharactersInDictionary(), ps.remainingDictionary.getDistinctCharactersInDictionary()).size();
    }
}
