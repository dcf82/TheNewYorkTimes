package com.nytimes.news.controller;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.nytimes.news.greendao.DaoMaster;
import com.nytimes.news.greendao.DaoSession;

/**
 * @author Erick Flores
 */
public class NyTimesNewsController extends Application {
    private static final String DATABASE_NAME = "NyTimesNewsController.db";
    private static NyTimesNewsController mAppContext;

    private SQLiteDatabase mDatabase;

    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private SharedPreferences mSharedPreferences;

    public void onCreate() {
        super.onCreate();
        openSQLiteDatabase();
        mAppContext = this;
    }

    public static NyTimesNewsController getApp() {
        return mAppContext;
    }

    public SQLiteDatabase openSQLiteDatabase() throws SQLiteException {
        if (mDatabase == null) {
            mDatabase = new DaoMaster.DevOpenHelper(this, DATABASE_NAME,
                    null).getWritableDatabase();
        } else if (!mDatabase.isOpen()) {
            mDatabase = new DaoMaster.DevOpenHelper(this, DATABASE_NAME,
                    null).getWritableDatabase();
        }
        return mDatabase;
    }

    public DaoSession getDAOSession() {
        DaoMaster dm = getDAOMaster();
        if (mDaoSession == null) {
            mDaoSession = dm.newSession();
        }
        return mDaoSession;
    }

    public DaoMaster getDAOMaster() {
        SQLiteDatabase d = openSQLiteDatabase();

        if (mDaoMaster == null) {
            mDaoMaster = new DaoMaster(d);
        } else if (mDaoMaster.getDatabase() != d) {
            mDaoMaster = new DaoMaster(d);
        }

        return mDaoMaster;
    }

}
