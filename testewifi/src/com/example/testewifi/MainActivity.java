package com.example.testewifi;

import java.util.List;

import wifiController.WIFI;
import wifiController.WiFiConfigurator;
import wifiController.WiFiController;
import android.app.Activity;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends Activity {

   private WIFI wifi;
   private Activity activity;
   private List<ScanResult> scanResultList;
   private WiFiConfigurator wifiConfigurator;
   private List<WifiConfiguration> configuredNetworkList;
   
   
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        activity = this;
    	wifi = new WiFiController(this.getBaseContext());
    	    	
    	Button wifiButton = (Button) this.findViewById(R.id.checkWifiAbleButton);	
		Button wifiScanButton = (Button) this.findViewById(R.id.startwifiscan);
    	RadioButton wifiOffBtn = (RadioButton) this.findViewById(R.id.wifioff);
		RadioButton wifiOnBtn = (RadioButton) this.findViewById(R.id.wifion);
		
		wifiOnBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			wifi.setWifiEnabled(((RadioButton)v).isChecked());			
			}
		});
	    wifiOffBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				wifi.setWifiEnabled(!((RadioButton)v).isChecked());				
			}
		});
	
		wifiButton.setOnClickListener(new OnClickListener() {	
			public void onClick(View v) {
		    String result;
			if(wifi.checkWifiEnabled()){
				result = "wifi abled";	
			}else{
				result = "wifi disabled";
			}
				Toast.makeText(activity, result, Toast.LENGTH_LONG).show();	
			}
		});
        
		wifiScanButton.setOnClickListener(new OnClickListener() {	
			
			public void onClick(View v) {
			    String text = "wifi network list size = ";
			    int listSize = 0;
			    try {
			    	listSize = scanResultList.size();
				} catch (Exception e) {
				  listSize++;
				  Toast.makeText(activity, e + " list not initialized", Toast.LENGTH_LONG).show();
				}
			   
			    if(listSize>0){
				    try {
				    	scanResultList = wifi.getScanResults();
				    	 listSize = scanResultList.size();
				    	 Toast.makeText(activity, text+listSize, Toast.LENGTH_LONG).show();
					} catch (Exception e) {
						 Toast.makeText(activity, e + " list not initialized", Toast.LENGTH_LONG).show();
					  }
				}	    
			    
			    for( ScanResult i : scanResultList )
			    {
			    	 Toast.makeText(activity, "network SSID - " + i.SSID + " | PWD - " + i.capabilities, Toast.LENGTH_LONG).show();
			    }
			   
			    wifiConfigurator = new WiFiConfigurator();
			    wifiConfigurator.configureSSID();	
			    Toast.makeText(activity, wifiConfigurator.getConfiguredSSID(), Toast.LENGTH_LONG).show();
			    //wifiConfigurator.setOpenNetworkPassword(); 
			    wifiConfigurator.configureWpaPassword();
			    Toast.makeText(activity, wifiConfigurator.getConfiguredWPAPassword(), Toast.LENGTH_LONG).show();
			    wifi.addWifiNetwork(wifiConfigurator);
			   			    
		        for( WifiConfiguration i : wifi.getConfiguredNetworks() ) {
		        		        	
		        	if(i.SSID != null && i.SSID.equals(wifiConfigurator.getConfiguredSSID())) {
				       		
		        		Toast.makeText(activity,"id -"+ i.networkId, Toast.LENGTH_LONG).show();					    
		        	    wifi.disconnect();
					    wifi.enableNetwork(i.networkId);
			    	    wifi.connect();        		
		        		break;
		        	}
		        }
		        Toast.makeText(activity, "end", Toast.LENGTH_LONG).show(); 
			}
		});
	
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
