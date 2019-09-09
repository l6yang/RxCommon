package com.loyal.rx.impl;

import androidx.annotation.IntRange;

import com.loyal.rx.BaseRxSubscriber;

public interface RxImpl<T> {
    BaseRxSubscriber<T> setWhat(@IntRange(from = 2, to = 1000) int what);

    BaseRxSubscriber<T> showProgressDialog(boolean showProgressDialog);

    BaseRxSubscriber<T> setDialogMessage(CharSequence message);

    BaseRxSubscriber<T> setTag(Object objTag);

    BaseRxSubscriber<T> setCancelable(boolean cancelable);

    void setCanceledOnTouchOutside(boolean flag);

    void setSubscribeListener(RxSubscriberListener<T> listener);

    void dispose();
}
