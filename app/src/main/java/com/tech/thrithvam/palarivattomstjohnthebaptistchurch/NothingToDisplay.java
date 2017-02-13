package com.tech.thrithvam.palarivattomstjohnthebaptistchurch;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NothingToDisplay extends AppCompatActivity {
Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nothing_to_display);
        extras=getIntent().getExtras();
        //Fonts---------------
        TextView activityHead=(TextView)findViewById(R.id.activity_head);
        Typeface typeSegoe = Typeface.createFromAsset(getAssets(),"fonts/segoeui.ttf");
        Typeface typeQuicksand = Typeface.createFromAsset(getAssets(),"fonts/quicksandbold.otf");
        activityHead.setTypeface(typeQuicksand);
        final TextView nothingLabel=(TextView)findViewById(R.id.nothing_label);
        nothingLabel.setTypeface(typeSegoe);
        if(getIntent().hasExtra("activityHead")){
            activityHead.setText(extras.getString("activityHead"));
        }
        if(getIntent().hasExtra("msg")){
            nothingLabel.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    nothingLabel.setText(extras.getString("msg"));
                    return true;
                }
            });
        }
    }
    public void back(View view)
    {
    onBackPressed();
    }
}
