package com.tech.thrithvam.palarivattomstjohnthebaptistchurch;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CustomAdapter extends BaseAdapter {
    private Context adapterContext;
    private static LayoutInflater inflater=null;
    private ArrayList<String[]> objects;
    private String calledFrom;
    private Typeface typeSegoe;
    private Typeface typeBLKCHCRY;
    private Typeface typeQuicksand;
    private SimpleDateFormat formatted;
    private Calendar cal;
    private Animation animation;
    private int lastPosition;
    public CustomAdapter(Context context, ArrayList<String[]> objects, String calledFrom) {
        // super(context, textViewResourceId, objects);
        adapterContext=context;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.objects=objects;
        this.calledFrom=calledFrom;
        typeSegoe = Typeface.createFromAsset(context.getAssets(),"fonts/segoeui.ttf");
        typeBLKCHCRY = Typeface.createFromAsset(context.getAssets(),"fonts/blackchancery.ttf");
        typeQuicksand = Typeface.createFromAsset(context.getAssets(),"fonts/quicksandbold.otf");
        formatted = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        cal= Calendar.getInstance();
        lastPosition=-1;
    }
    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class Holder
    {
        //Searching Church---------------------
        TextView churchName,address,town;
        ImageView churchImage;
        RelativeLayout setAsMyChurch;
        //Novena Church list-------------------
        TextView novenaCaption,novenaDescription,novenaDate,dayAndTime;
        ImageView novenaImg;
        //Pious Organisation-------------------
        TextView orgName, orgPatronName;
        ImageView orgPatronImage;
        //Institutions-------------------------
        TextView institutionName, institutionAddress;
        ImageView institutionImage;
        //Events-------------------------------
        TextView eventsHead, eventsDate;
        ImageView eventImage;
        //Towns--------------------------------
        TextView townHead;
        //Priest-------------------------------
        TextView pName, pDOB, pAbout, pDateOrdination, pDesign, pAddress, pEmail, pMob, pParish, pStatus, pBaptism;
        ImageView pImage;
        //Events-------------------------------
        TextView noticeHead, noticeType;
        ImageView noticeImage;
        //Gallery-----------------------------
        TextView albumTitle,itemCount;
        ImageView galleryAlbum,galleryItem,videoIcon;
        //Family------------------------------
        TextView familyUnitHead;
        //FamilyDetails------------------------
        TextView familyHead, familyName;
        //Family unit Executives---------------
        TextView personName, personMob, personPosition;
        ImageView personImg;
        //Notifications----------------------
        TextView notTitle,notDesc,notDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;
        final int fPos=position;
        switch (calledFrom) {
            //--------------------------for search results------------------
            case "ChurchTownSearchResults":
                if (convertView == null) {
                    holder = new Holder();
                    convertView = inflater.inflate(R.layout.item_church, null);
                    holder.churchName = (TextView) convertView.findViewById(R.id.church_name);
                    holder.address=(TextView)convertView.findViewById(R.id.address);
                    holder.town=(TextView)convertView.findViewById(R.id.town);
                    holder.churchImage=(ImageView)convertView.findViewById(R.id.church_image);
                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }
                //Label loading--------------------
                holder.churchName.setText(objects.get(position)[1]);
                if(!objects.get(position)[2].equals("null")) {
                    holder.town.setText(objects.get(position)[2]);
                    holder.town.setVisibility(View.VISIBLE);
                }
                else holder.town.setVisibility(View.INVISIBLE);

                if(!objects.get(position)[3].equals("null")){
                    holder.churchImage.setPadding(0,0,0,0);
                    holder.churchImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Glide.with(adapterContext)
                            .load(adapterContext.getResources().getString(R.string.url) +objects.get(position)[3].substring((objects.get(position)[3]).indexOf("img")))
                            .thumbnail(0.1f)
                            .into(holder.churchImage);
                    holder.churchImage.setPadding(0,0,0,0);
                }
                else{
                    holder.churchImage.setPadding(15,15,15,15);
                    holder.churchImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    Glide.with(adapterContext)
                            .load(R.drawable.church)
                            .into(holder.churchImage);
                }
                if(!objects.get(position)[4].equals("null")){
                    holder.address.setText(objects.get(position)[4]);
                    holder.address.setVisibility(View.VISIBLE);
                }
                else holder.address.setVisibility(View.INVISIBLE);
                holder.churchName.setTypeface(typeQuicksand);
                holder.town.setTypeface(typeSegoe);
                holder.address.setTypeface(typeSegoe);
                if(position>lastPosition){
                    animation = AnimationUtils.loadAnimation(adapterContext, R.anim.up_from_bottom);
                    convertView.startAnimation(animation);
                }
                lastPosition = position;
                break;
              //-----------------------------------Novena Details-------------------------------------
            case "NovenaDetailsList":
                if (convertView == null) {
                    holder = new Holder();
                    convertView = inflater.inflate(R.layout.item_novena_church, null);
                    holder.novenaCaption=(TextView)convertView.findViewById(R.id.novena_caption);
                    holder.novenaDescription=(TextView)convertView.findViewById(R.id.novena_description);
                    holder.novenaDate=(TextView)convertView.findViewById(R.id.novena_date);
                    holder.novenaImg=(ImageView)convertView.findViewById(R.id.detail_image);
                    holder.dayAndTime=(TextView)convertView.findViewById(R.id.novena_time);
                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }
                //Label loading--------------------
                holder.novenaCaption.setText(objects.get(position)[1]);
                holder.novenaCaption.setTypeface(typeQuicksand);




                if(!objects.get(position)[3].equals("null")){
                    holder.novenaDescription.setText(objects.get(position)[3]);
                    holder.novenaDescription.setVisibility(View.VISIBLE);
                    holder.novenaDescription.setTypeface(typeSegoe);
                }
                else holder.novenaDescription.setVisibility(View.INVISIBLE);


                if(!objects.get(position)[4].equals("null")){
                    holder.novenaDate.setVisibility(View.VISIBLE);
                    if(!objects.get(position)[5].equals("null")){
                        cal.setTimeInMillis(Long.parseLong(objects.get(position)[4]));
                        String startDate=formatted.format(cal.getTime());
                        cal.setTimeInMillis(Long.parseLong(objects.get(position)[5]));
                        String endDate=formatted.format(cal.getTime());
                        holder.novenaDate.setText(adapterContext.getResources().getString(R.string.novena_dates,startDate,endDate));
                    }
                    else {
                        cal.setTimeInMillis(Long.parseLong(objects.get(position)[4]));
                        String startDate=formatted.format(cal.getTime());
                        holder.novenaDate.setText(startDate);
                    }
                }
                else {
                    holder.novenaDate.setVisibility(View.GONE);
                }
                holder.novenaDate.setTypeface(typeSegoe);

                if(!objects.get(position)[6].equals("null")){
                    Glide.with(adapterContext)
                            .load(adapterContext.getResources().getString(R.string.url) +objects.get(position)[6].substring((objects.get(position)[6]).indexOf("img")))
                            .thumbnail(0.1f)
                            .into(holder.novenaImg)
                    ;
                    holder.novenaImg.setPadding(0,0,0,0);
                }
                else{
                    holder.novenaImg.setPadding(15,15,15,15);
                    holder.novenaImg.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    Glide.with(adapterContext)
                            .load(R.drawable.church)
                            .into(holder.novenaImg);
                }

                if(!objects.get(position)[7].equals("null")){
                    holder.dayAndTime.setText(objects.get(position)[7]);
                    holder.dayAndTime.setVisibility(View.VISIBLE);
                    holder.dayAndTime.setTypeface(typeSegoe);
                }
                else holder.dayAndTime.setVisibility(View.GONE);

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.novenaDescription.setMaxLines(1000);//Expand description
                    }
                });
                if(position>lastPosition){
                    animation = AnimationUtils.loadAnimation(adapterContext, R.anim.up_from_bottom);
                    convertView.startAnimation(animation);
                }
                lastPosition = position;
                break;
         //-----------------------------ChurchPiousOrgList-------------------------------------------
            case "ChurchPiousOrgList":
                if (convertView == null) {
                    holder = new Holder();
                    convertView = inflater.inflate(R.layout.item_pious_organisation, null);
                    holder.orgPatronImage =(ImageView)convertView.findViewById(R.id.patron_image );
                    holder.orgName = (TextView) convertView.findViewById(R.id.Pious_org_name );
                    holder.orgPatronName =(TextView)convertView.findViewById(R.id.Pious_org_patron_name);
                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }
                //----------------Label loading--------------------
                holder.orgName.setText(objects.get(position)[1]);
                holder.orgPatronName.setText(objects.get(position)[2]);
                holder.orgName.setTypeface(typeQuicksand);
                holder.orgPatronName.setTypeface(typeSegoe);
                if(!objects.get(position)[3].equals("null")){
                    Glide.with(adapterContext)
                            .load(adapterContext.getResources().getString(R.string.url) +objects.get(position)[3].substring((objects.get(position)[3]).indexOf("img")))
                            .fitCenter()
                            .thumbnail(0.1f)
                            .into(holder.orgPatronImage);
                }
                else {
                    Glide.with(adapterContext)
                            .load(R.drawable.priest)
                            .thumbnail(0.1f)
                            .into(holder.orgPatronImage);
                }
                if(position>lastPosition){
                    animation = AnimationUtils.loadAnimation(adapterContext, R.anim.up_from_bottom);
                    convertView.startAnimation(animation);
                }
                lastPosition = position;
                break;
            //---------------------ChurchInstitutions-------------------------
            case "ChurchInstitutions":
                if (convertView == null) {
                    holder = new Holder();
                    convertView = inflater.inflate(R.layout.item_institution, null);
                    holder.institutionImage =(ImageView)convertView.findViewById(R.id.institution_image );
                    holder.institutionName = (TextView) convertView.findViewById(R.id.institution_name );
                    holder.institutionAddress =(TextView)convertView.findViewById(R.id.institution_address);
                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }
                //----------------Label loading--------------------
                holder.institutionName.setText(objects.get(position)[1]);
                if(!objects.get(position)[2].equals("null"))
                    holder.institutionAddress.setText(objects.get(position)[2]);
                else
                    holder.institutionAddress.setText("");
                holder.institutionName.setTypeface(typeQuicksand);
                holder.institutionAddress.setTypeface(typeSegoe);

                if(!objects.get(position)[3].equals("null")){
                    Glide.with(adapterContext)
                            .load(adapterContext.getResources().getString(R.string.url) +objects.get(position)[3].substring((objects.get(position)[3]).indexOf("img")))
                            .thumbnail(0.1f)
                            .dontTransform()
                            .into(holder.institutionImage);
                }
                else{
                    holder.institutionImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    Glide.with(adapterContext)
                            .load(R.drawable.church)
                            .into(holder.institutionImage)
                    ;
                }
                if(position>lastPosition){
                    animation = AnimationUtils.loadAnimation(adapterContext, R.anim.up_from_bottom);
                    convertView.startAnimation(animation);
                }
                lastPosition = position;
                break;
            //-------------------Church Events-------------------------------
            case "ChurchEvents":
                if (convertView == null) {
                    holder = new Holder();
                    convertView = inflater.inflate(R.layout.item_events, null);
                    holder.eventImage =(ImageView)convertView.findViewById(R.id.events_image);
                    holder.eventsHead = (TextView) convertView.findViewById(R.id.events_head );
                    holder.eventsDate =(TextView)convertView.findViewById(R.id.events_date);
                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }
                //----------------Label loading--------------------
                holder.eventsHead.setText(objects.get(position)[1]);
                holder.eventsHead.setTypeface(typeSegoe);
                holder.eventsDate.setTypeface(typeSegoe);

                if(!objects.get(position)[0].equals("null")){
                    cal.setTimeInMillis(Long.parseLong(objects.get(position)[0]));
                    String startDate=formatted.format(cal.getTime());
                    holder.eventsDate.setText(startDate);
                }
                else {
                    holder.eventsDate.setText("");
                }
                if(!objects.get(position)[3].equals("null")){
                    Glide.with(adapterContext)
                            .load(adapterContext.getResources().getString(R.string.url) +objects.get(position)[3].substring((objects.get(position)[3]).indexOf("img")))
                            .thumbnail(0.1f)
                            .into(holder.eventImage);
                }
                else{
                    holder.eventImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    Glide.with(adapterContext)
                            .load(R.drawable.events)
                            .into(holder.eventImage)
                    ;
                }
                if(position>lastPosition){
                    animation = AnimationUtils.loadAnimation(adapterContext, R.anim.up_from_bottom);
                    convertView.startAnimation(animation);
                }
                lastPosition = position;
                break;

            //------------------------------PriestList-----------------------------
            case "PriestList":
                if (convertView == null) {
                    holder = new Holder();
                    convertView = inflater.inflate(R.layout.item_priest, null);
                    holder.pImage =(ImageView)convertView.findViewById(R.id.priest_image);
                    holder.pName = (TextView) convertView.findViewById(R.id.priest_name );
                    holder.pParish =(TextView)convertView.findViewById(R.id.parish);
                    holder.pBaptism = (TextView) convertView.findViewById(R.id.baptismalname);
                    holder.pDesign =(TextView)convertView.findViewById(R.id.designation);
                    holder.pStatus = (TextView) convertView.findViewById(R.id.priest_status);
                    holder.pDOB =(TextView)convertView.findViewById(R.id.date_of_birth);
                    holder.pMob = (TextView) convertView.findViewById(R.id.priest_mobile);
                    holder.pDateOrdination =(TextView)convertView.findViewById(R.id.date_of_ordination);
                    holder.pEmail =(TextView)convertView.findViewById(R.id.priest_email);
                    holder.pAddress =(TextView)convertView.findViewById(R.id.priest_address);
                    holder.pAbout =(TextView)convertView.findViewById(R.id.priest_about);
                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }
                //----------------Label loading--------------------

                holder.pName.setText(objects.get(position)[1]);

                if(!objects.get(position)[2].equals("null")){
                    holder.pAddress.setText(objects.get(position)[2]);
                }
                else holder.pAddress.setText("-");

                if(!objects.get(position)[4].equals("null")){
                    holder.pAbout.setText(objects.get(position)[4]);
                }
                else holder.pAbout.setText("-");

                if(!objects.get(position)[5].equals("null")) {
                    holder.pParish.setText(objects.get(position)[5]);
                }
                else holder.pParish.setText("-");

                if(!objects.get(position)[8].equals("null")){
                    holder.pEmail.setText(objects.get(position)[8]);
                    holder.pEmail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("*/*");
                            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{objects.get(fPos)[8]});
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Mail from Church App User");
                            if (intent.resolveActivity(adapterContext.getPackageManager()) != null) {
                                adapterContext.startActivity(intent);
                            }
                        }
                    });
                }
                else holder.pEmail.setText("");

                if(!objects.get(position)[9].equals("null")){
                    holder.pMob.setText(objects.get(position)[9]);
                    holder.pMob.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri number = Uri.parse("tel:" +objects.get(fPos)[9]);
                            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                            adapterContext.startActivity(callIntent);
                        }
                    });
                }
                else holder.pMob.setText("");

                if(!objects.get(position)[10].equals("null")){
                    holder.pDesign.setText(objects.get(position)[10]);
                }
                else holder.pDesign.setText("-");

                if(!objects.get(position)[11].equals("null")){
                    holder.pStatus.setText(objects.get(position)[11]);
                }
                else holder.pStatus.setText("-");

                if(!objects.get(position)[12].equals("null")){
                    holder.pBaptism.setText(objects.get(position)[12]);
                }
                else holder.pBaptism.setText("-");


                if(!objects.get(position)[6].equals("null")){
                    cal.setTimeInMillis(Long.parseLong(objects.get(position)[6]));
                    String dob=formatted.format(cal.getTime());
                    holder.pDOB.setText(dob);
                }
                else {
                    holder.pDOB.setText("-");
                }

                if(!objects.get(position)[7].equals("null")){
                    cal.setTimeInMillis(Long.parseLong(objects.get(position)[7]));
                    String date=formatted.format(cal.getTime());
                    holder.pDateOrdination.setText(date);
                }
                else {
                    holder.pDateOrdination.setText("-");
                }

                if(!objects.get(position)[3].equals("null")){
                    Glide.with(adapterContext)
                            .load(adapterContext.getResources().getString(R.string.url) +objects.get(position)[3].substring((objects.get(position)[3]).indexOf("img")))
                            .thumbnail(0.1f)
                            .into(holder.pImage);
                }
                else {
                    Glide.with(adapterContext)
                            .load(R.drawable.priest)
                            .thumbnail(0.1f)
                            .into(holder.pImage);
                }
                break;
            //------------------ChurchNotices---------------------------
            case "ChurchNotices":
                if (convertView == null) {
                    holder = new Holder();
                    convertView = inflater.inflate(R.layout.item_notices, null);
                    holder.noticeImage =(ImageView)convertView.findViewById(R.id.notice_image );
                    holder.noticeHead = (TextView) convertView.findViewById(R.id.notice_name );
                    holder.noticeType =(TextView)convertView.findViewById(R.id.notice_type);
                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }
                //----------------Label loading--------------------
                holder.noticeHead.setText(objects.get(position)[1]);
                if(!objects.get(position)[4].equals("null"))
                    holder.noticeType.setText(objects.get(position)[4]);
                else holder.noticeType.setText("");

                if(!objects.get(position)[3].equals("null")){
                    Glide.with(adapterContext)
                            .load(adapterContext.getResources().getString(R.string.url) +objects.get(position)[3].substring((objects.get(position)[3]).indexOf("img")))
                            .thumbnail(0.1f)
                            .into(holder.noticeImage);
                }
                else {
                    Glide.with(adapterContext)
                            .load(R.drawable.notices)
                            .thumbnail(0.1f)
                            .into(holder.noticeImage);
                }
                if(position>lastPosition){
                    animation = AnimationUtils.loadAnimation(adapterContext, R.anim.up_from_bottom);
                    convertView.startAnimation(animation);
                }
                lastPosition = position;
                break;
            //--------------------Gallery Album grid----------------------
            case "GalleryAlbums":
                if (convertView == null) {
                    holder = new Holder();
                    convertView = inflater.inflate(R.layout.item_gallery_album, null);
                    holder.galleryAlbum =(ImageView)convertView.findViewById(R.id.grid_img );
                    holder.videoIcon=(ImageView)convertView.findViewById(R.id.video_icon);
                    holder.albumTitle = (TextView) convertView.findViewById(R.id.grid_txt );
                    holder.itemCount  =(TextView)convertView.findViewById(R.id.item_count);
                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }
                //----------------Label loading--------------------
                if(!objects.get(position)[1].equals("null"))
                    holder.albumTitle.setText(objects.get(position)[1]);
                else
                    holder.albumTitle.setText("");

                if(!objects.get(position)[2].equals("null"))
                    holder.itemCount.setText(objects.get(position)[2]);
                else
                    holder.itemCount.setText("");

                holder.albumTitle.setTypeface(typeQuicksand);
                holder.itemCount.setTypeface(typeSegoe);

                if(!objects.get(position)[3].equals("null")){
                    holder.galleryAlbum.setVisibility(View.VISIBLE);
                    if(objects.get(position)[4].equals("video")){   //it is a video album
                        holder.videoIcon.setVisibility(View.VISIBLE);
                        if(objects.get(position)[3].contains("youtube")){//||objects.get(position)[3].contains("vimeo")){//youtube or vimeo link of thumbnail
                            Glide.with(adapterContext)
                                    .load(objects.get(position)[3])
                                    .dontTransform()
                                    .thumbnail(0.1f)
                                    .into(holder.galleryAlbum);
                        }
                        else {//video is from own server
                            Glide.with(adapterContext)
                                    .load(adapterContext.getResources().getString(R.string.url) +objects.get(position)[3].substring((objects.get(position)[3]).indexOf("vid")))
                                    .dontTransform()
                                    .thumbnail(0.1f)
                                    .into(holder.galleryAlbum);
                        }
                    }
                    else {  //It is an image album
                        holder.videoIcon.setVisibility(View.INVISIBLE);
                        Glide.with(adapterContext)
                                .load(adapterContext.getResources().getString(R.string.url) +objects.get(position)[3].substring((objects.get(position)[3]).indexOf("img")))
                                .dontTransform()
                                .thumbnail(0.1f)
                                .into(holder.galleryAlbum);
                    }
                }
                else {
                    holder.galleryAlbum.setVisibility(View.INVISIBLE);
                }
                holder.albumTitle.setMaxLines(1);
                holder.albumTitle.setEllipsize(TextUtils.TruncateAt.END);
                break;
            //--------------------Gallery Album grid----------------------
            case "GalleryItems":
                if (convertView == null) {
                    holder = new Holder();
                    convertView = inflater.inflate(R.layout.item_gallery_item, null);
                    holder.galleryItem =(ImageView)convertView.findViewById(R.id.image);
                    holder.videoIcon=(ImageView)convertView.findViewById(R.id.video_icon);
                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }
                if(!objects.get(position)[4].equals("null")){
                    holder.galleryItem.setVisibility(View.VISIBLE);
                    if(objects.get(position)[2].equals("video")){   //it is a video item
                        holder.videoIcon.setVisibility(View.VISIBLE);
                        if(objects.get(position)[1].contains("youtube")){//||objects.get(position)[1].contains("vimeo")){//youtube or vimeo link of thumbnail
                            Glide.with(adapterContext)
                                    .load(objects.get(position)[1])
                                    .dontTransform()
                                    .thumbnail(0.1f)
                                    .into(holder.galleryItem);
                        }
                        else {//video is from own server
                            if(!objects.get(position)[1].equals("null")) {
                                Glide.with(adapterContext)
                                        .load(adapterContext.getResources().getString(R.string.url) + objects.get(position)[1].substring((objects.get(position)[1]).indexOf("vid")))
                                        .dontTransform()
                                        .thumbnail(0.1f)
                                        .into(holder.galleryItem);
                            }
                        }
                    }
                    else {  //It is an image item
                        holder.videoIcon.setVisibility(View.INVISIBLE);
                        Glide.with(adapterContext)
                                .load(adapterContext.getResources().getString(R.string.url) +objects.get(position)[4].substring((objects.get(position)[4]).indexOf("img")))
                                .dontTransform()
                                .thumbnail(0.1f)
                                .into(holder.galleryItem);
                    }
                }
                else {
                    holder.galleryItem.setVisibility(View.INVISIBLE);
                }
                break;
            //------------------Church family units---------------------------
            case "ChurchFamilyUnits":
                if (convertView == null) {
                    holder = new Holder();
                    convertView = inflater.inflate(R.layout.item_familyunits, null);
                    holder.familyUnitHead = (TextView) convertView.findViewById(R.id.familyunit_name );
                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }
                holder.familyUnitHead.setText(objects.get(position)[1]);
                break;
            //------------------FamilyDetails---------------------------
            case "FamilyDetails":
                if (convertView == null) {
                    holder = new Holder();
                    convertView = inflater.inflate(R.layout.item_family, null);
                    holder.familyHead = (TextView) convertView.findViewById(R.id.family_head );
                    holder.familyName =(TextView) convertView.findViewById(R.id.family_name );
                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }
                holder.familyHead.setText(objects.get(position)[2]+" "+objects.get(position)[3]);
                if(!objects.get(position)[1].equals("null"))
                    holder.familyName.setText(objects.get(position)[1]);
                else
                    holder.familyName.setText("");
                break;
            //------------------FamilyDetails---------------------------
            case "FamilyExecutive":
                if (convertView == null) {
                    holder = new Holder();
                    convertView = inflater.inflate(R.layout.item_fam_unit_executives, null);
                    holder.personName = (TextView) convertView.findViewById(R.id.per_name );
                    holder.personMob =(TextView) convertView.findViewById(R.id.per_mobile );
                    holder.personPosition =(TextView) convertView.findViewById(R.id.per_postion );
                    holder.personImg =(ImageView)convertView.findViewById(R.id.person_img );
                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }
                holder.personName.setText(objects.get(position)[1]+" "+objects.get(position)[2]);

                if(!objects.get(position)[5].equals("null"))
                    holder.personMob.setText(objects.get(position)[5]);
                else holder.personMob.setText("");

                if(!objects.get(position)[4].equals("null"))
                    holder.personPosition.setText(objects.get(position)[4]);
                else
                    holder.personPosition.setText("");

                if(!objects.get(position)[3].equals("null")){
                    Glide.with(adapterContext)
                            .load(adapterContext.getResources().getString(R.string.url) +objects.get(position)[3].substring((objects.get(position)[3]).indexOf("img")))
                            .dontTransform()
                            .thumbnail(0.1f)
                            .into(holder.personImg);
                }
                break;
            //--------------------------for home screen items------------------
            case "ChurchTownMyChurchSearchResults":
                if (convertView == null) {
                    holder = new Holder();
                    convertView = inflater.inflate(R.layout.item_church_for_mychurch, null);
                    holder.churchName = (TextView) convertView.findViewById(R.id.church_name);
                    holder.address=(TextView)convertView.findViewById(R.id.address);
                    holder.town=(TextView)convertView.findViewById(R.id.town);
                    holder.churchImage=(ImageView)convertView.findViewById(R.id.church_image);
                    holder.setAsMyChurch=(RelativeLayout)convertView.findViewById(R.id.set_mychurch_button);
                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }
                //Label loading--------------------
                holder.churchName.setText(objects.get(position)[1]);
                if(!objects.get(position)[2].equals("null")) {
                    holder.town.setText(objects.get(position)[2]);
                    holder.town.setVisibility(View.VISIBLE);
                }
                else holder.town.setVisibility(View.INVISIBLE);

                if(!objects.get(position)[3].equals("null")){
                    holder.churchImage.setPadding(0,0,0,0);
                    holder.churchImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Glide.with(adapterContext)
                            .load(adapterContext.getResources().getString(R.string.url) +objects.get(position)[3].substring((objects.get(position)[3]).indexOf("img")))
                            .thumbnail(0.1f)
                            .into(holder.churchImage);
                    holder.churchImage.setPadding(0,0,0,0);
                }
                else{
                    holder.churchImage.setPadding(15,15,15,15);
                    holder.churchImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    Glide.with(adapterContext)
                            .load(R.drawable.church)
                            .into(holder.churchImage);
                }
                if(!objects.get(position)[4].equals("null")){
                    holder.address.setText(objects.get(position)[4]);
                    holder.address.setVisibility(View.VISIBLE);
                }
                else holder.address.setVisibility(View.INVISIBLE);
                holder.churchName.setTypeface(typeQuicksand);
                holder.town.setTypeface(typeSegoe);
                holder.address.setTypeface(typeSegoe);
                if(convertView.isSelected())
                {
                    holder.setAsMyChurch.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        convertView.setBackgroundColor(adapterContext.getColor(R.color.selection_colour));
                    }
                    else {
                        convertView.setBackgroundColor(adapterContext.getResources().getColor(R.color.selection_colour));
                    }
                }
                else {
                    holder.setAsMyChurch.setVisibility(View.INVISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        convertView.setBackgroundColor(adapterContext.getColor(R.color.white_patch));
                    }
                    else {
                        convertView.setBackgroundColor(adapterContext.getResources().getColor(R.color.white_patch));
                    }
                }
                break;
            //------------------Notifications list---------------------------
      /*      case "Notifications":
                if (convertView == null) {
                    holder = new Holder();
                    convertView = inflater.inflate(R.layout.item_notification, null);
                    holder.notTitle = (TextView) convertView.findViewById(R.id.title);
                    holder.notDesc =(TextView) convertView.findViewById(R.id.description );
                    holder.notDate =(TextView) convertView.findViewById(R.id.date );
                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }
                holder.notTitle.setText(objects.get(position)[0]);
                holder.notDesc.setText(objects.get(position)[1]);
                cal.setTimeInMillis(Long.parseLong(objects.get(position)[2]));
                holder.notDate.setText(formatted.format(cal.getTime()));
                holder.notTitle.setTypeface(typeQuicksand);
                holder.notDesc.setTypeface(typeSegoe);
                holder.notDate.setTypeface(typeSegoe);
                break;*/
            default:
                break;
        }
        return convertView;
    }
}
