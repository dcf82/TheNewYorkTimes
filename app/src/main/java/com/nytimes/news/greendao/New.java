package com.nytimes.news.greendao;

import com.nytimes.news.greendao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "NEW".
 */
public class New {

    private Long id;
    private String section;
    private String title;
    private String abstract_info;
    private String url;
    private String thumbnail_standard;
    private String item_type;
    private String source;
    private String published_date;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient NewDao myDao;


    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public New() {
    }

    public New(Long id) {
        this.id = id;
    }

    public New(Long id, String section, String title, String abstract_info, String url, String thumbnail_standard, String item_type, String source, String published_date) {
        this.id = id;
        this.section = section;
        this.title = title;
        this.abstract_info = abstract_info;
        this.url = url;
        this.thumbnail_standard = thumbnail_standard;
        this.item_type = item_type;
        this.source = source;
        this.published_date = published_date;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getNewDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstract_info() {
        return abstract_info;
    }

    public void setAbstract_info(String abstract_info) {
        this.abstract_info = abstract_info;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail_standard() {
        return thumbnail_standard;
    }

    public void setThumbnail_standard(String thumbnail_standard) {
        this.thumbnail_standard = thumbnail_standard;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}