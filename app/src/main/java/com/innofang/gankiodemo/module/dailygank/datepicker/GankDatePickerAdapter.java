package com.innofang.gankiodemo.module.dailygank.datepicker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.constant.URL;
import com.innofang.gankiodemo.utils.TimeUtil;

import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/8 12:30
 * Description:
 */

public class GankDatePickerAdapter extends RecyclerView.Adapter<GankDatePickerAdapter.GankDatePickerHolder>{
    private static final String TAG = "GankDatePickerAdapter";

    private Context mContext;
    private List<String> mDateList;
    private OnClickItemListener mOnClickItemListener;

    public GankDatePickerAdapter(Context context, List<String> dateList) {
        mContext = context;
        mDateList = dateList;
    }

    public void setDateList(List<String> dateList) {
        mDateList = dateList;
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        mOnClickItemListener = onClickItemListener;
    }

    @Override
    public GankDatePickerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_date_picker, parent, false);
        return new GankDatePickerHolder(view);
    }

    @Override
    public void onBindViewHolder(GankDatePickerHolder holder, int position) {
        holder.bindHolder(mDateList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDateList == null ? 0 : mDateList.size();
    }

    public class GankDatePickerHolder extends RecyclerView.ViewHolder{

        private TextView mTextView;

        public GankDatePickerHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.gank_date_text_view);
        }

        public void bindHolder(final String date){
            mTextView.setText(date);
            if (null != mOnClickItemListener){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String d = TimeUtil.formatPublishAt(TimeUtil.formatPublishAt(date));
                        String url = URL.DAILY_DATA + d;
                        mOnClickItemListener.onClick(url);
                    }
                });
            }
        }
    }

    public interface OnClickItemListener{
        void onClick(String url);
    }
}
