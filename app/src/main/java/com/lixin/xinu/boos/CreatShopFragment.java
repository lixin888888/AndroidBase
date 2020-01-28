package com.lixin.xinu.boos;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lixin.xinu.beans.WhatDo;
import com.lixin.xinu.R;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import com.lixin.xinu.utils.GifSizeFilter;
import com.lixin.xinu.utils.Glide4Engine;
import com.lixin.xinu.utils.UUIDutils;
import com.lixin.xinu.utils.UploadThread;

import static android.app.Activity.RESULT_OK;

public class CreatShopFragment extends Fragment implements View.OnClickListener  {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Context mContext;
    private Toast toast;

    private EditText shopName,shopPhone ,shopCateroty,shopDescription ;

    private LinearLayout shopsImageContainer;

    private OnFragmentInteractionListener mListener;
    private static Uri imgurl;
    private Dialog dialog;

    // 代表总共要处理的图片
    private ArrayList<File> processFile = new ArrayList<>();
    //    private BossEditHandler egfHandler = null;
    private View.OnClickListener listener = new Listener();

    private View.OnClickListener pop = new PopWindow();

    private PopupWindow popupWindow;

    private boolean popupWindowIsShow = false;

    private LinearLayout  root;

    private static final int REQUEST_CODE_CHOOSE = 23;

    // 保存的是裁剪后的地址集合
    private static ArrayList<Uri> ImageAddress= new ArrayList<>(4);

    private ArrayList<String> qiniuList = new ArrayList<>();

    private int totalcount;

    private Button saveGoods,backto,editGoods;

    // 监听变量
    private boolean allImageReady = false;

    // glide的加载参数
    RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).override(400,400);

    public CreatShopFragment() {
    }

    public static CreatShopFragment newInstance() {
        CreatShopFragment fragment = new CreatShopFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_creat_shop, container, false);
        shopName = view.findViewById(R.id.shop_Title);
        saveGoods = view.findViewById(R.id.publishMessage);
        saveGoods.setOnClickListener(this);
        backto = view.findViewById(R.id.backto);
        shopPhone = view.findViewById(R.id.shop_phone);
        shopCateroty = view.findViewById(R.id.shop_catogery);
        shopCateroty = view.findViewById(R.id.shop_description);
        shopsImageContainer = view.findViewById(R.id.shops_image_container);
        root = view.findViewById(R.id.bigroot);
        InitImage();
        toast = Toast.makeText(mContext, "正在处理图片", Toast.LENGTH_LONG);
        editGoods= view.findViewById(R.id.edit_my_goods);
        editGoods.setOnClickListener(this);
//        egfHandler = new BossEditHandler(new WeakReference<EditGoodsFragment>(this));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.publishMessage:
                PublishGoods();
                break;
            case R.id.backto:

                break;
            case R.id.edit_my_goods:
                MyOnFragmentInteractionListener listener = (MyOnFragmentInteractionListener) mContext;
                WhatDo wd =new WhatDo("edit",v.getId());// 调用 该接口  并 展示 viewImage
                listener.onFragmentInteraction(wd);
                break;
        }
    }

    //   内部接口
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(WhatDo wd);
    }
    //  初始化ImageCobtainer
    void InitImage() {
        ImageView iView = new ImageView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(200, 200);
        iView.setLayoutParams(params);
        iView.setImageResource(R.drawable.ic_add);
        iView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(v);
            }
        });
        shopsImageContainer.addView(iView);
    }

    void chooseImage(View v) {
        // 开启权限
        if (Build.VERSION.SDK_INT >= 23) {
            int i = ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            // 检查是否授权
            if (i != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            }
        }
        // 判断是拍照还是相册
        showPopWindow(v);
    }

    private void takePhoto() {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String filename = timeStampFormat.format(new Date());
        File tempFile = new File(Environment.getExternalStorageDirectory(), filename + ".jpg");
        // imgurl 就是要保存的图片地址
        imgurl = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".my.provider", tempFile);
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgurl);
        startActivityForResult(intent, 0);
    }

    // 查看获取的权限的情况
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                // 判断用户是否点击了不再提醒
                boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                return;
            }

        }
    }

    public void addImage(Uri path,int Position) {
        ImageView iView = new ImageView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(200, 200);
        iView.setLayoutParams(params);
        Glide.with(this)
                .load(path)
                .thumbnail(0.1f)
                .apply(options)
                .into(iView);
        iView.setId(Position);
        iView.setOnClickListener(listener);
        shopsImageContainer.addView(iView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            ImageAddress.add(imgurl);
//            compress(imgurl);
            addImage(imgurl, ImageAddress.size() - 1);
        }
    }

    void compress(Uri path) {
        Luban.with(mContext)
                .load(path)
                .ignoreBy(100)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        toast.show();
                    }

                    @Override
                    public void onSuccess(File file) {
                        // toast.cancel();
                        // 检测数据是否准备好了  一直在检测
                        if(processFile.size()!=ImageAddress.size()){
                            processFile.add(file);
                        }else {
                            uploadToQiniu(processFile,qiniuList);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();
    }
    // 回调这个方法
    public void doGalleryWork(List<Uri> uris){
        int size = uris.size();
        for (int i = 0; i <size ; i++) {
            ImageAddress.add(uris.get(i));
//            compress(imgurl);
            addImage(uris.get(i),ImageAddress.size()-1);
        }
    }
    // 压缩图片
    public void cropImage(Uri uri) {
        // 裁剪完毕后的图片地址
        Uri destinationUri = Uri.fromFile(new File(mContext.getCacheDir(),
                System.currentTimeMillis() + ".jpg"));
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(50);
        UCrop.of(uri, destinationUri)
                .withOptions(options)
                .withAspectRatio(1, 1)
                .start((BossEditActivity) mContext);
    }
    //    static class BossEditHandler extends Handler {
    //        WeakReference<EditGoodsFragment> weakFrag;
    //        public BossEditHandler(WeakReference<EditGoodsFragment> weakFrag) {
    //            this.weakFrag = weakFrag;
    //        }
    //        @Override
    //        public void handleMessage(Message msg) {
    //            EditGoodsFragment agf = weakFrag.get();
    //            if (agf != null) {
    //                if (msg.what == 0x01) {
    //                    agf.addImage(processUri,compressImageAddress.size()-1);
    //                }
    //            }
    //        }
    //    }
    class Listener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            MyOnFragmentInteractionListener listener = (MyOnFragmentInteractionListener) mContext;
            WhatDo wd =new WhatDo("show",v.getId());// 调用 该接口  并 展示 viewImage
            listener.onFragmentInteraction(wd);
        }
    }


    class PopWindow implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.fromGallery:
                    Matisse.from(getActivity())
                            .choose(MimeType.ofAll(), false)
                            .countable(true)
