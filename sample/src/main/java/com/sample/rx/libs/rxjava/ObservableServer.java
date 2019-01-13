package com.sample.rx.libs.rxjava;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ObservableServer {
    String action = "action.do?method=";
    public static final String method_login = "mwmLogin";

    @FormUrlEncoded
    @POST(action + "doLoginTest")
    Observable<String> doTestLogin(@Field("account") String account, @Field("password") String password);

    @FormUrlEncoded
    @POST(action + method_login)
    Observable<String> doLogin(@Field("beanJson") String json);

    @Streaming
    @GET
    Observable<ResponseBody> downloadImage(@Url String url);
}