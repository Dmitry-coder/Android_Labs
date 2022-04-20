package com.example.feedthecat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Table extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        try {
            File internalStorageDir = getFilesDir();
            File table = new File(internalStorageDir, "results.txt");
            FileReader fr = new FileReader(table);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                String[] tableRow = line.split("_");
                addRow(tableRow[0], tableRow[1], tableRow[2]);
                line = reader.readLine();
            }
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addRow(String c0, String c1, String c2) {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.table);
        LayoutInflater inflater = LayoutInflater.from(this);
        TableRow tr = (TableRow) inflater.inflate(R.layout.table_row, null);

        TextView tv = (TextView) tr.findViewById(R.id.col1);
        tv.setText("feeds: " + c0);

        tv = (TextView) tr.findViewById(R.id.col2);
        tv.setText(c1);

        tv = (TextView) tr.findViewById(R.id.col3);
        tv.setText(c2);

        tableLayout.addView(tr);
    }
}