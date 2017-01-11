package com.dartoxia.words;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

import java.util.*;

/**
 * Created by bgazzard on 10/01/2017.
 */
public class Dictionary implements Iterable<char[]>{

    public static final List<char[]> greekAlphabet = ImmutableList.<char[]>builder()
            .add("alpha".toCharArray())
            .add("beta".toCharArray())
            .add("gamma".toCharArray())
            .add("delta".toCharArray())
            .add("epsilon".toCharArray())
            .add("zeta".toCharArray())
            .add("eta".toCharArray())
            .add("theta".toCharArray())
            .add("iota".toCharArray())
            .add("kappa".toCharArray())
            .add("lambda".toCharArray())
            .add("mu".toCharArray())
            .add("nu".toCharArray())
            .add("xi".toCharArray())
            .add("omicron".toCharArray())
            .add("pi".toCharArray())
            .add("rho".toCharArray())
            .add("sigma".toCharArray())
            .add("tau".toCharArray())
            .add("upsilon".toCharArray())
            .add("phi".toCharArray())
            .add("chi".toCharArray())
            .add("psi".toCharArray())
            .add("omega".toCharArray())
            .build();

    private Set<char[]> words;
    private char[] longestWord;

    public Dictionary(Collection<char[]> words) {
        this.words = Sets.newHashSet(words);
        longestWord = new char[0];
        for (char[] word : words) {
            if (longestWord.length < word.length) {
                longestWord = word;
            }
        }
    }

    public Dictionary getDictionaryWithoutWords(final Set<char[]> bannedWords) {
        return new Dictionary(Sets.filter(this.words, new Predicate<char[]>() {
            @Override
            public boolean apply(char[] input) {
                for (char[] word: bannedWords) {
                    if (new String(input).equals(new String(word))){
                        return false;
                    }
                }
                return true;
            }
        }));
    }

    @Override
    public Iterator<char[]> iterator() {
        return words.iterator();
    }

    public char[] getLongestWord() {
        return longestWord;
    }

    private int distinctCharactersInDictionary = -1;
    private int totalCharactersInDictionary = -1;
    private Set<Character> seenCharacters = null;
    private void initialiseStats() {
        if (distinctCharactersInDictionary < 0) {
            totalCharactersInDictionary = 0;
            seenCharacters = Sets.newHashSet();
            for (char[] word : words) {
                for (Character w : word) {
                    seenCharacters.add(w);
                }
                totalCharactersInDictionary += word.length;
            }
            distinctCharactersInDictionary = seenCharacters.size();
        }
    }

    public int getDistinctCharacterCountInDictionary() {
        initialiseStats();
        return distinctCharactersInDictionary;
    }

    public Set<Character> getDistinctCharactersInDictionary() {
        initialiseStats();
        return seenCharacters;
    }

    public int getTotalCharactersInDictionary() {
        initialiseStats();
        return totalCharactersInDictionary;
    }

}
