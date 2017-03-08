package com.project.my.secmarket.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.my.secmarket.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Login extends AppCompatActivity {

    private ImageView login_head;
    private EditText login_edit_username;
    private EditText login_edit_password;
    private Button login_login;
    private Button login_redister;
    private TextInputLayout login_username,login_password;
    private String username,password;
    private final OkHttpClient client=new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        setListener();
    }


    public void init(){

        login_head= (ImageView) findViewById(R.id.login_head);
        login_edit_username= (EditText) findViewById(R.id.login_edit_username);
        login_edit_password= (EditText) findViewById(R.id.login_edit_password);
        login_login= (Button) findViewById(R.id.login_login);
        login_redister= (Button) findViewById(R.id.login_register);
        login_username= (TextInputLayout) findViewById(R.id.login_username);
        login_password= (TextInputLayout) findViewById(R.id.login_password);
    }

    public void setListener(){
        login_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(login_edit_username.getText().toString().length()==0){
                        login_edit_username.setError("用户名不能为空！");
                        login_username.setError("用户名不能为空！");
                    }else{
                        login_username.setError(null);
                    }
                }
                username=login_edit_username.getText().toString();
            }
        });

        login_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(login_edit_password.getText().toString().length()==0){
                        login_edit_password.setError("密码不能为空！");
                        login_password.setError("密码不能为空！");
                    }else{
                        login_password.setError(null);
                    }
                }
                //password=
            }
        });

        login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=login_edit_username.getText().toString();
                password=login_edit_password.getText().toString();
                String url="";
                try{
                    post(url,username,password);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        login_redister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
    }

    void post(String url,String name,String psw)throws IOException{
        FormBody formBody=new FormBody.Builder()
                .add("username",name)
                .add("password",psw)
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
                    Toast.makeText(Login.this,"请检查用户名",Toast.LENGTH_SHORT).show();
                }
                if(res.equals("")){
                    Toast.makeText(Login.this,"密码错误",Toast.LENGTH_SHORT).show();
                }
                if(res.equals("")){
                    Toast.makeText(Login.this,"登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    intent.putExtra("username",username);
                    setResult(1,intent);
                    finish();
                }
            }
        });
    }
}
