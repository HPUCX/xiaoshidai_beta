package com.school.xiaoshidai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.school.xiaoshidai.bean.MyselfBean;
import com.school.xiaoshidi.R;

import java.util.List;

/**
 * Created by hjs on 2016/3/16.
 */
public class MyselfAdapter extends ArrayAdapter<MyselfBean> {
    private int resource;
    public MyselfAdapter(Context context, int resource, List<MyselfBean> list) {
        super(context, resource,list);
        this.resource=resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyselfBean myselfBean=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resource,null);
            viewHolder=new ViewHolder();
            viewHolder.leftImage=(ImageView)view.findViewById(R.id.leftImage);
            viewHolder.textView=(TextView)view.findViewById(R.id.textView);
            viewHolder.rightImage=(ImageView)view.findViewById(R.id.rightImage);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }
        viewHolder.leftImage.setImageResource(myselfBean.getLeftImage());
        viewHolder.rightImage.setImageResource(myselfBean.getRightImage());
        viewHolder.textView.setText(myselfBean.getTextView());

        return view;
    }
   final class ViewHolder{
        ImageView leftImage;
        TextView textView;
       ImageView rightImage;
    }

}
