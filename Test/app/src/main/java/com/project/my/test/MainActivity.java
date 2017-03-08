package com.project.my.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.localalbum.R;
import com.example.localalbum.common.ImageUtils;
import com.example.localalbum.ui.LocalAlbum;

public class MainActivity extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button= (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(MainActivity.this, DynamicPost.class);
                startActivity(intent);*/
                Intent intent = new Intent(MainActivity.this, LocalAlbum.class);
                startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
            }
        });
    }
}
