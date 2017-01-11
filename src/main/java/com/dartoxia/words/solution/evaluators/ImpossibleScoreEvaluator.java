package com.dartoxia.words.solution.evaluators;

import com.dartoxia.words.solution.PartialSolution;
import com.dartoxia.words.solution.PartialSolutionEvaluator;

/**
 * Created by bgazzard on 11/01/2017.
 */
public class ImpossibleScoreEvaluator implements PartialSolutionEvaluator {

    public static final PartialSolutionEvaluator INSTANCE = new ImpossibleScoreEvaluator();

    @Override
    public boolean couldWork(PartialSolution ps) {
        return ps.score() < Integer.MAX_VALUE;
    }
}
