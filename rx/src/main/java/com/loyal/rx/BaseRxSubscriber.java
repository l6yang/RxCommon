package com.loyal.rx;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.loyal.rx.impl.ProgressCancelListener;
import com.loyal.rx.impl.RxImpl;
import com.loyal.rx.impl.RxSubscriberListener;
import com.loyal.rx.impl.UnSubscriberListener;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseRxSubscriber<T> implements RxImpl<T>, Observer<T>, ProgressCancelListener, UnSubscriberListener {
    private Disposable disposable;
    private RxSubscriberListener<T> subscribeListener;
    private int mWhat = 2;
    private Object object;
    private boolean showProgressDialog = false;//default=false
    private RxHandler.Builder builder;

    public BaseRxSubscriber(Context context) {
        this(context, 0);
    }

    public BaseRxSubscriber(Context context, int dialogTheme) {
        initDialog(context, dialogTheme);
    }

    private void initDialog(Context context, int theme) {
        if (null != context) {
            builder = new RxHandler.Builder(context, theme, this);
            setDialogMessage("").setCancelable(true).setCanceledOnTouchOutside(true);
        }
    }

    @Override
    public BaseRxSubscriber<T> setWhat(@IntRange(from = 2, to = 1000) int what) {
        this.mWhat = what;
        return this;
    }

    @Override
    public BaseRxSubscriber<T> showProgressDialog(boolean showProgressDialog) {
        this.showProgressDialog = showProgressDialog;
        return this;
    }

    @Override
    public BaseRxSubscriber<T> setDialogMessage(CharSequence message) {
        if (builder != null) {
            builder.setMessage(message);
        }
        return this;
    }

    @Override
    public BaseRxSubscriber<T> setTag(Object objTag) {
        this.object = objTag;
        return this;
    }

    @Override
    public BaseRxSubscriber<T> setCancelable(boolean cancelable) {
        if (builder != null)
            builder.setCancelable(cancelable);
        return this;
    }

    @Override
    public void setCanceledOnTouchOutside(boolean flag) {
        if (builder != null)
            builder.setCanceledOnTouchOutside(flag);
    }

    @Override
    public void setSubscribeListener(RxSubscriberListener<T> listener) {
        this.subscribeListener = listener;
    }

    @Override
    public void onSubscribe(@NonNull Disposable disposable) {
        this.disposable = disposable;
        showDialog();
    }

    private void showDialog() {
        if (showProgressDialog && null != builder) {
            builder.show();
        }
    }

    private void dismissDialog() {
        showProgressDialog = false;
        if (null != builder) {
            builder.dismiss();
            builder = null;
        }
    }

    @Override
    public void onNext(T result) {
        if (null != subscribeListener)
            subscribeListener.onResult(mWhat, object, result);
    }

    @Override
    public void onError(Throwable e) {
        dismissDialog();
        boolean cancelError = TextUtils.equals("已取消操作", null == e ? "" : e.getMessage());
        if (cancelError) {
            if (null != subscribeListener)
                subscribeListener.onError(mWhat, object, e);
            subscribeListener = null;
        } else {
            if (null != subscribeListener)
                subscribeListener.onError(mWhat, object, e);
        }
        dispose();
    }

    @Override
    public void onComplete() {
        dismissDialog();
        dispose();
    }

    @Override
    public void onCancelProgress() {
        onError(new Exception("已取消操作"));
        onComplete();
    }

    /*不显示dialog，取消执行*/
    @Override
    public void unsubscribe() {
        onComplete();
    }

    @Override
    public void dispose() {
        if (null != disposable && !disposable.isDisposed())
            disposable.dispose();
    }
}
