package com.sample.rx.libs.rxjava;

import android.content.Context;

import com.loyal.rx.BaseRxServerSubscriber;
import com.loyal.rx.RetrofitManage;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;

public class RxProgressSubscriber<T> extends BaseRxServerSubscriber<T> implements ObservableServer {

    private ObservableServer server;

    public RxProgressSubscriber(Context context) {
        this(context, "192.168.0.110");
    }

    public RxProgressSubscriber(Context context, String ipAdd) {
        super(context, ipAdd);
    }

    @Override
    public int defaultPort() {
        return 8080;
    }

    @Override
    public void createServer(RetrofitManage retrofitManage) {
        server = retrofitManage.createServer(ObservableServer.class);
    }

    @Override
    public String serverNameSpace() {
        return "mwm";
    }

    @Override
    public Observable<String> doTestLogin(@Field("account") String account, @Field("password") String password) {
        return server.doTestLogin(account, password);
    }

    @Override
    public Observable<String> doLogin(@Field("beanJson") String json) {
        return server.doLogin(json);
    }

    @Override
    public Observable<ResponseBody> downloadImage(String url) {
        return server.downloadImage(url);
    }
}