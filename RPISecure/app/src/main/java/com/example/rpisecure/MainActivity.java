package com.example.rpisecure;

        import android.annotation.SuppressLint;
        import android.app.ProgressDialog;
        import android.media.MediaPlayer;
        import android.net.Uri;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.webkit.WebView;
        import android.widget.ImageButton;
        import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog dialog;
    WebView videoView;
    ImageButton btnPlay;

    String videoURL = "http://192.168.137.216:8081/";
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = (WebView) findViewById(R.id.videoView);
        btnPlay = (ImageButton) findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(this);
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Please wait....");
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        WebView videoView = (WebView) findViewById(R.id.videoView);
        videoView.loadUrl(videoURL);
        dialog.dismiss();

    }

    @Override
    public void onClick(View v) {

    }
}


