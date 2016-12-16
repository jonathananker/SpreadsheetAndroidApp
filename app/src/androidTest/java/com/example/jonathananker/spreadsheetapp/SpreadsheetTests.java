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
    private SpreadsheetController spreadsheetController ;
    private Stack<CellHistory> histories;

    @Test
    public void addSaveLoad() throws Exception {
        spreadsheetController = new SpreadsheetController(1, 1);
        spreadsheetController.addColumn();
        assertEquals(2, spreadsheetController.getNumberOfColumns());
        spreadsheetController.setEditXAndY(1, 0);
        spreadsheetController.setCell("test");
        assertEquals("test", spreadsheetController.getCell(1, 0));
        String save = spreadsheetController.save();
        spreadsheetController.load(save);
        assertEquals("test", spreadsheetController.getCell(1, 0));
    }

    @Test
    public void loadTestString() throws Exception {
        spreadsheetController = new SpreadsheetController(1, 1);
        spreadsheetController.load("[[,,][,test,]]");
        assertEquals("test", spreadsheetController.getCell(1, 1));
    }



    @Test
    public void undoTest() throws Exception {
        spreadsheetController = new SpreadsheetController(3, 3);
        spreadsheetController.setEditXAndY(2, 2);
        spreadsheetController.setCell("test");
        spreadsheetController.addToHistory();
        spreadsheetController.setCell("test2");
        spreadsheetController.undoLastChange();
        assertEquals("test", spreadsheetController.getCell(2, 2));

    }


}
