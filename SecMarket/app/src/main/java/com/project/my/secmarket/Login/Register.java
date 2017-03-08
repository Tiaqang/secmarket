package com.project.my.secmarket.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.my.secmarket.MainActivity;
import com.project.my.secmarket.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Register extends AppCompatActivity {

    private TextInputLayout re_username;
    private TextInputLayout re_password1;
    private TextInputLayout re_password2;
    private TextInputLayout re_telnumber;
    private TextInputLayout re_tong;
    private EditText re_edit_username;
    private EditText re_edit_password1;
    private EditText re_edit_password2;
    private EditText re_edit_telnumber;
    private EditText re_edit_tong;
    private Spinner re_spinner_sex;
    private Spinner re_spinner_major;
    private Button re_submit;
    private String s;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        setListener();
    }

    public void init() {
        re_username = (TextInputLayout) findViewById(R.id.re_username);
        re_password1 = (TextInputLayout) findViewById(R.id.re_password1);
        re_password2 = (TextInputLayout) findViewById(R.id.re_password2);
        re_telnumber = (TextInputLayout) findViewById(R.id.re_telnumber);
        re_tong = (TextInputLayout) findViewById(R.id.re_tong);
        re_edit_username = (EditText) findViewById(R.id.re_edit_username);
        re_edit_password1 = (EditText) findViewById(R.id.re_edit_password1);
        re_edit_password2 = (EditText) findViewById(R.id.re_edit_password2);
        re_edit_telnumber = (EditText) findViewById(R.id.re_edit_telnumber);
        re_edit_tong = (EditText) findViewById(R.id.re_edit_tong);
        re_spinner_major = (Spinner) findViewById(R.id.re_spinner);
        re_spinner_sex = (Spinner) findViewById(R.id.re_spinner_sex);
        re_submit = (Button) findViewById(R.id.re_submit);

    }


    public void setListener() {
        re_edit_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (re_edit_username.getText().toString().trim().length() == 0) {
                        re_username.setError("用户名不能为空！");
                    } else {
                        re_username.setError(null);
                    }
                }
            }
        });

        re_edit_password1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = re_edit_password1.getText().toString().length();
                if (length < 6 || length > 18) {
                    re_password1.setError("请输入合适的长度！");
                } else {
                    re_password1.setError(null);
                }
            }
        });

        re_edit_password2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!re_edit_password2.getText().toString().equals(re_edit_password1.getText().toString())) {
                        re_password2.setError("密码错误！");
                        re_edit_password2.setText(null);
                    } else {
                        re_password2.setError(null);
                    }
                }
            }
        });

        re_edit_telnumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (re_edit_telnumber.getText().length() != 11) {
                        re_telnumber.setError("请输入正确的电话号码");
                        re_edit_telnumber.setError("格式有误");
                    } else {
                        re_telnumber.setError(null);
                    }
                }
            }
        });

        re_edit_tong.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (re_edit_tong.getText().length() != 9) {
                        re_tong.setError("请输入正确的一卡通号");
                        re_edit_tong.setError("格式有误");
                    } else {
                        re_tong.setError(null);
                    }
                }
            }
        });

        re_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sex1 = re_spinner_sex.getSelectedItem().toString();
                String major = re_spinner_major.getSelectedItem().toString();
                String username = re_edit_username.getText().toString();
                String password = re_edit_password2.getText().toString();
                String telnumber = re_edit_telnumber.getText().toString();
                String tong = re_edit_tong.getText().toString();

                String sex;
                if (sex1.equals("男"))
                    sex = "0";
                else
                    sex = "1";

                String url = "";
                try {
                    post(url, username, password, sex, tong, telnumber, major);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    void post(String url1, String s1, String s2, String s3, String s4, String s5, String s6) throws IOException {
        FormBody body = new FormBody.Builder()
                .add("username", s1)
                .add("password", s2)
                .add("sex", s3)
                .add("cardname", s4)
                .add("phone", s5)
                .add("college", s6)
                .build();
        Request request = new Request.Builder().url(url1).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                s = response.body().toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (s.equals("10000")) {
                            Toast.makeText(Register.this, "该用户已被注册", Toast.LENGTH_SHORT).show();
                        } else if (s.equals("10001")) {
                            Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Register.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
