package wifiController;

import android.net.wifi.WifiConfiguration;

public class WiFiConfigurator{

	private WifiConfiguration wifiConfiguration;

	public WiFiConfigurator(){
		wifiConfiguration = new WifiConfiguration();
	}

	public void configureSSID(String ssid){
		wifiConfiguration.SSID = ("\"" + ssid + "\"");
	}

	public void configureWpaPassword(String password){
		wifiConfiguration.preSharedKey = ("\"" + password + "\"")  ;
	}

	public void setWepPassword(String password){
		wifiConfiguration.wepKeys[0] = ("\"" + password + "\""); 
		wifiConfiguration.wepTxKeyIndex = 0;
		wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
		wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40); 
	}

	public void setOpenNetworkPassword(){
		wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
	}

	public WifiConfiguration getWifiConfiguration(){
		return wifiConfiguration;
	}
}