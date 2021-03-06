package com.example.rpisecure;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog dialog;
    WebView videoView;
    ImageButton btnPlay;
    BackendlessUser currentUser = Backendless.UserService.CurrentUser();
    String ip = currentUser.getProperty("ip").toString();
    ImageButton fab;
    EditText etIp;
    int monitor = 0;


    String videoURL = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        videoURL = "http://" + ip + "/";
        setContentView(R.layout.activity_main);
        videoView = (WebView) findViewById(R.id.videoView);
        fab = (ImageButton) findViewById(R.id.btn_ips);
        btnPlay.setOnClickListener(this);
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Please wait....");
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        WebView videoView = (WebView) findViewById(R.id.videoView);
        videoView.loadUrl(videoURL);
        dialog.dismiss();
        fab = new ImageButton(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IpButtonClicked();
            }
        });
    }
    private void IpButtonClicked()  {
        if (monitor == 0) {
            etIp = findViewById(R.id.et_ip);
            etIp.setVisibility(View.VISIBLE);
        }
        monitor = 1;
        if (monitor == 1) {
            BackendlessUser user = new BackendlessUser();
            etIp.setVisibility(View.GONE);
            user.setProperty("ip", ip);

            // save object asynchronously
            Backendless.Persistence.save(ip, new AsyncCallback<Ip>() {
                @Override
                public void handleResponse(Ip response) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                @Override
                public void handleFault(BackendlessFault fault) {

                }
            });
        }
    }

    @Override
    public void onClick(View v) {

    }
}


