package com.example.jonathananker.spreadsheetapp;

import java.util.Stack;

/**
 * Created by jonathananker on 12/15/16.
 * Controller of spreadsheet data
 */

public class SpreadsheetController {
    private final SpreadsheetData spreadsheet; //contains spreadsheet data
    private final Stack<CellHistory> history; //Stack holding history of changes
    private int editX = 1, editY = 1; //edit holds the coordinates of the cell to edit

    public int getEditX() {
        return editX;
    }

    public void setEditXAndY(int editX, int editY) {
        this.editX = editX;
        this.editY = editY;
    }

    public int getEditY() {
        return editY;
    }

    public SpreadsheetController(int c, int r)
    {
        spreadsheet = new SpreadsheetData(c, r);
        history = new Stack<CellHistory>();
    }

    public void addRow() {
        spreadsheet.addRowToData();
    }

    public void addColumn() {
        spreadsheet.addColumnToData();
    }

    public int getNumberOfRows() {
        return spreadsheet.getRowsNumber();
    }

    public int getNumberOfColumns() {
        return spreadsheet.getColumnsNumber();
    }

    public String getCell(int valueX, int valueY) {
        return spreadsheet.getCellData(valueX, valueY);
    }

    /**
     * Sets the data for cell of editX, editY
     * @param text String to set data to
     * @return if successful
     */
    public boolean setCell(String text)
    {
        return spreadsheet.setCellData(editX, editY, text);
    }

    public boolean isHistoryEmpty() {
        return history.empty();
    }

    public void addToHistory() {
        history.add(new CellHistory(editX, editY, getCell(editX, editY)));
    }

    /**
     * user presses the undo button and resets last change
     */
    public void undoLastChange() {
        CellHistory last = history.pop();
        setEditXAndY(last.getValueX(), last.getValueY());
        setCell(last.getOldText());
    }

    public String save() {
        return spreadsheet.saveData();
    }

    public void load(String data) {
        spreadsheet.loadData(data);
    }

    /**
     * sets the text of the header cell
     * @param valueX X
     * @param valueY Y
     * @return text to set to ui cell
     */
    public String setHeaderCell(int valueX, int valueY)
    {
        String text;
        if (valueX == valueY) {
            text = "";
        }
        else if (valueY == 0) {
            text = toAlphabetic(valueX - 1);
        }
        else {
            text = (Integer.toString(valueY));
        }
        spreadsheet.setCellData(valueX, valueY, text);
        return text;
    }

    /**
     * convert header x value to alphabetic header
     * @param i x - 1
     * @return
     */
    public static String toAlphabetic(int i) {
        int quotient = i/26;
        int remainder = i%26;
        char letter = (char)('A' + remainder);
        if( quotient == 0 ) {
            return String.valueOf(letter);
        } else {
            return toAlphabetic(quotient - 1) + letter;
        }
    }
}
