package com.loyal.rx.impl;

import android.support.annotation.IntRange;

public interface SinglePermissionListener {
    void onSinglePermission(@IntRange(from = 2, to = 1000) int reqCode, boolean successful);
}