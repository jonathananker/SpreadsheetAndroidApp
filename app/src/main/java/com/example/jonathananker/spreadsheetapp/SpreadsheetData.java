package com.example.jonathananker.spreadsheetapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jonathananker on 12/15/16.
 * Holds the data of the spreadsheet
 */

 class SpreadsheetData {
    private static final String TAG = "SpreadsheetData";
    private int rows; //number of rows
    private int columns; //number of columns
    private ArrayList<ArrayList<String>> sheet;
    private final String defaultValue = ""; //default value of an empty cell
    /*
    AL:
        AL: String, String, String
        AL: String, String, String
        AL: String, String, String
     */
    public SpreadsheetData(int c, int r)
    {
        sheet = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < r; i++) {
            sheet.add(new ArrayList<String>());
            for (int j = 0; j < c; j++)
            {
                sheet.get(i).add(defaultValue);
            }
        }
        rows = r;
        columns = c;
    }

    public void addRowToData() {
        sheet.add(new ArrayList<String>());
        for (int j = 0; j < columns; j++)
        {
            sheet.get(rows).add(defaultValue);
        }
        rows++;
    }

    public void addColumnToData() {
        for (int i = 0; i < rows; i++)
        {
            sheet.get(i).add(defaultValue);
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
     * @param valueX X value of cell
     * @param valueY Y value of cell
     * @param text text to set in cell
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

    public String saveData() {
        StringBuilder sb = new StringBuilder();
        String delim = ""; //no comma before first value
        sb.append("["); //start of document
        for (ArrayList<String> row: sheet)
        {
            sb.append("["); //start of row
            for (String data: row)
            {
                sb.append(delim).append(data);
                delim = ","; //comma between values
            }
            sb.append("]"); //end row
            delim = "";
        }
        sb.append("]"); //end documnet
        Log.i(TAG, "saveData: " + sb.toString());
        return sb.toString();
    }

    public void loadData(String data) {
        if (data == null) return;
        String trim = data.substring(1, data.length() - 1); //removing surrounding []
        String[] split = trim.split("]");
        sheet = new ArrayList<ArrayList<String>>();
        for (String row: split)
        {
            if (row.length() > 1)
                sheet.add(loadRow(row.substring(1))); //load row, removing the front [
            else
            {
                ArrayList<String> list = new ArrayList<String>(); //Row only has a empty cell in it
                list.add(defaultValue);
                sheet.add(list);
            }
        }
        rows = sheet.size();
        if (rows > 0)
            columns = sheet.get(0).size();

    }

    private ArrayList<String> loadRow(String string) {
        ArrayList<String> list = new ArrayList<String>();
        String[] split = string.split(",", -1);
        Collections.addAll(list, split);
        return list;
    }
}
