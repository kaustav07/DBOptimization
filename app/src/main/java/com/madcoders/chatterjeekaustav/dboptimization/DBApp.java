package com.madcoders.chatterjeekaustav.dboptimization;

import android.app.Application;

import com.madcoders.chatterjeekaustav.dboptimization.DB.Model.DaoMaster;
import com.madcoders.chatterjeekaustav.dboptimization.DB.Model.DaoSession;
import com.madcoders.chatterjeekaustav.dboptimization.Util.Constants;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Kaustav on 11-03-2018.
 */

public class DBApp extends Application {

    DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DATABASE_NAME);
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession(){
        return mDaoSession;
    }
}
