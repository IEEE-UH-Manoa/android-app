package com.example.bryan.ieee;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    public static final int NUMEVENTS = 10;

    ListView newsFeedList;

    // URL to get contacts JSON
    private static String url = "http://api.ieeeatuhm.com/events";

    // JSON Node names
    private static final String TAG_EVENTS = "events";
    private static final String TAG_EVENTNAME = "eventname";
    private static final String TAG_DATE = "date";
    private static final String TAG_STARTTIME = "starttime";
    private static final String TAG_ENDTIME = "endtime";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_TYPE = "type";
    private static final String TAG_DESCRIPTION = "description";

    private ProgressDialog pDialog;

    JSONArray events = null;

    private ArrayList<Event> eventList;

    private NewsFeedAdapter adapter;

    private int firstEvent = 0; // first event to load
    private int lastEvent = NUMEVENTS; // last event displayed on news feed

    private int preLast = 0;
    private boolean firstLoad = true;



    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        newsFeedList = (ListView) rootView.findViewById(R.id.newsFeedList);

        eventList = new ArrayList<>();

        // Calling async task to get json
        GetEvents getEvents = new GetEvents();
        getEvents.execute();

        return rootView;
    }


    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetEvents extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog

            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            if (jsonStr != null) {
                try {

                    events = new JSONArray(jsonStr);


                    if(firstEvent == lastEvent)
                        getActivity().runOnUiThread((new Runnable() {
                            public void run() {
                                Toast.makeText(getActivity(), "No events left to display", Toast.LENGTH_SHORT).show();
                            }
                        }));

                    else {
                        // looping through All Contacts
                        for (int i = firstEvent; i < lastEvent; i++) {
                            JSONObject e = events.getJSONObject(i);

                            String name = e.getString(TAG_EVENTNAME);
                            String date = e.getString(TAG_DATE);
                            String startTime = e.getString(TAG_STARTTIME);
                            String endTime = e.getString(TAG_ENDTIME);
                            String location = e.getString(TAG_LOCATION);
                            String type = e.getString(TAG_TYPE);
                            String description = e.getString(TAG_DESCRIPTION);

                            // create new event
                            Event event = new Event(name, date, startTime, endTime, location, type, description);

                            // adding event to event list
                            eventList.add(event);
                        }
                    }

                    updateLastEvent();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
                   if (pDialog.isShowing())
                     pDialog.dismiss();


            if(firstLoad) {
                // set ArrayAdapter for the ListView adapter
                adapter = new NewsFeedAdapter(getActivity(), R.layout.news_feed_card, eventList);
                newsFeedList.setAdapter(adapter);

                AbsListView.OnScrollListener l = new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {

                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                        if(view.getId() == R.id.newsFeedList) {

                            int lastItem = firstVisibleItem + visibleItemCount;

                            if(lastItem == totalItemCount) {
                                if(preLast != lastItem) { // to avoid multiple calls for last item

                                    GetEvents getEvents = new GetEvents();
                                    getEvents.execute();

                                    preLast = lastItem;
                                }
                            }
                        }
                    }
                };

                newsFeedList.setOnScrollListener(l);
                newsFeedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Event e = (Event) parent.getAdapter().getItem(position);

                        Activity currActivity = getActivity();
                        Intent intent = new Intent(currActivity, EventActivity.class);
                        intent.putExtra("event", e);
                        currActivity.startActivity(intent);
                    }
                });

                firstLoad = false;
            }

            adapter.notifyDataSetChanged();
        }

    }

    private void updateLastEvent() {

        firstEvent = lastEvent;

        if(lastEvent + NUMEVENTS > events.length())
            lastEvent = events.length();

        else
            lastEvent += NUMEVENTS;

    }

}

