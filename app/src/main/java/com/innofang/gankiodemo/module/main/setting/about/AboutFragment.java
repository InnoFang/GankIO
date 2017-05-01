package com.innofang.gankiodemo.module.main.setting.about;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.innofang.gankiodemo.R;

/**
 * Author: Inno Fang
 * Time: 2017/2/8 14:25
 * Description:
 */

public class AboutFragment extends DialogFragment {
    private static final String TAG = "AboutFragment";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_about, null, false);

        TextView author = (TextView) view.findViewById(R.id.author);
        TextView projectLocal = (TextView) view.findViewById(R.id.project_local);
        TextView sourceFrom = (TextView) view.findViewById(R.id.source_form);

        author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://github.com/InnoFang"));
                startActivity(Intent.createChooser(intent, "请选择浏览器"));
            }
        });

        projectLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://github.com/InnoFang/GankIO"));
                startActivity(Intent.createChooser(intent, "请选择浏览器"));
            }
        });

        sourceFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://gank.io/"));
                startActivity(Intent.createChooser(intent, "请选择浏览器"));
            }
        });
        dialog.setCancelable(true);
        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        return dialog.show();
    }
}
