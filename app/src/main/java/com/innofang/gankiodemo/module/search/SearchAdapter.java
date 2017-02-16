package com.innofang.gankiodemo.module.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.bean.GankSearch;
import com.innofang.gankiodemo.utils.StringFormatUtil;

import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/10 16:59
 * Description:
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {
    private static final String TAG = "SearchAdapter";

    private Context mContext;
    private List<GankSearch.ResultsBean> mList;
    private OnClickItemListener mOnClickItemListener;

    public SearchAdapter(Context context, List<GankSearch.ResultsBean> list) {
        mList = list;
        mContext = context;
    }

    public void setList(List<GankSearch.ResultsBean> list) {
        mList = list;
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        mOnClickItemListener = onClickItemListener;
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gank, parent, false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchHolder holder, int position) {
        holder.bindHolder(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class SearchHolder extends RecyclerView.ViewHolder {

        private TextView mDescTextView,
                mTypeTextView,
                mWhoTextView,
                mPublishAtTextView;

        public SearchHolder(View itemView) {
            super(itemView);
            mDescTextView = (TextView) itemView.findViewById(R.id.gank_desc_text_view);
            mTypeTextView = (TextView) itemView.findViewById(R.id.gank_type_text_view);
            mWhoTextView = (TextView) itemView.findViewById(R.id.gank_who_text_view);
            mPublishAtTextView = (TextView) itemView.findViewById(R.id.gank_publish_at_text_view);

        }

        public void bindHolder(final GankSearch.ResultsBean result) {
            mDescTextView.setText(result.getDesc());
            mTypeTextView.setText(result.getType());
            mWhoTextView.setText(result.getWho());
            mPublishAtTextView.setText(StringFormatUtil.formatPublishAt(result.getPublishedAt()));
            if (null != mOnClickItemListener) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnClickItemListener.onClick(result.getUrl(),
                                result.getDesc(),
                                result.getWho(),
                                result.getType(),
                                StringFormatUtil.formatPublishAt(result.getPublishedAt()));
                    }
                });
            }
        }
    }

    interface OnClickItemListener {
        void onClick(String url, String desc, String who, String type, String publishAt);
    }
}
