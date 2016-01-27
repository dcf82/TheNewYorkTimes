package com.nytimes.news.greendao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.nytimes.news.greendao.New;

import com.nytimes.news.greendao.NewDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig newDaoConfig;

    private final NewDao newDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        newDaoConfig = daoConfigMap.get(NewDao.class).clone();
        newDaoConfig.initIdentityScope(type);

        newDao = new NewDao(newDaoConfig, this);

        registerDao(New.class, newDao);
    }
    
    public void clear() {
        newDaoConfig.getIdentityScope().clear();
    }

    public NewDao getNewDao() {
        return newDao;
    }

}