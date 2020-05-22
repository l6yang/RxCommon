package com.sample.rx;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class NetWorkUtil {
    public static final int NET_ETHERNET = 1;
    public static final int NET_WIFI = 2;
    public static final int NET_NOCONNECT = -1;
 
    /**
     * 获取设备网络状态
     */
    public static int networkStatus(Context context) {
        try {
            ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null == connectMgr)
                return NET_NOCONNECT;
            NetworkInfo ethNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
            NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (ethNetInfo != null && ethNetInfo.isConnected()) {
                return NET_ETHERNET;
            } else if (wifiNetInfo != null && wifiNetInfo.isConnected()) {
                return NET_WIFI;
            } else {
                return NET_NOCONNECT;
            }
        } catch (Exception e) {
            return NET_NOCONNECT;
        }
    }

    /**
     * 获取wifi信号强度
     * 其中0到-50表示信号最好，-50到-70表示信号偏差，小于-70表示最差
     */
    public static int getWifiRssi(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (null == wifiManager) {
                return -1;
            }
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int level = Math.abs(wifiInfo.getRssi());
            if (level >= 70) {
                return RSSI_BED;
            } else if (level > 50) {
                return RSSI_GOOD;
            } else if (level >= 0) {
                return RSSI_BEST;
            } else return RSSI_UNKNOWN;
        } catch (Exception e) {
            return RSSI_UNKNOWN;
        }
    }

    public static final int RSSI_BEST = 0;
    public static final int RSSI_GOOD = 50;
    public static final int RSSI_BED = 100;
    public static final int RSSI_UNKNOWN = -1;

    public static boolean isNetWorkAvailable(Context context) {
        try {
            int netWorkStatus = networkStatus(context);
            if (NET_ETHERNET == netWorkStatus)
                return true;
            int rssi = getWifiRssi(context);
            return RSSI_GOOD == rssi || RSSI_BEST == rssi;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
