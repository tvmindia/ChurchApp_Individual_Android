package com.tech.thrithvam.palarivattomstjohnthebaptistchurch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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

public class GalleryItems extends AppCompatActivity {
    Typeface typeQuicksand;
    Bundle extras;
    AsyncTask getGalleryItems=null;
    String albumID,albumName;
    GridView galleryGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_items);
        typeQuicksand = Typeface.createFromAsset(getAssets(),"fonts/quicksandbold.otf");
        extras=getIntent().getExtras();
        albumID=extras.getString("albumID");
        albumName=extras.getString("albumName");
        TextView activityHead=(TextView)findViewById(R.id.activity_head);
        activityHead.setTypeface(typeQuicksand);
        activityHead.setText(albumName);
        galleryGrid =(GridView) findViewById(R.id.gallery_grid);
        if (isOnline()) {
            getGalleryItems=new GetGalleryItems().execute();
        } else {
            Toast.makeText(GalleryItems.this, R.string.network_off_alert, Toast.LENGTH_LONG).show();
            Intent noItemsIntent=new Intent(GalleryItems.this,NothingToDisplay.class);
            noItemsIntent.putExtra("msg",getResources().getString(R.string.network_off_alert));
            noItemsIntent.putExtra("activityHead","Gallery");
            startActivity(noItemsIntent);
            finish();
        }
    }
    public class GetGalleryItems extends AsyncTask<Void , Void, Void> {
        int status;StringBuilder sb;
        String strJson, postData;
        JSONArray jsonArray;
        String msg;
        boolean pass=false;
        AVLoadingIndicatorView loadingIndicator =(AVLoadingIndicatorView)findViewById(R.id.itemsLoading);
        ArrayList<String[]> galleryItems=new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingIndicator.setVisibility(View.VISIBLE);
            galleryGrid.setVisibility(View.INVISIBLE);
            //----------encrypting ---------------------------
            // usernameString=cryptography.Encrypt(usernameString);
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            String url =getResources().getString(R.string.url) + "WebServices/WebService.asmx/GetGalleryItems";
            HttpURLConnection c = null;
            try {
                postData =  "{\"albumID\":\"" + albumID+ "\"}";
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
                    data[1]=jsonObject.optString("Thumbnail",null);
                    data[2]=jsonObject.optString("Type");
                    data[3]=jsonObject.optString("Source");
                    data[4]=jsonObject.optString("URL");
                    galleryItems.add(data);
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
                Intent noItemsIntent=new Intent(GalleryItems.this,NothingToDisplay.class);
                noItemsIntent.putExtra("msg",msg);
                noItemsIntent.putExtra("activityHead","Gallery");
                startActivity(noItemsIntent);
                finish();
            }
            else {
                CustomAdapter adapter=new CustomAdapter(GalleryItems.this, galleryItems,"GalleryItems");
                galleryGrid.setAdapter(adapter);
                galleryGrid.setVisibility(View.VISIBLE);
                galleryGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(galleryItems.get(position)[2].equals("video")){  //Video item
                            if(galleryItems.get(position)[3].equals("EXTL")){
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(galleryItems.get(position)[4]));
                                startActivity(browserIntent);
                            }
                            else {//video can be played within app
                                Intent intent=new Intent(GalleryItems.this,VideoPlayer.class);
                                intent.putExtra("URL",getResources().getString(R.string.url) +galleryItems.get(position)[4].substring((galleryItems.get(position)[4]).indexOf("vid")));
                                startActivity(intent);
                            }
                        }
                        else {  //image item
                            Intent intent=new Intent(GalleryItems.this,ImageViewerActivity.class);
                            intent.putExtra("URL",galleryItems.get(position)[4]);
                            startActivity(intent);
                        }
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
        if(getGalleryItems!=null)getGalleryItems.cancel(true);
    }
}
