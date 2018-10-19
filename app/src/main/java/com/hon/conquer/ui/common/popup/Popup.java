package com.hon.conquer.ui.common.popup;

import android.content.Context;
import android.support.v7.widget.ListPopupWindow;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.hon.conquer.R;
import com.hon.conquer.util.Util;

import java.util.List;

/**
 * Created by Frank_Hon on 10/19/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class Popup {

    private final ListPopupWindow mPopup;

    public Popup(Context context,View anchor){
        this.mPopup = new ListPopupWindow(context);
        mPopup.setAnchorView(anchor);
        mPopup.setModal(true);
    }

    public void setWidth(int width){
        mPopup.setWidth(width);
    }

    public void setAdapter(ListAdapter adapter){
        mPopup.setAdapter(adapter);
    }

    public void show(){
        mPopup.show();
    }

    public void dismiss(){
        mPopup.dismiss();
    }

    public static class PopupAdapter extends BaseAdapter{

        private Context context;
        private List<PopupItem> list;

        private OnItemClickListener listener;

        public PopupAdapter(Context context, List<PopupItem> list,OnItemClickListener listener){
            this.context=context;
            this.list=list;
            this.listener=listener;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_popup, parent, false);
                viewHolder=new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder= (ViewHolder) view.getTag();
            }

            viewHolder.imageView.setImageResource(list.get(position).getIcon());
            viewHolder.textView.setText(list.get(position).getDesc());
            viewHolder.textView.setOnClickListener(
                    v -> {
                        if (listener!=null){
                            listener.onClick(view,position);
                        }
                    }
            );

            return view;
        }

        private static class ViewHolder{

            ImageView imageView;
            TextView textView;

            ViewHolder(View view){
                imageView = view.findViewById(R.id.iv_icon);
                textView = view.findViewById(R.id.tv_text);
            }
        }

        public interface OnItemClickListener{
            void onClick(View view,int position);
        }
    }
}
