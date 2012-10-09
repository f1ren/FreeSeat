package navat.bgu.freeseat;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class RoomsProvider {
	ArrayList<Room> roomList;
	DatabaseHelper db;
	Context context;
	MainActivity activity;
	
	public RoomsProvider(MainActivity fromActivity) {
		context = fromActivity;
		activity = fromActivity;
		db = new DatabaseHelper(context);
		loadFromDb();
	}
	public void loadFromDb() {
		roomList = db.getAllRooms();
	}
	public boolean isExpired() {
		if (db.isRoomsTableEmpty()) {
			return true;
		}
		return false;
	}
	public void feedDatabase() {
		new ParseSite().execute("http://gezer.bgu.ac.il/compclass/compclass.php");
		//AsyncTask task = new ProgressTask(this.activity).execute();
	}

	private class ParseSite extends AsyncTask<String, Void, Boolean> {
		private ProgressDialog pdia;

        protected Boolean doInBackground(String... arg) {
            try {
            	URL url = new URL(arg[0]);
        		//URLConnection urlConnection = url.openConnection();
        		//InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            	RoomsHTMLParser parser = new RoomsHTMLParser(url);
        		
        		if (parser != null) {
    	        	parser.getRoomsStates();
    	            
    	            Iterator<Room> it = parser.roomsState.iterator();
    	            while (it.hasNext()) {
    	            	db.setRoom(it.next());
    	            }
            	} else {
            		// TODO error fetching url! Notify user and log!
            	}
            	loadFromDb();
            	activity.adapter = new ArrayAdapter<Room>(activity,
                        android.R.layout.simple_list_item_1, 
                        roomList);
            	activity.setListAdapter(activity.adapter);
            	activity.adapter.notifyDataSetChanged();
            } catch(Exception e) {
                e.printStackTrace();
            }

            return true;
        }
        
        @Override
        protected void onPreExecute(){ 
           super.onPreExecute();
                pdia = new ProgressDialog(context);
                pdia.setMessage("Retriving data from server");
                pdia.show();
                db.onUpgrade(db.getWritableDatabase(), 1, 1);
        }

        protected void onPostExecute(RoomsHTMLParser parser) {
        	pdia.dismiss();
        }
    }
}