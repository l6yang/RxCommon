package com.sample.rx;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.loyal.rx.Config;
import com.loyal.rx.RetrofitManage;
import com.loyal.rx.RxUtil;
import com.loyal.rx.impl.RxSubscriberListener;
import com.sample.rx.libs.rxjava.RxProgressSubscriber;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RxSubscriberListener<String>, View.OnClickListener {

    private static final int WHAT_LOGIN = 2;
    private static final int WHAT_TIMEOUT = 4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.rx_timeout1).setOnClickListener(this);
        findViewById(R.id.rx_timeout2).setOnClickListener(this);
        findViewById(R.id.rx_timeout3).setOnClickListener(this);
        //startActivity(new Intent(this, ServerActivity.class));
        //finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:/*注意*/
                int rssi = NetWorkUtil.getWifiRssi(this);
                int status = NetWorkUtil.networkStatus(this);
                Log.e("getWifiRssi", "" + rssi);
                Log.e("networkStatus", "" + status);
                break;
            case R.id.rx_timeout1:
                rxTimeOut(15);
                break;
            case R.id.rx_timeout2:
                rxTimeOut(5);
                break;
            case R.id.rx_timeout3:
                login();
                break;
        }
    }

    private void login() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = formatter.format(new Date());
        Log.e("start->", "" + time);
        RetrofitManage.setConfig(new Config());
        RxProgressSubscriber<String> subscriber = new RxProgressSubscriber<>(this, "192.168.0.110");
        subscriber.setDialogMessage("loading...").showProgressDialog(true);
        subscriber.setWhat(WHAT_LOGIN).setSubscribeListener(this);
        Map<String, String> map = new HashMap<>();
        map.put("account", "loyal");
        map.put("password", "loyal");
        RxUtil.rxExecute(subscriber.doLogin(new Gson().toJson(map)), subscriber);
    }

    private void rxTimeOut(int timeOut) {
        Config config = new Config();
        config.setConnectTimeout(timeOut);
        config.setReadTimeout(timeOut);
        config.setWriteTimeout(timeOut);

        RetrofitManage.setConfig(config);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = formatter.format(new Date());
        Log.e("start->", "" + time);

        RxProgressSubscriber<String> subscriber = new RxProgressSubscriber<>(this, "192.168.0.110");
        subscriber.setDialogMessage("loading...").showProgressDialog(true);
        subscriber.setWhat(WHAT_TIMEOUT).setSubscribeListener(this);
        Map<String, String> map = new HashMap<>();
        map.put("account", "loyal");
        map.put("password", "loyal");
        RxUtil.rxExecute(subscriber.doLogin(new Gson().toJson(map)), subscriber);
    }

    @Override
    public void onResult(int what, Object tag, String result) {
        switch (what) {
            case WHAT_LOGIN:
                SimpleDateFormat xxx = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String x = xxx.format(new Date());
                Log.e("onResult->", "" + x);
                break;
            case WHAT_TIMEOUT:
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = formatter.format(new Date());
                Log.e("onResult->", "" + time);
                break;
        }
    }

    @Override
    public void onError(int what, Object tag, Throwable e) {
        switch (what) {
            case WHAT_LOGIN:
                SimpleDateFormat xxx = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String x = xxx.format(new Date());
                Log.e("onResult->", "" + x);
                break;
            case WHAT_TIMEOUT:
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = formatter.format(new Date());
                Log.e("onError->", "" + time);
                break;
        }
    }
}