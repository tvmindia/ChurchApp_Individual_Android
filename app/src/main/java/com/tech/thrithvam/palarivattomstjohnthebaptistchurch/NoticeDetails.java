package com.tech.thrithvam.palarivattomstjohnthebaptistchurch;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class NoticeDetails extends AppCompatActivity {
    Bundle extras;
    String URL;
    TextView noticeHead, noticeContent;
    ImageView noticeImage;
    Typeface typeQuicksand,typeSegoe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_details);
        extras = getIntent().getExtras();

        URL = extras.getString("URL");
        typeQuicksand = Typeface.createFromAsset(getAssets(), "fonts/quicksandbold.otf");
        typeSegoe = Typeface.createFromAsset(getAssets(),"fonts/segoeui.ttf");
        noticeHead = (TextView) findViewById(R.id.activity_notice_head);
        noticeHead.setTypeface(typeQuicksand);

        noticeContent = (TextView) findViewById(R.id.notice_details);
        noticeContent.setTypeface(typeSegoe);
        noticeImage = (ImageView) findViewById(R.id.notice_img);

        if (getIntent().hasExtra("NoticeName")) {
            noticeHead.setText(extras.getString("NoticeName"));
        }
        if (getIntent().hasExtra("Description")) {
            noticeContent.setText(extras.getString("Description"));
        }
        //image loading using url
        if (!URL.equals("null")) {
            Glide.with(NoticeDetails.this)
                    .load(getResources().getString(R.string.url) + URL.substring((URL).indexOf("img")))
                    .dontTransform()
                    .thumbnail(0.1f)
                    .crossFade()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            noticeImage.setVisibility(View.GONE);
                            return true;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(noticeImage)
            ;
        }
        else {
            noticeImage.setVisibility(View.GONE);
        }
    }

    public void  imageclick (View view){
        Intent intent=new Intent(NoticeDetails.this,ImageViewerActivity.class);
        intent.putExtra("URL",URL);
        startActivity(intent);
    }
}
