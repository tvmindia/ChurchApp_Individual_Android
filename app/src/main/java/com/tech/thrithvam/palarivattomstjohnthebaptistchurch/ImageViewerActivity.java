package com.tech.thrithvam.palarivattomstjohnthebaptistchurch;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import uk.co.senab.photoview.PhotoView;

public class ImageViewerActivity extends AppCompatActivity {
    PhotoView photoView;
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageviewer);
        extras=getIntent().getExtras();
        String imageURL=extras.getString("URL");
        photoView=(PhotoView)findViewById(R.id.punchAttachView);

        if (isOnline()){
            Glide.with(ImageViewerActivity.this)
                    .load(getResources().getString(R.string.url) +imageURL.substring((imageURL).indexOf("img")))
                    .thumbnail(0.1f)
                    .crossFade()
                    .dontTransform()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            Toast.makeText(ImageViewerActivity.this,R.string.error_loading_image, Toast.LENGTH_LONG).show();
                            finish();
                            return true;
                        }
                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    }).into(photoView);
        }
        else {
            Toast.makeText(ImageViewerActivity.this,R.string.network_off_alert,Toast.LENGTH_LONG).show();
            Intent noItemsIntent=new Intent(this,NothingToDisplay.class);
            noItemsIntent.putExtra("msg",getResources().getString(R.string.network_off_alert));
            noItemsIntent.putExtra("activityHead","Image");
            startActivity(noItemsIntent);
            finish();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    //for rotating the image
    public void rotateImage(View view){
        photoView.setRotationBy(90f);
    }
}
