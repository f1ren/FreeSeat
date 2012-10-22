package navat.bgu.freeseat;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ListActivity {
	public final static String EXTRA_MESSAGE = "navat.bgu.freeseat.link";
	private EditText filterText = null;
	ArrayAdapter<Room> adapter = null;
	RoomsProvider rp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        filterText = (EditText) findViewById(R.id.search_box);
        filterText.addTextChangedListener(filterTextWatcher);
        
        rp = new RoomsProvider(this, this.getSharedPreferences("settings", 0));
        
        if (rp.isExpired()) {
        	rp.feedDatabase();
        }
        
        //adapter = new ArrayAdapter<Room>(this,
        //        android.R.layout.simple_list_item_1, 
        //        rp.roomList);
        
        //SimpleAdapter adapter2 = new RoomAdapter(this, RoomAdapter.roomsToHashMapList(rp.roomList), R.layout.room_row,
        //        new String[] {"room", "availability"}, new int[] {R.id.roomName, R.id.availability});
        BaseAdapter adapter2 = new RoomAdapter(this, R.id.roomName, rp.roomList);
        
        setListAdapter(adapter2);
        
        final Button refreshButton = (Button) findViewById(R.id.refresh);
        refreshButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				rp.feedDatabase();
			}
		});
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private TextWatcher filterTextWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                int count) {
        	adapter.getFilter().filter(s);
        }
    };
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        filterText.removeTextChangedListener(filterTextWatcher);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	//Toast.makeText(this, adapter.getItem(position).link, Toast.LENGTH_SHORT).show();
    	Intent i = new Intent(this, RoomWebViewActivity.class);
    	i.putExtra(EXTRA_MESSAGE, adapter.getItem(position).link);
    	startActivity(i);
    }
}
