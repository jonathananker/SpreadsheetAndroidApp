package com.example.jonathananker.spreadsheetapp;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Stack;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SpreadsheetTests {
    private SpreadsheetController spreadsheetController;
    private Stack<CellHistory> histories;

    @Test
    public void addToSpreadsheet() throws Exception {
        spreadsheetController = new SpreadsheetController(1, 1);
        spreadsheetController.addColumn();
        assertEquals(2, spreadsheetController.getNumberOfColumns());
        spreadsheetController.setEditXAndY(1, 0);
        spreadsheetController.setCell("test");
        assertEquals("test", spreadsheetController.getCell(1, 0));
    }

    @Test
    public void setHistories() throws Exception {
        histories = new Stack<CellHistory>();
        histories.add(new CellHistory(0,0,"one"));
        histories.add(new CellHistory(0,1,"two"));
        histories.pop();
        assertEquals("one", histories.pop().getOldText());
    }


}
