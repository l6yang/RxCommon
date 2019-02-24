package com.sample.rx.libs.rxjava;

import android.content.Context;

import com.loyal.rx.BaseRxServerSubscriber;
import com.loyal.rx.RetrofitManage;
import com.sample.rx.State;

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
    public String httpOrHttps() {
        return State.httpOrHttps;
    }

    @Override
    public boolean trustedCert() {
        return State.trustedCert;
    }

    @Override
    public String defaultPort() {
        return State.defaultPort;
    }

    @Override
    public void createServer(RetrofitManage retrofitManage) {
        server = retrofitManage.createServer(ObservableServer.class);
    }

    @Override
    public String serverNameSpace() {
        return State.serverNameSpace;
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

    @Override
    public Observable<String> getTransDataInfo(String sblx) {
        //serverNameSpace()="ydjw";
        return server.getTransDataInfo(sblx);
    }

    @Override
    public Observable<String> httpsTest(String yhdh, String yhmm, String sbmac, String sbip, String appver, String sblx) {
        return server.httpsTest("test", "111111", "FJH7N18703010820", "192.168.0.2", "0.0.1", "1");
    }

    @Override
    public Observable<String> httpsApi(String page, String count) {
        return server.httpsApi("1", "5");
    }

    @Override
    public Observable<String> httpsGetApi(String url) {
        return server.httpsGetApi("https://api.apiopen.top/getImages?page=1&count=5");
    }
}