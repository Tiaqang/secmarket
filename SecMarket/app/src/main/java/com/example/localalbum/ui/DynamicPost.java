package com.example.localalbum.ui;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.localalbum.common.ImageUtils;
import com.example.localalbum.common.LocalImageHelper;
import com.example.localalbum.common.StringUtils;
import com.example.localalbum.widget.AlbumViewPager;
import com.example.localalbum.widget.FilterImageView;
import com.example.localalbum.widget.MatrixImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.project.my.secmarket.R;
import com.project.my.secmarket.common.BizService;
import com.tencent.upload.task.ITask;
import com.tencent.upload.task.IUploadTaskListener;
import com.tencent.upload.task.UploadTask;
import com.tencent.upload.task.data.FileInfo;
import com.tencent.upload.task.impl.PhotoUploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author linjizong
 * @Description:发布动态界面
 * @date 2015-5-14
 */
public class DynamicPost extends BaseActivity implements OnClickListener, MatrixImageView.OnSingleTapListener {

    private ImageView mBack;//返回键
    private View mSend;//发送
    private EditText mContent;//动态内容编辑框
    private InputMethodManager imm;//软键盘管理
    private TextView textRemain;//字数提示
    private TextView picRemain;//图片数量提示
    private ImageView add;//添加图片按钮
    private LinearLayout picContainer;//图片容器
    private List<LocalImageHelper.LocalFile> pictures = new ArrayList<>();//图片路径数组
    HorizontalScrollView scrollView;//滚动的图片容器
    View editContainer;//动态编辑部分
    View pagerContainer;//图片显示部分

    //显示大图的viewpager 集成到了Actvity中 下面是和viewpager相关的控件
    AlbumViewPager viewpager;//大图显示pager
    ImageView mBackView;//返回/关闭大图
    TextView mCountView;//大图数量提示
    View mHeaderBar;//大图顶部栏
    ImageView delete;//删除按钮

    int size;//小图大小
    int padding;//小图间距
    DisplayImageOptions options;
    private EditText price;
    private EditText telnumber;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private final OkHttpClient client = new OkHttpClient();

    private UploadTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_dynamic);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        //设置ImageLoader参数
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .showImageForEmptyUri(R.drawable.dangkr_no_picture_small)
                .showImageOnFail(R.drawable.dangkr_no_picture_small)
                .showImageOnLoading(R.drawable.dangkr_no_picture_small)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer()).build();
        initViews();
        initData();
    }


    /**
     * @Description： 初始化Views
     */
    private void initViews() {
        // TODO Auto-generated method stub
        mBack = (ImageView) findViewById(R.id.post_back);
        mSend = findViewById(R.id.post_send);
        mContent = (EditText) findViewById(R.id.post_content);
        textRemain = (TextView) findViewById(R.id.post_text_remain);
        picRemain = (TextView) findViewById(R.id.post_pic_remain);
        add = (ImageView) findViewById(R.id.post_add_pic);
        picContainer = (LinearLayout) findViewById(R.id.post_pic_container);
        scrollView = (HorizontalScrollView) findViewById(R.id.post_scrollview);
        viewpager = (AlbumViewPager) findViewById(R.id.albumviewpager);
        mBackView = (ImageView) findViewById(R.id.header_bar_photo_back);
        mCountView = (TextView) findViewById(R.id.header_bar_photo_count);
        mHeaderBar = findViewById(R.id.album_item_header_bar);
        delete = (ImageView) findViewById(R.id.header_bar_photo_delete);
        editContainer = findViewById(R.id.post_edit_container);
        pagerContainer = findViewById(R.id.pagerview);
        price= (EditText) findViewById(R.id.text_price);
        telnumber=(EditText) findViewById(R.id.text_telnumber);
        delete.setVisibility(View.VISIBLE);

        viewpager.setOnPageChangeListener(pageChangeListener);
        viewpager.setOnSingleTapListener(this);
        mBackView.setOnClickListener(this);
        mCountView.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mSend.setOnClickListener(this);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);

        mContent.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable content) {
                textRemain.setText(content.toString().length() + "/140");
            }
        });
    }

    private void initData() {
        size = (int) getResources().getDimension(R.dimen.size_100);
        padding = (int) getResources().getDimension(R.dimen.padding_10);
    }

    @Override
    public void onBackPressed() {
        if (pagerContainer.getVisibility() != View.VISIBLE) {
            //showSaveDialog();
            finish();
        }
        else {
            hideViewPager();
        }
    }



    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.post_back:
               finish();
                break;
            case R.id.header_bar_photo_back:
            case R.id.header_bar_photo_count:
                hideViewPager();
                break;
            case R.id.header_bar_photo_delete:
                final int index = viewpager.getCurrentItem();
