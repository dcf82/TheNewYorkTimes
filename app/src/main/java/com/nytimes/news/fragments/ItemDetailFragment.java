package com.nytimes.news.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nytimes.news.R;
import com.nytimes.news.activities.ItemDetailActivity;
import com.nytimes.news.activities.ItemListActivity;
import com.nytimes.news.greendao.New;
import com.nytimes.news.helpers.Utility;

/**
 * @author Erick Flores
 */
public class ItemDetailFragment extends Fragment {
    private static final String LOG = "ItemDetailFragment";
    public static final String ARG_ITEM_ID = "item_id";
    private New mItem;
    private WebView mWebView;

    public ItemDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItem = Utility.getNewByID(getArguments().getLong(ARG_ITEM_ID));
        Activity activity = getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mItem.getSection());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_detail, container, false);
    }

    public void onActivityCreated(Bundle onSaveInstanceState) {
        super.onActivityCreated(onSaveInstanceState);

        mWebView = (WebView)getView();
        mWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.i(LOG, "Error While Loading :: " + failingUrl);
                if (getActivity() instanceof ItemDetailActivity) {
                    ((ItemDetailActivity) getActivity()).openLoadBar(false);
                }

                if (getActivity() instanceof ItemListActivity) {
                    ((ItemListActivity) getActivity()).openLoadBar(false);
                }
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(LOG, "Finished Loading :: " + url);
                if (getActivity() instanceof ItemDetailActivity) {
                    ((ItemDetailActivity) getActivity()).openLoadBar(false);
                }

                if (getActivity() instanceof ItemListActivity) {
                    ((ItemListActivity) getActivity()).openLoadBar(false);
                }
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.i(LOG, "Start Loading :: " + url);
                if (getActivity() instanceof ItemDetailActivity) {
                    ((ItemDetailActivity) getActivity()).openLoadBar(true);
                }

                if (getActivity() instanceof ItemListActivity) {
                    ((ItemListActivity) getActivity()).openLoadBar(true);
                }
            }

        });

        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.loadUrl(mItem.getUrl());
    }

    public boolean canWebPageGoBack() {
        return mWebView.canGoBack();
    }

    public WebView getWebPage() {
        return mWebView;
    }
}
