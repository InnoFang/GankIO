package com.innofang.gankiodemo.module.main.category.gank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.bean.Gank;
import com.innofang.gankiodemo.utils.StringFormatUtil;

import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/7 20:02
 * Description:
 */

public class GankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "GankAdapter";
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_NORMAL = 2;

    private List<Gank.ResultsBean> mList;
    private Context mContext;
    private OnClickGankItemListener mOnClickGankItemListener;

    public GankAdapter(Context context, List<Gank.ResultsBean> list) {
        mContext = context;
        mList = list;
    }

    public void setOnClickGankItemListener(OnClickGankItemListener onClickGankItemListener) {
        mOnClickGankItemListener = onClickGankItemListener;
    }

    public void setList(List<Gank.ResultsBean> list) {
        mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).get_id() != null) {
            return TYPE_NORMAL;
        } else {
            Log.i(TAG, "getItemViewType: type footer");
            return TYPE_FOOTER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            default:
            case TYPE_NORMAL:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_gank_with_img, parent, false);
                return new GankHolder(view);
            case TYPE_FOOTER:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_footer, parent, false);
                return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder){
            ((FooterViewHolder) holder).bindHolder();
        }
        if (holder instanceof GankHolder){
            ((GankHolder) holder).bindHolder(mList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class GankHolder extends RecyclerView.ViewHolder {

        private TextView mWhoTextView,
                mPublishAtTextView,
                mDescTextView,
                mTypeTextView;
        private ImageView mImageView;

        public GankHolder(View itemView) {
            super(itemView);
            mWhoTextView = (TextView) itemView.findViewById(R.id.gank_who_text_view);
            mPublishAtTextView = (TextView) itemView.findViewById(R.id.gank_publish_at_text_view);
            mDescTextView = (TextView) itemView.findViewById(R.id.gank_desc_text_view);
            mTypeTextView = (TextView) itemView.findViewById(R.id.gank_type_text_view);
            mImageView = (ImageView) itemView.findViewById(R.id.gank_image_view);
        }

        public void bindHolder(final Gank.ResultsBean result) {
            Log.i(TAG, "bindHolder: Gank " + result.toString());
            mWhoTextView.setText(result.getWho());
            mTypeTextView.setText(result.getType());
            mPublishAtTextView.setText(StringFormatUtil.formatPublishAt(result.getPublishedAt()));
            mDescTextView.setText(result.getDesc());
            if (null != result.getImages() && null != result.getImages().get(0)) {
                mImageView.setVisibility(View.VISIBLE);
                String imgUrl = result.getImages().get(0);
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(R.drawable.image_default)
                        .into(mImageView);
            }
            if (null != mOnClickGankItemListener) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnClickGankItemListener.onClick(
                                result.getUrl(),
                                result.getDesc(),
                                result.getWho(),
                                result.getType(),
                                StringFormatUtil.formatPublishAt(result.getPublishedAt()));
                    }
                });
            }
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

    public interface OnClickGankItemListener {
        void onClick(String url, String desc, String who, String type, String publishAt);
    }
}
