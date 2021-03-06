package wiseasily;

import android.Manifest;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;

import java.util.List;

import wiseasily.poolbroadcast.PoolBroadcastAPFound;
import wiseasily.source.SourceCallback;
import wiseasily.util.ConnectivityUtil;
import wiseasily.util.ScanFilter;
import wiseasily.util.WifiUtil;

import static wiseasily.util.ConnectivityUtil.currentConnection;
import static wiseasily.util.ConnectivityUtil.isConnectedToAPContainsChar;


/**
 * Bismillahirrahmanirrahim
 * Created by Innovation, eFishery  on 4/20/17.
 */

public class WisEasily {

    @NonNull
    private final ConnectWifi connectWifi;
    @NonNull
    private final ScanWifi scanWifi;
    @NonNull
    private final WifiDisabled wifiDisabled;
    private final WifiManager mWifiManager;
    @NonNull
    private final Context context;
    private final WifiUtil wifiUtil;
    private final ConnectivityUtil connectivityUtil;

    public WisEasily(@NonNull Context context) {
        wifiDisabled = new WifiDisabled(context);
        connectWifi = new ConnectWifi(context);
        scanWifi = new ScanWifi(context);
        this.context = context;
        wifiUtil = new WifiUtil();
        connectivityUtil = new ConnectivityUtil();
        mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @RequiresPermission(allOf = {Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_NETWORK_STATE, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET})
    public void connect(@NonNull String ssid, @NonNull final SourceCallback.WisEasilyCallback callback) {
        connectWifi.start(ssid, callback);
    }

    public void connectBackToPrev(){
        connectWifi.backToPrevNetwork();
    }

    public List<ScanResult> getWifiResult() {
        return mWifiManager.getScanResults();
    }

    public List<ScanResult> getWifiResultFilter(ScanFilter scanFilter) {
        return scanFilter.filterScanResult(mWifiManager.getScanResults());
    }

    @RequiresPermission(allOf = {Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_NETWORK_STATE, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION})
    public void isWifiPatternFound(@NonNull String patternSsid, int timeoutSecond, int totalScan, @NonNull SourceCallback.APFoundCallback callback) {
        PoolBroadcastAPFound poolBroadcastAPFound = new PoolBroadcastAPFound(context, timeoutSecond, totalScan, patternSsid);
        poolBroadcastAPFound.startListen(callback);
    }

    @RequiresPermission(allOf = {Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_NETWORK_STATE, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION})
    public void scan(@NonNull SourceCallback.WisEasilyScanCallback callback) {
        scanWifi.start(callback);
    }

    public void scanSetInterval(@NonNull int interval) {
        scanWifi.setScanInterval(interval);
    }

    public void scanSetFilter(@NonNull ScanFilter scanFilter) {
        scanWifi.setFilter(scanFilter);
    }

    public void scanSetAutoEnableWifi(boolean enable) {
        scanWifi.autoEnableWifi(enable);
    }

    public void stopScan() {
        scanWifi.stop();
    }

    public void startIsWifiDisable(@NonNull SourceCallback.WisEasilyWifiDisable callback){
        wifiDisabled.start(callback::onDisabled);
    }

    public boolean isWifiEnable(){
        return connectWifi.isWifiMEnable();
    }

    public boolean enableWifi(@NonNull boolean enable){
        return connectWifi.enableWifi(enable);
    }

    public int getCurrentConnection(){
        return currentConnection(context);
    }

    public String getCurrentSsid(){
        return wifiUtil.getCurrentWifi(context);
    }

    public boolean disconnectedToSsid(){
        return connectWifi.disconnectedToAP();
    }

    public boolean isWifiConnectedToSsid(String ssid){
        return connectivityUtil.isConnectedToAP(ssid, context);
    }

    public boolean isWifiConnectedToSsidContainsChar(String character){
        return isConnectedToAPContainsChar(character, context);
    }

    public boolean forgetCurrentSssid(){
        return wifiUtil.forgetCurrentNetwork(context);
    }
}
