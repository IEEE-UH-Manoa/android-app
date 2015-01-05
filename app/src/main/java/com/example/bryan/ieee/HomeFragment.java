package com.example.bryan.ieee;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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

    // contacts JSONArray
    JSONArray events = null;

    private ArrayList<Event> eventList;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        newsFeedList = (ListView) rootView.findViewById(R.id.newsFeedList);

        Event[] events = new Event[NUMEVENTS];

        Bundle args = this.getArguments();
        eventList = args.getParcelableArrayList("events");

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
/*
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
*/
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {

                    eventList = new ArrayList<Event>();
                    events = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < events.length(); i++) {
                        JSONObject e = events.getJSONObject(i);

                        String name = e.getString(TAG_EVENTNAME);
                        String date = e.getString(TAG_DATE);
                        String startTime = e.getString(TAG_STARTTIME);
                        String endTime = e.getString(TAG_ENDTIME);
                        String location = e.getString(TAG_LOCATION);
                        String type = e.getString(TAG_TYPE);
                        String description = e.getString(TAG_DESCRIPTION);

                        Log.d("events", name);

                        // create new event
                        Event event = new Event(name, date, startTime, endTime, location, type, description);

                        // adding event to event list
                        eventList.add(event);
                    }
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
            //       if (pDialog.isShowing())
            //         pDialog.dismiss();


            // set ArrayAdapter for the ListView adapter
            NewsFeedAdapter adapter = new NewsFeedAdapter(getActivity(), R.layout.news_feed_card, eventList);
            newsFeedList.setAdapter(adapter);

        }

    }



}

