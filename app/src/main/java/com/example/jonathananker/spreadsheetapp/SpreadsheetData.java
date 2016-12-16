package com.example.jonathananker.spreadsheetapp;

import java.util.ArrayList;

/**
 * Created by jonathananker on 12/15/16.
 * Holds the data of the spreadsheet
 */

 class SpreadsheetData {
    private int rows; //number of rows
    private int columns; //number of columns
    private final ArrayList<ArrayList<String>> sheet;
    /*
    AL:
        AL: String, String, String
        AL: String, String, String
        AL: String, String, String
     */
    public SpreadsheetData(int r, int c)
    {
        sheet = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < r; i++) {
            sheet.add(new ArrayList<String>());
            for (int j = 0; j < c; j++)
            {
                sheet.get(i).add("");
            }
        }
        rows = r;
        columns = c;
    }

    public void addRowToData() {
        sheet.add(new ArrayList<String>());
        for (int j = 0; j < columns; j++)
        {
            sheet.get(rows).add("");
        }
        rows++;
    }

    public void addColumnToData() {
        for (int i = 0; i < rows; i++)
        {
            sheet.get(i).add("");
        }
        columns++;
    }

    public int getRowsNumber() {
        return rows;
    }

    public int getColumnsNumber() {
        return columns;
    }

    public String getCellData(int valueX, int valueY) {
        if (rows <= valueY || columns <= valueX)
        {
            return "";
        }
        else
        {
            return sheet.get(valueY).get(valueX);
        }
    }

    /**
     *
     * @param valueX
     * @param valueY
     * @param text
     * @return if successful
     */
    public boolean setCellData(int valueX, int valueY, String text)
    {
        if (rows <= valueY || columns <= valueX)
            return false;
        else
        {
            sheet.get(valueY).set(valueX, text);
            return true;
        }
    }
}
