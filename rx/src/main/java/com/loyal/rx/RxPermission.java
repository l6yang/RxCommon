package com.loyal.rx;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.loyal.rx.impl.MultiplePermissionsListener;
import com.loyal.rx.impl.SinglePermissionListener;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RxPermission {
    private RxPermissions rxPermissions;
    private Disposable disposable;

    public RxPermission(FragmentActivity activity) {
        if (null == rxPermissions)
            rxPermissions = new RxPermissions(activity);
    }

    public RxPermission(Fragment fragment) {
        if (null == rxPermissions)
            rxPermissions = new RxPermissions(fragment);
    }

    public void singlePermission(final int reqCode, final SinglePermissionListener listener, String... permissions) {
        disposable = rxPermissions.request(permissions).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                if (null != listener)
                    listener.onSinglePermission(reqCode, aBoolean);
            }
        });
    }

    public void multiplePermissions(final MultiplePermissionsListener listener, String... permission) {
        disposable = rxPermissions.requestEach(permission).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) {
                if (null != listener)
                    listener.onMultiplePermissions(permission.name, permission.granted, permission.shouldShowRequestPermissionRationale);
            }
        });
    }

    public void dispose() {
        if (null != disposable && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
