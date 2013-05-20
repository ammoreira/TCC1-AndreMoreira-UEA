package wifiController;

import java.util.List;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;

public interface WIFI {

	boolean checkWifiEnabled();

	boolean setWifiEnabled(boolean enable);

	boolean startWifiScan();

	void addWifiNetwork(WiFiConfigurator wifiConfigurator);

	void enableNetwork(int id);

	void connect();

	void disconnect() throws RuntimeException;

	List<ScanResult>  getScanResults();

	List<WifiConfiguration> getConfiguredNetworks();

}
