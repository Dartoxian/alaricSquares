package com.dartoxia.words;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Represents one of Alaric's word squares
 *
 * Created by bgazzard on 10/01/2017.
 */
public class WordSquare {

    private int width;
    private int height;
    // This grid has a layout like this for a 3x3 grid
    // \ 0 1 2
    // 0 a b c
    // 1 d e f
    // 2 g h i
    // So (0, 1) == d for example
    private Character[][] grid;

    /**
     * Creates a blank word square
     * @param width
     * @param height
     */
    public WordSquare(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Character[width][height];
    }

    protected WordSquare(int width, int height, Character[][] grid) {
        this.width = width;
        this.height = height;
        this.grid = grid;
    }

    public WordSquare makeCopy() {
        Character[][] gridClone = new Character[width][height];
        for (int i = 0; i<width; i++) {
            for (int j = 0; j<height; j++) {
                gridClone[i][j] = grid[i][j];
            }
        }

        return new WordSquare(width, height, gridClone);
    }

    public void setGrid(char[][] grid) {
        if (grid.length != width) {
            throw new RuntimeException("The new grid is not wide enough");
        }
        for (int i = 0; i < grid.length; i++) {
            if (grid[i].length != height) {
                throw new RuntimeException("The new grid has a column that is the wrong length");
            }
        }
        this.grid = new Character[width][height];
        for (int i = 0; i<width; i++) {
            for (int j = 0; j<height; j++) {
                this.grid[i][j] = grid[i][j];
            }
        }
    }

    public void setGrid(Character[][] grid) {
        if (grid.length != width) {
            throw new RuntimeException("The new grid is not wide enough");
        }
        for (int i = 0; i < grid.length; i++) {
            if (grid[i].length != height) {
                throw new RuntimeException("The new grid has a column that is the wrong length");
            }
        }
        this.grid = grid;
    }

    public void setCell(int x, int y, Character value) {
        grid[x][y] = value;
    }

    public Character getCell(int x, int y) {
        return grid[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Returns true if the provided dictionary is present in the word square.
     * The word square does not have to be fully populated to be complete - the unpopulated entries
     * could be filled with anything.
     * @param dictionary
     * @return
     */
    public boolean isComplete(List<char[]> dictionary) {
        for (char[] word : dictionary) {
            if (! isWordPresent(word)) {
                return false;
            }
        }
        return true;
    }

    public boolean isWordPresent(char[] word) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (isWordPresentFrom(word, 0, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Work through the word in every direction possible to find its presence
     * @param word
     * @param index
     * @param x
     * @param y
     * @return
     */
    private boolean isWordPresentFrom(char[] word, int index, int x, int y) {
        if (word.length == index) {
            return true;
        }
        if (grid[x][y] != null && word[index] == grid[x][y]) {
            return
                    // above
                    (y > 0 && isWordPresentFrom(word, index + 1, x, y -1)) ||
                    // right
                    (x < width - 1 && isWordPresentFrom(word, index + 1, x +1, y)) ||
                    // below
                    (y < height - 1 && isWordPresentFrom(word, index + 1, x, y +1)) ||
                    // left
                    (x > 0 && isWordPresentFrom(word, index + 1, x -1, y));
        }
        return false;
    }

    public Set<Character> getDistinctCharacters() {
        Set<Character> result = Sets.newHashSet();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[i][j] != null) {
                    result.add(grid[i][j]);
                }
            }
        }

        return result;
    }

    public String getVerticalMirrorString() {
        Character[][] mirror = new Character[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                mirror[i][j] = grid[i][height -1 -j];
            }
        }
        return toString(mirror);
    }

    public String getHorizontalMirrorString() {
        Character[][] mirror = new Character[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                mirror[i][j] = grid[width -1 -i][j];
            }
        }
        return toString(mirror);
    }

    public String get90DegreesClockwiseString() {
        if (width != height) {
            return null;
        }
        Character[][] rotation = new Character[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                rotation[j][height -1 -i] = grid[i][j];
            }
        }
        return toString(rotation);
    }

    public String get90DegreesAntiClockwiseString() {
        if (width != height) {
            return null;
        }
        Character[][] rotation = new Character[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                rotation[width -1 -j][i] = grid[i][j];
            }
        }
        return toString(rotation);
    }

    public String get180DegreeString() {
        if (width != height) {
            return null;
        }
        Character[][] rotation = new Character[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                rotation[width -1 -i][height -1 -j] = grid[i][j];
            }
        }
        return toString(rotation);
    }

    public String getDiagonalString() {
        Character[][] mirror = new Character[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                mirror[i][j] = grid[j][i];
            }
        }
        return toString(mirror);
    }

    public String getMirroredDiagonalString() {
        Character[][] mirror = new Character[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                mirror[i][j] = grid[width -1 - j][height -1 - i];
            }
        }
        return toString(mirror);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[i][j] == null) {
                    sb.append('*');
                } else {
                    sb.append(grid[i][j]);
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public String toString(Character[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (board[i][j] == null) {
                    sb.append('*');
                } else {
                    sb.append(board[i][j]);
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * The canonical string for this board is the string containing all possible permutations of this board in sorted order.
     * This string can be used for equality checks to determine if another board is actually just this one in a meaningless
     * permutation.
     * @return
     */
    public String toCanonicalString() {
        String[] permutations = new String[8];
        permutations[0] = toString();
        permutations[1] = getVerticalMirrorString();
        permutations[2] = getHorizontalMirrorString();
        permutations[3] = get90DegreesAntiClockwiseString();
        permutations[4] = get90DegreesClockwiseString();
        permutations[5] = getDiagonalString();
        permutations[6] = getMirroredDiagonalString();
        permutations[7] = get180DegreeString();
        Arrays.sort(permutations);

        return Joiner.on("\n").join(permutations);
    }

    public void print() {
        System.out.println(toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordSquare that = (WordSquare) o;

        if (width != that.width) return false;
        if (height != that.height) return false;
        return toCanonicalString().equals(that.toCanonicalString());
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        result = 31 * result + toCanonicalString().hashCode();
        return result;
    }
}
