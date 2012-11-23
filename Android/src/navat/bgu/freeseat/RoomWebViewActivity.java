package navat.bgu.freeseat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebSettings;

public class RoomWebViewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String link = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        
        setContentView(R.layout.activity_room_web_view);
        
        WebView wv = (WebView) findViewById(R.id.roomWebView);
        wv.setBackgroundColor(Color.parseColor("#000000"));
        wv.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        wv.loadUrl(link);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_room_web_view, menu);
        return true;
    }

}
