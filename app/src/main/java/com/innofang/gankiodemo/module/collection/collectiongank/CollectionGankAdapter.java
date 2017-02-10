package com.innofang.gankiodemo.module.collection.collectiongank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.bean.Collection;

import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/7 20:02
 * Description:
 */

public class CollectionGankAdapter extends RecyclerView.Adapter<CollectionGankAdapter.CollectionGankHolder> {
    private static final String TAG = "GankAdapter";

    private List<Collection> mList;
    private Context mContext;
    private OnClickGankItemListener mOnClickGankItemListener;

    public CollectionGankAdapter(Context context, List<Collection> list) {
        mContext = context;
        mList = list;
    }

    public void setOnClickGankItemListener(OnClickGankItemListener onClickGankItemListener) {
        mOnClickGankItemListener = onClickGankItemListener;
    }

    public void setList(List<Collection> list) {
        mList = list;
    }

    @Override
    public CollectionGankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gank_with_img, parent, false);
        return new CollectionGankHolder(view);
    }

    @Override
    public void onBindViewHolder(CollectionGankHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: is called");
        holder.bindHolder(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class CollectionGankHolder extends RecyclerView.ViewHolder {

        private TextView mWhoTextView,
                    mPublishAtTextView,
                    mDescTextView,
                    mTypeTextView;

        public CollectionGankHolder(View itemView) {
            super(itemView);
            mWhoTextView = (TextView) itemView.findViewById(R.id.gank_who_text_view);
            mPublishAtTextView = (TextView) itemView.findViewById(R.id.gank_publish_at_text_view);
            mDescTextView = (TextView) itemView.findViewById(R.id.gank_desc_text_view);
            mTypeTextView = (TextView) itemView.findViewById(R.id.gank_type_text_view);
        }

        public void bindHolder(final Collection collection) {
            Log.i(TAG, "bindHolder: collection gank " + collection.toString());
            mWhoTextView.setText(collection.getWho());
            mTypeTextView.setText(collection.getType());
            mPublishAtTextView.setText(collection.getPublishAt());
            mDescTextView.setText(collection.getDesc());

            if (null != mOnClickGankItemListener) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnClickGankItemListener.onClick(
                                collection.getUrl(),
                                collection.getDesc(),
                                collection.getWho(),
                                collection.getType(),
                                collection.getPublishAt());
                    }
                });
            }
        }
    }

    public interface OnClickGankItemListener {
        void onClick(String url, String desc, String who, String type, String publishAt);
    }
}
