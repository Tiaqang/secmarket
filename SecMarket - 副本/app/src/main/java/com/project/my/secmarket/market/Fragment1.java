package com.project.my.secmarket.market;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.my.secmarket.R;
import com.project.my.secmarket.imagehandler.MainActivity_image;
import com.project.my.secmarket.ninegridimage.Image;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {

    private Fragment1_adapter adapter;
    private ArrayList<market_info_obj> market_obj;

    private RecyclerView recyclerView;
    private Button zan,pinglun,zhuanfa;
    private ArrayList<String> image_bun;
    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment1, container, false);

        market_obj=new ArrayList<market_info_obj>();
        recyclerView= (RecyclerView)view.findViewById(R.id.view_pager);
        zan= (Button) view.findViewById(R.id.zan);
        pinglun= (Button) view.findViewById(R.id.pinglun);
        zhuanfa= (Button) view.findViewById(R.id.zhuanfa);

        image_bun=new ArrayList<>();
        initdata();

        LinearLayoutManager manager=new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(manager);
        manager.setOrientation(OrientationHelper.VERTICAL);
        adapter=new Fragment1_adapter(getActivity(),market_obj);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new Fragment1_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), MainActivity_image.class);
                Bundle bundle=new Bundle();
                //Map<String,Object> map=new HashMap<String,Object>();
                bundle.putString("username", market_obj.get(position).getUsername());
                bundle.putString("time", market_obj.get(position).getTime());
                bundle.putString("price", market_obj.get(position).getPrice());
                bundle.putString("text", market_obj.get(position).getText());
                bundle.putString("telnumber", market_obj.get(position).getTelnumber());
                bundle.putInt("head", market_obj.get(position).getHead_image());
                List<Image> list=new ArrayList<Image>();
                list=market_obj.get(position).getImage();
                for(int i=0;i<image_bun.size();i++){
                    bundle.putString("image"+i,list.get(i).getUrl());
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        /*zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MainActivity_image.class);
                startActivity(intent);
            }
        });*/


        return view;
    }

    public void initdata() {
        String[][] images=new String[][]{
                {"file:///android_asset/img2.jpg","250","250"}
                ,{"file:///android_asset/p3.jpg","250","250"}
                ,{"file:///android_asset/img4.jpg","250","250"}
                ,{"file:///android_asset/p2.jpg","250","250"}
                ,{"file:///android_asset/img6.jpg","250","250"}
                ,{"file:///android_asset/p1.jpg","250","250"}
                ,{"file:///android_asset/img8.jpg","250","250"}
        } ;
        ArrayList<Image> image=new ArrayList<>();
        for(int j=0;j<images.length;j++){
            image_bun.add(images[j][0]);
            image.add(new Image(images[j][0], Integer.parseInt(images[j][1]),Integer.parseInt( images[j][2])));
        }
        market_obj=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            market_obj.add(new market_info_obj("清晨72"+i, "7:2"+i, "咖啡了开始放假卡罗拉是否考虑萨菲隆"+i,
                    "25.00", "15151515108",R.mipmap.ic_launcher,image));
        }
    }
}
