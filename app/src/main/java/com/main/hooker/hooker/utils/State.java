package com.main.hooker.hooker.utils;

import android.content.Context;
import android.util.Log;

import com.main.hooker.hooker.utils.state.MetaItem;
import com.yuyh.easydao.DB;
import com.yuyh.easydao.exception.DBException;
import com.yuyh.easydao.interfaces.IDAO;
import com.yuyh.easydao.interfaces.IDBListener;

import java.util.List;

public class State {
    private static final String database = "dianshu";
    Context mContext;
    private IDBListener listener = (dao, oldVersion, newVersion) -> {
    };

    public State(Context context) {
        mContext = context;
    }

    private IDAO getDB() throws DBException {
        return DB.getInstance(mContext).getDatabase(1, database, listener);
    }

    public void clearAll() {
        DB.getInstance(mContext).deleteDatabase(database);
    }

    private IDAO<MetaItem> getMetaDao() throws DBException {
        return DB.getInstance(mContext).getDatabase(1, database, "meta", MetaItem.class, listener);
    }

    public String getMeta(String key) {
        try {
            IDAO<MetaItem> dao = getMetaDao();
            List<MetaItem> list = dao.findByCondition("key = '" + key + "'");
            if (list == null || list.size() <= 0) {
                return null;
            } else {
                MetaItem item = list.get(0);
                return item.getValue();
            }
        } catch (DBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setMeta(String key, String value) {
        try {
            IDAO<MetaItem> dao = getMetaDao();
            List<MetaItem> list = dao.findByCondition("key = '" + key + "'");
            if (list == null || list.size() <= 0) {
                MetaItem item = new MetaItem(key, value);
                dao.save(item);
            } else {
                MetaItem item = list.get(0);
                item.setValue(value);
                dao.update(item);
            }
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    public void removeMeta(String key) {
        try {
            IDAO<MetaItem> dao = getMetaDao();
            List<MetaItem> list = dao.findByCondition("key = '" + key + "'");
            if (list == null || list.size() > 0) {
                MetaItem item = list.get(0);
                dao.delete(item.getId());
            }
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    public boolean userHasLogin() {
        String uid = getMeta("user_uid");
        String api_token = getMeta("user_api_token");
        return uid != null && api_token != null;
    }

    public void userLogin(int uid, String api_token) {
        setMeta("user_uid", String.valueOf(uid));
        setMeta("user_api_token", api_token);
    }

    public void userLogout() {
        removeMeta("user_uid");
        removeMeta("user_api_token");
    }

    public int userGetUid() {
        return Integer.valueOf(getMeta("user_uid"));
    }

    public String userGetApiToken() {
        return getMeta("user_api_token");
    }

    enum Meta {uid, api_token}
}
