package navat.bgu.freeseat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.widget.Filter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class RoomAdapter extends ArrayAdapter<Room> implements Filterable {
	public ArrayList<Room> subItems;
	public ArrayList<Room> allItems;
    //private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

	//public RoomAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
    public RoomAdapter(Context context, int textViewResourceId, ArrayList<Room> items) {
    	super(context, textViewResourceId, items);
		//activity = a;
        //data = d;
        //inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	this.subItems = items;
        this.allItems = this.subItems;
        inflater = LayoutInflater.from(context);
        }
    public int getCount() {
         return subItems.size();
    }
    /*public Object getItem(int position) {
        return position;
    }*/
    /*public long getItemId(int position) {
        return position;
    }*/
	public static ArrayList<HashMap<String, String>> roomsToHashMapList(ArrayList<Room> roomsList) {
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		Room room = null;
		for (Iterator<Room> iter = roomsList.iterator(); iter.hasNext(); ) {
			room = iter.next();
			HashMap<String, String> roomFields = new HashMap<String, String>();
			roomFields.put("room", room.room);
			roomFields.put("availability", String.valueOf(room.occupied) + "/" + String.valueOf(room.total));
			result.add(roomFields);
		}
		return result;
	}

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.room_row, null);

        TextView roomName = (TextView) vi.findViewById(R.id.roomName);
        TextView availability = (TextView) vi.findViewById(R.id.availability);

        //HashMap<String, String> venue = new HashMap<String, String>();
        Room room = subItems.get(position);

        // Setting all values in listview
        roomName.setText(room.room);
        availability.setText(String.valueOf(room.occupied) + "/" + String.valueOf(room.total));

        return vi;
	    }
	@Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                //ArrayList<HashMap<String, String>> filteredArrayVenues = new ArrayList<HashMap<String, String>>();
                ArrayList<Room> filteredArrayRooms = new ArrayList<Room>();

                if (constraint == null || constraint.length() == 0) {
                    results.count = allItems.size();
                    results.values = allItems;
                } else {
                    constraint = constraint.toString();
                    for (int index = 0; index < allItems.size(); index++) {
                        Room room = allItems.get(index);

                        if (room.room.contains(
                        constraint.toString())) {
                            filteredArrayRooms.add(room);
                        }
                    }

                    results.count = filteredArrayRooms.size();
                    System.out.println(results.count);

                    results.values = filteredArrayRooms;
                }

                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
            FilterResults results) {

                subItems = (ArrayList<Room>) results.values;
                notifyDataSetChanged();
            }

        };

        return filter;
    }
}
