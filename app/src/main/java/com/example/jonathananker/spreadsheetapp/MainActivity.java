package com.example.jonathananker.spreadsheetapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import static com.example.jonathananker.spreadsheetapp.R.id.fab;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TableLayout table; //holds tablerows of cells (textviews)
    /*
    TL:
    TR: TV TV TV
    TR: TV TV TV
    TR: TV TV TV
     */
    EditText editor;
    SpreadsheetController spreadsheetController;
    boolean hasTouchedCell = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton undoButton = (FloatingActionButton) findViewById(fab);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spreadsheetController.isHistoryEmpty()) {
                    //nothing to undo
                    Snackbar.make(view, "No history", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //gets most recent change and undos it
                    spreadsheetController.undoLastChange();
                    setCellText();
                }
            }
        });

        table = (TableLayout) findViewById(R.id.spreadsheet);
        editor = (EditText) findViewById(R.id.editText);
        editor.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    View view = getCurrentFocus();
                    if (view != null) {
                        //hide keyboard
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        if (!hasTouchedCell) {
                            Snackbar.make(view, "Tap on a cell to enter text", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            return false;
                        }
                        spreadsheetController.addToHistory();
                        spreadsheetController.setCell(editor.getText().toString());
                        setCellText();
                        editor.setText(""); //clear editText
                        return true;
                    }
                }
                return false;
            }
        });
        final Button rowButton = (Button) findViewById(R.id.rowButton);
        rowButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addRow();
            }
        });
        final Button columnButton = (Button) findViewById(R.id.columnButton);
        columnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addColumn();
            }
        });
        spreadsheetController = new SpreadsheetController(1, 1);
        TableRow tr = new TableRow(getApplicationContext());
        tr.addView(newCell(0, 0));
        table.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    /**
     * Adds column
     */
    public void addColumn() {
        int columns = spreadsheetController.getNumberOfColumns();
        spreadsheetController.addColumn();
        for (int i = 0, j = table.getChildCount(); i < j; i++) {
            //finds and adds a cell to every TableRow
            View view = table.getChildAt(i);
            if (view instanceof TableRow) {
                TableRow tr = (TableRow) view;
                tr.addView(newCell(columns, i));
            }
        }
    }

    /**
     * Adds row
     */
    public void addRow() {
        //adds a new TableRow
        int rows = spreadsheetController.getNumberOfRows(), columns = spreadsheetController.getNumberOfColumns();
        spreadsheetController.addRow();
        TableRow tr = new TableRow(getApplicationContext());
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        for (int i = 0; i < columns; i++) {
            //adds a cell for every column
            tr.addView(newCell(i, rows));
        }
        table.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }

    /**
     * Sets the cell text
     */
    private void setCellText() {
        int setX = spreadsheetController.getEditX();
        int setY = spreadsheetController.getEditY();
        if (table.getChildCount() > setY) {
            View view = table.getChildAt(setY);
            if (view instanceof TableRow) {
                TableRow tr = (TableRow) view;
                if (tr.getChildCount() > setX) {
                    view = tr.getChildAt(setX);
                    if (view instanceof TextView) {
                        TextView text = (TextView) view;
                        text.setText(spreadsheetController.getCell(setX, setY));
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Creates a new cell (TextView) to add to a TableRow
     *
     * @param valueX X coordinate of cell
     * @param valueY Y coordinate of cell
     * @return new cell
     */
    public TextView newCell(final int valueX, final int valueY) {
        TextView textview = new TextView(getApplicationContext());
        textview.setText("");
        textview.setBackgroundResource(R.drawable.cell_shape);
        textview.setMinHeight(100);
        textview.setMinWidth(100);
        textview.setPadding(10, 10, 10, 10);
        textview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //on cell click sets the coordinates of the current cell to the edit values and gives focus to the edittext
                hasTouchedCell = true; //user has touched cell, allowing editor to set a value
//                Snackbar.make(v, " " + valueX + " " + valueY + " ", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                spreadsheetController.setEditXAndY(valueX, valueY);
                editor.requestFocus();
            }
        });
        return textview;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
