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
import android.widget.AdapterView;
import android.widget.ListView;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Institutions extends AppCompatActivity {
    Bundle extras;
    String ChurchID;
    Typeface typeQuicksand;
    TextView activity_head;
    AsyncTask getInstitutionsList=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institutions);
        extras=getIntent().getExtras();
        ChurchID=extras.getString("ChurchID");

        typeQuicksand = Typeface.createFromAsset(getAssets(),"fonts/quicksandbold.otf");
        activity_head =(TextView)findViewById(R.id.activity_head);
        activity_head.setTypeface(typeQuicksand);

        if (isOnline()) {
            getInstitutionsList=new GetInstitutionsList().execute();
        } else {
            Toast.makeText(Institutions.this, R.string.network_off_alert, Toast.LENGTH_LONG).show();
        }
    }

    //---------------------------------Async Tasks--------------------------------------
    public class GetInstitutionsList extends AsyncTask<Void , Void, Void> {
        int status;StringBuilder sb;
        String strJson, postData;
        JSONArray jsonArray;
        String msg;
        boolean pass=false;
        AVLoadingIndicatorView loadingIndicator =(AVLoadingIndicatorView)findViewById(R.id.itemsLoading);
        ArrayList<String[]> institutionListItems =new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingIndicator.setVisibility(View.VISIBLE);
            //----------encrypting ---------------------------
            // usernameString=cryptography.Encrypt(usernameString);
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            String url =getResources().getString(R.string.url) + "WebServices/WebService.asmx/GetInstitutionsByChurchID";
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
                    String[] data=new String[12];
                    data[0]=jsonObject.optString("ID");
                    data[1]=jsonObject.optString("Name");
                    data[2]=jsonObject.optString("Address");
                    data[3]=jsonObject.optString("URL");
                    data[4]=jsonObject.optString("desc");
                    data[5]=jsonObject.optString("Email");
                    data[6]=jsonObject.optString("Founder");
                    data[7]=jsonObject.optString("Founded").replace("/Date(", "").replace(")/", "");
                    data[8]=jsonObject.optString("Mobile");
                    data[9]=jsonObject.optString("Phone1");
                    data[10]=jsonObject.optString("Phone2");
                    data[11]=jsonObject.optString("Website");
                    institutionListItems.add(data);
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
                Intent noItemsIntent=new Intent(Institutions.this,NothingToDisplay.class);
                noItemsIntent.putExtra("msg",msg);
                noItemsIntent.putExtra("activityHead","Institutions");
                startActivity(noItemsIntent);
                finish();
            }
            else {
                CustomAdapter adapter=new CustomAdapter(Institutions.this, institutionListItems,"ChurchInstitutions");
                ListView InstitutionList=(ListView) findViewById(R.id.institutions_list);
                InstitutionList.setAdapter(adapter);
                InstitutionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent=new Intent(Institutions.this,InstitutionDetails.class);
                        intent.putExtra("ID", institutionListItems.get(position)[0]);
                        intent.putExtra("Name", institutionListItems.get(position)[1]);
                        intent.putExtra("Address", institutionListItems.get(position)[2]);
                        intent.putExtra("URL", institutionListItems.get(position)[3]);
                        intent.putExtra("desc", institutionListItems.get(position)[4]);
                        intent.putExtra("Email", institutionListItems.get(position)[5]);
                        intent.putExtra("Founder", institutionListItems.get(position)[6]);
                        intent.putExtra("Phone1", institutionListItems.get(position)[9]);
                        intent.putExtra("Phone2", institutionListItems.get(position)[10]);
                        intent.putExtra("Website", institutionListItems.get(position)[11]);
                        SimpleDateFormat formatted = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
                        Calendar date=Calendar.getInstance();
                        if(!institutionListItems.get(position)[7].equals("null"))
                        {date.setTimeInMillis(Long.parseLong(institutionListItems.get(position)[7]));
                        intent.putExtra("Founded",formatted.format(date.getTime()));}
                        else {
                            intent.putExtra("Founded","-");
                        }
                        intent.putExtra("Mobile", institutionListItems.get(position)[8]);
                        startActivity(intent);
                    }
                });
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
        if(getInstitutionsList!=null)getInstitutionsList.cancel(true);
    }
}
