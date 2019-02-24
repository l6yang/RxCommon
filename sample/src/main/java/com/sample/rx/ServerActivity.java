package com.sample.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import com.loyal.rx.RxUtil;
import com.loyal.rx.impl.RxSubscriberListener;
import com.sample.rx.libs.rxjava.RxProgressSubscriber;

public class ServerActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, RxSubscriberListener<String> {

    private Toolbar toolbar;
    private RadioGroup radioGroup;
    private View trustedCertLayout;
    private SwitchCompat switchTrustedCert;
    private AppCompatEditText editIpAdd;
    private AppCompatEditText editPort;
    private View testView;
    private View saveView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("接入地址设置");
        setSupportActionBar(toolbar);
        radioGroup = findViewById(R.id.radioGroup);
        trustedCertLayout = findViewById(R.id.trustedCertLayout);
        switchTrustedCert = findViewById(R.id.switchTrustedCert);
        editIpAdd = findViewById(R.id.edit_ipAdd);
        editPort = findViewById(R.id.edit_port);
        testView = findViewById(R.id.testView);
        saveView = findViewById(R.id.saveView);
        radioGroup.setOnCheckedChangeListener(this);
        saveView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.testView:
                httpsApiTest();
                break;
            case R.id.saveView:
                httpsApi();
                break;
        }
    }

    private void httpsApiTest() {
        int checkId = radioGroup.getCheckedRadioButtonId();
        State.serverNameSpace = "ydjw";
        String port;
        if (checkId == R.id.tbtn_https) {
            port = "9444";
            State.httpOrHttps = "https";
            State.trustedCert = switchTrustedCert.isChecked();
        } else {
            State.trustedCert = true;
            State.httpOrHttps = "http";
            port = "9999";
        }
        String ipAdd = String.format("192.168.0.155:%s", port);
        RxProgressSubscriber<String> subscriber = new RxProgressSubscriber<>(this, ipAdd);
        subscriber.setDialogMessage("测试中...").showProgressDialog(true);
        subscriber.setSubscribeListener(this);
        System.out.println("-----\"" + subscriber.baseUrl(ipAdd) + "\"");
        RxUtil.rxExecute(subscriber.httpsTest("", "", "", "", "", ""), subscriber);
    }

    private void httpsApi() {
        int checkId = radioGroup.getCheckedRadioButtonId();
        if (checkId == R.id.tbtn_https) {
            State.httpOrHttps = "https";
            State.trustedCert = switchTrustedCert.isChecked();
        } else {
            State.httpOrHttps = "http";
            State.trustedCert = true;
        }
        State.serverNameSpace = "";
        State.defaultPort="";
        String ipAdd = "api.apiopen.top";
        RxProgressSubscriber<String> subscriber = new RxProgressSubscriber<>(this, ipAdd);
        subscriber.setDialogMessage("测试中...").showProgressDialog(true);
        subscriber.setSubscribeListener(this);
        System.out.println("-----\"" + subscriber.baseUrl(ipAdd) + "\"");
        RxUtil.rxExecute(subscriber.httpsApi("", ""), subscriber);
        //RxUtil.rxExecute(subscriber.httpsGetApi(""), subscriber);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.tbtn_http:
                trustedCertLayout.setVisibility(View.GONE);
                break;
            case R.id.tbtn_https:
                trustedCertLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onResult(int what, Object tag, String result) {
        try {
            String string = "{\"excuteCode\":{\"code\":\"-99\",\"mess\":\"操作失败：系统异常，\\r＃＃＃ Error querying database．  Cause： org．springframework．jdbc．CannotGetJdbcConnectionException： Could not get JDBC Connection； nested exception is com．ibm．websphere．ce．cm．StaleConnectionException： IO 错误： The Network Adapter could not establish the connection DSRA0010E： SQL 状态：08006，错误码：17，002。\\r＃＃＃ The error may exist in file ［G：￥Program Files （x86）￥IBM￥WebSphere￥AppServer￥profiles￥AppSrv01￥installedApps￥PC－20160229UFWZNode02Cell￥tra＿ydjw＿ear．ear￥tra＿ydjw＿web．war￥WEB－INF￥classes￥mybatis￥mapper￥PdaValidateMapper．xml］\\r＃＃＃ The error may involve com．zlkj．frm．resource．pdaLogin\\r＃＃＃ The error occurred while executing a query\\r＃＃＃ Cause： org．springframework．jdbc．CannotGetJdbcConnectionException： Could not get JDBC Connection； nested exception is com．ibm．websphere．ce．cm．StaleConnectionException： IO 错误： The Network Adapter could not establish the connection DSRA0010E： SQL 状态：08006，错误码：17，002。【excuteProcedure,pdaLogin】\"},\"excuteObj\":null,\"excuteObjList\":null}";
            System.out.println("http://baidu.com ---" + result);
            if (TextUtils.equals(result, string))
                result = "{\"code\":\"-99\",\"mess\":\"操作失败：系统异常，\\r＃＃＃ Error querying database．  Cause： org．springframework．jdbc．CannotGetJdbcConnectionException： Could not get JDBC Connection； nested exception is com．ibm．websphere．ce．cm．StaleConnectionException： IO 错误： The Network Adapter could not establish the connection DSRA0010E： SQL 状态：08006，错误码：17，002。\\r＃＃＃ The error may exist in file ［G：￥Program Files （x86）￥IBM￥WebSphere￥AppServer￥profiles￥AppSrv01￥installedApps￥PC－20160229UFWZNode02Cell￥tra＿ydjw＿ear．ear￥tra＿ydjw＿web．war￥WEB－INF￥classes￥mybatis￥mapper￥PdaValidateMapper．xml］\\r＃＃＃ The error may involve com．zlkj．frm．resource．pdaLogin\\r＃＃＃ The error occurred while executing a query\\r＃＃＃ Cause： org．springframework．jdbc．CannotGetJdbcConnectionException： Could not get JDBC Connection； nested exception is com．ibm．websphere．ce．cm．StaleConnectionException： IO 错误： The Network Adapter could not establish the connection DSRA0010E： SQL 状态：08006，错误码：17，002。【excuteProcedure,pdaLogin】\"}";
            System.out.println("---" + result);
        } catch (Exception e) {
            onError(what, tag, e);
        }
    }

    @Override
    public void onError(int what, Object tag, Throwable e) {
        System.out.println(e);
    }
}
