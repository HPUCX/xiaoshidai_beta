package com.school.xiaoshidai.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.school.xiaoshidai.adapter.ShouyeGridAdapter;
import com.school.xiaoshidai.bean.ShouyeGridBean;
import com.school.xiaoshidai.view.NetworkImageViewHolder;
import com.school.xiaoshidi.MainActivity;
import com.school.xiaoshidi.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hjs on 2016/3/12.
 */
public class ShouYe extends Fragment {
    //顶部广告栏控件,相当于viewpager
    private ConvenientBanner convenientBanner;

    private GridView mGridView;
    //网络图片
    private List<String> networkImages;
    private List<ShouyeGridBean> gridBeanList = new ArrayList<>();
    private ShouyeGridAdapter shouyeGridAdapter;
    private String[] images = {
            "http://file.bmob.cn/M03/E5/F4/oYYBAFbpX-mAHoDSAAGbakhy-IQ642.jpg",
            "http://file.bmob.cn/M03/E6/FD/oYYBAFbqR72AcmASAAFWEdEszvg938.jpg",
            "http://file.bmob.cn/M03/E6/FD/oYYBAFbqR7mAZ-_TAAHsijMlGPo410.jpg",
            "http://file.bmob.cn/M03/E6/FD/oYYBAFbqR7GAQLE4AAEEV4uAmV4853.jpg",
            "http://file.bmob.cn/M03/DE/9F/oYYBAFbkxXqAEozxAAPf0h1D2ew905.png",
            "http://file.bmob.cn/M03/DE/9F/oYYBAFbkxYSAPSvPAAEP2fd-mIc003.jpg"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity activity = ((MainActivity) getActivity());
        initImageLoader();
//        localImages_school.add(R.mipmap.ligong_nihao);
//        localImages_school.add(R.mipmap.hpu1);
//        localImages_school.add(R.mipmap.hpu2);
//        localImages_school.add(R.mipmap.hpu3);
//        localImages_school.add(R.mipmap.hpu4);
        convenientBanner = (ConvenientBanner) this.getView().findViewById(R.id.conven_school_history);
        mGridView = (GridView) this.getView().findViewById(R.id.shouye_grid);

        initGridView();
        shouyeGridAdapter=new ShouyeGridAdapter(getActivity(),R.layout.shouye_grid_item,gridBeanList);
        mGridView.setAdapter(shouyeGridAdapter);
        networkImages = Arrays.asList(images);
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
        //设置
        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }

    private void initGridView() {
        ShouyeGridBean firstRestrount=new ShouyeGridBean("任性吃","学苑 一餐",R.mipmap.xueyuan);
        gridBeanList.add(firstRestrount);
        ShouyeGridBean secondRestrount=new ShouyeGridBean("随性吃","学府 二餐",R.mipmap.xuefu);
        gridBeanList.add(secondRestrount);
        ShouyeGridBean thirdRestrount=new ShouyeGridBean("开心吃","学士 三餐",R.mipmap.xueshi);
        gridBeanList.add(thirdRestrount);
        ShouyeGridBean fourthRestrount=new ShouyeGridBean("大胆吃","学子 四餐",R.mipmap.xuezi);
        gridBeanList.add(fourthRestrount);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.shouye, null, false);

        return mView;
    }

    //初始化网络图片缓存库
    private void initImageLoader() {
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.mipmap.ligong_nihao)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                ((MainActivity) getActivity())).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
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
