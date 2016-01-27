package com.nytimes.news.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nytimes.news.R;
import com.nytimes.news.adapters.NyTimesNewsAdapter;
import com.nytimes.news.beans.Wrapper;
import com.nytimes.news.controller.Config;
import com.nytimes.news.controller.NyTimesNewsController;
import com.nytimes.news.greendao.New;
import com.nytimes.news.helpers.JSONHandler;
import com.nytimes.news.helpers.Utility;
import com.nytimes.news.interfaces.NyTimesNewsService;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * @author Erick Flores
 */
public class ItemListActivity extends AppCompatActivity implements SwipyRefreshLayout.OnRefreshListener {
    private static final String LOG = "ItemListActivity";
    private static final int LIMIT = 10;

    private boolean mTwoPane;
    private NyTimesNewsAdapter mNyTimesNewsAdapter;
    private List<New> mNews = new ArrayList<>();
    private long offset;
    private boolean downloading;

    @Bind(R.id.swipeRefreshLayout)
    SwipyRefreshLayout mSwipyRefreshLayout;
    @Bind(R.id.swipeDown)
    TextView mSwipeDown;
    @Bind(R.id.progressbar)
    ProgressBar mWaitBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        ListView listView = (ListView)findViewById(R.id.item_list);
        assert listView != null;
        setupListView(listView);

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }
        mNyTimesNewsAdapter.setTwoPane(mTwoPane);

        // Start getting data
        if (savedInstanceState == null) {
            offset = 0;
            updateRequest();
        } else {
            mNyTimesNewsAdapter.addNews(Utility.getNewsList());
            offset = mNews.size();
        }
        changeMessageVisibility();

        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTTOM);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        openProgressBar(downloading);
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void changeMessageVisibility() {
        if (mNyTimesNewsAdapter.getCount() == 0) {
            mSwipeDown.setVisibility(View.VISIBLE);
        } else {
            mSwipeDown.setVisibility(View.GONE);
        }
    }

    // The event is executed in the UI Thread
    public void onEventMainThread(Wrapper wrapper) {
        if (isFinishing()) return;
        mNyTimesNewsAdapter.addNews(wrapper.getResults());
        changeMessageVisibility();
    }

    // The event is executed in the a Background Thread
    public void onEventBackgroundThread(String response) {

        Wrapper wrapper = JSONHandler.processResponse(response);

        if (wrapper.getResults().size() == 0) return;

        Utility.storeNewsList(wrapper.getResults(), offset == 0 ? true : false);

        offset += wrapper.getResults().size();

        EventBus.getDefault().post(wrapper);
    }

    /**
     * Request to the web service
     */
    private void updateRequest() {

        if (downloading) return;

        if (!Utility.haveNetworkConnection()) {
            Utility.showMessage(NyTimesNewsController.getApp().getString(R.string.network_error));
            openProgressBar(false);
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NyTimesNewsService forecastService = retrofit.create(NyTimesNewsService.class);
        Call<ResponseBody> call = forecastService.getNews(LIMIT, offset);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {

                // Close progress bar
                openProgressBar(false);

                // Process the response in a background thread
                try {
                    EventBus.getDefault().post(response.body().string());
                } catch (IOException e) {
                    Log.i(LOG, "Error :: " + e);
                } catch (Exception e) {
                    Log.i(LOG, "Error :: " + e);
                }

            }

            @Override
            public void onFailure(Throwable t) {

                // Close progress bar
                openProgressBar(false);

                // Error message
                Utility.showMessage(NyTimesNewsController.getApp().getString(R.string.network_error));
            }

        });

        openProgressBar(true);

    }

    private void openProgressBar(boolean open) {
        downloading = open;
        mSwipyRefreshLayout.setRefreshing(downloading);
    }

    public void openLoadBar(boolean open) {
        if (open) {
            mWaitBar.setVisibility(View.VISIBLE);
        } else {
            mWaitBar.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupListView(@NonNull ListView listView) {
        mNyTimesNewsAdapter =   new NyTimesNewsAdapter(this, mNews, getSupportFragmentManager());
        listView.setAdapter(mNyTimesNewsAdapter);
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        updateRequest();
    }
}
