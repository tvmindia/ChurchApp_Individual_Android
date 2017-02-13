package com.tech.thrithvam.palarivattomstjohnthebaptistchurch;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class InstitutionDetails extends AppCompatActivity {
    Bundle extras;
    TextView insName, founder, founded, mobile, email, address, history,phone1,phone2,website;
    ImageView institution_image;
    Typeface typeQuicksand;
    Typeface typeSegoe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institution_details);
        extras=getIntent().getExtras();

        final String imageURL=extras.getString("URL");

        typeQuicksand = Typeface.createFromAsset(getAssets(),"fonts/quicksandbold.otf");
        typeSegoe = Typeface.createFromAsset(getAssets(),"fonts/segoeui.ttf");

        insName =(TextView)findViewById(R.id.activity_head);
        insName.setTypeface(typeQuicksand);
        TextView addressLabel=(TextView)findViewById(R.id.address_label);
        TextView genInfoLabel=(TextView)findViewById(R.id.general_info_label);
        TextView historyLabel=(TextView)findViewById(R.id.history_label);
        founder =(TextView)findViewById(R.id.founder_name);
        founded =(TextView)findViewById(R.id.founded);
        mobile =(TextView)findViewById(R.id.mobile_number);
        email =(TextView)findViewById(R.id.email_id);
        address =(TextView)findViewById(R.id.address_details);
        history =(TextView)findViewById(R.id.history_details);
        phone1 =(TextView)findViewById(R.id.phone1);
        phone2 =(TextView)findViewById(R.id.phone2);
        website =(TextView)findViewById(R.id.website);
        institution_image =(ImageView)findViewById(R.id.institution_image);

        addressLabel.setTypeface(typeQuicksand);
        genInfoLabel.setTypeface(typeQuicksand);
        historyLabel.setTypeface(typeQuicksand);
        founder.setTypeface(typeSegoe);
        founded.setTypeface(typeSegoe);
        mobile.setTypeface(typeSegoe);
        email.setTypeface(typeSegoe);
        address.setTypeface(typeSegoe);
        history.setTypeface(typeSegoe);
        phone1.setTypeface(typeSegoe);
        phone2.setTypeface(typeSegoe);
        website.setTypeface(typeSegoe);
        //Loading data------------
        if(getIntent().hasExtra("Name")){
            insName.setText(extras.getString("Name"));
        }
        if(getIntent().hasExtra("desc")){
            history.setText(extras.getString("desc"));
        }
        if(getIntent().hasExtra("Address")){
            address.setText(extras.getString("Address"));
        }
        if(getIntent().hasExtra("Email")){
            if(!extras.getString("Email").equals("null")){
            email.setText(extras.getString("Email"));
            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("*/*");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{extras.getString("Email")});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Mail from Church App User");
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            });
            }
            else {
                email.setText("-");
            }
        }

        if(getIntent().hasExtra("Mobile")){
            if(!extras.getString("Mobile").equals("null")){
                mobile.setText(extras.getString("Mobile"));
                mobile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {                                   //Phone call function
                        Uri number = Uri.parse("tel:" + extras.getString("Mobile"));
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                        startActivity(callIntent);
                    }
                });
            }
            else {
                mobile.setText("-");
            }
        }
        if(getIntent().hasExtra("Founded")){
            if(!extras.getString("Founded").equals("null"))
                founded.setText(extras.getString("Founded"));
            else
                founded.setText("-");
        }
        if(getIntent().hasExtra("Founder")){
            if(!extras.getString("Founder").equals("null"))
                founder.setText(extras.getString("Founder"));
            else
                founder.setText("-");
        }
        if(getIntent().hasExtra("Phone1")){
            if(!extras.getString("Phone1").equals("null")){
                phone1.setText(extras.getString("Phone1"));
                phone1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {                                   //Phone call function
                        Uri number = Uri.parse("tel:" + extras.getString("Phone1"));
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                        startActivity(callIntent);
                    }
                });
            }
            else
                phone1.setText("-");
        }
        if(getIntent().hasExtra("Phone2")){
            if(!extras.getString("Phone2").equals("null")){
            phone2.setText(extras.getString("Phone2"));
                phone2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {                                   //Phone call function
                        Uri number = Uri.parse("tel:" + extras.getString("Phone2"));
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                        startActivity(callIntent);
                    }
                });
            }
            else {
                phone2.setVisibility(View.GONE);
            }
        }
        if(getIntent().hasExtra("Website")){
            if(!extras.getString("Website").equals("null")){
                website.setText(extras.getString("Website"));
                website.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(extras.getString("Website")));
                        startActivity(browserIntent);
                    }
                });
            }
            else {
                website.setText("-");
            }
        }

        //image loading using url
        if(!imageURL.equals("null")){
            Glide.with(InstitutionDetails.this)
                    .load(getResources().getString(R.string.url) +imageURL.substring((imageURL).indexOf("img")))
                    .thumbnail(0.1f)
                    .crossFade()
                    .dontTransform()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            institution_image.setVisibility(View.GONE);
                            return true;
                        }
                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(institution_image)
            ;
            institution_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent photoIntent=new Intent(InstitutionDetails.this,ImageViewerActivity.class);
                    photoIntent.putExtra("URL",imageURL);
                    startActivity(photoIntent);
                }
            });
        }
        else {
            institution_image.setVisibility(View.GONE);
        }
    }
}
