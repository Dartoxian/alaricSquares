package com.dartoxia.words;

import java.util.List;

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
        if (word[index] == grid[x][y]) {
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

    public void print() {
        System.out.println(toString());
    }
}
