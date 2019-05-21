package com.example.rpisecure;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog dialog;
    WebView videoView;
    ImageButton btnPlay;
    BackendlessUser currentUser = Backendless.UserService.CurrentUser();
    String ip = currentUser.getProperty("ip").toString();
    ImageButton jab;
    EditText etIp;
    int monitor = 0;
    Context context;
    String ipanew;
    private final String TAG = this.getClass().getSimpleName();

    String videoURL = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        etIp = findViewById(R.id.newip);
        context = getApplicationContext();
        Intent intent = getIntent();
        ImageButton btnPlay = findViewById(R.id.btn_play);
        videoURL = "http://" + ip + "/";
        setContentView(R.layout.activity_main);
        videoView = (WebView) findViewById(R.id.videoView);
        jab = (ImageButton) findViewById(R.id.btn_ips);
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Please wait....");
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        WebView videoView = (WebView) findViewById(R.id.videoView);
        videoView.loadUrl(videoURL);
        dialog.dismiss();
        jab = findViewById(R.id.btn_ips);
        jab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IpButtonClicked();
            }
        });
    }

    private void IpButtonClicked()  {
        etIp = findViewById(R.id.newip);
        if (monitor == 0) {
            etIp.setVisibility(View.VISIBLE);
            monitor = 1;
        }
        else if (monitor == 1) {
            BackendlessUser user = currentUser;
            etIp.setVisibility(View.GONE);
            ipanew = etIp.getText().toString();
            user.setProperty("ip", ipanew);
            monitor = 0;
        }

    }

    @Override
    public void onClick(View v) {

    }
}


