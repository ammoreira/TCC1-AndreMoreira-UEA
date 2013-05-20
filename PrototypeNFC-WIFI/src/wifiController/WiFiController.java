package wifiController;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class WiFiController extends Activity implements WIFI{

	private WifiManager wifiManager;

	public WiFiController(Context context){
		wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE); 
	}

	public boolean checkWifiEnabled(){
		return wifiManager.isWifiEnabled();
	}

	public boolean setWifiEnabled(boolean enable)
	{
		return wifiManager.setWifiEnabled(enable);
	}

	public boolean startWifiScan(){
		return wifiManager.startScan();
	}

	public List<ScanResult> getScanResults(){
		return wifiManager.getScanResults(); 
	}

	public WifiManager getWifiManager(){
		return wifiManager;
	}

	public void disconnect() throws RuntimeException{
		try{
			wifiManager.disconnect();
		}catch (Exception e) {
			e.printStackTrace();
			Log.d("disconnect failure", e.getMessage());
		}		
	}

	public void connect() {
		wifiManager.reconnect();
	}

	public void addWifiNetwork(WiFiConfigurator wifiConfigurator) {
		wifiManager.addNetwork(wifiConfigurator.getWifiConfiguration());
	}

	public void enableNetwork(int id) {
		wifiManager.enableNetwork(id, true) ;
	}

	public List<WifiConfiguration> getConfiguredNetworks(){
		return wifiManager.getConfiguredNetworks();	
	}
}
