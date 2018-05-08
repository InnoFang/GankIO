package com.innofang.gankiodemo.module.main.dailygank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.bean.Luck;
import com.innofang.gankiodemo.constant.URL;
import com.innofang.gankiodemo.utils.StringFormatUtil;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

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

    public DailyGankAdapter(Context context, List<Luck.ResultsBean> luckResutList) {
        mContext = context;
        mLuckResutList = luckResutList;
    }

    public void setList(List<Luck.ResultsBean> list) {
        mLuckResutList = list;
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
        }
    }

    @Override
    public int getItemCount() {
        return mLuckResutList == null ? 0 : mLuckResutList.size();
    }

    public class DailyGankHolder extends RecyclerView.ViewHolder {

        public TextView mDateTextView;
        public ImageView mMeizhiImageView;
        private String date;

        public DailyGankHolder(View itemView) {
            super(itemView);
            mDateTextView = (TextView) itemView.findViewById(R.id.gank_date_text_view);
            mMeizhiImageView = (ImageView) itemView.findViewById(R.id.meizhi_image_view);
        }

        public void bindHolder(Luck.ResultsBean results) {
            date = StringFormatUtil.formatPublishAt(results.getPublishedAt());
            String url = results.getUrl() + URL.REQUEST_IMAGE_POSTFIX;
            mDateTextView.setText(date);
            Glide.with(mContext)
                    .load(url)
                    .apply(new RequestOptions()
                            .skipMemoryCache(false)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    )
                    .transition(withCrossFade())
                    .into(mMeizhiImageView);
        }

        public String getDate() {
            return date;
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

}
