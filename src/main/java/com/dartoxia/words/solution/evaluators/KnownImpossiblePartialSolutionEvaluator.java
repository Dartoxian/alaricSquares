package com.dartoxia.words.solution.evaluators;

import com.dartoxia.words.solution.PartialSolution;
import com.dartoxia.words.solution.PartialSolutionEvaluator;

import java.util.Set;

/**
 * Created by bgazzard on 11/01/2017.
 */
public class KnownImpossiblePartialSolutionEvaluator implements PartialSolutionEvaluator {

    private Set<String> exploredPartialIds;

    public KnownImpossiblePartialSolutionEvaluator(Set<String> exploredPartialIds) {
        this.exploredPartialIds = exploredPartialIds;
    }


    @Override
    public boolean couldWork(PartialSolution ps) {
        return ! exploredPartialIds.contains(ps.stateId());
    }
}