//                          .capture(true)
                            .captureStrategy(
                                    new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider","test"))
                            .maxSelectable(6)
                            .addFilter(new GifSizeFilter(320, 320, 5 * com.zhihu.matisse.filter.Filter.K * com.zhihu.matisse.filter.Filter.K))
                            .gridExpectedSize(
                                    getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                            .thumbnailScale(0.4f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                            .imageEngine(new Glide4Engine())    // for glide-V4
//                                            .setOnSelectedListener(new OnSelectedListener() {
//                                                @Override
//                                                public void onSelected(
//                                                        @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
//                                                    // DO SOMETHING IMMEDIATELY HERE
////                                                    Log.e("onSelected", "onSelected: pathList=" + pathList);
//
//                                                }
//                                            })
                            .originalEnable(true)
                            .maxOriginalSize(10)
                            .autoHideToolbarOnSingleTap(true)
//                                            .setOnCheckedListener(new OnCheckedListener() {
//                                                @Override
//                                                public void onCheck(boolean isChecked) {
//                                                    // DO SOMETHING IMMEDIATELY HERE
////                                                    Log.e("isChecked", "onCheck: isChecked=" + isChecked);
//                                                }
//                                            })
                            .forResult(REQUEST_CODE_CHOOSE);
                    break;
                case R.id.takePhoto:
                    takePhoto();
                    break;
            }
        }
    }

    // 返回所有的照片地址
    ArrayList<Uri> getImageAddress(){
        return ImageAddress;
    }
    /**
     * 显示popwindow的布局方法
     * @param view
     */

    private void showPopWindow(View view){
        if(popupWindowIsShow){
            return;
        }
        View contentView = LayoutInflater.from(getActivity())
                .inflate(R.layout.popwindow_layout,null,false);
        popupWindow = new PopupWindow(contentView,ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,true);
        TextView tp = contentView.findViewById(R.id.takePhoto);
        tp.setOnClickListener(pop);
        TextView fg = contentView.findViewById(R.id.fromGallery);
        fg.setOnClickListener(pop);
        //        仿写
        //产生背景变暗效果
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.4f;
        getActivity().getWindow().setAttributes(lp);
        //点击外面popupWindow消失
        popupWindow.setOutsideTouchable(true);
        // popwindow 获取焦点
        //设置popupWindow消失时的监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
                popupWindowIsShow = false;
            }
        });
//
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.showAtLocation(root,Gravity.CENTER,0,0);
        popupWindowIsShow= true;

    }

    //  一个线程上传七牛云
    void uploadToQiniu(ArrayList<File> dataurl ,ArrayList<String> keyurl){
        //        要上传的地址列表
        ArrayList<String> target = new ArrayList<>();
        target.add("http://172.23.161.166:8080/lixin_war_exploded/home/gettoken");
        target.add("https://upload.qiniup.com/");
        new UploadThread(dataurl,target,keyurl).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(popupWindowIsShow){
            popupWindow.dismiss();
            popupWindowIsShow=false;
        }
    }

    // 新开一个线程 分别去上传图片 和 上传到本地服务器

    public void PublishGoods(){
        // 产生一个数组key 两个线程公用
        final int count = ImageAddress.size();
        for (int i = 0; i < count ; i++) {
            String uuid = UUIDutils.getUUID();
            qiniuList.add(uuid);
        }
        // 上传到七牛云
        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < count; i++) {
                    compress(ImageAddress.get(i));
                }
            }
        }.start();
        // 上传至业务服务器
        new Thread(){
            @Override
            public void run() {
                String[] s = new String[count];
                ArrayList<String> newlist = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    newlist.add("http://six.ijavascript.club/"+qiniuList.get(i));
                }
                // 组合goods的属性
//                Goods goods = new Goods();
//                goods.setGoodsName(goodsName.getText().toString());
//                goods.setGoodsDetail(goodsDescription.getText().toString());
////                goods.setGoodsPrices(Integer.parseInt(goodsPrice.getText().toString()));
//                goods.setGoodsSize(goodsSize.getText().toString());
//                goods.setGoodsImages(newlist);
//                // 帖子类型
//                Log.i("goods",goods.getGoodsName()+goods.getGoodsImages().size());
//                new httpOk().addUrl("http://192.168.43.254:9090/saveGoods")
//                        .addData(goods)
//                        .postJsonHaving();
            }
        }.start();
    }

}