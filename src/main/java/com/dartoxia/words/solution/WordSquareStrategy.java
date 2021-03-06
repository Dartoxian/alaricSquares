package com.dartoxia.words.solution;

import com.dartoxia.words.Dictionary;
import com.dartoxia.words.WordSquare;
import com.dartoxia.words.solution.evaluators.ImpossibleScoreEvaluator;
import com.dartoxia.words.solution.evaluators.OneTimeOnlyPartialSolutionEvaluator;
import com.dartoxia.words.solution.evaluators.TooManyDistinctCharactersEvaluator;
import com.google.common.collect.Sets;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Try to search the word square space for an x by y word square with the provided dictionary
 *
 * Created by bgazzard on 10/01/2017.
 */
public class WordSquareStrategy {

    private int width;
    private int height;
    private List<char[]> dictionary;
    private PriorityBlockingQueue<PartialSolution> partialSolutions;
    private final PartialSolutionEvaluator[] solutionEvaluators = new PartialSolutionEvaluator[] {
            ImpossibleScoreEvaluator.INSTANCE,
            TooManyDistinctCharactersEvaluator.INSTANCE,
            OneTimeOnlyPartialSolutionEvaluator.INSTANCE
    };

    public WordSquareStrategy(int width, int height, List<char[]> dictionary) {
        this.width = width;
        this.height = height;
        this.dictionary = dictionary;

        partialSolutions = new PriorityBlockingQueue<>(100, new Comparator<PartialSolution>() {
            @Override
            public int compare(PartialSolution o1, PartialSolution o2) {
                return o1.score() - o2.score();
            }
        });
        PartialSolution initialSolution = new PartialSolution(new WordSquare(width, height), new Dictionary(dictionary));
        partialSolutions.add(initialSolution);
    }

    public WordSquare findSolution() {
        // Populate the initial queue a bit
        for (int i = 0; i < 5 && partialSolutions.size() > 0 && partialSolutions.peek().score() > 0; i++) {
            PartialSolution solutionToWorkOn = partialSolutions.poll();
            Set<PartialSolution> nextSolutions = workOnPartialSquare(solutionToWorkOn);
            partialSolutions.addAll(nextSolutions);
        }

        final Lock queueLock = new ReentrantLock(false);
        final AtomicLong solutionsProcessed = new AtomicLong();
        for (int i = 0; i < 8; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    queueLock.lock();
                    while (partialSolutions.size() > 0 && partialSolutions.peek().score() > 0) {
                        PartialSolution solutionToWorkOn = partialSolutions.poll();
                        queueLock.unlock();
                        solutionsProcessed.incrementAndGet();
                        Set<PartialSolution> nextSolutions = workOnPartialSquare(solutionToWorkOn);
                        queueLock.lock();
                        partialSolutions.addAll(nextSolutions);
                    }
                    queueLock.unlock();
                }
            }, "WorkerThread-"+i).start();
        }

        while (partialSolutions.size() > 0 && partialSolutions.peek().score() > 0) {
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Processed "+solutionsProcessed.get()+" steps so far...");
        }

        if (partialSolutions.size() > 0) {
            System.out.println("There is a solution with the provided parameters. with score " + partialSolutions.peek().score() + " it is:");
            partialSolutions.peek().wordSquare.print();
            return partialSolutions.peek().wordSquare;
        } else {
            System.out.println("The entire problem space was explored and there was no solution");
        }

        return null;
    }

    /**
     * Given a PartialSolution return all possible child solutions made by inserting the arbitrarily chosen longest word.
     * A child solution has a remaining
     * dictionary that is at least one smaller.
     *
     * @param partialSolution
     * @return
     */
    public Set<PartialSolution> workOnPartialSquare(PartialSolution partialSolution) {
        Set<PartialSolution> result = Sets.newHashSet();

        char[] word = partialSolution.remainingDictionary.getLongestWord();
        Set<WordSquare> partialSolutionsForWord = Sets.newHashSet();
        for (int i = 0; i <  partialSolution.wordSquare.getWidth(); i++) {
            for (int j = 0; j < partialSolution.wordSquare.getHeight(); j++) {
                partialSolutionsForWord.addAll(tryToPlaceWord(partialSolution.wordSquare, word, 0, i, j));
            }
        }

        Dictionary dictionaryWithoutWord = partialSolution.remainingDictionary.getDictionaryWithoutWords(Sets.newHashSet(word));
        for (WordSquare ws : partialSolutionsForWord) {
            PartialSolution ps = new PartialSolution(ws, dictionaryWithoutWord);
            if (partialSolutionCouldWork(ps)) {
                result.add(ps);
            }
        }

        return result;
    }

    protected boolean partialSolutionCouldWork(PartialSolution ps) {
        for (PartialSolutionEvaluator pse: solutionEvaluators) {
            if (! pse.couldWork(ps)) {
                return false;
            }
        }
        return true;
    }

    protected static Set<WordSquare> tryToPlaceWord(WordSquare wordSquare, char[] word, int index, int x, int y) {
        if (word.length == index) {
            return Sets.newHashSet(wordSquare);
        }

        // If x,y was blank we should try out our next character there.
        Character charAtXY = wordSquare.getCell(x, y);
        WordSquare nextWS = wordSquare;
        if (charAtXY == null) {
            nextWS = nextWS.makeCopy();
            nextWS.setCell(x, y, word[index]);
            charAtXY = word[index];
        }

        // If x,y is our next character we should explore around it for possible solutions.
        if (charAtXY == word[index]) {
            Set<WordSquare> result = Sets.newHashSet();
            // Try looking above
            if (y > 0) {
                result.addAll(tryToPlaceWord(nextWS, word, index + 1, x, y -1));
            }
            // Try looking right
            if (x < nextWS.getWidth() -1) {
                result.addAll(tryToPlaceWord(nextWS, word, index + 1, x+1, y ));
            }
            // Try looking down
            if (y < nextWS.getHeight() -1) {
                result.addAll(tryToPlaceWord(nextWS, word, index + 1, x, y+1 ));
            }
            // Try looking left
            if (x > 0) {
                result.addAll(tryToPlaceWord(nextWS, word, index + 1, x-1, y));
            }
            return result;
        }

        // If we get here we weren't able to even start placing the word at x,y - probably because x,y wasn't a
        // compatible starting spot.
        return Sets.newHashSet();
    }
}
