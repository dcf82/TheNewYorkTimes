package com.nytimes.news.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nytimes.news.R;
import com.nytimes.news.activities.ItemDetailActivity;
import com.nytimes.news.fragments.ItemDetailFragment;
import com.nytimes.news.greendao.New;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Erick Flores
 */
public class NyTimesNewsAdapter extends ArrayAdapter<New> implements View.OnClickListener {
    private Context mContext;
    private boolean mTwoPane;
    private New mNew;
    private List<New> mNews;
    private ViewHolder mHolder;
    private LayoutInflater mLayoutInflater;
    private FragmentManager mFragmentManager;

    public NyTimesNewsAdapter(Context context, List<New> news, FragmentManager fragmentManager) {
        super(context, 0, news);
        mContext = context;
        mNews = news;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFragmentManager = fragmentManager;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null || !(view.getTag() instanceof ViewHolder)) {
            view = mLayoutInflater.inflate(R.layout.item_list_content, parent, false);
            mHolder = new ViewHolder(view);
            view.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) view.getTag();
        }

        mNew = mNews.get(position);
        mHolder.mNew = mNew;
        mHolder.mTitle.setText(mNew.getTitle());
        mHolder.mSection.setText(StringEscapeUtils.unescapeJava(mNew.getSection()));
        mHolder.mAbstractDescription.setText(StringEscapeUtils.unescapeJava(mNew.getAbstract_info()));
        if (!mNew.getThumbnail_standard().equals("")) {
            Picasso.with(mContext)
                    .load(mNew.getThumbnail_standard())
                    .placeholder(R.drawable.nytimes_logo)
                    .error(R.drawable.nytimes_logo)
                    .into(mHolder.mImage);
        }  else {
            mHolder.mImage.setImageResource(R.drawable.nytimes_logo);
        }

        view.setOnClickListener(this);

        return view;
    }

    public void addNews(List<New> news) {
        this.mNews.addAll(news);
        notifyDataSetChanged();
    }

    public void cleanNews() {
        this.mNews.clear();
        notifyDataSetChanged();
    }

    public void setTwoPane(boolean mTwoPane) {
        this.mTwoPane = mTwoPane;
    }

    @Override
    public void onClick(View v) {
        ViewHolder vh = (ViewHolder)v.getTag();
        if (vh.mNew.getUrl() == null || vh.mNew.getUrl().length() == 0) return;
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putLong(ItemDetailFragment.ARG_ITEM_ID, vh.mNew.getId());
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            mFragmentManager.beginTransaction().replace(R.id.item_detail_container, fragment).commit();
        } else {
            Context context = v.getContext();
            Intent intent = new Intent(context, ItemDetailActivity.class);
            intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, vh.mNew.getId().longValue());
            context.startActivity(intent);
        }
    }

    public static class ViewHolder {
        @Bind(R.id.image)
        ImageView mImage;
        @Bind(R.id.section)
        TextView mSection;
        @Bind(R.id.abstract_description)
        TextView mAbstractDescription;
        @Bind(R.id.title)
        TextView mTitle;
        public New mNew;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}

