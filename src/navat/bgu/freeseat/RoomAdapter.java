package navat.bgu.freeseat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class RoomAdapter extends SimpleAdapter {
	private int[] colors = new int[] { 0x30FF0000, 0x300000FF };

	public RoomAdapter(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to) {
		        super(context, items, resource, from, to);
		    }
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
	    public View getView(int position, View convertView, ViewGroup parent) {
	      View view = super.getView(position, convertView, parent);
	      int colorPos = position % colors.length;
	      view.setBackgroundColor(colors[colorPos]);
	      return view;
	    }

}
