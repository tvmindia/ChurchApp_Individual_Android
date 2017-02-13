package com.tech.thrithvam.palarivattomstjohnthebaptistchurch;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ChurchDetailsExpansion extends AppCompatActivity {
    Bundle extras;
    Typeface typeQuicksand;
    Typeface typeSegoe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_church_details_expansion);
        extras=getIntent().getExtras();

        //Fonts------------
        typeQuicksand = Typeface.createFromAsset(getAssets(),"fonts/quicksandbold.otf");
        typeSegoe = Typeface.createFromAsset(getAssets(),"fonts/segoeui.ttf");

        TextView activityHead=(TextView)findViewById(R.id.heading);
        TextView description=(TextView)findViewById(R.id.description);
        ImageView detailImage=(ImageView)findViewById(R.id.detail_image);
        activityHead.setTypeface(typeQuicksand);
        description.setTypeface(typeSegoe);

        //Loading data------------------------
        activityHead.setText(extras.getString("heading"));
        description.setText(extras.getString("description"));
        if(!extras.getString("image").equals("null")){
            Glide.with(ChurchDetailsExpansion.this)
                    .load(getResources().getString(R.string.url) +extras.getString("image").substring((extras.getString("image")).indexOf("img")))
                    .thumbnail(0.1f)
                    .dontTransform()
                    .into(detailImage)
            ;
            detailImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent photoIntent=new Intent(ChurchDetailsExpansion.this,ImageViewerActivity.class);
                    photoIntent.putExtra("URL",extras.getString("image"));
                    startActivity(photoIntent);
                }
            });
        }
        else {
            detailImage.setVisibility(View.GONE);
        }
    }
}
