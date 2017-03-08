package com.project.my.secmarket.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.my.secmarket.R;

public class My_HomePage extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout home_layout_university;
    private RelativeLayout home_layout_major;
    private RelativeLayout home_layout_tong;
    private RelativeLayout home_layout_telnumber;
    private TextView home_university;
    private TextView home_major;
    private TextView home_tong;
    private TextView home_telnumber;
    private Button home_out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__home_page);

        init();
    }

    public void init(){
        home_layout_major= (RelativeLayout) findViewById(R.id.home_layout_major);
        home_layout_university= (RelativeLayout) findViewById(R.id.home_layout_university);
        home_layout_tong= (RelativeLayout) findViewById(R.id.home_layout_tong);
        home_layout_telnumber= (RelativeLayout) findViewById(R.id.home_layout_telnumber);
        home_university= (TextView) findViewById(R.id.home_university);
        home_major= (TextView) findViewById(R.id.home_major);
        home_tong= (TextView) findViewById(R.id.home_tong);
        home_telnumber= (TextView) findViewById(R.id.home_telnumber);
        home_out= (Button) findViewById(R.id.home_out);

        home_layout_major.setOnClickListener(this);
        home_layout_university.setOnClickListener(this);
        home_layout_tong.setOnClickListener(this);
        home_layout_telnumber.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_layout_university:
                break;
            case R.id.home_layout_major:
                break;
            case R.id.home_layout_tong:
                break;
            case R.id.home_layout_telnumber:
                break;
        }
    }
}
