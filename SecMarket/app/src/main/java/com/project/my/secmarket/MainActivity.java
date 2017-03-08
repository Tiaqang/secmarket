package com.project.my.secmarket;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.project.my.secmarket.market.Fragment1;

public class MainActivity extends AppCompatActivity {

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    FragmentManager manager;
    private TextView market_text,aboutme_text,user_text;
    private ImageView market_image,aboutme_image,user_image;
    private RadioGroup group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager=getSupportFragmentManager();
        final FragmentTransaction transaction=manager.beginTransaction();

        group= (RadioGroup) findViewById(R.id.group);
        market_image= (ImageView) findViewById(R.id.market_image);
        aboutme_image= (ImageView) findViewById(R.id.aboutme_image);
        user_image= (ImageView) findViewById(R.id.user_image);
        market_text= (TextView) findViewById(R.id.market_text);
        aboutme_text= (TextView) findViewById(R.id.aboutme_text);
        user_text= (TextView) findViewById(R.id.user_text);

        fragment1=new Fragment1();
        fragment2=new Fragment2();
        fragment3=new Fragment3();
        transaction.add(R.id.frame, fragment1, "fragment1");
        transaction.add(R.id.frame, fragment2, "fragment2");
        transaction.add(R.id.frame,fragment3,"fragment3");
        transaction.show(fragment1);
        transaction.hide(fragment2);
        transaction.hide(fragment3);
        market_text.setTextColor(this.getResources().getColor(R.color.blue_in));
        manager.executePendingTransactions();
        transaction.commit();
    }

    public void click(View view){
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.hide(fragment1);
        transaction.hide(fragment2);
        transaction.hide(fragment3);

        switch (view.getId()){
            case R.id.market:
                transaction.show(fragment1);
                transaction.commit();
                market_text.setTextColor(this.getResources().getColor(R.color.blue_in));
                aboutme_text.setTextColor(this.getResources().getColor(R.color.black));
                user_text.setTextColor(this.getResources().getColor(R.color.black));
                break;
            case R.id.aboutme:
                transaction.show(fragment2);
                transaction.commit();
                market_text.setTextColor(this.getResources().getColor(R.color.black));
                aboutme_text.setTextColor(this.getResources().getColor(R.color.blue_in));
                user_text.setTextColor(this.getResources().getColor(R.color.black));
                break;
            case R.id.user:
                transaction.show(fragment3);
                transaction.commit();
                market_text.setTextColor(this.getResources().getColor(R.color.black));
                aboutme_text.setTextColor(this.getResources().getColor(R.color.black));
                user_text.setTextColor(this.getResources().getColor(R.color.blue_in));
                break;
        }
    }
}
