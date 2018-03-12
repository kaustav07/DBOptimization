package com.madcoders.chatterjeekaustav.dboptimization.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.madcoders.chatterjeekaustav.dboptimization.DB.Model.DaoSession;
import com.madcoders.chatterjeekaustav.dboptimization.DB.Model.DictionaryData;
import com.madcoders.chatterjeekaustav.dboptimization.DB.Model.DictionaryDataDao;
import com.madcoders.chatterjeekaustav.dboptimization.DB.Model.DownloadCompleteEvent;
import com.madcoders.chatterjeekaustav.dboptimization.DB.Model.DownloadProgress;
import com.madcoders.chatterjeekaustav.dboptimization.DBApp;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DownloadService extends IntentService {

    public DownloadService() {
        super("DownloadService");
    }


    DaoSession mDaoSession;
    DictionaryDataDao mDictionaryDataDao;
    OkHttpClient mOkHttpClient;

    String BASE_URL = "http://unreal3112.16mb.com/wb1913_";

    @Override
    protected void onHandleIntent(Intent intent) {
        List<Long> execTime = new ArrayList<>();
        int i=0;
        mDaoSession = ((DBApp) getApplication()).getDaoSession();
        mDictionaryDataDao = mDaoSession.getDictionaryDataDao();
        mOkHttpClient = new OkHttpClient();
        mDictionaryDataDao.deleteAll();
        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {

            String downloadURL = BASE_URL + alphabet + ".html";

            Request request = new Request.Builder()
                    .url(downloadURL)
                    .build();

            try {
                Response response = mOkHttpClient.newCall(request).execute();
                DictionaryData dictionaryData = new DictionaryData(String.valueOf(alphabet), response.body().toString());
                Long timebefore = System.currentTimeMillis();
                mDictionaryDataDao.insert(dictionaryData);
                Long timeafter = System.currentTimeMillis();
                Long timediff = timeafter - timebefore;
                execTime.add(timediff);
                EventBus.getDefault().post(new DownloadProgress(++i));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        Long minExec = Collections.min(execTime);
        Long totaltime = 0L;
        for (Long aLong : execTime) {
            totaltime += aLong;
        }

        Long execTimeFinal = totaltime/minExec;
        DownloadCompleteEvent completeEvent = new DownloadCompleteEvent("exec_time = "+execTimeFinal+" Units\nMinimum time in all submission - "+minExec+"ms(1 unit)");
        EventBus.getDefault().post(completeEvent);
    }

}

