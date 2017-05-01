package com.innofang.gankiodemo.module.main.dailygank;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.bean.Luck;
import com.innofang.gankiodemo.constant.URL;
import com.innofang.gankiodemo.utils.StringFormatUtil;

import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/4 01:56
 * Description:
 */

public class DailyGankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "DailyGankAdapter";
    public static final int TYPE_FOOTER = 1;
    public static final int TYPE_NORMAL = 2;

    private Context mContext;
    private List<Luck.ResultsBean> mLuckResutList;
    private OnShowDailyGankClickListener mOnShowDailyGankClickListener;

    public DailyGankAdapter(Context context, List<Luck.ResultsBean> luckResutList) {
        mContext = context;
        mLuckResutList = luckResutList;
    }

    public void setList(List<Luck.ResultsBean> list) {
        mLuckResutList = list;
    }


    public void setOnShowDailyGankClickListener(OnShowDailyGankClickListener onShowDailyGankClickListener) {
        mOnShowDailyGankClickListener = onShowDailyGankClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (mLuckResutList.get(position).get_id() != null) {
            return TYPE_NORMAL;
        } else {
            return TYPE_FOOTER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            default:
            case TYPE_NORMAL:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_daily_gank, parent, false);
                return new DailyGankHolder(view);
            case TYPE_FOOTER:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_footer, parent, false);
                return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).bindHolder();
        }
        if (holder instanceof DailyGankHolder) {
            final Luck.ResultsBean luckResult = mLuckResutList.get(position);
            ((DailyGankHolder) holder).bindHolder(luckResult);
            if (null != mOnShowDailyGankClickListener) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date = StringFormatUtil.formatPublishAt(luckResult.getPublishedAt());
                        String url = URL.DAILY_DATA + date;
                        ViewCompat.setTransitionName(
                                ((DailyGankHolder) holder).mMeizhiImageView, "meizhi");
                        ActivityOptionsCompat options = ActivityOptionsCompat
                                .makeSceneTransitionAnimation(
                                        (Activity) mContext,
                                        ((DailyGankHolder) holder).mMeizhiImageView,
                                        "meizhi");
                        mOnShowDailyGankClickListener.onClick(url, options);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mLuckResutList == null ? 0 : mLuckResutList.size();
    }

    public class DailyGankHolder extends RecyclerView.ViewHolder {

        public TextView mDateTextView;
        public ImageView mMeizhiImageView;

        public DailyGankHolder(View itemView) {
            super(itemView);
            mDateTextView = (TextView) itemView.findViewById(R.id.gank_date_text_view);
            mMeizhiImageView = (ImageView) itemView.findViewById(R.id.meizhi_image_view);
        }

        public void bindHolder(Luck.ResultsBean results) {
            String date = StringFormatUtil.formatPublishAt(results.getPublishedAt());
            String url = results.getUrl() + URL.REQUEST_IMAGE_POSTFIX;
            mDateTextView.setText(date);
            Glide.with(mContext)
                    .load(url)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .crossFade()
                    .into(mMeizhiImageView);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar mProgressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progress);
        }

        public void bindHolder() {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public interface OnShowDailyGankClickListener {
        void onClick(String url, ActivityOptionsCompat options);
    }
}
