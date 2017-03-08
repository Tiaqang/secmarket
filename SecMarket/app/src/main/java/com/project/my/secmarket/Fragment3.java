package com.project.my.secmarket;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.localalbum.ui.DynamicPost;
import com.project.my.secmarket.Login.Login;
import com.project.my.secmarket.Login.My_HomePage;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment implements View.OnClickListener{

    private RelativeLayout layout_info;
    private RelativeLayout layout_aboutme;
    private RelativeLayout layout_increase;
    private RelativeLayout layout_record;
    private RelativeLayout layout_intro;
    private ImageView my_head;
    private TextView my_name;
    public Fragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment3, container, false);

        layout_aboutme= (RelativeLayout) view.findViewById(R.id.my_layout_aboutme);
        layout_info= (RelativeLayout) view.findViewById(R.id.my_layout_info);
        layout_increase= (RelativeLayout) view.findViewById(R.id.my_layout_increase);
        layout_intro= (RelativeLayout) view.findViewById(R.id.my_layout_intro);
        layout_record= (RelativeLayout) view.findViewById(R.id.my_layout_record);
        my_head= (ImageView) view.findViewById(R.id.my_head);
        my_name= (TextView) view.findViewById(R.id.my_name);
        init();

        initInfo();
        return view;
    }

    public void init(){
        layout_aboutme.setOnClickListener(this);
        layout_record.setOnClickListener(this);
        layout_intro.setOnClickListener(this);
        layout_info.setOnClickListener(this);
        layout_increase.setOnClickListener(this);
    }

    public void initInfo(){

        my_name.setText("点击登录");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_layout_info:
                if(my_name.getText().toString().equals("点击登录")){
                    Intent intent=new Intent(getActivity(),Login.class);
                    startActivityForResult(intent,1);
                }else{
                    Intent intent=new Intent(getActivity(), My_HomePage.class);
                    startActivity(intent);
                }
                break;
            case R.id.my_layout_aboutme:

                break;
            case R.id.my_layout_increase:
                Intent intent=new Intent(getActivity(), DynamicPost.class);
                startActivity(intent);
                break;
            case R.id.my_layout_record:

                break;
            case R.id.my_layout_intro:

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String username=data.getStringExtra("username");
        if (requestCode==1){
            my_name.setText(username);
        }
    }
}
