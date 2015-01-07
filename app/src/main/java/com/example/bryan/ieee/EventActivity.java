package com.example.bryan.ieee;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class EventActivity extends Activity {

    private Event e;
    private TextView eMonth;
    private TextView eDay;
    private TextView eDayOfWeek;
    private TextView eTitle;
    private TextView eType;
    private TextView eLocation;
    private TextView eTime;
    private TextView eDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        eMonth = (TextView) findViewById(R.id.eventMonth);
        eDay = (TextView) findViewById(R.id.eventDayOfMonth);
        eDayOfWeek = (TextView) findViewById(R.id.eventDayOfWeek);
        eTitle = (TextView) findViewById(R.id.eventTitle);
        eType = (TextView) findViewById(R.id.eventType);
        eLocation = (TextView) findViewById(R.id.eventLocation);
        eTime = (TextView) findViewById(R.id.eventTime);
        eDesc = (TextView) findViewById(R.id.eventDescription);

        // get event from listview adapter
        Intent intent = getIntent();
        e = (Event) intent.getParcelableExtra("event");

        setTextViews();

        setTitle(e.getName());

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
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

        this.finish();

        return super.onOptionsItemSelected(item);
    }

    private void setTextViews() {

        eMonth.setText(e.monthToString());
        eDay.setText(e.dayToString());
        eDayOfWeek.setText(e.getDayOfWeek());
        eTitle.setText(e.getName());
        eType.setText(e.getType());
        eLocation.setText(e.getLocation());
        eTime.setText(e.setEventTime());
        eDesc.setText(e.getDescription());

    }
}
