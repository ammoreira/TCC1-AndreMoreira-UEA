package wifiController;

import android.net.wifi.WifiConfiguration;

public class WiFiConfigurator{
	
	private WifiConfiguration wifiConfiguration;
	private final String testSSID = ("\""+"estwifi"+"\"");
	private final String testPWD = ("\""+"12345678"+"\"");
	//private final String testSSID = ("\""+"TUCUMA"+"\"");
	//private final String testSSID = ("\""+"netvirtua204"+"\"");
	//private final String testPWD = ("\""+"2095426940"+"\"");

	public WiFiConfigurator(){
		wifiConfiguration = new WifiConfiguration();
	}
	
	public void configureSSID(){
		wifiConfiguration.SSID = testSSID;
	}
	
	public void configureWpaPassword(){
		wifiConfiguration.preSharedKey = testPWD ;
	}
	
	public void setWepPassword(){
		wifiConfiguration.wepKeys[0] = testPWD ; 
		wifiConfiguration.wepTxKeyIndex = 0;
		wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
		wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40); 
	}

	public void setOpenNetworkPassword(){
		wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
	}
	
	public String getConfiguredSSID(){ 
		return wifiConfiguration.SSID; 
	}	
	
	public String getConfiguredWPAPassword() {
		return  wifiConfiguration.preSharedKey;	
	}	
	
	public WifiConfiguration getWifiConfiguration(){
		return wifiConfiguration;
	}
}