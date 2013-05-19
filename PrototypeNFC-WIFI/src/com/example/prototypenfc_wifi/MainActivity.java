package com.example.prototypenfc_wifi;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Activity mainActivity; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mainActivity = this;
        
        Button nfcWriterButton = (Button) this.findViewById(R.id.nfc_writer_btn);
        Button wifiConnectorButton = (Button) this.findViewById(R.id.wifi_connect_btn);
         
        nfcWriterButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				Intent nfcWriterLayout =  new Intent(v.getContext(), NfcWriterActivity.class);
				startActivity(nfcWriterLayout);
			}       	
        });
       
        wifiConnectorButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				setContentView(R.layout.wifi_connector);
			}       	
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
