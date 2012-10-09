package navat.bgu.freeseat;

import android.os.Bundle;
import android.app.ListActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ListActivity {
	
	private EditText filterText = null;
	ArrayAdapter<Room> adapter = null;
	RoomsProvider rp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        filterText = (EditText) findViewById(R.id.search_box);
        filterText.addTextChangedListener(filterTextWatcher);
        
        rp = new RoomsProvider(this);
        
        if (rp.isExpired()) {
        	rp.feedDatabase();
        }
        
        adapter = new ArrayAdapter<Room>(this,
                android.R.layout.simple_list_item_1, 
                rp.roomList);
        setListAdapter(adapter);
        
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
}
