package com.example.bryan.ieee;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    public static final int NUMEVENTS = 10;

    ListView newsFeedList;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        newsFeedList = (ListView) rootView.findViewById(R.id.newsFeedList);

        Event[] events = new Event[NUMEVENTS];

        // set ArrayAdapter for the ListView adapter
        NewsFeedAdapter adapter = new NewsFeedAdapter(getActivity(), R.layout.news_feed_card, events);
        newsFeedList.setAdapter(adapter);

        return rootView;
    }

}
