package com.example.rpisecure;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
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
    Button btnUser;
    Button btnSubmit;
    EditText note;
    EditText newUse;
    EditText newPass;
    int monitor = 0;
    int monitor2 = 0;
    int monitor4 = 0;
    Context context;
    Boolean on;
    String ipanew;
    String oldIp;
    ImageButton btnNotes;
    String notes = "";
    ImageButton btnLogout;
    BackendlessUser currentUser;
    String noteOld;
    ImageButton set;
    int monitor3 = 0;
    String store1 = "";
    String store2 = "";


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
        btnUser = findViewById(R.id.userconfig);
        btnSubmit = findViewById(R.id.btn_submit);
        newUse = findViewById(R.id.edit_user);
        newPass = findViewById(R.id.edit_password);
        on=false;
        String email = currentUser.getEmail().toString();
        newUse.setText(email);
        store2 = newUse.getText().toString();
        newPass.setText("");
        btnLogout = findViewById(R.id.logout);

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
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSetupButtonClicked();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitButtonClicked();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutButtonClicked();
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
            //settings
            btnUser.setVisibility(View.GONE);
            if(on){
                newPass.setVisibility(View.GONE);
                newUse.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.GONE);
                on = false;
            }
            btnLogout.setVisibility(View.GONE);
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
            btnLogout.setVisibility(View.VISIBLE);
            //settings
            btnUser.setVisibility(View.VISIBLE);
            if(on){
                newPass.setVisibility(View.GONE);
                newUse.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.GONE);
                on = false;
            }
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
            oldIp = etIp.getText().toString();
            videoURL = "http://" + oldIp + "/";
            WebView videoView = (WebView) findViewById(R.id.videoView);
            videoView.loadUrl(videoURL);
            monitor4 = 0;
        }

    }
    private void UserSetupButtonClicked() {
        btnUser.setVisibility(View.GONE);
        newPass.setVisibility(View.VISIBLE);
        newUse.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.VISIBLE);
        on = true;
    }
    private void SubmitButtonClicked() {
        BackendlessUser user = currentUser;
        String password = currentUser.getPassword();
        newUse = findViewById(R.id.edit_user);
        newPass = findViewById(R.id.edit_password);
        store2 = newUse.getText().toString();
        newPass.setText("");
        store1 = newUse.getText().toString();
        store2 = newPass.getText().toString();


        if(store1 != ""){
            if(newPass.equals("") || newPass.equals(password)){
                btnNotes.setVisibility(View.GONE);
                videoView.setVisibility(View.GONE);
                ipchange.setVisibility(View.GONE);
                etIp.setVisibility(View.GONE);
                ipchange.setVisibility(View.GONE);
                btnPlay.setVisibility(View.GONE);
                btnNotes.setVisibility(View.GONE);
                //settings
                btnUser.setVisibility(View.VISIBLE);
                newPass.setVisibility(View.GONE);
                newUse.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.GONE);
                on = false;
            }
            else{
                user.setEmail(store1);
                user.setPassword(store2);
                Backendless.UserService.update( user, new AsyncCallback<BackendlessUser>()
                {
                    @Override
                    public void handleResponse( BackendlessUser backendlessUser )
                    {
                        btnNotes.setVisibility(View.GONE);
                        videoView.setVisibility(View.GONE);
                        ipchange.setVisibility(View.GONE);
                        etIp.setVisibility(View.GONE);
                        ipchange.setVisibility(View.GONE);
                        btnPlay.setVisibility(View.GONE);
                        btnNotes.setVisibility(View.GONE);
                        //settings
                        btnUser.setVisibility(View.VISIBLE);
                        newPass.setVisibility(View.GONE);
                        newUse.setVisibility(View.GONE);
                        btnSubmit.setVisibility(View.GONE);
                        on = false;

                    }

                    @Override
                    public void handleFault( BackendlessFault backendlessFault )
                    {

                    }
                } );
            }
        }
        else {
            btnNotes.setVisibility(View.GONE);
            videoView.setVisibility(View.GONE);
            ipchange.setVisibility(View.GONE);
            etIp.setVisibility(View.GONE);
            ipchange.setVisibility(View.GONE);
            btnPlay.setVisibility(View.GONE);
            btnNotes.setVisibility(View.GONE);
            //settings
            btnUser.setVisibility(View.VISIBLE);
            newPass.setVisibility(View.GONE);
            newUse.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.GONE);
            on = false;
        }
    }
    private void LogoutButtonClicked() {
        Backendless.UserService.logout( new AsyncCallback<Void>()
        {
            public void handleResponse( Void response )
            {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            public void handleFault( BackendlessFault fault )
            {
                // something went wrong and logout failed, to get the error code call fault.getCode()
            }
        });



    }


    @Override
    public void onClick(View v) {

    }
}


