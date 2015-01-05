package com.example.bryan.ieee;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsFeedAdapter extends ArrayAdapter<Event> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<Event> eventList;

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
        Event e = eventList.get(position);

        nfDayOfMonth.setText(dayToString(e.getDay()));
        nfDayOfWeek.setText(e.getDayOfWeek());
        nfMonth.setText(monthToString(e.getMonth()));
        nfEventLocation.setText(locationToString(e.getLocation()));
        nfEventTime.setText(setEventTime(e));
        nfEventTitle.setText(e.getName());
        nfEventType.setText(e.getType());

        return listItem;
    }

    private String setEventTime(Event e) {
        if(e.getStartTime().equals(""))
            return "TBD";

        String eventTime = e.getStartTime() + " - " + e.getEndTime();

        return eventTime;
    }

    private String monthToString(int month) {
        switch(month) {
            case 1: return "JAN";
            case 2: return "FEB";
            case 3: return "MAR";
            case 4: return "APR";
            case 5: return "MAY";
            case 6: return "JUN";
            case 7: return "JUL";
            case 8: return "AUG";
            case 9: return "SEP";
            case 10: return "OCT";
            case 11: return "NOV";
            case 12: return "DEC";
            default: return "TBD";
        }
    }

    private String dayToString(int day) {
        if(day == 0)
            return "";

        else
            return String.valueOf(day);
    }

    private String locationToString(String location) {
        if(location.equals(""))
            return "TBD";

        else
            return location;
    }
}
