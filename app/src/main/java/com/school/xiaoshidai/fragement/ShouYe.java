package com.school.xiaoshidai.fragement;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.school.xiaoshidai.adapter.ShouyeGridAdapter;
import com.school.xiaoshidai.bean.ShouyeGridBean;
import com.school.xiaoshidai.bean.ViewPager_Image;
import com.school.xiaoshidai.view.NetworkImageViewHolder;
import com.school.xiaoshidi.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by hjs on 2016/3/12.
 */
public class ShouYe extends Fragment {
    //顶部广告栏控件,相当于viewpager
    private ConvenientBanner convenientBanner;
    //网络图片
    private List<String> networkImages = new ArrayList<>();
    public static String APPID = "3b72404152f4513f21733475e8c71a7c";
    //用于gridview的显示
    private GridView mGridView;
    private List<ShouyeGridBean> gridBeanList = new ArrayList<>();
    private ShouyeGridAdapter shouyeGridAdapter;
    private PullToRefreshListView mPullToRefreshListView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(getActivity(),APPID);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //从网络加载图片
        acquireImage();
        convenientBanner = (ConvenientBanner) this.getView().findViewById(R.id.conven_shouye);
        mGridView = (GridView) this.getView().findViewById(R.id.shouye_grid);

        mPullToRefreshListView=(PullToRefreshListView)this.getView().findViewById(R.id.shouye_PullToRefreshListView);

        //初始化gridview
        initGridView();

        shouyeGridAdapter = new ShouyeGridAdapter(getActivity(), R.layout.shouye_grid_item, gridBeanList);
        mGridView.setAdapter(shouyeGridAdapter);
    }

    public void acquireImage() {
        BmobQuery<ViewPager_Image> query = new BmobQuery<>();
        query.addWhereNotEqualTo("objectId", "ud");
        query.order("createdAt");
        query.setMaxCacheAge(TimeUnit.DAYS.toMillis(1));
        //判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
        boolean isCache = query.hasCachedResult(getActivity(), ViewPager_Image.class);
        if (isCache) {
            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        } else {
            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }
        query.findObjects(getActivity(), new FindListener<ViewPager_Image>() {
            @Override
            public void onSuccess(List<ViewPager_Image> list) {
                List<String> netgroups = new ArrayList<>();
                for (ViewPager_Image viewPager_image : list) {
                    /**
                     * 在服务器上存放的是图片的地址，为了保证图片地址的正确性，需要
                     * 添加双引号。
                     * 在本地获取到了地址后，为了保证能够正确的访问该图片，需要把双引号去掉
                     * 通过如下的方法
                     */
                    String viewPagerString=viewPager_image.getContent();
                    viewPagerString=viewPagerString.substring(1,viewPagerString.length()-1);

                    netgroups.add(viewPagerString);
                    Log.d("hjs", viewPagerString);
                }
                Message msg = Message.obtain();
                msg.obj = netgroups;
                msg.what = 0;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    List<String> netgroups = (List<String>) msg.obj;
                    for (String group : netgroups) {
                        networkImages.add(group);
                    }
                    break;
            }
            initView();
        }
    };

    void initView() {
        initImageLoader();
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageViewHolder>() {
            @Override
            public NetworkImageViewHolder createHolder() {
                return new NetworkImageViewHolder();
            }
        }, networkImages)
                .setPageIndicator(new int[]{
                        R.mipmap.white,
                        R.mipmap.blue
                });
    }
    //初始化网络图片缓存库
    private void initImageLoader() {
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.mipmap.ligong_nihao)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                (getActivity())).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }
    private void initGridView() {
        ShouyeGridBean firstRestrount = new ShouyeGridBean("任性吃", "学苑 一餐", R.mipmap.xueyuan);
        gridBeanList.add(firstRestrount);
        ShouyeGridBean secondRestrount = new ShouyeGridBean("随性吃", "学府 二餐", R.mipmap.xuefu);
        gridBeanList.add(secondRestrount);
        ShouyeGridBean thirdRestrount = new ShouyeGridBean("开心吃", "学士 三餐", R.mipmap.xueshi);
        gridBeanList.add(thirdRestrount);
        ShouyeGridBean fourthRestrount = new ShouyeGridBean("大胆吃", "学子 四餐", R.mipmap.xuezi);
        gridBeanList.add(fourthRestrount);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.shouye, null, false);

        return mView;
    }



    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(3000);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }

}
