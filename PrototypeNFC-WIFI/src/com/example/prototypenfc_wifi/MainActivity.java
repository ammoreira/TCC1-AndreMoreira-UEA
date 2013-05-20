package com.example.prototypenfc_wifi;

import java.util.List;
import wifiController.WIFI;
import wifiController.WiFiConfigurator;
import wifiController.WiFiController;
import nfcController.ForegroundDispatcher;
import nfcController.NFC;
import nfcController.NfcDefaultProcessor;
import nfcController.NfcForegroundDispatcher;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String onNewIntent = "On new intent method";
	private static final String onPause = "On pause method";
	private static final String onResume = "On resume method";
	private static final String wep = "WEP Encryption";
	private static final String wpa = "WAP/WPA2 Encryption";
	private static final String openNetwork = "Open Network, No Encryption";
	private static final String noWifi = "No wifi hotspots available"; 
	private static final String wifiEnabler = "Enabling Wifi device";
	private static final String wifiEnabled = "wifi Enabed";
	private static final String ssidNotFound = "Network SSID not found";
	private static final String TAG = "main_activity";
	private static final String encryptionTagWep = "WEP";
	private static final String encryptionTagWpa = "WPA";
	private static final String locNetwork = "Locating Network";
	private static final String connecting = "connecting to Network";
	private static final String attemptingConnection = "attempting to set Connection";
	private static final String addNetwork = "adding wifi Network";
	private static final String networkNotFound = "Sorry, Network Not Found";

	private String collectedSSID; 
	private String collectedPWD;
	private String collectedEncryptionType;

	private Activity mainActivity; 
	private NFC nfc;
	private ForegroundDispatcher foregroundDispatcher;
	private WIFI wifi;
	private List<ScanResult> scanResultList;
	private WiFiConfigurator wifiConfigurator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		wifi = new WiFiController(this.getBaseContext());
		foregroundDispatcher = new NfcForegroundDispatcher(this);
		nfc = new NfcDefaultProcessor();
		mainActivity = this;

		Button nfcWriterButton = (Button) this.findViewById(R.id.nfc_writer_btn);
		Button aboutButton = (Button) this.findViewById(R.id.about_btn);

		nfcWriterButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				Intent nfcWriterLayout =  new Intent(v.getContext(), NfcWriterActivity.class);
				startActivity(nfcWriterLayout);
			}       	
		});
		aboutButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				Intent aboutLayout =  new Intent(v.getContext(), AboutActivity.class);
				startActivity(aboutLayout);
			}       	
		});    
	}

	@Override
	public void onNewIntent(Intent intent) {
		Log.d(TAG, onNewIntent);

		collectedSSID = null;
		collectedPWD = null;
		nfc.readNdefTag(intent);

		if (nfc.getSSID() != null) {
			collectedSSID = nfc.getSSID();
			collectedPWD = nfc.getPWD();
		} else {
			Toast.makeText(this, ssidNotFound, Toast.LENGTH_LONG).show();
		}

		if(collectedSSID != null & collectedPWD != null){

			if(!wifi.checkWifiEnabled()){
				wifi.setWifiEnabled(true);
				Toast.makeText(this, wifiEnabler, Toast.LENGTH_LONG).show();
				Log.d(TAG, wifiEnabled );
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}		
			try {		
				scanResultList = wifi.getScanResults();
				boolean networkFound = false;
				for( ScanResult incResulList : scanResultList ) {
					if(collectedSSID.equals(incResulList.SSID)){
						networkFound = true;
						collectedEncryptionType = incResulList.capabilities;
						wifiConfigurator = new WiFiConfigurator();
						wifiConfigurator.configureSSID(collectedSSID);    		

						if (collectedEncryptionType.contains(encryptionTagWpa)){
							wifiConfigurator.configureWpaPassword(collectedPWD);
							Log.d(TAG, wpa);
						}else if(collectedEncryptionType.contains(encryptionTagWep)){
							wifiConfigurator.setWepPassword(collectedPWD);
							Log.d(TAG, wep);
						}else{
							wifiConfigurator.setOpenNetworkPassword();
							Log.d(TAG, openNetwork);
						}

						wifi.addWifiNetwork(wifiConfigurator);
						Log.d(TAG, addNetwork);

						for( WifiConfiguration configuredNetworkList  : wifi.getConfiguredNetworks()) {
							if(configuredNetworkList.SSID.equals("\"" + collectedSSID + "\"")) {  
								Log.d(TAG, attemptingConnection);
								wifi.disconnect();
								wifi.enableNetwork(configuredNetworkList.networkId);
								wifi.connect();        		
							}
						}		
					}
				}
				if (!networkFound ) {
					Toast.makeText(this, networkNotFound, Toast.LENGTH_LONG).show();
				}
			}catch (Exception e) {
				Log.d(TAG, e.getMessage());
				Toast.makeText(this, e + noWifi, Toast.LENGTH_LONG).show();
			}	
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onResume() {
		Log.d(TAG, onResume);
		super.onResume();
		foregroundDispatcher.enable();
	}

	@Override
	public void onPause() {
		Log.d(TAG, onPause);
		super.onPause();
		foregroundDispatcher.disable();
	}
}
