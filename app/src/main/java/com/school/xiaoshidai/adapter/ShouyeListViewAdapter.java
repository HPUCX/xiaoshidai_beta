package com.school.xiaoshidai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.school.xiaoshidai.bean.ShouyeListViewBean;
import com.school.xiaoshidi.R;

import java.util.List;

/**
 * Created by hjs on 2016/3/26.
 */
public class ShouyeListViewAdapter extends ArrayAdapter<ShouyeListViewBean> {
    private int resource;
    public ShouyeListViewAdapter(Context context, int resource, List<ShouyeListViewBean> list) {
        super(context, resource,list);
        this.resource=resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShouyeListViewBean shouyeListViewBean=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resource,null);
            viewHolder=new ViewHolder();
            viewHolder.listImage=(ImageView)view.findViewById(R.id.shouye_list_image);
            viewHolder.topTextView=(TextView)view.findViewById(R.id.shou_list_text_top);
            viewHolder.bottomTextViTew=(TextView)view.findViewById(R.id.shouye_list_text_bottom);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }
        viewHolder.listImage.setImageResource(shouyeListViewBean.getShouyeListImage());
        viewHolder.topTextView.setText(shouyeListViewBean.getTopTextView());
        viewHolder.bottomTextViTew.setText(shouyeListViewBean.getBottomTextView());
        return view;
    }
    final class ViewHolder{
        ImageView listImage;
        TextView topTextView;
        TextView bottomTextViTew;
    }

}
