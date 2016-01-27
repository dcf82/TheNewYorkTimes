package com.nytimes.news.helpers;

import android.util.Log;

import com.nytimes.news.beans.Wrapper;
import com.nytimes.news.greendao.New;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Erick Flores
 */
public class JSONHandler {

    private static String LOG = "JSONHandler";

    public static Wrapper processResponse(String response) {
        Wrapper wrapper = new Wrapper();
        JSONObject json;
        JSONArray jarr;

        try {
            json = new JSONObject(response);
            try {
                wrapper.setNumResults(json.getLong("num_results"));
            } catch (Exception e) {
                wrapper.setNumResults(0);
                Log.i(LOG, "Error in getting -> num_results");
            }

            jarr = json.getJSONArray("results");
            New newItem;
            for (int i = 0; i < jarr.length(); i++) {
                newItem = new New();
                json = jarr.getJSONObject(i);

                try {
                    newItem.setSection(json.getString("section"));
                } catch (Exception e) {
                    newItem.setSection("");
                    Log.i(LOG, "Error in getting -> section");
                }

                try {
                    newItem.setTitle(json.getString("title"));
                } catch (Exception e) {
                    newItem.setTitle("");
                    Log.i(LOG, "Error in getting -> title");
                }

                try {
                    newItem.setAbstract_info(json.getString("abstract"));
                } catch (Exception e) {
                    newItem.setAbstract_info("");
                    Log.i(LOG, "Error in getting -> abstract");
                }

                try {
                    newItem.setUrl(json.getString("url"));
                } catch (Exception e) {
                    newItem.setUrl("");
                    Log.i(LOG, "Error in getting -> url");
                }

                try {
                    newItem.setThumbnail_standard(json.getString("thumbnail_standard"));
                } catch (Exception e) {
                    newItem.setThumbnail_standard("");
                    Log.i(LOG, "Error in getting -> thumbnail_standard");
                }

                try {
                    newItem.setItem_type(json.getString("item_type"));
                } catch (Exception e) {
                    newItem.setItem_type("");
                    Log.i(LOG, "Error in getting -> item_type");
                }

                try {
                    newItem.setSource(json.getString("source"));
                } catch (Exception e) {
                    newItem.setSource("");
                    Log.i(LOG, "Error in getting -> source");
                }

                try {
                    newItem.setPublished_date(json.getString("published_date"));
                } catch (Exception e) {
                    newItem.setPublished_date("");
                    Log.i(LOG, "Error in getting -> published_date");
                }

                wrapper.getResults().add(newItem);
            }

        } catch (JSONException e) {
            Log.i(LOG, "Error processing Json String :: " + e);
        } catch (Exception e) {
            Log.i(LOG, "Error processing Json String :: " + e);
        }


        return wrapper;
    }
}
