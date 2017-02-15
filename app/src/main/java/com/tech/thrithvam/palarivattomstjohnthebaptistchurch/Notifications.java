package com.tech.thrithvam.palarivattomstjohnthebaptistchurch;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {
    Typeface typeQuicksand;
    TextView activity_head;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        typeQuicksand = Typeface.createFromAsset(getAssets(),"fonts/quicksandbold.otf");
        activity_head =(TextView)findViewById(R.id.activity_head);
        activity_head.setTypeface(typeQuicksand);
        DatabaseHandler db=DatabaseHandler.getInstance(this);
        ArrayList<String[]> notifications=db.GetNotifications();
        if(notifications.size()==0)
        {
            Intent noItemsIntent=new Intent(Notifications.this,NothingToDisplay.class);
            noItemsIntent.putExtra("msg",R.string.no_items_to_display);
            noItemsIntent.putExtra("activityHead","Notifications");
            startActivity(noItemsIntent);
            finish();
        }
        CustomAdapter adapter=new CustomAdapter(Notifications.this, notifications,"Notifications");
        ListView notificationList=(ListView) findViewById(R.id.notification_list);
        notificationList.setAdapter(adapter);
    }
}
