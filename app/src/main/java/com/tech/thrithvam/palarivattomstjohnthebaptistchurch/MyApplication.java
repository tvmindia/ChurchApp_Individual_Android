package com.tech.thrithvam.palarivattomstjohnthebaptistchurch;


import android.app.Application;
import android.content.Context;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

@ReportsCrashes(
        formUri =  "http://jdadmin-001-site4.itempurl.com/WebServices/WebService.asmx/ErrorDetection",
        reportType = HttpSender.Type.JSON,
        httpMethod = HttpSender.Method.POST,
        customReportContent = { ReportField.ANDROID_VERSION,
                ReportField.APP_VERSION_CODE,
                ReportField.AVAILABLE_MEM_SIZE,
                ReportField.BUILD,
                ReportField.CRASH_CONFIGURATION,
                ReportField.LOGCAT,
                ReportField.PACKAGE_NAME,
                ReportField.REPORT_ID},
        mode = ReportingInteractionMode.SILENT
)
public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // The following line triggers the initialization of ACRA
        ACRA.init(this);
    }
}