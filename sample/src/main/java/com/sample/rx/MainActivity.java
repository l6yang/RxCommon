package com.sample.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.gson.Gson;
import com.loyal.rx.RxUtil;
import com.loyal.rx.impl.RxSubscriberListener;
import com.sample.rx.libs.rxjava.RxProgressSubscriber;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RxSubscriberListener<String>, View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.login).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        /*注意*/
        RxProgressSubscriber<String> subscriber = new RxProgressSubscriber<>(this, "192.168.0.110:8080");
        subscriber.setDialogMessage("loading...").showProgressDialog(true);
        subscriber.setSubscribeListener(this);
        Map<String, String> map = new HashMap<>();
        map.put("account", "loyal");
        map.put("password", "loyal");
        RxUtil.rxExecute(subscriber.doLogin(new Gson().toJson(map)), subscriber);
    }

    @Override
    public void onResult(int what, Object tag, String result) {

    }

    @Override
    public void onError(int what, Object tag, Throwable e) {

    }
}