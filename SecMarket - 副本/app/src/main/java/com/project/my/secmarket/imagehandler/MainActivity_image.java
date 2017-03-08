package com.project.my.secmarket.imagehandler;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.my.secmarket.R;
import com.project.my.secmarket.ninegridimage.CustomImageView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity_image extends AppCompatActivity {

    public FocusImageAdapter mAdapter;
    public ViewPager mViewPager;
    public ImageHandler mImageHandler = new ImageHandler(new WeakReference<MainActivity_image>(this));
    private CircleIndicator mCircleIndicator;
    private Button send;
    private TextView username,time,text,price,telnumber;
    private EditText edit;
    private ImageView head,image_viewpager;
    private List<CommentInfo> comment;
    private CommentAdapter commentAdapter;
    private ListView list_comment;
    private String image[];
    private List<ImageView> imageViews;
    String temp="： ";
    int temp1=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matket_info_detail);
        image=new String[9];
        init();

        Bundle bundle=this.getIntent().getExtras();
        String username_str=bundle.getString("username");
        String time_str=bundle.getString("time");
        String text_str=bundle.getString("text");
        String price_str=bundle.getString("price");
        String telnumber_str=bundle.getString("telnumber");
        int head_str=bundle.getInt("head");
        for(int i=0;i<9;i++){
            if(bundle.getString("image"+i)!=null) {
                image[i] = bundle.getString("image" + i);
                temp1++;
            }
        }
        username.setText(username_str);
        time.setText(time_str);
        price.setText(price_str);
        text.setText(text_str);
        telnumber.setText(telnumber_str);
        head.setImageResource(head_str);

        initHandler();
        initComment();

        setListener();
    }
    public ImageView LoadImage(String url){
        CustomImageView imageView=new CustomImageView(this);
        imageView.setImageUrl(url);
        return imageView;
    }

    public void setListener(){
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    comment.add(new CommentInfo("aaaaaa" + temp, edit.getText().toString()));
                    CommentAdapter adapter= (CommentAdapter) list_comment.getAdapter();
                    adapter.notifyDataSetChanged();
                    edit.setText(null);
                }
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//显示输入法
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }

    public void init(){
        send= (Button) findViewById(R.id.send);
        username= (TextView) findViewById(R.id.username_detail);
        time= (TextView) findViewById(R.id.time_detail);
        text= (TextView) findViewById(R.id.text_detail);
        price= (TextView) findViewById(R.id.price_detail);
        telnumber= (TextView) findViewById(R.id.telnumber_detail);
        edit= (EditText) findViewById(R.id.edit_detail);
        head= (ImageView) findViewById(R.id.head_image_detail);
        list_comment= (ListView) findViewById(R.id.list_detail);
        image_viewpager= (ImageView) findViewById(R.id.image_viewpager);
    }

    public void initComment(){

        comment=new ArrayList<CommentInfo>();
        comment.add(new CommentInfo("wwwww" + temp, "hahahahahahha"));
        commentAdapter=new CommentAdapter(this,comment);
        list_comment.setAdapter(commentAdapter);
    }
    public void initHandler() {
        imageViews = new ArrayList<>();
        for (int i = 0; i < temp1; i++) {
            imageViews.add(LoadImage(image[i]));
        }
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        Log.d(temp1+"","11111111");
        if (temp1 == 1) {
            image_viewpager.setBackgroundDrawable(imageViews.get(0).getBackground());
            //mViewPager.setVisibility(View.GONE);
        } else {

            mCircleIndicator = (CircleIndicator) findViewById(R.id.circleIndicator);
            mAdapter = new FocusImageAdapter(this, imageViews);
            mViewPager.setAdapter(mAdapter);
            mCircleIndicator.setViewPager(mViewPager, temp1);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
//                告诉Handler滑倒哪个位置了
                    mImageHandler.sendMessage(Message.obtain(mImageHandler, ImageHandler.IMAGE_PAGE, position, 0));

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    switch (state) {
                        case ViewPager.SCROLL_STATE_DRAGGING:
                            mImageHandler.sendEmptyMessage(ImageHandler.IMAGE_PAUSE);
                            break;
                    }
                }
            });
            mViewPager.setCurrentItem(Integer.MAX_VALUE / 2);
            mImageHandler.sendEmptyMessageDelayed(ImageHandler.IMAGE_UPDATE, ImageHandler.IMAGE_DURATION);

        }
    }
}
