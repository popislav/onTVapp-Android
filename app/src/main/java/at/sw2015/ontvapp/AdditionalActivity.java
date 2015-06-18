package at.sw2015.ontvapp;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import java.util.ArrayList;


public class AdditionalActivity  extends ActionBarActivity implements View.OnClickListener {

    ArrayList<String> allTimes, allTitles;
    TextView timeView, titleView, largeText;
    TableLayout table;
    TableRow row;
    String channel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional);

        ImageButton imageButtonBack = (ImageButton) findViewById(R.id.imageButtonBack);
        imageButtonBack.setOnClickListener(this);

        table = (TableLayout)findViewById(R.id.tableLayout);
        table.setColumnStretchable(0, true);
        table.setColumnStretchable(1, true); ///try without txt width

        allTimes = getIntent().getStringArrayListExtra("times");
        allTitles = getIntent().getStringArrayListExtra("titles");

        channel = getIntent().getStringExtra("channel");
        largeText =(TextView) findViewById(R.id.textLarge);
        largeText.setText(channel);
        largeText.setTextSize(30);


        renderTable(allTimes, allTitles);
    }

    public void renderTable(ArrayList<String> allTimes, ArrayList<String> allTitles){

        for(int i=0; i<allTimes.size(); i++){
            row = new TableRow(this);
            timeView = new TextView(this);
            titleView = new TextView(this);
            timeView.setText(allTimes.get(i));
            timeView.setTextAppearance(this, R.style.Base_TextAppearance_AppCompat_Display1);
            timeView.setTextSize(16);
            timeView.setTextColor(Color.BLACK);
            timeView.setPadding(0, 0, 10, 5);

            titleView.setText(allTitles.get(i));
            titleView.setTextAppearance(this, R.style.Base_TextAppearance_AppCompat_Display1);
            titleView.setTextSize(16);
            titleView.setPadding(10, 0, 0, 5);

            row.addView(timeView);
            row.addView(titleView);
            table.setColumnShrinkable(1, true);
            table.addView(row);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_additional, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.imageButtonBack:
                button2Click();
                break;
        }
    }

    public void button2Click() {
        finish();
        overridePendingTransition(0, 0);
    }

}


