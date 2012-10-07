package navat.bgu.freeseat;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.os.AsyncTask;

public class RoomsProvider {
	public RoomsProvider(Context context) {
		db = new DatabaseHelper(context);
	}
	public boolean isExpired() {
		if (db.isRoomsTableEmpty()) {
			return true;
		}
		return false;
	}
	public void feedDatabase() {
		new ParseSite().execute("http://gezer.bgu.ac.il/compclass/compclass.php");
	}
	Rooms roomList = null;
	DatabaseHelper db;
	private class ParseSite extends AsyncTask<String, Void, List<String>> {

        protected List<String> doInBackground(String... arg) {
            List<String> output = new ArrayList<String>();

            try {
            	URL url = new URL(arg[0]);
        		URLConnection urlConnection = url.openConnection();
        		InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                //roomList = new Rooms(new URL(arg[0]));
        		roomList = new Rooms(in);
                roomList.getRoomsStates();
                
                Iterator<Entry<String, Room>> it = roomList.roomsState.entrySet().iterator();
                while (it.hasNext()) {
                	Map.Entry<String, Room> pairs = (Map.Entry<String, Room>)it.next();
                	Room room = pairs.getValue();
                	db.setRoom(room);
                }
                /*List<TagNode> roomsList = roomList.getRoomsList();

                for (Iterator<TagNode> iterator = roomsList.iterator(); iterator.hasNext();)
                {
                    TagNode trElement = (TagNode) iterator.next();
                    TagNode[] cells = trElement.getElementsByName("td", false);
                    if (cells.length > 2) {
                    	output.add(cells[1].getText().toString());
                    }
                }*/
            } catch(Exception e) {
                e.printStackTrace();
            }

            return output;
        }

        protected void onPostExecute(List<String> output) {
            //ListView listview = (ListView) findViewById(R.id.roomslist);
            //listview.setAdapter(new ArrayAdapter<String>(RoomsWidgetConfig.this, android.R.layout.simple_list_item_1 , output));
        	
            //TODO feed DB
        	//TODO finished callback
        }
    }
}