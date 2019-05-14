package com.example.rpisecure;

        import android.annotation.SuppressLint;
        import android.app.ProgressDialog;
        import android.graphics.Bitmap;
        import android.graphics.Canvas;
        import android.graphics.Paint;
        import android.graphics.Picture;
        import android.media.MediaPlayer;
        import android.net.Uri;
        import android.os.Environment;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.ImageButton;
        import android.widget.VideoView;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.OutputStream;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        webView = (WebView) findViewById(R.id.webView1);
        webView.loadUrl("http://developer.android.com/reference/packages.html");
//      webView.loadUrl("http://developer.android.com/training/basics/firstapp/creating-project.html");

        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                webView.measure(MeasureSpec.makeMeasureSpec(
                        MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                webView.layout(0, 0, webView.getMeasuredWidth(),
                        webView.getMeasuredHeight());
                webView.setDrawingCacheEnabled(true);
                webView.buildDrawingCache();
                Bitmap bm = Bitmap.createBitmap(webView.getMeasuredWidth(),
                        webView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

                Canvas bigcanvas = new Canvas(bm);
                Paint paint = new Paint();
                int iHeight = bm.getHeight();
                bigcanvas.drawBitmap(bm, 0, iHeight, paint);
                webView.draw(bigcanvas);
                System.out.println("1111111111111111111111="
                        + bigcanvas.getWidth());
                System.out.println("22222222222222222222222="
                        + bigcanvas.getHeight());

                if (bm != null) {
                    try {
                        String path = Environment.getExternalStorageDirectory()
                                .toString();
                        OutputStream fOut = null;
                        File file = new File(path, "/aaaa.png");
                        fOut = new FileOutputStream(file);

                        bm.compress(Bitmap.CompressFormat.PNG, 50, fOut);
                        fOut.flush();
                        fOut.close();
                        bm.recycle();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

    }
}


