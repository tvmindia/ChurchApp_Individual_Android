package com.tech.thrithvam.palarivattomstjohnthebaptistchurch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.clans.fab.FloatingActionMenu;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChurchDetails extends AppCompatActivity {
    Bundle extras;
    String ChurchID,aboutGlobal,churchImageGlobal;
    AsyncTask getChurchDetails=null,getMassTimings=null,getExtraDetails=null;
    Typeface typeQuicksand,typeSegoe,typeBLKCHCRY;
    RelativeLayout viewMap;
    ImageView churchImage,priestImage;
    ScrollView activityScrollView;
    RelativeLayout priestLayout,aboutLayout,contactLayout,massLayout;
    TextView churchName,town,address,about,priestName,parishName,priestMobile;
    TextView priestAbout,dateOrdination,churchAddress,phone1,phone2, priestViewMore;
    TextView sundayLabel,mondayLabel,tuesdayLabel,wednesdayLabel,thursdayLabel,fridayLabel,saturdayLabel;
    TextView sundayTiming,mondayTiming,tuesdayTiming,wednesdayTiming,thursdayTiming,fridayTiming,saturdayTiming;
    TextView aboutLabel,priestLabel,massLabel,contactLabel;
    LinearLayout extraDetails;
    FloatingActionMenu floatingActionMenu;
    String churchNameStringGlobal,townNameStringGlobal,addressStringGlobal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_church_details);
        extras=getIntent().getExtras();
        ChurchID=extras.getString("churchID");
        if (isOnline()) {
            getChurchDetails=new GetChurchDetails().execute();
        } else {
            Toast.makeText(ChurchDetails.this, R.string.network_off_alert, Toast.LENGTH_LONG).show();
        }
        //Layouts-----------------------------------
        floatingActionMenu=(FloatingActionMenu)findViewById(R.id.material_design_android_floating_action_menu);
        priestLayout=(RelativeLayout)findViewById(R.id.priestLayout);
        aboutLayout=(RelativeLayout)findViewById(R.id.aboutLayout);
        contactLayout=(RelativeLayout)findViewById(R.id.contactLayout);
        massLayout=(RelativeLayout) findViewById(R.id.massLayout);
        extraDetails=(LinearLayout) findViewById(R.id.extra_items);

        priestLayout.setVisibility(View.INVISIBLE);
        contactLayout.setVisibility(View.INVISIBLE);
        massLayout.setVisibility(View.GONE);
        extraDetails.setVisibility(View.GONE);
        //fonts-------------------------------
        activityScrollView=(ScrollView)findViewById(R.id.activity_scrollview);
        activityScrollView.setVisibility(View.GONE);

        Typeface typeQuicksand = Typeface.createFromAsset(getAssets(),"fonts/quicksandbold.otf");
        typeSegoe = Typeface.createFromAsset(getAssets(),"fonts/segoeui.ttf");
        typeBLKCHCRY = Typeface.createFromAsset(getAssets(),"fonts/blackchancery.ttf");

        churchName=(TextView)findViewById(R.id.activity_head);
        town=(TextView)findViewById(R.id.town);
        address=(TextView)findViewById(R.id.address);
        aboutLabel=(TextView)findViewById(R.id.about_label);
        about=(TextView)findViewById(R.id.about);
        priestLabel=(TextView)findViewById(R.id.priest_label);
        priestName=(TextView)findViewById(R.id.priest_name);
        parishName=(TextView)findViewById(R.id.parish);
        priestMobile=(TextView)findViewById(R.id.priest_mobile);
        priestAbout=(TextView)findViewById(R.id.priest_about);
        dateOrdination=(TextView)findViewById(R.id.date_of_ordination);
        churchImage=(ImageView)findViewById(R.id.church_image);
        priestImage=(ImageView)findViewById(R.id.priest_image);
        priestViewMore =(TextView)findViewById(R.id.priest_viewmore);
        contactLabel=(TextView)findViewById(R.id.contact_label);
        churchAddress=(TextView)findViewById(R.id.church_address);
        phone1=(TextView)findViewById(R.id.phone1);
        phone2=(TextView)findViewById(R.id.phone2);
        viewMap=(RelativeLayout)findViewById(R.id.view_in_map);
        massLabel=(TextView)findViewById(R.id.mass_label);
        sundayLabel=(TextView)findViewById(R.id.sunday_label);
        mondayLabel=(TextView)findViewById(R.id.monday_label);
        tuesdayLabel=(TextView)findViewById(R.id.tuesday_label);
        wednesdayLabel=(TextView)findViewById(R.id.wednesday_label);
        thursdayLabel=(TextView)findViewById(R.id.thursday_label);
        fridayLabel=(TextView)findViewById(R.id.friday_label);
        saturdayLabel=(TextView)findViewById(R.id.saturday_label);
        sundayTiming=(TextView)findViewById(R.id.sunday_timings);
        mondayTiming=(TextView)findViewById(R.id.monday_timings);
        tuesdayTiming=(TextView)findViewById(R.id.tuesday_timings);
        wednesdayTiming=(TextView)findViewById(R.id.wednesday_timings);
        thursdayTiming=(TextView)findViewById(R.id.thursday_timings);
        fridayTiming=(TextView)findViewById(R.id.friday_timings);
        saturdayTiming=(TextView)findViewById(R.id.saturday_timings);


        churchName.setTypeface(typeBLKCHCRY);
        town.setTypeface(typeQuicksand);
        address.setTypeface(typeSegoe);
        aboutLabel.setTypeface(typeQuicksand);
        about.setTypeface(typeSegoe);
        priestLabel.setTypeface(typeQuicksand);
        priestName.setTypeface(typeSegoe);
        parishName.setTypeface(typeSegoe);
        priestMobile.setTypeface(typeSegoe);
        priestAbout.setTypeface(typeSegoe);
        dateOrdination.setTypeface(typeSegoe);
        contactLabel.setTypeface(typeQuicksand);
        churchAddress.setTypeface(typeSegoe);
        phone1.setTypeface(typeSegoe);
        phone2.setTypeface(typeSegoe);
        massLabel.setTypeface(typeQuicksand);
        sundayLabel.setTypeface(typeSegoe);
        mondayLabel.setTypeface(typeSegoe);
        tuesdayLabel.setTypeface(typeSegoe);
        wednesdayLabel.setTypeface(typeSegoe);
        thursdayLabel.setTypeface(typeSegoe);
        fridayLabel.setTypeface(typeSegoe);
        saturdayLabel.setTypeface(typeSegoe);
        sundayTiming.setTypeface(typeSegoe);
        mondayTiming.setTypeface(typeSegoe);
        tuesdayTiming.setTypeface(typeSegoe);
        wednesdayTiming.setTypeface(typeSegoe);
        thursdayTiming.setTypeface(typeSegoe);
        fridayTiming.setTypeface(typeSegoe);
        saturdayTiming.setTypeface(typeSegoe);

        priestViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChurchDetails.this,PriestDetails.class);
                intent.putExtra("ChurchID",ChurchID);
                startActivity(intent);
            }
        });


        //getting details from intent if available-----------------
        if(getIntent().hasExtra("churchname")){
            churchName.setText(extras.getString("churchname"));
        }
        if(getIntent().hasExtra("address")){
            address.setText(extras.getString("address"));
        }
        else {
            address.setVisibility(View.INVISIBLE);
        }
        if(getIntent().hasExtra("town")){
            town.setText(extras.getString("town"));
        }
        else {
            town.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (floatingActionMenu.isOpened()) {
                Rect outRect = new Rect();
                floatingActionMenu.getGlobalVisibleRect(outRect);
                if(!outRect.contains((int)event.getRawX(), (int)event.getRawY()))
                    floatingActionMenu.close(true);
            }
        }
        return super.dispatchTouchEvent(event);
    }
    //-----------------------------Async Tasks----------------------------------------
    public class GetChurchDetails extends AsyncTask<Void , Void, Void> {
        int status;StringBuilder sb;
        String strJson, postData;
        JSONArray jsonArray;
        String msg;
        boolean pass=false;
        AVLoadingIndicatorView loadingIndicator =(AVLoadingIndicatorView)findViewById(R.id.itemsLoading);
        String aboutString,mapCoOrdinatesString,phone1String,phone2String, townCodeString,imageURLString,priestNameString,priestAboutString,parishString,priestMobileString,dateOrdinationString,priestURLStringString;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingIndicator.setVisibility(View.VISIBLE);
            //----------encrypting ---------------------------
            // usernameString=cryptography.Encrypt(usernameString);
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            String url =getResources().getString(R.string.url) + "WebServices/WebService.asmx/ChurchDetailsByID";
            HttpURLConnection c = null;
            try {
                postData =  "{\"ChurchID\":\"" + ChurchID+ "\"}";
                URL u = new URL(url);
                c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("POST");
                c.setRequestProperty("Content-type", "application/json; charset=utf-16");
                c.setRequestProperty("Content-length", Integer.toString(postData.length()));
                c.setDoInput(true);
                c.setDoOutput(true);
                c.setUseCaches(false);
                c.setConnectTimeout(10000);
                c.setReadTimeout(10000);
                DataOutputStream wr = new DataOutputStream(c.getOutputStream());
                wr.writeBytes(postData);
                wr.flush();
                wr.close();
                status = c.getResponseCode();
                switch (status) {
                    case 200:
                    case 201: BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                        sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                        br.close();
                        int a=sb.indexOf("[");
                        int b=sb.lastIndexOf("]");
                        strJson=sb.substring(a, b + 1);
                        //   strJson=cryptography.Decrypt(strJson);
                        strJson="{\"JSON\":" + strJson.replace("\\\"","\"").replace("\\\\","\\") + "}";
                }
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                msg=ex.getMessage();
            } finally {
                if (c != null) {
                    try {
                        c.disconnect();
                    } catch (Exception ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                        msg=ex.getMessage();
                    }
                }
            }
            if(strJson!=null)
            {try {
                JSONObject jsonRootObject = new JSONObject(strJson);
                jsonArray = jsonRootObject.optJSONArray("JSON");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    msg=jsonObject.optString("Message");
                    pass=jsonObject.optBoolean("Flag",true);
                    churchNameStringGlobal=jsonObject.optString("ChurchName");
                    aboutString=jsonObject.optString("About");
                    townNameStringGlobal=jsonObject.optString("TownName");
                    addressStringGlobal=jsonObject.optString("Address");
                    mapCoOrdinatesString=jsonObject.optString("Latitude","null")+","+jsonObject.optString("Longitude","null");
                    phone1String=jsonObject.optString("Phone1");
                    phone2String=jsonObject.optString("Phone2");
                    townCodeString=jsonObject.optString("TownCode");
                    imageURLString=jsonObject.optString("ImageURL");
                    priestNameString=jsonObject.optString("PriestName");
                    priestAboutString=jsonObject.optString("PriestAbout");
                    parishString=jsonObject.optString("Parish");
                    priestMobileString=jsonObject.optString("PriestMobile");
                    dateOrdinationString=jsonObject.optString("DateOrdination").replace("/Date(", "").replace(")/", "");
                    priestURLStringString=jsonObject.optString("PriestURL");
                }
            } catch (Exception ex) {
                msg=ex.getMessage();
            }}
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            loadingIndicator.setVisibility(View.GONE);
            if(!pass) {
                Intent noItemsIntent=new Intent(ChurchDetails.this,NothingToDisplay.class);
                noItemsIntent.putExtra("msg",msg);
                noItemsIntent.putExtra("activityHead","Church");
                startActivity(noItemsIntent);
                finish();
            }
            else {
                activityScrollView.setVisibility(View.VISIBLE);
                if(!churchNameStringGlobal.equals("null")){
                    churchName.setText(churchNameStringGlobal);
                    churchName.setVisibility(View.VISIBLE);
                }
                else {
                    churchName.setText("-");
                }

                if(!aboutString.equals("null")){
                    about.setText(aboutString);
                    about.setVisibility(View.VISIBLE);
                    aboutGlobal=aboutString;
                }
                else {
                    about.setText("-");
                    aboutGlobal="-";
                }

                if(!townNameStringGlobal.equals("null")){
                    town.setText(townNameStringGlobal);
                    town.setVisibility(View.VISIBLE);
                }
                else {
                    town.setText("-");
                }

                if(!addressStringGlobal.equals("null")){
                    address.setText(addressStringGlobal);
                    address.setVisibility(View.VISIBLE);
                    churchAddress.setText(addressStringGlobal);
                }
                else {
                    address.setText("-");
                    churchAddress.setText("-");
                }

                //Church Image----
                if(!imageURLString.equals("null")){
                    Glide.with(ChurchDetails.this)
                            .load(getResources().getString(R.string.url) +imageURLString.substring((imageURLString).indexOf("img")))
                            .placeholder(R.drawable.my_church_sample)
                            .thumbnail(0.1f)
                            .crossFade()
                            .listener(new RequestListener<String, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    churchImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    Glide.with(ChurchDetails.this)
                                            .load(R.drawable.my_church_sample)
                                            .into(churchImage)
                                    ;
                                    return true;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    return false;
                                }
                            })
                            .into(churchImage)
                    ;
                    churchImageGlobal=imageURLString;
                }
                else {
                    churchImageGlobal="null";
                }

                //Priest-----------
                if(!priestNameString.equals("null")){
                    priestName.setText(priestNameString);
                }
                else {
                    priestName.setText("-");
                }

                if(!priestAboutString.equals("null")){
                    priestAbout.setText(priestAboutString);
                }
                else {
                    priestAbout.setText("-");
                }

                if(!parishString.equals("null")){
                    parishName.setText(parishString);
                }
                else {
                    parishName.setText("-");
                }

                if(!priestMobileString.equals("null")){
                    priestMobile.setText(priestMobileString);
                    priestMobile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {                                   //Phone call function
                            Uri number = Uri.parse("tel:" + priestMobileString);
                            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                            startActivity(callIntent);
                        }
                    });
                }
                else {
                    priestMobile.setText("-");
                }

                if(!dateOrdinationString.equals("null")){
                    SimpleDateFormat formatted = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
                    Calendar cal= Calendar.getInstance();
                    cal.setTimeInMillis(Long.parseLong(dateOrdinationString));
                    dateOrdination.setText(formatted.format(cal.getTime()));
                }
                else {
                    dateOrdination.setText("-");
                }

                if(!priestURLStringString.equals("null")){
                    Glide.with(ChurchDetails.this)
                            .load(getResources().getString(R.string.url) +priestURLStringString.substring((priestURLStringString).indexOf("img")))
                            .thumbnail(0.1f)
                            .crossFade()
                            .listener(new RequestListener<String, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    Glide.with(ChurchDetails.this)
                                            .load(R.drawable.priest)
                                            .into(priestImage)
                                    ;
                                    return true;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    return false;
                                }
                            })
                            .dontTransform()
                            .into(priestImage)
                    ;
                }
                //Contact---------
                if(!phone1String.equals("null")){
                    phone1.setText(phone1String);
                    phone1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {                                   //Phone call function
                            Uri number = Uri.parse("tel:" + phone1String);
                            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                            startActivity(callIntent);
                        }
                    });
                }
                else {
                    phone1.setText("-");
                }

                if(!phone2String.equals("null")){
                    phone2.setText(phone2String);
                    phone2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {                                   //Phone call function
                            Uri number = Uri.parse("tel:" + phone2String);
                            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                            startActivity(callIntent);
                        }
                    });
                }
                else {
                    phone2.setVisibility(View.GONE);
                }

                if(!mapCoOrdinatesString.equals("null,null")){
                    viewMap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + (mapCoOrdinatesString)+"("+churchName.getText().toString()+")"));
                                startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else {
                    viewMap.setVisibility(View.GONE);
                }
                //Animation-----------------------------------------------------
                final Animation fromBottom = AnimationUtils.loadAnimation(ChurchDetails.this, R.anim.fade_in_from_bottom);
                final Animation fromBottom2 = AnimationUtils.loadAnimation(ChurchDetails.this, R.anim.fade_in_from_bottom);
                final Animation fromBottom3 = AnimationUtils.loadAnimation(ChurchDetails.this, R.anim.fade_in_from_bottom);

                Handler handler = new Handler();
                aboutLayout.startAnimation(fromBottom);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        priestLayout.setVisibility(View.VISIBLE);
                        priestLayout.startAnimation(fromBottom2);
                    }
                },500);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        contactLayout.setVisibility(View.VISIBLE);
                        contactLayout.startAnimation(fromBottom3);
                    }
                },1000);

                getMassTimings=new GetMassTimings().execute();
            }
        }
    }
    public class GetMassTimings extends AsyncTask<Void , Void, Void> {
        int status;StringBuilder sb;
        String strJson, postData;
        JSONArray jsonArray;
        String msg;
        boolean pass=false;
        ArrayList<String> sunday=new ArrayList<>();
        ArrayList<String> monday=new ArrayList<>();
        ArrayList<String> tuesday=new ArrayList<>();
        ArrayList<String> wednesday=new ArrayList<>();
        ArrayList<String> thursday =new ArrayList<>();
        ArrayList<String> friday=new ArrayList<>();
        ArrayList<String> saturday=new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //----------encrypting ---------------------------
            // usernameString=cryptography.Encrypt(usernameString);
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            String url =getResources().getString(R.string.url) + "WebServices/WebService.asmx/GetMassTimings";
            HttpURLConnection c = null;
            try {
                postData =  "{\"ChurchID\":\"" + ChurchID+ "\"}";
                URL u = new URL(url);
                c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("POST");
                c.setRequestProperty("Content-type", "application/json; charset=utf-16");
                c.setRequestProperty("Content-length", Integer.toString(postData.length()));
                c.setDoInput(true);
                c.setDoOutput(true);
                c.setUseCaches(false);
                c.setConnectTimeout(10000);
                c.setReadTimeout(10000);
                DataOutputStream wr = new DataOutputStream(c.getOutputStream());
                wr.writeBytes(postData);
                wr.flush();
                wr.close();
                status = c.getResponseCode();
                switch (status) {
                    case 200:
                    case 201: BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                        sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                        br.close();
                        int a=sb.indexOf("[");
                        int b=sb.lastIndexOf("]");
                        strJson=sb.substring(a, b + 1);
                        //   strJson=cryptography.Decrypt(strJson);
                        strJson="{\"JSON\":" + strJson.replace("\\\"","\"").replace("\\\\","\\") + "}";
                }
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                msg=ex.getMessage();
            } finally {
                if (c != null) {
                    try {
                        c.disconnect();
                    } catch (Exception ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                        msg=ex.getMessage();
                    }
                }
            }
            if(strJson!=null)
            {try {
                JSONObject jsonRootObject = new JSONObject(strJson);
                jsonArray = jsonRootObject.optJSONArray("JSON");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    msg=jsonObject.optString("Message");
                    pass=jsonObject.optBoolean("Flag",true);
                    switch (jsonObject.optString("Day")){
                        case "Sun":
                                sunday.add(jsonObject.optString("FormattedTime"));
                            break;
                        case "Mon":
                                monday.add(jsonObject.optString("FormattedTime"));
                            break;
                        case "Tue":
                                tuesday.add(jsonObject.optString("FormattedTime"));
                            break;
                        case "Wed":
                                wednesday.add(jsonObject.optString("FormattedTime"));
                            break;
                        case "Thu":
                                thursday.add(jsonObject.optString("FormattedTime"));
                            break;
                        case "Fri":
                                friday.add(jsonObject.optString("FormattedTime"));
                            break;
                        case "Sat":
                                saturday.add(jsonObject.optString("FormattedTime"));
                            break;
                        default:
                    }
                }
            } catch (Exception ex) {
                msg=ex.getMessage();
            }}
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(!pass) {
                massLayout.setVisibility(View.GONE);
            }
            else {

                setTimingsToTextViews(sunday,sundayTiming,sundayLabel);
                setTimingsToTextViews(monday,mondayTiming,mondayLabel);
                setTimingsToTextViews(tuesday,tuesdayTiming,tuesdayLabel);
                setTimingsToTextViews(wednesday,wednesdayTiming,wednesdayLabel);
                setTimingsToTextViews(thursday,thursdayTiming,thursdayLabel);
                setTimingsToTextViews(friday,fridayTiming,fridayLabel);
                setTimingsToTextViews(saturday,saturdayTiming,saturdayLabel);


                final Animation fromBottom = AnimationUtils.loadAnimation(ChurchDetails.this, R.anim.fade_in_from_bottom);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        massLayout.setVisibility(View.VISIBLE);
                        massLayout.startAnimation(fromBottom);
                    }
                },1000);
            }
            getExtraDetails=new GetExtraDetails().execute();
        }
        private void setTimingsToTextViews(ArrayList<String> timings, TextView timingsDisplay, TextView dayLabel){
            //To set mass timings to corresponding text views
            String dayTimings="";
            for(int i=0;i<timings.size();i++) {
                if (i==0)//to avoid first comma
                {
                    dayTimings = timings.get(0);
                }
                else
                {
                    dayTimings = dayTimings+", "+timings.get(i);
                }
            }
            if(!dayTimings.equals(""))
                timingsDisplay.setText(dayTimings);
            else {
                dayLabel.setVisibility(View.GONE);
                timingsDisplay.setVisibility(View.GONE);
            }
        }
    }
    public class GetExtraDetails extends AsyncTask<Void , Void, Void> {
        int status;StringBuilder sb;
        String strJson, postData;
        JSONArray jsonArray;
        String msg;
        boolean pass=false;
        ArrayList<String[]> extraDetailsArrayList=new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //----------encrypting ---------------------------
            // usernameString=cryptography.Encrypt(usernameString);
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            String url =getResources().getString(R.string.url) + "WebServices/WebService.asmx/GetChurchExtraDetails";
            HttpURLConnection c = null;
            try {
                postData =  "{\"ChurchID\":\"" + ChurchID+ "\"}";
                URL u = new URL(url);
                c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("POST");
                c.setRequestProperty("Content-type", "application/json; charset=utf-16");
                c.setRequestProperty("Content-length", Integer.toString(postData.length()));
                c.setDoInput(true);
                c.setDoOutput(true);
                c.setUseCaches(false);
                c.setConnectTimeout(10000);
                c.setReadTimeout(10000);
                DataOutputStream wr = new DataOutputStream(c.getOutputStream());
                wr.writeBytes(postData);
                wr.flush();
                wr.close();
                status = c.getResponseCode();
                switch (status) {
                    case 200:
                    case 201: BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                        sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                        br.close();
                        int a=sb.indexOf("[");
                        int b=sb.lastIndexOf("]");
                        strJson=sb.substring(a, b + 1);
                        //   strJson=cryptography.Decrypt(strJson);
                        strJson="{\"JSON\":" + strJson.replace("\\\"","\"").replace("\\\\","\\") + "}";
                }
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                msg=ex.getMessage();
            } finally {
                if (c != null) {
                    try {
                        c.disconnect();
                    } catch (Exception ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                        msg=ex.getMessage();
                    }
                }
            }
            if(strJson!=null)
            {try {
                JSONObject jsonRootObject = new JSONObject(strJson);
                jsonArray = jsonRootObject.optJSONArray("JSON");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    msg=jsonObject.optString("Message");
                    pass=jsonObject.optBoolean("Flag",true);
                    String[] data=new String[4];
                    data[0]=jsonObject.optString("ID");
                    data[1]=jsonObject.optString("Caption");
                    data[2]=jsonObject.optString("Description");
                    data[3]=jsonObject.optString("URL");
                    extraDetailsArrayList.add(data);
                }
            } catch (Exception ex) {
                msg=ex.getMessage();
            }}
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(!pass) {
                extraDetails.setVisibility(View.GONE);
            }
            else {
               extraDetails.setVisibility(View.VISIBLE);
               for (int i=0;i<extraDetailsArrayList.size();i++){//Adding each extra item card
                   final View extraItemCard=getLayoutInflater().inflate(R.layout.item_church_extra_detail, null);
                   TextView title=(TextView)extraItemCard.findViewById(R.id.extra_detail_label);
                   ImageView image=(ImageView)extraItemCard.findViewById(R.id.detail_image);
                   TextView description=(TextView)extraItemCard.findViewById(R.id.extra_details);
                   TextView view_more_extra_detail=(TextView)extraItemCard.findViewById(R.id.view_more_extra_detail);
                   title.setTypeface(typeQuicksand);
                   description.setTypeface(typeSegoe);

                   view_more_extra_detail.setTag(extraDetailsArrayList.get(i));
                   title.setText(extraDetailsArrayList.get(i)[1]);
                   description.setText(extraDetailsArrayList.get(i)[2]);
                   if(!extraDetailsArrayList.get(i)[3].equals("null")){
                           Glide.with(ChurchDetails.this)
                                   .load(getResources().getString(R.string.url) +extraDetailsArrayList.get(i)[3].substring((extraDetailsArrayList.get(i)[3]).indexOf("img")))
                                   .placeholder(R.drawable.church)
                                   .thumbnail(0.1f)
                                   .into(image)
                           ;
                   }
                   else {
                       image.setVisibility(View.GONE);
                   }
                   extraDetails.addView(extraItemCard);
                   final Animation fromBottom = AnimationUtils.loadAnimation(ChurchDetails.this, R.anim.fade_in_from_bottom);
                   Handler handler = new Handler();
                   handler.postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           extraItemCard.setVisibility(View.VISIBLE);
                           extraItemCard.startAnimation(fromBottom);
                       }
                   },(i+1)*1000);//animation with delay for each
               }
            }
        }
    }
    public boolean isOnline() {
        ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getChurchDetails!=null)getChurchDetails.cancel(true);
        if(getMassTimings!=null)getMassTimings.cancel(true);
        if(getExtraDetails!=null)getExtraDetails.cancel(true);
    }

    public void pious_org_click (View view){
        Intent intent=new Intent(ChurchDetails.this,PiousOrgs.class);
        intent.putExtra("ChurchID",ChurchID);
        floatingActionMenu.close(true);
        startActivity(intent);
    }
    public void institutions_click (View view){
        Intent intent=new Intent(ChurchDetails.this,Institutions.class);
        intent.putExtra("ChurchID",ChurchID);
        floatingActionMenu.close(true);
        startActivity(intent);
    }
    public void gallery_click (View view){
        Intent intent=new Intent(ChurchDetails.this, Gallery.class);
        intent.putExtra("ChurchID",ChurchID);
        floatingActionMenu.close(true);
        startActivity(intent);
    }
    public void events_click (View view){
        Intent intent=new Intent(ChurchDetails.this,Events.class);
        intent.putExtra("ChurchID",ChurchID);
        intent.putExtra("ChurchName",churchNameStringGlobal);
        floatingActionMenu.close(true);
        startActivity(intent);
    }
    public void novenas_click (View view){
        Intent intent=new Intent(ChurchDetails.this,NovenaDetailsList.class);
        intent.putExtra("churchID",ChurchID);
        intent.putExtra("churchName",churchName.getText().toString());
        intent.putExtra("from","church");
        floatingActionMenu.close(true);
        startActivity(intent);
    }
    public void family_units_click (View view){
        Intent intent=new Intent(ChurchDetails.this,FamilyUnits.class);
        intent.putExtra("ChurchID",ChurchID);
        intent.putExtra("churchName",churchName.getText().toString());
        floatingActionMenu.close(true);
        startActivity(intent);
    }
    public void notice_click (View view){
        Intent intent=new Intent(ChurchDetails.this,Notices.class);
        intent.putExtra("churchID",ChurchID);
        floatingActionMenu.close(true);
        startActivity(intent);
    }
    public void view_more_about (View view){
        Intent intent=new Intent(ChurchDetails.this,ChurchDetailsExpansion.class);
        intent.putExtra("from","view_more_about");
        intent.putExtra("heading","About");
        intent.putExtra("description",aboutGlobal);
        intent.putExtra("image",churchImageGlobal);
        startActivity(intent);
    }
    public void view_more_extra_detail (View view){
        Intent intent=new Intent(ChurchDetails.this,ChurchDetailsExpansion.class);
        intent.putExtra("from","view_more_extra_detail");
        String[] details= (String[]) view.getTag();
        intent.putExtra("heading",details[1]);
        intent.putExtra("description",details[2]);
        intent.putExtra("image",details[3]);
        startActivity(intent);
    }


}
