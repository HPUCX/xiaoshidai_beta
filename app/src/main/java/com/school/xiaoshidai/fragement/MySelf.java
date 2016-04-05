package com.school.xiaoshidai.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.school.xiaoshidai.adapter.MyselfAdapter;
import com.school.xiaoshidai.bean.MyselfBean;
import com.school.xiaoshidi.ContactUs;
import com.school.xiaoshidi.MainActivity;
import com.school.xiaoshidi.MyLove;
import com.school.xiaoshidi.R;
import com.school.xiaoshidi.SendFeedBack;
import com.school.xiaoshidi.Setting;
import com.school.xiaoshidai.third.UserManger;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * Created by hjs on 2016/3/12.
 */
public class MySelf extends Fragment {
    private ListView mListView;
    private List<MyselfBean> myselfBeanList = new ArrayList<MyselfBean>();
    private MyselfAdapter myselfAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView = (ListView) this.getView().findViewById(R.id.myself_list);

        initMyself();
        myselfAdapter=new MyselfAdapter((MainActivity)getActivity(),R.layout.myself_item,myselfBeanList);
        mListView.setAdapter(myselfAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.setClass((MainActivity) getActivity(), UserManger.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.setClass((MainActivity) getActivity(), SendFeedBack.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent.setClass((MainActivity) getActivity(), ContactUs.class);
                        startActivity(intent);
                        break;
                    case 3:
                        //手动更新
                        update_byHand();
                        break;
                    case 4:
                        intent.setClass((MainActivity) getActivity(), MyLove.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent.setClass((MainActivity) getActivity(), Setting.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    //手动更新
    void update_byHand(){
        BmobUpdateAgent.forceUpdate(getActivity());
    }
    void initMyself() {
        MyselfBean myUser = new MyselfBean("账号管理", R.mipmap.myuser,R.mipmap.right_arrow);
        myselfBeanList.add(myUser);
        MyselfBean myFeedback = new MyselfBean("我要吐槽", R.mipmap.feedback,R.mipmap.right_arrow);
        myselfBeanList.add(myFeedback);
        MyselfBean myOurs = new MyselfBean("关于我们", R.mipmap.about_us,R.mipmap.right_arrow);
        myselfBeanList.add(myOurs);
        MyselfBean myUpdate = new MyselfBean("检查更新", R.mipmap.update_hjs,R.mipmap.right_arrow);
        myselfBeanList.add(myUpdate);
        MyselfBean myLove = new MyselfBean("我喜欢", R.mipmap.love,R.mipmap.right_arrow);
        myselfBeanList.add(myLove);
        MyselfBean mySetting  = new MyselfBean("设置", R.mipmap.setting,R.mipmap.right_arrow);
        myselfBeanList.add(mySetting);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.myself, null);

    }
}
