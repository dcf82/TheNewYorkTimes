package com.nytimes.news.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.nytimes.news.R;
import com.nytimes.news.fragments.ItemDetailFragment;

/**
 * @author Erick Flores
 */
public class ItemDetailActivity extends AppCompatActivity {

    private ProgressBar mWaitBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        mWaitBar = (ProgressBar)findViewById(R.id.progressbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putLong(ItemDetailFragment.ARG_ITEM_ID, getIntent().getLongExtra
                    (ItemDetailFragment.ARG_ITEM_ID, 0));
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment, "ItemDetailFragment")
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentByTag("ItemDetailFragment");
        if (f != null && f.isResumed() && f instanceof ItemDetailFragment && (
                (ItemDetailFragment)f).canWebPageGoBack()) {
            ((ItemDetailFragment)f).getWebPage().goBack();
        } else {
            super.onBackPressed();
        }
    }

    public void openLoadBar(boolean open) {
        if (open) {
            mWaitBar.setVisibility(View.VISIBLE);
        } else {
            mWaitBar.setVisibility(View.GONE);
        }
    }
}
