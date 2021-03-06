package com.loyal.rx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

import com.loyal.rx.impl.ProgressCancelListener;

public class RxHandler extends Handler implements DialogInterface.OnCancelListener {
    private ProgressDialog progressDialog;
    private Context context;
    private ProgressCancelListener listener;

    public RxHandler(Context context, int theme, ProgressCancelListener cancelListener) {
        this.context = context;
        initDialog(theme);
        this.listener = cancelListener;
    }

    private void initDialog(int theme) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context, theme);
            progressDialog.setOnCancelListener(this);
        }
    }

    private void setMessage(CharSequence message) {
        if (progressDialog != null)
            progressDialog.setMessage(message);
    }

    private void setCancelable(boolean cancelable) {
        if (progressDialog != null)
            progressDialog.setCancelable(cancelable);
    }

    private void setCanceledOnTouchOutside(boolean flag) {
        if (progressDialog != null)
            progressDialog.setCanceledOnTouchOutside(flag);
    }

    private void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    private void showDialog() {
        if (context instanceof Activity && ((Activity) context).isFinishing())
            return;
        if (null != progressDialog && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        if (listener != null)
            listener.onCancelProgress();
    }

    public static final class Builder {
        private RxHandler handler;

        public Builder(Context context, int theme, ProgressCancelListener cancelListener) {
            handler = new RxHandler(context, theme, cancelListener);
        }

        public Builder setMessage(CharSequence sequence) {
            handler.setMessage(sequence);
            return this;
        }

        public void setCancelable(boolean cancelable) {
            handler.setCancelable(cancelable);
        }

        public void setCanceledOnTouchOutside(boolean flag) {
            handler.setCanceledOnTouchOutside(flag);
        }

        public void show() {
            if (null != handler)
                handler.showDialog();
        }

        public void dismiss() {
            if (null != handler)
                handler.dismissDialog();
        }
    }
}
