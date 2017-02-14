package com.tech.thrithvam.palarivattomstjohnthebaptistchurch;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        // Log.d(TAG, "Refreshed token: " + refreshedToken);

       // FirebaseMessaging.getInstance().subscribeToTopic("common");

        Constants constants=new Constants();
        FirebaseMessaging.getInstance().subscribeToTopic(constants.ChurchID);

    }
}