//                new AlertDialog(this).builder()
//                        .setTitle("提示")
//                        .setMsg("要删除这张照片吗?")
//                        .setNegativeButton("取消", new OnClickListener() {
//
//                            @Override
//                            public void onClick(View arg0) {
//                                // TODO Auto-generated method stub
//
//                            }
//                        })
//                        .setPositiveButton("确定", new OnClickListener() {
//
//                            @Override
//                            public void onClick(View arg0) {
//                                // TODO Auto-generated method stub
//                                pictures.remove(index);
//                                if (pictures.size() == 9) {
//                                    add.setVisibility(View.GONE);
//                                } else {
//                                    add.setVisibility(View.VISIBLE);
//                                }
//                                if (pictures.size() == 0) {
//                                    hideViewPager();
//                                }
//                                picContainer.removeView(picContainer.getChildAt(index));
//                                picRemain.setText(pictures.size() + "/9");
//                                mCountView.setText((viewpager.getCurrentItem() + 1) + "/" + pictures.size());
//                                viewpager.getAdapter().notifyDataSetChanged();
//                                LocalImageHelper.getInstance().setCurrentSize(pictures.size());
//                            }
//                        }).show();

                break;
            case R.id.post_send:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                String content = mContent.getText().toString();
                if (StringUtils.isEmpty(content) && pictures.isEmpty()) {
                    Toast.makeText(this, "请添写动态内容或至少添加一张图片", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //view.setEnabled(false);
                   // if(mUploadTask == null || mUploadTask.getTaskState() == ITask.TaskState.SUCCEED || mUploadTask.getTaskState() == ITask.TaskState.CANCEL) {
                        // 新建上传任务

                        for(int i=0;i<pictures.size();i++) {
                            Uri path = Uri.parse(pictures.get(i).getThumbnailUri());
                        //Log.d(path.toString(), "111111");
                            String filepath= getPath(path);

                            Log.e(filepath,"111111");

                            //mUploadTask = createUploadTask(filepath);
                            //BizService.getInstance().upload(mUploadTask);
                        }
                /*    } else {
                        // 断线续传
                        BizService.getInstance().resume(mUploadTask);
                    }*/

                }
                break;
            case R.id.post_add_pic:
                Intent intent = new Intent(DynamicPost.this, LocalAlbum.class);
                startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
                break;
            default:
                if (view instanceof FilterImageView) {
                    for (int i = 0; i < picContainer.getChildCount(); i++) {
                        if (view == picContainer.getChildAt(i)) {
                            showViewPager(i);
                        }
                    }
                }
                break;
        }
    }

    protected String getPath(Uri uri)
    {
        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery( uri,
                proj,                 // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null);                 // Order-by clause (ascending by name)

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }



    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            if (viewpager.getAdapter() != null) {
                String text = (position + 1) + "/" + viewpager.getAdapter().getCount();
                mCountView.setText(text);
            } else {
                mCountView.setText("0/0");
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }
    };

    //显示大图pager
    private void showViewPager(int index) {
        pagerContainer.setVisibility(View.VISIBLE);
        editContainer.setVisibility(View.GONE);
        viewpager.setAdapter(viewpager.new LocalViewPagerAdapter(pictures));
        viewpager.setCurrentItem(index);
        mCountView.setText((index + 1) + "/" + pictures.size());
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation((float) 0.9, 1, (float) 0.9, 1, pagerContainer.getWidth() / 2, pagerContainer.getHeight() / 2);
        scaleAnimation.setDuration(200);
        set.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation((float) 0.1, 1);
        alphaAnimation.setDuration(200);
        set.addAnimation(alphaAnimation);
        pagerContainer.startAnimation(set);
    }

    //关闭大图显示
    private void hideViewPager() {
        pagerContainer.setVisibility(View.GONE);
        editContainer.setVisibility(View.VISIBLE);
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, (float) 0.9, 1, (float) 0.9, pagerContainer.getWidth() / 2, pagerContainer.getHeight() / 2);
        scaleAnimation.setDuration(200);
        set.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(200);
        set.addAnimation(alphaAnimation);
        pagerContainer.startAnimation(set);
    }

    @Override
    public void onSingleTap() {
        hideViewPager();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
                if (LocalImageHelper.getInstance().isResultOk()) {
                    LocalImageHelper.getInstance().setResultOk(false);
                    //获取选中的图片
                    List<LocalImageHelper.LocalFile> files = LocalImageHelper.getInstance().getCheckedItems();
                    for (int i = 0; i < files.size(); i++) {
                        LayoutParams params = new LayoutParams(size, size);
                        params.rightMargin = padding;
                        FilterImageView imageView = new FilterImageView(this);
                        imageView.setLayoutParams(params);
                        imageView.setScaleType(ScaleType.CENTER_CROP);
                        ImageLoader.getInstance().displayImage(files.get(i).getThumbnailUri(), new ImageViewAware(imageView), options,
                                null, null, files.get(i).getOrientation());
                        imageView.setOnClickListener(this);
                        pictures.add(files.get(i));
                        if (pictures.size() == 9) {
                            add.setVisibility(View.GONE);
                        } else {
                            add.setVisibility(View.VISIBLE);
                        }
                        picContainer.addView(imageView, picContainer.getChildCount() - 1);
                        picRemain.setText(pictures.size() + "/9");
                        LocalImageHelper.getInstance().setCurrentSize(pictures.size());
                    }
                    //清空选中的图片
                    files.clear();
                    //设置当前选中的图片数量
                    LocalImageHelper.getInstance().setCurrentSize(pictures.size());
                    //延迟滑动至最右边
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                        }
                    }, 50L);
                }
                //清空选中的图片
                LocalImageHelper.getInstance().getCheckedItems().clear();
                break;
            default:
                break;
        }
    }

    void post(String url,String name,String text,String price,String tel)
        throws IOException{
        FormBody formBody=new FormBody.Builder()
                .add("username",name)
                .add("text",text)
                .add("price",price)
                .add("telnumber",tel)
                .build();
        final Request request=new Request.Builder().url(url).post(formBody).build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String res=response.body().toString();
                if(res.equals("")){
                    Toast.makeText(DynamicPost.this,"发布失败",Toast.LENGTH_SHORT).show();
                }
                if(res.equals("")){
                    Toast.makeText(DynamicPost.this,"发布成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
    public UploadTask createUploadTask(String filePath) {
        PhotoUploadTask task = new PhotoUploadTask(filePath,
                new IUploadTaskListener() {

                    @Override
                    public void onUploadSucceed(final FileInfo result) {

                        Log.i("Demo", "upload succeed: " + result.url);
                    }

                    @Override
                    public void onUploadStateChange(ITask.TaskState state) {
                    }

                    @Override
                    public void onUploadProgress(long totalSize, long sendSize) {
                        long p = (long) ((sendSize * 100) / (totalSize * 1.0f));
                        Log.i("Demo", "上传进度: " + p + "%");
                    }

                    @Override
                    public void onUploadFailed(final int errorCode, final String errorMsg) {
                        Log.i("Demo", "上传结果:失败! ret:" + errorCode + " msg:" + errorMsg);
                    }
                }
        );
        task.setBucket(BizService.PHOTO_BUCKET);  // 设置Bucket(可选)
        task.setFileId("test_fileId_" + UUID.randomUUID()); // 为图片自定义FileID(可选)
        task.setAuth(BizService.PHOTO_SIGN);
        //photoUploadMgr.upload(task);  // 开始上传
        return task;
    }
}
