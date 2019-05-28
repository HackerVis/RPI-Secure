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
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog dialog;
    WebView videoView;
    ImageButton btnPlay;
    ImageButton ipchange;
    EditText etIp;
    EditText note;
    int monitor = 0;
    int monitor2 = 0;
    int monitor4 = 0;
    Context context;
    String ipanew;
    String oldIp;
    ImageButton btnNotes;
    String notes = "";
    BackendlessUser currentUser;
    String noteOld;
    ImageButton set;
    int monitor3 = 0;


    private final String TAG = this.getClass().getSimpleName();

    String videoURL = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();
        setContentView(R.layout.activity_main);
        currentUser = Backendless.UserService.CurrentUser();
        String ip = currentUser.getProperty("ip").toString();
        Intent intent = getIntent();
        ImageButton note = findViewById(R.id.btn_notes);
        ImageButton set = findViewById(R.id.btn_set);
        btnPlay = findViewById(R.id.btn_recordings);
        ipanew = ip;



        videoURL = "http://" + ip + "/";
        etIp = findViewById(R.id.newip);
        Log.i(TAG, ip);

        if(ip != null) {
            etIp.setText(ip);
        }
        else{
            etIp.setText("");
        }
        videoView = (WebView) findViewById(R.id.videoView);
        ipchange = (ImageButton) findViewById(R.id.btn_ips);
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Please wait....");
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        WebView videoView = (WebView) findViewById(R.id.videoView);
        videoView.loadUrl(videoURL);
        dialog.dismiss();
        ipchange = findViewById(R.id.btn_ips);
        ipchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IpButtonClicked();
            }
        });
        btnNotes = findViewById(R.id.btn_notes);
        btnNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotesButtonClicked();
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetButtonClicked();
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordButtonClicked();
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
            oldIp = etIp.getText().toString();
            ipanew = etIp.getText().toString();
            user.setProperty("ip", ipanew);
            videoURL = "http://" + ipanew + "/";
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Configuring....");
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
            WebView videoView = (WebView) findViewById(R.id.videoView);
            videoView.loadUrl(videoURL);
            if(ipanew.equals(oldIp)){
                dialog.setMessage("Stopping...");
                dialog.dismiss();
            }
            else{
                Backendless.UserService.update( user, new AsyncCallback<BackendlessUser>()
                {
                    @Override
                    public void handleResponse( BackendlessUser backendlessUser )
                    {
                        dialog.setMessage("Updating......");
                        dialog.dismiss();

                    }

                    @Override
                    public void handleFault( BackendlessFault backendlessFault )
                    {

                    }
                } );
            }
            if(ipanew != null) {
                etIp.setText(ipanew);
            }
            else{
                etIp.setText("");
            }
            monitor = 0;
        }



    }
    private void NotesButtonClicked() {
        note = findViewById(R.id.note);
        notes = currentUser.getProperty("note").toString();
        noteOld = currentUser.getProperty("note").toString();
        if (monitor2 == 0) {
            note.setVisibility(View.VISIBLE);
            monitor2 = 1;
        }
        else if (monitor2 == 1){
            BackendlessUser user = currentUser;
            note.setVisibility(View.GONE);
            notes = note.getText().toString();
            user.setProperty("note", notes);
            if(notes.equals(noteOld)){
                dialog.setMessage("Stopping...");
                dialog.dismiss();
            }
            else{
                Backendless.UserService.update( user, new AsyncCallback<BackendlessUser>()
                {
                    @Override
                    public void handleResponse( BackendlessUser backendlessUser )
                    {
                        dialog.setMessage("Updating......");
                        dialog.dismiss();

                    }

                    @Override
                    public void handleFault( BackendlessFault backendlessFault )
                    {

                    }
                } );
            }
            monitor2 = 0;


        }

    }
    private void SetButtonClicked() {
        //Display Stuff
        if(monitor3 == 0){
            btnNotes.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.VISIBLE);
            ipchange.setVisibility(View.VISIBLE);
            etIp.setVisibility(View.VISIBLE);
            btnPlay.setVisibility(View.VISIBLE);
            etIp.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            monitor3 = 1;
        }
        // Display setting options
        else if(monitor3 == 1){
            btnNotes.setVisibility(View.GONE);
            videoView.setVisibility(View.GONE);
            ipchange.setVisibility(View.GONE);
            etIp.setVisibility(View.GONE);
            ipchange.setVisibility(View.GONE);
            btnPlay.setVisibility(View.GONE);
            btnNotes.setVisibility(View.GONE);
            videoView.setVisibility(View.GONE);
            monitor3 = 0;
        }
    }
    private void RecordButtonClicked() {
        String ip = currentUser.getProperty("ip").toString();
        if (monitor4 == 0){
            videoURL = "https://github.com/HackerVis/RPISecure/tree/master/RPISafeSecure/Pictures";
            videoView.loadUrl(videoURL);
            monitor4 = 1;
        }
        else if (monitor4 == 1){
            videoURL = "http://" + etIp.getText().toString() + "/";
            videoView.loadUrl(videoURL);
            monitor4 = 0;
        }

    }


    @Override
    public void onClick(View v) {

    }
}


