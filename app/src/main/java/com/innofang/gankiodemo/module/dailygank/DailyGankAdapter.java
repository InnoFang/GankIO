package com.innofang.gankiodemo.module.dailygank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.bean.Luck;
import com.innofang.gankiodemo.constant.URL;
import com.innofang.gankiodemo.utils.TimeUtil;

import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/4 01:56
 * Description:
 */

public class DailyGankAdapter extends RecyclerView.Adapter<DailyGankAdapter.DailyGankHolder> {

    private static final String TAG = "DailyGankAdapter";

    private Context mContext;
    private List<Luck.ResultsBean> mLuckResutList;
    private OnShowDailyGankClickListener mOnShowDailyGankClickListener;

    public DailyGankAdapter(Context context, List<Luck.ResultsBean> luckResutList) {
        mContext = context;
        mLuckResutList = luckResutList;
    }

    public void setList(List<Luck.ResultsBean> list){
        mLuckResutList = list;
    }


    public void setOnShowDailyGankClickListener(OnShowDailyGankClickListener onShowDailyGankClickListener) {
        mOnShowDailyGankClickListener = onShowDailyGankClickListener;
    }

    @Override
    public DailyGankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_daily_gank, parent, false);
        return new DailyGankHolder(view);
    }

    @Override
    public void onBindViewHolder(DailyGankHolder holder, int position) {
        final Luck.ResultsBean luckResult = mLuckResutList.get(position);
        holder.bindHolder(luckResult);
        if (null != mOnShowDailyGankClickListener) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String date = TimeUtil.formatPublishAt(luckResult.getPublishedAt());
                    String url = URL.DAILY_DATA + date;
                    mOnShowDailyGankClickListener.onClick(url);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mLuckResutList == null ? 0 : mLuckResutList.size();
    }

    public class DailyGankHolder extends RecyclerView.ViewHolder {

        private TextView mDateTextView;
        private ImageView mMeizhiImageView;

        public DailyGankHolder(View itemView) {
            super(itemView);
            mDateTextView = (TextView) itemView.findViewById(R.id.gank_date_text_view);
            mMeizhiImageView = (ImageView) itemView.findViewById(R.id.meizhi_image_view);
        }

        public void bindHolder(Luck.ResultsBean results) {
            String date = TimeUtil.formatPublishAt(results.getPublishedAt());
            String url = results.getUrl() + URL.REQUEST_IMAGE_POSTFIX;
            Log.i(TAG, "bindHolder: image url " + url);
            mDateTextView.setText(date);
            Glide.with(mContext)
                    .load(url)
                    .into(mMeizhiImageView);
        }
    }

    public interface OnShowDailyGankClickListener {
        void onClick(String url);
    }
}
