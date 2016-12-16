package com.example.jonathananker.spreadsheetapp;

/**
 * Created by jonathananker on 12/15/16.
 *     Used in a stack to hold changes. Used when pressing the undo button
 */

public class CellHistory {
    private final int valueX;
    private final int valueY;
    private final String oldText;

    CellHistory(int x, int y, String s1) {
        valueX = x;
        valueY = y;
        oldText = s1;
    }

    public int getValueX() {
        return valueX;
    }

    public int getValueY() {
        return valueY;
    }

    public String getOldText() {
        return oldText;
    }
}
