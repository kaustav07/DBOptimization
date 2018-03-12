package com.madcoders.chatterjeekaustav.dboptimization;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.madcoders.chatterjeekaustav.dboptimization.DB.Model.DownloadCompleteEvent;
import com.madcoders.chatterjeekaustav.dboptimization.DB.Model.DownloadProgress;
import com.madcoders.chatterjeekaustav.dboptimization.service.DownloadService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DBOptimizerActivity extends AppCompatActivity {

    ProgressDialog mProgressDialog;
    TextView mTextView;
    DonutProgress mDonutProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dboptimizer);
        mTextView = findViewById(R.id.exectime);
        mDonutProgress = findViewById(R.id.donut_progress);
        mDonutProgress.setMax(100);
        mDonutProgress.setVisibility(View.VISIBLE);
        Intent downloadservice = new Intent(this, DownloadService.class);
        startService(downloadservice);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DownloadCompleteEvent event) {
        mDonutProgress.setVisibility(View.GONE);
        mTextView.setText(event.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DownloadProgress event) {
       int progress = (event.getProgress() * 100)/26;
       mDonutProgress.setDonut_progress(String.valueOf(progress));
    }
}
