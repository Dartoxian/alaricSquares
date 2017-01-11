package com.dartoxia.words.solution.evaluators;

import com.dartoxia.words.solution.PartialSolution;
import com.dartoxia.words.solution.PartialSolutionEvaluator;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Only Let possible solutions be enqueued one time.
 *
 * Created by bgazzard on 11/01/2017.
 */
public class OneTimeOnlyPartialSolutionEvaluator implements PartialSolutionEvaluator {

    public static final PartialSolutionEvaluator INSTANCE = new OneTimeOnlyPartialSolutionEvaluator();

    private Set<String> exploredPartialIds = Sets.newHashSet();

    @Override
    public boolean couldWork(PartialSolution ps) {
        if (exploredPartialIds.contains(ps.stateId())) {
            return false;
        }
        exploredPartialIds.add(ps.stateId());
        return true;
    }
}
