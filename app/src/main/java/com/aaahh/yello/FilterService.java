package com.aaahh.yello;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Binder;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by aaahh on 8/26/14. Edited 9/13/14
 */
public class FilterService extends Service {
    public static FilterService mThis;
    public static View vw;
    public static GradientDrawable gt;
    public final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("android.intent.action.CONFIGURATION_CHANGED")) {
                Common.O = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
                FilterService.mThis.setRotation();
            }
        }
    };
    private final IBinder rBinder = new LocalBinder();
    public WindowManager.LayoutParams localLayoutParams;
    public WindowManager localWindowManager;

    public void addView() {
//        DatabaseActivity db = new DatabaseActivity(this);
        //      db.open();
        //    Cursor A = db.getTitle(8);
        //  A.moveToFirst();
        //Cursor H = db.getTitle(6);
        //moveToFirst();
        // Cursor Ar = db.getTitle(7);
        //Ar.moveToFirst();
        //Log.d("lololoololololol", A.getString(3));
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displaymetrics);
        float screenWidth = displaymetrics.widthPixels;
        float screenHeight = displaymetrics.heightPixels;
        //    Common.Height = ;
        //  Common.Area = (;
        //  db.close();
        vw = new View(this);
        localLayoutParams = new WindowManager.LayoutParams((int) screenWidth, (int) screenHeight, 2006, 1288, -3);
        localWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        localLayoutParams.height = (int) ((Common.Height / 100f) * screenHeight);
        localLayoutParams.width = (int) screenWidth;
        localLayoutParams.y = (int) ((((((Common.Area) * 2) / 100f)) * (screenHeight / 2)) * -1);
        localLayoutParams.x = 0;
        //TODO: fix this \/
        int i = Common.Color;
        String hexColor = String.format("#%06X", (0xFFFFFF & i));
        String fade = hexColor.replace("#", "#00");
        if (MainActivity.ToggleButton2.isChecked()) {
            int b = (Color.parseColor(fade));
            gt = new GradientDrawable();
            if (Common.GradientType.contains("1")) {
                int colors[] = {b, i};
                gt.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                gt.setColors(colors);
            }
            if (Common.GradientType.contains("2")) {
                int colors[] = {b, i, b};
                gt.setColors(colors);
            }
            if (Common.GradientType.contains("3")) {
                int colors[] = {b, i};
                gt.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                gt.setColors(colors);
            }
            vw.setBackground(gt);
        } else {
            vw.setBackgroundColor(i);
        }
        vw.getBackground().setAlpha(Common.Alpha);
        vw.getBackground().setDither(true);
        vw.setRotation(0);
        localWindowManager.addView(vw, localLayoutParams);
        if (!Common.Notif) {
            startNotification();
        }
        rotationReceiver();
    }

    public void endNotification() {
        startForeground(0, new Notification());
    }

    public IBinder onBind(Intent paramIntent) {
        return this.rBinder;
    }

    @SuppressLint({"NewApi"})
    public void onCreate() {
        super.onCreate();
        mThis = this;
    }

    public void onDestroy() {
        super.onDestroy();
        removeView();
    }

    public void removeView() {
        if (vw != null) {
            ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).removeView(vw);
        }
        vw = null;
    }

    public void setAlpha(int paramInt) {
        if (vw == null) {
            return;
        }
        vw.getBackground().setAlpha(paramInt);
    }

    public void setHeight(int paramInt) {
        if (!(vw == null)) {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displaymetrics);
            float screenHeight = displaymetrics.heightPixels;
            localLayoutParams.height = ((int) ((paramInt / 100f) * screenHeight));
            localWindowManager.updateViewLayout(vw, localLayoutParams);
        }
    }

    public void setArea(int paramInt) {
        if (!(vw == null)) {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displaymetrics);
            float screenHeight = displaymetrics.heightPixels;
            localLayoutParams.y = (int) ((((paramInt - 50) * 2) / 100f) * (screenHeight / 2) * -1);
            localWindowManager.updateViewLayout(vw, localLayoutParams);
        }
    }

    public void setColor() {
        if (vw == null) {
        } else {
            int i = Common.Color;
            String hexColor = String.format("#%06X", (0xFFFFFF & i));
            String fade = hexColor.replace("#", "#00");
            if (MainActivity.ToggleButton2.isChecked()) {
                int b = (Color.parseColor(fade));
                if (Common.GradientType.contains("1")) {
                    int colors[] = {b, i};
                    gt.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                    gt.setColors(colors);
                }
                if (Common.GradientType.contains("2")) {
                    int colors[] = {b, i, b};
                    gt.setColors(colors);
                    gt.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                }
                if (Common.GradientType.contains("3")) {
                    int colors[] = {b, i};
                    gt.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                    gt.setColors(colors);
                }
                vw.setBackground(gt);
            } else {
                vw.setBackgroundColor(i);
            }
            vw.getBackground().setAlpha(Common.Alpha);
        }
        // ImageView imageView = (ImageView) getResources(R.id.textureView);
    }

    public void setRotation() {
        if (vw == null) {
        } else {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displaymetrics);
            float screenWidth = displaymetrics.widthPixels;
            float screenHeight = displaymetrics.heightPixels;
            //TODO: fix this \/
            int i = Common.Color;
            String hexColor = String.format("#%06X", (0xFFFFFF & i));
            String fade = hexColor.replace("#", "#00");
            Log.d("2", String.valueOf(i));
            Log.d("h", String.valueOf(Common.O));
            if (Common.O == 0) {
                localLayoutParams.height = (int) ((Common.Height / 100f) * screenHeight);
                localLayoutParams.width = (int) screenWidth;
                localLayoutParams.y = (int) ((((((Common.Area) * 2) / 100f)) * (screenHeight / 2)) * -1);
                localLayoutParams.x = 0;

                if (MainActivity.ToggleButton2.isChecked()) {
                    int b = (Color.parseColor(fade));
                    gt = new GradientDrawable();
                    if (Common.GradientType.contains("1")) {
                        int colors[] = {b, i};
                        gt.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                        gt.setColors(colors);
                    }
                    if (Common.GradientType.contains("2")) {
                        int colors[] = {b, i, b};
                        gt.setColors(colors);
                    }
                    if (Common.GradientType.contains("3")) {
                        int colors[] = {b, i};
                        gt.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                        gt.setColors(colors);
                    }
                    vw.setBackground(gt);
                } else {
                    vw.setBackgroundColor(i);
                }
            }
            if (Common.O == 1) {
                localLayoutParams.width = (int) ((Common.Height / 100f) * screenWidth);
                localLayoutParams.height = (int) screenWidth;
                localLayoutParams.x = (int) ((((((Common.Area) * 2) / 100f)) * (screenHeight / 2)) * -1);
                localLayoutParams.y = 0;

                if (MainActivity.ToggleButton2.isChecked()) {
                    int b = (Color.parseColor(fade));
                    gt = new GradientDrawable();
                    if (Common.GradientType.contains("1")) {
                        int colors[] = {b, i};
                        gt.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                        gt.setColors(colors);
                    }
                    if (Common.GradientType.contains("2")) {
                        int colors[] = {b, i, b};
                        gt.setColors(colors);
                        gt.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                    }
                    if (Common.GradientType.contains("3")) {
                        int colors[] = {b, i};
                        gt.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
                        gt.setColors(colors);
                    }
                    vw.setBackground(gt);
                } else {
                    vw.setBackgroundColor(i);
                }
            }
            if (Common.O == 2) {
                localLayoutParams.height = ((int) ((Common.Height / 100f) * screenHeight) * -1);
                localLayoutParams.width = (int) screenWidth;
                localLayoutParams.x = 0;
                localLayoutParams.y = (int) (((((Common.Area) * 2) / 100f)) * (screenHeight / 2) * -1);

                if (MainActivity.ToggleButton2.isChecked()) {
                    int b = (Color.parseColor(fade));
                    gt = new GradientDrawable();
                    if (Common.GradientType.contains("1")) {
                        int colors[] = {b, i};
                        gt.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                        gt.setColors(colors);
                    }
                    if (Common.GradientType.contains("2")) {
                        int colors[] = {b, i, b};
                        gt.setColors(colors);
                    }
                    if (Common.GradientType.contains("3")) {
                        int colors[] = {b, i};
                        gt.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                        gt.setColors(colors);
                    }
                    vw.setBackground(gt);
                } else {
                    vw.setBackgroundColor(i);
                }
            }
            if (Common.O == 3) {
                localLayoutParams.width = (int) ((Common.Height / 100f) * screenWidth);
                localLayoutParams.height = (int) screenWidth;
                localLayoutParams.x = (int) (((((Common.Area) * 2) / 100f)) * (screenHeight / 2));
                localLayoutParams.y = 0;
                Log.d("m", String.valueOf(screenHeight));
                if (MainActivity.ToggleButton2.isChecked()) {
                    int b = (Color.parseColor(fade));
                    gt = new GradientDrawable();
                    if (Common.GradientType.contains("1")) {
                        int colors[] = {b, i};
                        gt.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
                        gt.setColors(colors);
                    }
                    if (Common.GradientType.contains("2")) {
                        int colors[] = {b, i, b};
                        gt.setColors(colors);
                        gt.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
                    }
                    if (Common.GradientType.contains("3")) {
                        int colors[] = {b, i};
                        gt.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                        gt.setColors(colors);
                    }
                    vw.setBackground(gt);
                } else {
                    vw.setBackgroundColor(i);

                }
            }
            localWindowManager.updateViewLayout(vw, localLayoutParams);
            vw.getBackground().setAlpha(Common.Alpha);
        }
    }

    public void startNotification() {
        Intent localIntent = new Intent(getApplicationContext(), MainActivity.class);
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent localPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, localIntent, 0);
        NotificationManager n = ((NotificationManager) getSystemService(NOTIFICATION_SERVICE));
        Notification localNotification = new Notification.Builder(this)
                .setContentTitle("Filter Screen")
                .setContentText("Activated")
                .setSmallIcon(android.R.drawable.ic_input_get)
                .setContentIntent(localPendingIntent)
                .build();
        n.notify(1, localNotification);
        startForeground(1, localNotification);
        Common.Notif = true;
    }

    public void rotationReceiver() {
        IntentFilter filter1 = new IntentFilter("android.intent.action.CONFIGURATION_CHANGED");
        if (Common.Receiver) {
            registerReceiver(myReceiver, filter1);
        }
        if (!Common.Receiver) {
            unregisterReceiver(myReceiver);
        }
    }

    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        FilterService getService() {
            return FilterService.this;
        }
    }
}