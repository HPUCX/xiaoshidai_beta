package com.school.xiaoshidai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.school.xiaoshidai.bean.ShouyeGridBean;
import com.school.xiaoshidi.R;

import java.util.List;

/**
 * Created by hjs on 2016/3/17.
 */
public class ShouyeGridAdapter extends ArrayAdapter<ShouyeGridBean> {
    private int resource;
    private Context context;
    public ShouyeGridAdapter(Context context, int resource,List<ShouyeGridBean> list) {
        super(context, resource,list);
        this.context=context;
        this.resource=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ShouyeGridBean shouyeGridBean=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resource,null);
            viewHolder=new ViewHolder();
            viewHolder.shou_text_top=(TextView)view.findViewById(R.id.shou_text_top);
            viewHolder.shouye_text_bottom=(TextView)view.findViewById(R.id.shouye_text_bottom);
            viewHolder.shouye_grid_image=(ImageView)view.findViewById(R.id.shouye_grid_image);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.shou_text_top.setText(shouyeGridBean.getTopTextView());
        viewHolder.shouye_grid_image.setImageResource(shouyeGridBean.getShouyeImage());
        viewHolder.shouye_text_bottom.setText(shouyeGridBean.getBottomTextView());
        return view;
    }
    final class ViewHolder{
        ImageView shouye_grid_image;
        TextView shou_text_top;
        TextView shouye_text_bottom;
    }
}

