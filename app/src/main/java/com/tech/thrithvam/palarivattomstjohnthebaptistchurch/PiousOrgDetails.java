package com.tech.thrithvam.palarivattomstjohnthebaptistchurch;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PiousOrgDetails extends AppCompatActivity {
    Bundle extras;
    String URL;
    TextView activityHead,P_Name,about,history_label;
    ImageView patronImage;
    Typeface typeQuicksand;
    Typeface typeSegoe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pious_org_details);
        extras=getIntent().getExtras();

        URL=extras.getString("URL");

        typeQuicksand = Typeface.createFromAsset(getAssets(),"fonts/quicksandbold.otf");
        typeSegoe = Typeface.createFromAsset(getAssets(),"fonts/segoeui.ttf");

        activityHead=(TextView)findViewById(R.id.activity_head);
        P_Name=(TextView)findViewById(R.id.patron_name);
        history_label=(TextView)findViewById(R.id.history_label);
        about=(TextView)findViewById(R.id.history_details);
        patronImage =(ImageView)findViewById(R.id.patron_img);
        activityHead.setTypeface(typeQuicksand);
        P_Name.setTypeface(typeSegoe);
        history_label.setTypeface(typeQuicksand);
        about.setTypeface(typeSegoe);


        if(getIntent().hasExtra("Name")){
            activityHead.setText(extras.getString("Name"));
        }
        if(getIntent().hasExtra("PatronName")){
            P_Name.setText(extras.getString("PatronName"));
        }
        if(getIntent().hasExtra("Desc")){
            about.setText(extras.getString("Desc"));
        }
        //image loading using url
        if(!URL.equals("null")){
            Glide.with(PiousOrgDetails.this)
                    .load(getResources().getString(R.string.url) +URL.substring((URL).indexOf("img")))
                    .thumbnail(0.1f)
                    .crossFade()
                    .dontTransform()
                    .into(patronImage)
            ;
        }
        else {
            patronImage.setVisibility(View.GONE);
        }
    }
}
