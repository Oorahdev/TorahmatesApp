package com.vb.torahmate.main;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.vb.torahmate.R;

public class WebViewActivity extends Activity {

    private WebView webView;
    Bundle extras;
    String loadurl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        extras = getIntent().getExtras();
        if (extras != null){
            loadurl = extras.getString("url",
                    "https://github.com/firebase/quickstart-android/blob/master/messaging/app/src/main/AndroidManifest.xml");
        }
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(loadurl);
    }


}
