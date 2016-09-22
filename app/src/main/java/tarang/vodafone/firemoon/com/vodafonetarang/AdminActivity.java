package tarang.vodafone.firemoon.com.vodafonetarang;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        WebView browser = (WebView) findViewById(R.id.webview);
//        browser.loadUrl("http://nutrashopy.biz/vodafone/api/api.php?method=zone_list");
//        browser.loadUrl("http://nutrashopy.biz/vodafone");
    }

}
