package com.example.kyancafe.foodorder;

import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class SettingActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    public Switch myswitch;
    public static SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        sharedPref = new SharedPref(this);
        if(sharedPref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }
        else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ImageView gotoCart = findViewById(R.id.gotocart);
        gotoCart.setVisibility(View.GONE);
        ImageView backIcon = findViewById(R.id.backButton);
        TextView judul = findViewById(R.id.judul);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SettingActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        judul.setText("Settings");

        progressBar = findViewById(R.id.progress_bar);
        myswitch = findViewById(R.id.myswitch);
        if (sharedPref.loadNightModeState()==true){
            myswitch.setChecked(true);
        }
        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    startAsyncTask(buttonView);
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sharedPref.setNightModeState(true);
                            restartApp();
                        }
                    }, 2600);

                }
                else{
                    startAsyncTask(buttonView);
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sharedPref.setNightModeState(false);
                            restartApp();
                        }
                    }, 2600);

                }
            }
        });



    }


    public void startAsyncTask(View v) {
        ExampleAsyncTask task = new ExampleAsyncTask(this);
        task.execute(10);
    }
    private static class ExampleAsyncTask extends AsyncTask<Integer, Integer, String> {
        private WeakReference<SettingActivity> activityWeakReference;
        ExampleAsyncTask(SettingActivity activity) {
            activityWeakReference = new WeakReference<SettingActivity>(activity);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            SettingActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(Integer... integers) {
            for (int i = 0; i < integers[0]; i++) {
                publishProgress((i * 100) / integers[0]);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Loading Done!";
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            SettingActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.progressBar.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SettingActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            activity.progressBar.setProgress(0);
            activity.progressBar.setVisibility(View.INVISIBLE);
        }
    }


    public void restartApp(){
        Intent i = new Intent(getApplicationContext(),SettingActivity.class);
        startActivity(i);
        finish();
    }

}