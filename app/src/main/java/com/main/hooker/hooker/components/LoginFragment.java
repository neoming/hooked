package com.main.hooker.hooker.components;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.main.hooker.hooker.MainActivity;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.model.UserModel;
import com.main.hooker.hooker.utils.http.ApiFailException;
import com.main.hooker.hooker.views.ProfileActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {


    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.login_qq).setOnClickListener(this);
        view.findViewById(R.id.login_user).setOnClickListener(this);
        view.findViewById(R.id.login_wechat).setOnClickListener(this);
        view.findViewById(R.id.register).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        switch (v.getId()) {
            case R.id.login_qq:
                Toast.makeText(getContext(), "QQ登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_wechat:
                Toast.makeText(getContext(), "WeChat登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_user:
                final View loginDialog = View.inflate(getContext(), R.layout.dialog_login, null);
                ((TextInputEditText) loginDialog.findViewById(R.id.name)).setText("heikezy");
                ((TextInputEditText) loginDialog.findViewById(R.id.password)).setText("12345678");
                builder.setTitle("账户密码登录")
                        .setView(loginDialog)
                        .setPositiveButton("登录", (dialog, which) -> {
                            // for demo
                            String name = ((TextInputEditText) loginDialog.findViewById(R.id.name)).getText().toString();
                            String password = ((TextInputEditText) loginDialog.findViewById(R.id.password)).getText().toString();
                            if (!name.trim().equals("")) {
                                new Thread(() -> {
                                    try {
                                    Log.d("userinfo", "onClick: " + name + "pass:" + password);
                                    UserModel.login(name, password);
                                    getActivity().runOnUiThread(()-> {
                                        Toast.makeText(getContext(), "Login success!", Toast.LENGTH_SHORT).show();
                                    });
                                    ((ProfileActivity) getActivity()).toProfile();
                                    } catch (ApiFailException e) {
                                        getActivity().runOnUiThread(()-> {
                                            Toast.makeText(getContext(), "Failed: " + e.getApiResult().msg, Toast.LENGTH_SHORT).show();
                                        });
                                    }
                                }).start();
                            } else
                                Toast.makeText(getContext(), "请输入正确的用户名", Toast.LENGTH_SHORT).show();
                        })
                        .create()
                        .show();
                break;
            case R.id.register:
                final View registerDialog = View.inflate(getContext(), R.layout.dialog_register, null);
                ((TextInputEditText) registerDialog.findViewById(R.id.name)).setText("xyyyyy");
                ((TextInputEditText) registerDialog.findViewById(R.id.phone)).setText("13524121679");
                ((TextInputEditText) registerDialog.findViewById(R.id.password)).setText("0012345");
                ((TextInputEditText) registerDialog.findViewById(R.id.password2)).setText("0012345");
                builder.setTitle("注册")
                        .setView(registerDialog)
                        .setPositiveButton("登录", (dialog, which) -> {
                            // for demo
                            String name =      ((TextInputEditText) registerDialog.findViewById(R.id.name)).     getText().toString();
                            String phone =     ((TextInputEditText) registerDialog.findViewById(R.id.phone)).    getText().toString();
                            String password =  ((TextInputEditText) registerDialog.findViewById(R.id.password)). getText().toString();
                            String password2 = ((TextInputEditText) registerDialog.findViewById(R.id.password2)).getText().toString();
                            if (!name.trim().equals("") && !password.trim().equals("") && password.equals(password2)) {
                                new Thread(() -> {
                                    try {
                                        Log.d("userinfo", "onClick: " + name + "pass:" + password);
                                        UserModel.register(name, password, phone);
                                        getActivity().runOnUiThread(()-> {
                                            Toast.makeText(getContext(), "Register success!", Toast.LENGTH_SHORT).show();
                                        });
                                    } catch (ApiFailException e) {
                                        getActivity().runOnUiThread(()->{
                                            Toast.makeText(getContext(), "Failed: " + e.getApiResult().msg, Toast.LENGTH_SHORT).show();
                                        });
                                    }
                                }).start();
                            } else
                                Toast.makeText(getContext(), "请正确填写表格", Toast.LENGTH_SHORT).show();
                        })
                        .create()
                        .show();
                break;

        }
    }
}
