package com.innofang.gankiodemo.module.gankdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.bean.GankDetail;
import com.innofang.gankiodemo.constant.GankItem;
import com.innofang.gankiodemo.utils.TimeUtil;

import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/6 12:49
 * Description:
 */

public class GankDetailAdapter extends RecyclerView.Adapter<GankDetailAdapter.GankDetailHolder> {
    private static final String TAG = "GankDetailAdapter";

    private Context mContext;

    private List mList;
    private String mGankItem;
    private OnClickItemListener mOnClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        mOnClickItemListener = onClickItemListener;
    }

    public GankDetailAdapter(Context context, List list, String gankItem) {
        mContext = context;
        mList = list;
        mGankItem = gankItem;
    }

    @Override
    public GankDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gank_detail, parent, false);
        return new GankDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(GankDetailHolder holder, int position) {
        holder.bindHolder(position);
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public class GankDetailHolder extends RecyclerView.ViewHolder {

        public TextView mAuthorTextView;
        public TextView mTitleTextView;

        public GankDetailHolder(View itemView) {
            super(itemView);
            mAuthorTextView = (TextView) itemView.findViewById(R.id.author_text_view);
            mTitleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
        }

        public void bindHolder(int position) {
            if (mGankItem.equals(GankItem.ANDROID)) {
                final GankDetail.ResultsBean.AndroidBean android =
                        (GankDetail.ResultsBean.AndroidBean) mList.get(position);
                mAuthorTextView.setText((CharSequence) android.getWho());
                mTitleTextView.setText(android.getDesc());
                if (null != mOnClickItemListener) {
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnClickItemListener.onClick(android.getUrl(),
                                    android.getDesc(),
                                    android.getWho().toString(),
                                    android.getType(),
                                    TimeUtil.formatPublishAt(android.getPublishedAt()));
                        }
                    });
                }
            } else if (mGankItem.equals(GankItem.IOS)) {
                final GankDetail.ResultsBean.IOSBean iOS =
                        (GankDetail.ResultsBean.IOSBean) mList.get(position);
                mAuthorTextView.setText(iOS.getWho());
                mTitleTextView.setText(iOS.getDesc());
                if (null != mOnClickItemListener) {
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnClickItemListener.onClick(iOS.getUrl(),
                                    iOS.getDesc(),
                                    iOS.getWho(),
                                    iOS.getType(),
                                    TimeUtil.formatPublishAt(iOS.getPublishedAt()));
                        }
                    });
                }
            } else if (mGankItem.equals(GankItem.WEB)) {
                final GankDetail.ResultsBean.前端Bean web = (GankDetail.ResultsBean.前端Bean) mList.get(position);
                mAuthorTextView.setText(web.getWho());
                mTitleTextView.setText(web.getDesc());
                if (null != mOnClickItemListener) {
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnClickItemListener.onClick(web.getUrl(),
                                    web.getDesc(),
                                    web.getWho(),
                                    web.getType(),
                                    TimeUtil.formatPublishAt(web.getPublishedAt()));
                        }
                    });
                }
            } else if (mGankItem.equals(GankItem.EXPAND_RESOURCE)) {
                final GankDetail.ResultsBean.拓展资源Bean expandResource = (GankDetail.ResultsBean.拓展资源Bean) mList.get(position);
                mAuthorTextView.setText(expandResource.getWho());
                mTitleTextView.setText(expandResource.getDesc());
                if (null != mOnClickItemListener) {
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnClickItemListener.onClick(expandResource.getUrl(),
                                    expandResource.getDesc(),
                                    expandResource.getWho(),
                                    expandResource.getType(),
                                    TimeUtil.formatPublishAt(expandResource.getPublishedAt()));
                        }
                    });
                }
            } else if (mGankItem.equals(GankItem.RECOMMEND)) {
                final GankDetail.ResultsBean.瞎推荐Bean recommend = (GankDetail.ResultsBean.瞎推荐Bean) mList.get(position);
                mAuthorTextView.setText(recommend.getWho());
                mTitleTextView.setText(recommend.getDesc());
                if (null != mOnClickItemListener) {
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnClickItemListener.onClick(recommend.getUrl(),
                                    recommend.getDesc(),
                                    recommend.getWho(),
                                    recommend.getType(),
                                    TimeUtil.formatPublishAt(recommend.getPublishedAt()));
                        }
                    });
                }
            } else if (mGankItem.equals(GankItem.APP)) {
                final GankDetail.ResultsBean.AppBean app = (GankDetail.ResultsBean.AppBean) mList.get(position);
                mAuthorTextView.setText(app.getWho());
                mTitleTextView.setText(app.getDesc());
                if (null != mOnClickItemListener) {
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnClickItemListener.onClick(app.getUrl(),
                                    app.getDesc(),
                                    app.getWho(),
                                    app.getType(),
                                    TimeUtil.formatPublishAt(app.getPublishedAt()));
                        }
                    });
                }
            } else if (mGankItem.equals(GankItem.VIDEO)) {
                final GankDetail.ResultsBean.休息视频Bean video = (GankDetail.ResultsBean.休息视频Bean) mList.get(position);
                mAuthorTextView.setText(video.getWho());
                mTitleTextView.setText(video.getDesc());
                if (null != mOnClickItemListener) {
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnClickItemListener.onClick(video.getUrl(),
                                    video.getDesc(),
                                    video.getWho(),
                                    video.getType(),
                                    TimeUtil.formatPublishAt(video.getPublishedAt()));
                        }
                    });
                }
            }
        }

    }

    public interface OnClickItemListener {
        void onClick(String url, String desc, String who, String type, String publishAt);
    }
}
