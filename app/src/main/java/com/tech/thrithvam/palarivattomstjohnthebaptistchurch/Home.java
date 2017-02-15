package com.tech.thrithvam.palarivattomstjohnthebaptistchurch;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

public class Home extends AppCompatActivity {
    AsyncTask getTowns=null;
    View popupView;
    Constants constants=new Constants();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseMessaging.getInstance().subscribeToTopic(constants.ChurchID);
        //Fonts--------------
        Typeface typeCopperplateGothic = Typeface.createFromAsset(getAssets(),"fonts/copperplate-gothic.ttf");
        Typeface typeoldeng = Typeface.createFromAsset(getAssets(),"fonts/oldeng.ttf");
        Typeface typeBLKCHCRY= Typeface.createFromAsset(getAssets(),"fonts/blackchancery.ttf");
        TextView appName=(TextView)findViewById(R.id.app_name);

        TextView timing=(TextView)findViewById(R.id.timings);
        TextView novenas=(TextView)findViewById(R.id.novenas);
        TextView notices=(TextView)findViewById(R.id.notices);
        TextView gallery=(TextView)findViewById(R.id.gallery);
        TextView priests=(TextView)findViewById(R.id.priests);
        TextView map=(TextView)findViewById(R.id.map);
        TextView more=(TextView)findViewById(R.id.more);
        TextView search=(TextView)findViewById(R.id.search);

        timing.setTypeface(typeCopperplateGothic);
        novenas.setTypeface(typeCopperplateGothic);
        notices.setTypeface(typeCopperplateGothic);
        gallery.setTypeface(typeCopperplateGothic);
        priests.setTypeface(typeCopperplateGothic);
        map.setTypeface(typeCopperplateGothic);
        more.setTypeface(typeCopperplateGothic);
        search.setTypeface(typeCopperplateGothic);

        appName.setTypeface(typeBLKCHCRY);
       // searchLabel.setTypeface(typeCopperplateGothic);
        //Information button-----------
        ImageView info=(ImageView) findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupView = getLayoutInflater().inflate(R.layout.item_app_info, null);
                final TextView email=(TextView)popupView.findViewById(R.id.email);
                email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("*/*");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email.getText().toString()});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Mail from Church App User");
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }
                });
                TextView shareApp=(TextView)popupView.findViewById(R.id.share_app);
                shareApp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.setType("text/*");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "Here is an innovative app to stay connected with Churches & Novenas in town \nhttps://goo.gl/jDMq6M");
                        startActivity(Intent.createChooser(shareIntent, "Share app"));
                    }
                });
                new AlertDialog.Builder(Home.this).setIcon(android.R.drawable.ic_dialog_alert)//.setTitle("")
                        .setView(popupView)
                        .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).setCancelable(false).show();
            }
        });
    }
    public void timings(View view){
        Intent intent=new Intent(Home.this,MyChurchDetails.class);
        intent.putExtra("from","timings");
        startActivity(intent);
    }
    public void novenas(View view){
        Intent intent=new Intent(Home.this,NovenaDetailsList.class);
        intent.putExtra("churchID",constants.ChurchID);
        startActivity(intent);
    }
    public void notices(View view){
        Intent intent=new Intent(Home.this,Notices.class);
        intent.putExtra("churchID",constants.ChurchID);
        startActivity(intent);
    }
    public void gallery(View view){
        Intent intent=new Intent(Home.this,Gallery.class);
        intent.putExtra("ChurchID",constants.ChurchID);
        startActivity(intent);
    }
    public void priests(View view){
        Intent intent=new Intent(Home.this,PriestDetails.class);
        intent.putExtra("ChurchID",constants.ChurchID);
        startActivity(intent);
    }
    public void map(View view){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + constants.Location+"("+constants.ChurchName+")"));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void more(View view){
        Intent intent=new Intent(Home.this,ChurchDetails.class);
        intent.putExtra("churchID",constants.ChurchID);
        startActivity(intent);
    }
    public void search(View view){
        Intent intent=new Intent(Home.this,SearchResults.class);
        startActivity(intent);
    }
    public void notification(View view){
        Intent intent=new Intent(Home.this,Notifications.class);
        startActivity(intent);
    }
    public boolean isOnline() {
        ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getTowns!=null)getTowns.cancel(true);
    }
}
