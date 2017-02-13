package com.tech.thrithvam.palarivattomstjohnthebaptistchurch;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EventDetails extends AppCompatActivity {
    Bundle extras;
    String URL;
    TextView Date, eventsHead, eventsContent,setReminder;
    ImageView eventsImage;
    Typeface typeQuicksand,typeSegoe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        extras=getIntent().getExtras();

        URL=extras.getString("URL");
        typeQuicksand = Typeface.createFromAsset(getAssets(),"fonts/quicksandbold.otf");
        typeSegoe = Typeface.createFromAsset(getAssets(),"fonts/segoeui.ttf");
        eventsHead =(TextView)findViewById(R.id.activity_event_head);
        eventsHead.setTypeface(typeQuicksand);
        Date=(TextView)findViewById(R.id.event_date);
        eventsContent =(TextView)findViewById(R.id.event_details);
        eventsImage =(ImageView)findViewById(R.id.event_img);
        setReminder=(TextView)findViewById(R.id.set_reminder);
        setReminder.setTypeface(typeSegoe);
        eventsContent.setTypeface(typeSegoe);


        if(getIntent().hasExtra("EventName")){
            eventsHead.setText(extras.getString("EventName"));
        }
        if(getIntent().hasExtra("StartDateInMillis")){
            final Calendar cal=Calendar.getInstance();
            SimpleDateFormat formatted = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
            cal.setTimeInMillis(Long.parseLong(extras.getString("StartDateInMillis")));
            cal.set(Calendar.HOUR,5);
            String startDate=formatted.format(cal.getTime());
            Date.setText(startDate);
            Date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setType("vnd.android.cursor.item/event");
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
                    intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
//                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis()+60*60*1000);
                    intent.putExtra(CalendarContract.Events.TITLE,extras.getString("EventName"));
                    intent.putExtra(CalendarContract.Events.DESCRIPTION,"Reminder set from goChurch app");
                    if(getIntent().hasExtra("ChurchName")){
                        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,extras.getString("ChurchName"));
                    }
                    intent.putExtra(CalendarContract.Events.HAS_ALARM,true);
                    startActivity(intent);
                }
            });
            setReminder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setType("vnd.android.cursor.item/event");
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
                    intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
//                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis()+60*60*1000);
                    intent.putExtra(CalendarContract.Events.TITLE,extras.getString("EventName"));
                    intent.putExtra(CalendarContract.Events.DESCRIPTION,"Reminder set from goChurch app");
                    if(getIntent().hasExtra("ChurchName")){
                        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,extras.getString("ChurchName"));
                    }
                    intent.putExtra(CalendarContract.Events.HAS_ALARM,true);
                    startActivity(intent);
                }
            });
        }
        if(getIntent().hasExtra("Description")){
            eventsContent.setText(extras.getString("Description"));
        }
        //image loading using url
        if(!URL.equals("null")){
            Glide.with(EventDetails.this)
                    .load(getResources().getString(R.string.url) +URL.substring((URL).indexOf("img")))
                    .dontTransform()
                    .thumbnail(0.1f)
                    .into(eventsImage)
            ;
            eventsImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent photoIntent=new Intent(EventDetails.this,ImageViewerActivity.class);
                    photoIntent.putExtra("URL",URL);
                    startActivity(photoIntent);
                }
            });
        }
        else {
            eventsImage.setVisibility(View.GONE);
        }

    }
}
