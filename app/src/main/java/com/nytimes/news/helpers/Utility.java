package com.nytimes.news.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.nytimes.news.controller.NyTimesNewsController;
import com.nytimes.news.greendao.New;
import com.nytimes.news.greendao.NewDao;

import java.util.List;

/**
 * @author Erick Flores
 */
public class Utility {

    /**
     * General utility function to print popup messages
     * @param message Response to be printed on the screen
     */
    public static void showMessage(String message) {
        Toast t = Toast.makeText(NyTimesNewsController.getApp(), message, Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER, 0, 0);
        t.show();
    }

    /**
     * Get the available forecast list
     * @return returns the last list of forecast stored in the data base
     */
    public static List<New> getNewsList() {
        return NyTimesNewsController.getApp().getDAOSession().getNewDao().loadAll();
    }

    /**
     * Stores New York Times News in the SQLite data base
     * @param news Next news
     * @param deleteAll Delete all items for the first time
     */
    public static void storeNewsList(List<New> news, boolean deleteAll) {
        NewDao dao = NyTimesNewsController.getApp().getDAOSession().getNewDao();
        if (deleteAll) {
            dao.deleteAll();
        }
        dao.insertInTx(news);
    }

    /**
     * Store forecast list in the SQLite data base
     * @param id
     */
    public static New getNewByID(long id) {
        NewDao dao = NyTimesNewsController.getApp().getDAOSession().getNewDao();
        return dao.load(id);
    }

    /**
     * Verify if internet connection is available
     */
    public static boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) NyTimesNewsController.getApp()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}
