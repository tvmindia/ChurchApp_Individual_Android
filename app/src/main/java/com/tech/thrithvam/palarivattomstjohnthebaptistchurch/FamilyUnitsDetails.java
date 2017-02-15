package com.tech.thrithvam.palarivattomstjohnthebaptistchurch;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
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

public class FamilyUnitsDetails extends AppCompatActivity {

    Bundle extras;
    String ChurchID,UnitID;
    Typeface typeQuicksand;
    TextView Fam_unit_head;
    AsyncTask getFamilyList=null,getFamilyExecutiveList=null;
    View popupView;
    PopupWindow popupWindow;
    RelativeLayout mRelativeLayout;
    FloatingActionMenu floatingActionMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_units_details);
        extras=getIntent().getExtras();
        ChurchID = extras.getString("ChurchID");
        UnitID=extras.getString("ID");

        floatingActionMenu=(FloatingActionMenu)findViewById(R.id.material_design_android_floating_action_menu);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.activity_family_units_details);

        typeQuicksand = Typeface.createFromAsset(getAssets(), "fonts/quicksandbold.otf");
        Fam_unit_head = (TextView) findViewById(R.id.family_units_name_head);
        Fam_unit_head.setTypeface(typeQuicksand);


        if(getIntent().hasExtra("UnitName")){
            Fam_unit_head.setText(extras.getString("UnitName"));
        }
        if (isOnline()) {
            getFamilyList = new  GetFamilyList().execute();
        } else {
            Toast.makeText(FamilyUnitsDetails.this, R.string.network_off_alert, Toast.LENGTH_LONG).show();
            Intent noItemsIntent=new Intent(this,NothingToDisplay.class);
            noItemsIntent.putExtra("msg",getResources().getString(R.string.network_off_alert));
            noItemsIntent.putExtra("activityHead","Family Units");
            startActivity(noItemsIntent);
            finish();
        }


        final FloatingActionButton btnOpenPopup = (FloatingActionButton)findViewById(R.id.menu_item0);
        btnOpenPopup.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                popupView = layoutInflater.inflate(R.layout.item_popup_family, null);
                popupWindow = new PopupWindow(popupView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
                if (isOnline()) {
                    getFamilyExecutiveList = new  GetFamilyExecutiveList().execute();
                } else {
                    Toast.makeText(FamilyUnitsDetails.this, R.string.network_off_alert, Toast.LENGTH_LONG).show();
                    Intent noItemsIntent=new Intent(FamilyUnitsDetails.this,NothingToDisplay.class);
                    noItemsIntent.putExtra("msg",getResources().getString(R.string.network_off_alert));
                    noItemsIntent.putExtra("activityHead","Family Units");
                    startActivity(noItemsIntent);
                    finish();
                }
                floatingActionMenu.close(true);
                ImageButton btnDismiss = (ImageButton)popupView.findViewById(R.id.ib_close);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }});
                popupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);
            }
        });




    }

    //--------------------------------------Async Tasks--------------------------------------
    public class GetFamilyList extends AsyncTask<Void , Void, Void> {
        int status;StringBuilder sb;
        String strJson, postData;
        JSONArray jsonArray;
        String msg;
        boolean pass=false;
        AVLoadingIndicatorView loadingIndicator =(AVLoadingIndicatorView)findViewById(R.id.itemsLoading);
        ArrayList<String[]> FamilyListItems =new ArrayList<>();
        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            loadingIndicator.setVisibility(View.VISIBLE);
            //----------encrypting ---------------------------
            // usernameString=cryptography.Encrypt(usernameString);
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            String url =getResources().getString(R.string.url) + "WebServices/WebService.asmx/GetFamilyDetails";
            HttpURLConnection c = null;
            try {
                postData =  "{\"ChurchID\":\"" + ChurchID+ "\",\"UnitID\":\"" + UnitID+ "\"}";
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
                    String[] data=new String[5];
                    data[0]=jsonObject.optString("ID");
                    data[1]=jsonObject.optString("FamilyName");
                    data[2]=jsonObject.optString("FirstName");
                    data[3]=jsonObject.optString("LastName");
                    data[4]=jsonObject.optString("UnitName");

                    FamilyListItems.add(data);
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
                Intent noItemsIntent=new Intent(FamilyUnitsDetails.this,NothingToDisplay.class);
                noItemsIntent.putExtra("msg",msg);
                noItemsIntent.putExtra("activityHead","Family Units");
                startActivity(noItemsIntent);
                finish();
            }
            else {
                CustomAdapter adapter=new CustomAdapter(FamilyUnitsDetails.this, FamilyListItems,"FamilyDetails");
                ListView churchList=(ListView) findViewById(R.id.family_list);
                churchList.setAdapter(adapter);
                churchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                      /*  Intent intent=new Intent(FamilyUnitsDetails.this,.class);
                        intent.putExtra("ChurchID",ChurchID);
                        intent.putExtra("UnitName", FamilyListItems.get(position)[1]);
                        startActivity(intent);*/
                    }
                });
            }
        }
    }
    public class GetFamilyExecutiveList extends AsyncTask<Void , Void, Void> {
        int status;StringBuilder sb;
        String strJson, postData;
        JSONArray jsonArray;
        String msg;
        boolean pass=false;
        AVLoadingIndicatorView loadingIndicator =(AVLoadingIndicatorView)popupView.findViewById(R.id.itemsLoading);
        ArrayList<String[]> FamilyExecutiveListItems =new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingIndicator.setVisibility(View.VISIBLE);

            //----------encrypting ---------------------------
            // usernameString=cryptography.Encrypt(usernameString);
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            String url =getResources().getString(R.string.url) + "WebServices/WebService.asmx/GetFamilyExecutives";
            HttpURLConnection c = null;
            try {
                postData =  "{\"ChurchID\":\"" + ChurchID+ "\",\"UnitID\":\"" + UnitID+ "\"}";
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
                    String[] data=new String[7];
                    data[0]=jsonObject.optString("ID");
                    data[1]=jsonObject.optString("FirstName");
                    data[2]=jsonObject.optString("LastName");
                    data[3]=jsonObject.optString("URL");
                    data[4]=jsonObject.optString("Position");
                    data[5]=jsonObject.optString("Phone");
                    data[6]=jsonObject.optString("UnitName");
                    FamilyExecutiveListItems.add(data);
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
                new AlertDialog.Builder(FamilyUnitsDetails.this).setIcon(android.R.drawable.ic_dialog_alert)//.setTitle("")
                        .setMessage(msg)//R.string.no_items)
                        .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).setCancelable(false).show();
            }
            else {
                CustomAdapter adapter=new CustomAdapter(FamilyUnitsDetails.this, FamilyExecutiveListItems,"FamilyExecutive");
                ListView executiveList=(ListView) popupView.findViewById(R.id.popup_listview);
                executiveList.setAdapter(adapter);

            }
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
            if(popupView!=null){
                    if (popupView.isShown()) {
                        Rect outRect = new Rect();
                        popupView.getGlobalVisibleRect(outRect);
                        if(!outRect.contains((int)event.getRawX(), (int)event.getRawY()))
                            popupWindow.dismiss();
                    }
            }
        }
        return super.dispatchTouchEvent(event);
    }
    public boolean isOnline() {
        ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getFamilyList!=null)getFamilyList.cancel(true);
       if(getFamilyExecutiveList!=null) {
           getFamilyExecutiveList.cancel(true);
           popupWindow.dismiss();
       }
    }
}
