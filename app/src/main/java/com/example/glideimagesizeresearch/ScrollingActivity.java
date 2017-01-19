package com.example.glideimagesizeresearch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ScrollingActivity extends AppCompatActivity {
    private static final String LOG_TAG = "GlideTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;
        int deviceHeight = displayMetrics.heightPixels;

        Log.d(LOG_TAG, "(Width x Height) = " + "(" + deviceWidth + " x " + deviceHeight + ")");

        final ImageView image1 = (ImageView) findViewById(R.id.image1);
        final ImageView image2 = (ImageView) findViewById(R.id.image2);
        final ImageView image3 = (ImageView) findViewById(R.id.image3);
        checkGlideImage(image1, image2, image3,
                "https://mp-seoul-image-production-s3.mangoplate.com/web/resources/um4ufwanv2ye6v35.jpg",
                getPixelFromDip(180f));

//        final ImageView image4 = (ImageView) findViewById(R.id.image4);
//        final ImageView image5 = (ImageView) findViewById(R.id.image5);
//        final ImageView image6 = (ImageView) findViewById(R.id.image6);
//        checkGlideImage(image4, image5, image6,
//                "https://mp-seoul-image-production-s3.mangoplate.com/web/resources/vm-3cyykwie2oz4t.jpg",
//                getPixelFromDip(280f));
    }

    private void checkGlideImage(final ImageView image1, final ImageView image2, final ImageView image3, final String imageUrl, int height) {
        Log.e(LOG_TAG, "checkGlideImage() -------------------------------------------------- ");

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, height);
        params.bottomMargin = getPixelFromDip(10f);

        image1.setLayoutParams(params);
        image2.setLayoutParams(params);
        image3.setLayoutParams(params);

        new Thread(new Runnable() {
            public void run() {
                Bitmap bitmap = null;
                try {
                    bitmap = getBitmap(imageUrl);
                } catch (Exception e) {
                } finally {
                    if (bitmap != null) {
                        Log.d(LOG_TAG, "bitmap (" + bitmap.getWidth() + " x " + bitmap.getHeight() + ")");
                    }
                }
            }
        }).start();

        Glide.with(this).load(imageUrl).listener(mRequestListener)
                .into(image1);

        image2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this).load(imageUrl).listener(mRequestListener)
                .fitCenter()
                .into(image2);

        image3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this).load(imageUrl).listener(mRequestListener)
                .centerCrop()
                .into(image3);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "image1(" + image1.getWidth() + " x " + image1.getHeight() + ")");
                Log.d(LOG_TAG, "image2(" + image2.getWidth() + " x " + image2.getHeight() + ")");
                Log.d(LOG_TAG, "image3(" + image3.getWidth() + " x " + image3.getHeight() + ")");
            }
        }, 2000);
    }

    private RequestListener mRequestListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            Log.e(LOG_TAG, "onException() : " + e);
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            Log.i(LOG_TAG, "onResourceReady() >> width : " + resource.getIntrinsicWidth() + ", height : " + resource.getIntrinsicHeight());
            return false;
        }
    };

    public int getPixelFromDip(float dip) {
        Context context = getApplicationContext();
        if (context == null) {
            return 0;
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, dm);
    }

    private Bitmap getBitmap(String url) {
        URL imgUrl;
        HttpURLConnection connection = null;
        InputStream is;
        Bitmap retBitmap = null;
        try {
            imgUrl = new URL(url);
            connection = (HttpURLConnection) imgUrl.openConnection();
            connection.setDoInput(true); //url로 input받는 flag 허용
            connection.connect(); //연결
            is = connection.getInputStream(); // get inputstream
            retBitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            return retBitmap;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
