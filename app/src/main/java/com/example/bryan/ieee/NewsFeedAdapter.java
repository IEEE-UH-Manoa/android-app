package com.example.bryan.ieee;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsFeedAdapter extends ArrayAdapter<Event> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<Event> eventList;

    private Event e;

    // elements in the news feed
    private TextView nfDayOfWeek;
    private TextView nfDayOfMonth;
    private TextView nfMonth;
    private TextView nfEventType;
    private TextView nfEventTitle;
    private TextView nfEventLocation;
    private TextView nfEventTime;

    /*
     * @mContext - app context
     *
     * @layoutResourceId - the listview_item_row.xml
     *
     * @data - the ListItem data
     */
    public NewsFeedAdapter(Context mContext, int layoutResourceId, ArrayList<Event> eventList) {

        super(mContext, layoutResourceId, eventList);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.eventList = eventList;
    }

    /*
 * @We'll overried the getView method which is called for every ListItem we
 * have.
 *
 * @There are lots of different caching techniques for Android ListView to
 * achieve better performace especially if you are going to have a very long
 * ListView.
 */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;
/*
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);
*/
        if(listItem == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            listItem = inflater.inflate(layoutResourceId, parent, false);
        }

        // get the elements in the layout
        nfDayOfMonth = (TextView) listItem.findViewById(R.id.newsFeedDayOfMonth);
        nfDayOfWeek = (TextView) listItem.findViewById(R.id.newsFeedDayOfWeek);
        nfMonth = (TextView) listItem.findViewById(R.id.newsFeedMonth);
        nfEventLocation = (TextView) listItem.findViewById(R.id.newsFeedEventLocation);
        nfEventTime = (TextView) listItem.findViewById(R.id.newsFeedEventTime);
        nfEventTitle = (TextView) listItem.findViewById(R.id.newsFeedEventTitle);
        nfEventType = (TextView) listItem.findViewById(R.id.newsFeedEventType);

		/*
		 * Set the data for the list item.
		 */
        e = eventList.get(position);

        setTextViews();

        return listItem;
    }

    private void setTextViews() {

        nfDayOfMonth.setText(e.dayToString());
        nfDayOfWeek.setText(e.getDayOfWeek());
        nfMonth.setText(e.monthToString());
        nfEventLocation.setText(e.locationToString());
        nfEventTime.setText(e.setEventTime());
        nfEventTitle.setText(e.getName());
        nfEventType.setText(e.getType());

    }

}
