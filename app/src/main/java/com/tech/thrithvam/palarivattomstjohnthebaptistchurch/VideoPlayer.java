package com.tech.thrithvam.palarivattomstjohnthebaptistchurch;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class VideoPlayer extends AppCompatActivity {
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        extras=getIntent().getExtras();
        String vidAddress=extras.getString("URL");
        Uri vidUri = Uri.parse(vidAddress);

        VideoView vidView=(VideoView)findViewById(R.id.video_view);
        vidView.setVideoURI(vidUri);

        vidView.start();


        MediaController vidControl = new MediaController(this);

        RelativeLayout playerScreen=(RelativeLayout)findViewById(R.id.activity_video_player);
        vidControl.setAnchorView(playerScreen);
        vidView.setMediaController(vidControl);
    }
}
