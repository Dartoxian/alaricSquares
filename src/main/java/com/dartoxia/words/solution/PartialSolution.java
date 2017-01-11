package com.dartoxia.words.solution;

import com.dartoxia.words.Dictionary;
import com.dartoxia.words.WordSquare;
import com.google.common.collect.Sets;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Set;

/**
 * Created by bgazzard on 10/01/2017.
 */
public class PartialSolution {

    public final WordSquare wordSquare;
    public Dictionary remainingDictionary;

    public PartialSolution(WordSquare wordSquare, Dictionary remainingDictionary) {
        this.wordSquare = wordSquare;
        this.remainingDictionary = remainingDictionary;
        minimiseRemainingDictionary();
    }

    /**
     * When this solution was created we might have inadvertently solved more words.
     */
    private void minimiseRemainingDictionary() {
        Set<char[]> t = Sets.newHashSet();
        for (char[] word: remainingDictionary) {
            if (wordSquare.isWordPresent(word)) {
                t.add(word);
            }
        }
        remainingDictionary = remainingDictionary.getDictionaryWithoutWords(t);
    }

    /**
     * Attempt to score this partial solution. Lots of remaining blanks are good, and
     * fewer remaining words are better. The lower scores are better.
     *
     * A score of 0 means we are done.
     * A score of Integer.MAX_VALUE means this PartialSolution is actually a Failed Solution.
     *
     * I won't try to create a total ordering of Partial Solutions so we can't make use of
     * Comparable here.
     * @return
     */
    private int scoreCache = -1;
    public int score() {
        if (scoreCache < 0) {
            int dictionaryScore = getRemainingCharsToPlace();
            int blankSquareCount = getRemainingBlankSquares();

            if (dictionaryScore > 0 && blankSquareCount == 0) {
                // it is impossible to progress from here
                return Integer.MAX_VALUE;
            }

            scoreCache = dictionaryScore * (wordSquare.getHeight() * wordSquare.getWidth() - blankSquareCount + 1);
        }
        return scoreCache;
    }

    private int getRemainingCharsToPlace() {
        return remainingDictionary.getTotalCharactersInDictionary();
    }

    private int remainingBlanksCache = -1;
    public int getRemainingBlankSquares() {
        if (remainingBlanksCache < 0) {
            remainingBlanksCache = 0;
            for (int i = 0; i < wordSquare.getWidth(); i++) {
                for (int j = 0; j < wordSquare.getHeight(); j++) {
                    if (wordSquare.getCell(i, j) == null) {
                        remainingBlanksCache++;
                    }
                }
            }
        }
        return remainingBlanksCache;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartialSolution that = (PartialSolution) o;

        if (wordSquare != null ? !wordSquare.equals(that.wordSquare) : that.wordSquare != null) return false;
        return remainingDictionary != null ? remainingDictionary.equals(that.remainingDictionary) : that.remainingDictionary == null;

    }

    @Override
    public int hashCode() {
        int result = wordSquare != null ? wordSquare.hashCode() : 0;
        result = 31 * result + (remainingDictionary != null ? remainingDictionary.hashCode() : 0);
        return result;
    }

    private String stateIdCache = null;
    public String stateId() {
        if (stateIdCache == null) {
            stateIdCache = DigestUtils.md5Hex(wordSquare.toCanonicalString() + remainingDictionary.toString());
        }
        return stateIdCache;
    }
}
