package com.main.hooker.hooker.components;

import android.support.design.widget.TextInputEditText;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.app.AlertDialog;

import com.main.hooker.hooker.R;
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
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_qq:
                Toast.makeText(getContext(), "QQ登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_wechat:
                Toast.makeText(getContext(), "WeChat登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_user:
                final View loginDialog = View.inflate(getContext(), R.layout.dialog_login, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Login")
                        .setView(loginDialog)
                        .setPositiveButton("登录", (dialog, which) -> {
                            String name = ((TextInputEditText) loginDialog.findViewById(R.id.name)).getText().toString();
                            if (!name.trim().equals("")){
                                // TODO: 需要登录验证
                                ((ProfileActivity) getActivity()).toProfile();
                            } else
                                Toast.makeText(getContext(), "请输入正确的用户名", Toast.LENGTH_SHORT).show();
                        })
                        .create()
                        .show();
                break;
        }
    }
}
