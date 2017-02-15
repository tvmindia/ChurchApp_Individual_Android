package com.tech.thrithvam.palarivattomstjohnthebaptistchurch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyChurchDetails extends AppCompatActivity {    //Currently only used to display MASS TIMINGS
    AsyncTask getChurchDetails=null,getMassTimings=null;
    Bundle extras;
    Typeface typeQuicksand;
    Typeface typeSegoe;
    Constants constants=new Constants();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_church_details);
        extras=getIntent().getExtras();
        //Fonts------------
        typeQuicksand = Typeface.createFromAsset(getAssets(),"fonts/quicksandbold.otf");
        typeSegoe = Typeface.createFromAsset(getAssets(),"fonts/segoeui.ttf");

        //Fonts---------------
        TextView activityHead=(TextView)findViewById(R.id.activity_head);
        activityHead.setTypeface(typeQuicksand);
        activityHead.setText(constants.ChurchName);
        if (isOnline()) {
            switch (extras.getString("from")){
                case "timings":
                    getMassTimings=new GetMassTimings().execute();
                    break;
                default:
                    finish();
            }
        } else {
            Toast.makeText(MyChurchDetails.this, R.string.network_off_alert, Toast.LENGTH_LONG).show();
            Intent noItemsIntent=new Intent(this,NothingToDisplay.class);
            noItemsIntent.putExtra("msg",getResources().getString(R.string.network_off_alert));
            noItemsIntent.putExtra("activityHead","Church");
            startActivity(noItemsIntent);
            finish();
        }
    }
    //------------------------Async Tasks------------------------------------
    public class GetMassTimings extends AsyncTask<Void , Void, Void> {
        int status;StringBuilder sb;
        String strJson, postData;
        JSONArray jsonArray;
        String msg;
        boolean pass=false;
        AVLoadingIndicatorView loadingIndicator =(AVLoadingIndicatorView)findViewById(R.id.itemsLoading);
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
            loadingIndicator.setVisibility(View.VISIBLE);
            //----------encrypting ---------------------------
            // usernameString=cryptography.Encrypt(usernameString);
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            String url =getResources().getString(R.string.url) + "WebServices/WebService.asmx/GetMassTimings";
            HttpURLConnection c = null;
            try {
                postData =  "{\"ChurchID\":\"" + constants.ChurchID+ "\"}";
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
            loadingIndicator.setVisibility(View.GONE);
            if(!pass) {
                Intent noItemsIntent=new Intent(MyChurchDetails.this,NothingToDisplay.class);
                noItemsIntent.putExtra("msg",msg);
                noItemsIntent.putExtra("activityHead","My Church");
                startActivity(noItemsIntent);
                finish();
            }
            else {
                RelativeLayout timingsLayout=(RelativeLayout)findViewById(R.id.timingsLayout);
                timingsLayout.setVisibility(View.VISIBLE);
                TextView timingsTitle=(TextView)findViewById(R.id.mass_label);
                timingsTitle.setTypeface(typeQuicksand);
                TextView sundayLabel,mondayLabel,tuesdayLabel,wednesdayLabel,thursdayLabel,fridayLabel,saturdayLabel;
                TextView sundayTiming,mondayTiming,tuesdayTiming,wednesdayTiming,thursdayTiming,fridayTiming,saturdayTiming;
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
                setTimingsToTextViews(sunday,sundayTiming,sundayLabel);
                setTimingsToTextViews(monday,mondayTiming,mondayLabel);
                setTimingsToTextViews(tuesday,tuesdayTiming,tuesdayLabel);
                setTimingsToTextViews(wednesday,wednesdayTiming,wednesdayLabel);
                setTimingsToTextViews(thursday,thursdayTiming,thursdayLabel);
                setTimingsToTextViews(friday,fridayTiming,fridayLabel);
                setTimingsToTextViews(saturday,saturdayTiming,saturdayLabel);
            }
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

    public void novenas_click (View view){
        Intent intent=new Intent(MyChurchDetails.this,NovenaDetailsList.class);
        intent.putExtra("churchID",constants.ChurchID);
        intent.putExtra("churchName","Novenas at "+constants.ChurchName);
        intent.putExtra("from","church");
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
        if(getChurchDetails !=null) getChurchDetails.cancel(true);
        if(getMassTimings !=null) getMassTimings.cancel(true);
    }
}
