package com.dartoxia.words;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by bgazzard on 10/01/2017.
 */
public class TestWordSquare {

    @Test
    public void testSimpleComplete() {
        WordSquare ws = new WordSquare(3, 3);
        ws.setGrid(new char[][] {
                "abc".toCharArray(),
                "def".toCharArray(),
                "ghi".toCharArray()
        });

        ws.print();

        assertTrue(ws.isComplete(Lists.<char[]>newArrayList()));
        assertTrue(ws.isComplete(Lists.newArrayList("abc".toCharArray())));
        assertTrue(ws.isComplete(Lists.newArrayList("abc".toCharArray(), "hed".toCharArray())));
        assertTrue(ws.isComplete(Lists.newArrayList("abcfihg".toCharArray())));
        assertFalse(ws.isComplete(Lists.newArrayList("efh".toCharArray())));
    }

    @Test
    public void testAlaricSolutionValid() {
        WordSquare ws = new WordSquare(9, 7);
        ws.setGrid(new char[][] {
                "azetled".toCharArray(),
                "mthaumk".toCharArray(),
                "gilonpa".toCharArray(),
                "aspuapl".toCharArray(),
                "mmercha".toCharArray(),
                "panoimm".toCharArray(),
                "ixihpob".toCharArray(),
                "etocmed".toCharArray(),
                "barhoga".toCharArray()
        });

        ws.print();

        assertTrue(ws.isComplete(Dictionary.greekAlphabet));

        List<char[]> invalidDictionary = Lists.newArrayList(Dictionary.greekAlphabet);
        invalidDictionary.add("thiswordismissing".toCharArray());
        assertFalse(ws.isComplete(invalidDictionary));
    }

    @Test
    public void testAlaric2ndSolutionValid() {
        WordSquare ws = new WordSquare(7, 8);
        ws.setGrid(new char[][] {
                "ppammbda".toCharArray(),
                "aikganuq".toCharArray(),
                "epsilome".toCharArray(),
                "auqxphig".toCharArray(),
                "thachrca".toCharArray(),
                "zetoionl".toCharArray(),
                "qbledahp".toCharArray()
        });

        ws.print();

        assertTrue(ws.isComplete(Dictionary.greekAlphabet));

        List<char[]> invalidDictionary = Lists.newArrayList(Dictionary.greekAlphabet);
        invalidDictionary.add("thiswordismissing".toCharArray());
        assertFalse(ws.isComplete(invalidDictionary));
    }

    @Test
    public void testVerticalMirror() {
        WordSquare ws = new WordSquare(3, 3);
        ws.setGrid(new char[][] {
                "abc".toCharArray(),
                "def".toCharArray(),
                "ghi".toCharArray()
        });

        System.out.println(ws.toString());
        String mirror = ws.getVerticalMirrorString();
        System.out.println(mirror);

        assertEquals(
                "cba\n"+
                "fed\n"+
                "ihg\n",
                mirror);
    }

    @Test
    public void testHorizontalMirror() {
        WordSquare ws = new WordSquare(3, 3);
        ws.setGrid(new char[][] {
                "abc".toCharArray(),
                "def".toCharArray(),
                "ghi".toCharArray()
        });

        System.out.println(ws.toString());
        String mirror = ws.getHorizontalMirrorString();
        System.out.println(mirror);

        assertEquals(
                "ghi\n"+
                "def\n"+
                "abc\n",
                mirror);
    }

    @Test
    public void test90DegreesClockwise() {
        WordSquare ws = new WordSquare(3, 3);
        ws.setGrid(new char[][] {
                "abc".toCharArray(),
                "def".toCharArray(),
                "ghi".toCharArray()
        });

        System.out.println(ws.toString());
        String mirror = ws.get90DegreesClockwiseString();
        System.out.println(mirror);

        assertEquals(
                "gda\n"+
                "heb\n"+
                "ifc\n",
                mirror);
    }

    @Test
    public void test90DegreesAntiClockwise() {
        WordSquare ws = new WordSquare(3, 3);
        ws.setGrid(new char[][] {
                "abc".toCharArray(),
                "def".toCharArray(),
                "ghi".toCharArray()
        });

        System.out.println(ws.toString());
        String mirror = ws.get90DegreesAntiClockwiseString();
        System.out.println(mirror);

        assertEquals(
                "cfi\n"+
                "beh\n"+
                "adg\n",
                mirror);
    }

    @Test
    public void test180Degrees() {
        WordSquare ws = new WordSquare(3, 3);
        ws.setGrid(new char[][] {
                "abc".toCharArray(),
                "def".toCharArray(),
                "ghi".toCharArray()
        });

        System.out.println(ws.toString());
        String mirror = ws.get180DegreeString();
        System.out.println(mirror);

        assertEquals(
                "ihg\n"+
                "fed\n"+
                "cba\n",
                mirror);
    }

    @Test
    public void testDiagonal() {
        WordSquare ws = new WordSquare(3, 3);
        ws.setGrid(new char[][] {
                "abc".toCharArray(),
                "def".toCharArray(),
                "ghi".toCharArray()
        });

        System.out.println(ws.toString());
        String mirror = ws.getDiagonalString();
        System.out.println(mirror);

        assertEquals(
                "adg\n"+
                "beh\n"+
                "cfi\n",
                mirror);
    }

    @Test
    public void testMirroredDiagonal() {
        WordSquare ws = new WordSquare(3, 3);
        ws.setGrid(new char[][] {
                "abc".toCharArray(),
                "def".toCharArray(),
                "ghi".toCharArray()
        });

        System.out.println(ws.toString());
        String mirror = ws.getMirroredDiagonalString();
        System.out.println(mirror);

        assertEquals(
                "ifc\n"+
                "heb\n"+
                "gda\n",
                mirror);
    }

    @Test
    public void testAllPermutationsDistinct() {
        Set<String> perms = Sets.newHashSet();
        WordSquare ws = new WordSquare(3, 3);
        ws.setGrid(new char[][] {
                "abc".toCharArray(),
                "def".toCharArray(),
                "ghi".toCharArray()
        });

        perms.add(ws.toString());
        perms.add(ws.get90DegreesAntiClockwiseString());
        perms.add(ws.get90DegreesClockwiseString());
        perms.add(ws.getHorizontalMirrorString());
        perms.add(ws.getVerticalMirrorString());
        perms.add(ws.get180DegreeString());
        perms.add(ws.getDiagonalString());
        perms.add(ws.getMirroredDiagonalString());

        assertEquals(8, perms.size());
    }

    @Test
    public void testCanonicalString() {
        WordSquare ws1 = new WordSquare(3, 3);
        ws1.setGrid(new char[][] {
                "abc".toCharArray(),
                "def".toCharArray(),
                "ghi".toCharArray()
        });
        WordSquare ws2 = new WordSquare(3, 3);
        ws2.setGrid(new char[][] {
                "gda".toCharArray(),
                "heb".toCharArray(),
                "ifc".toCharArray()
        });
        WordSquare ws3 = new WordSquare(3, 3);
        ws3.setGrid(new char[][] {
                "adg".toCharArray(),
                "heb".toCharArray(),
                "ifc".toCharArray()
        });

        assertEquals(ws1, ws2);
        assertNotEquals(ws1, ws3);
    }
}
