package com.example.prototypenfc_wifi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import nfcController.ForegroundDispatcher;
import nfcController.NFC;
import nfcController.NfcDefaultProcessor;
import nfcController.NfcForegroundDispatcher;
import android.app.Activity;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NfcWriterActivity extends Activity{

	private String TAG = "nfc_writer_activity";
	private String SSID; 
	private String PASSWORD;
	private NFC nfc;
	private ForegroundDispatcher foregroundDispatcher;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nfc_tag_writer);
		foregroundDispatcher = new NfcForegroundDispatcher(this);
		nfc = new NfcDefaultProcessor();
	}

	@Override
	public void onNewIntent(Intent intent) {
		Tag newTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		EditText editSsid = (EditText) this.findViewById(R.id.nfc_writer_ssid);
		SSID = "ssid:" + editSsid.getText().toString();
		EditText editPwd = (EditText) this.findViewById(R.id.nfc_writer_pwd);
		PASSWORD = "pwd:" + editPwd.getText().toString();
		try {
			nfc.writeNdefTag(newTag, SSID, PASSWORD);
			Toast.makeText(this, "Tag written Successfully", Toast.LENGTH_LONG).show();
		} catch (UnsupportedEncodingException e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		} catch (FormatException e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}	

	@Override
	public void onResume() {
		Log.d(TAG, "onResume");
		super.onResume();
		foregroundDispatcher.enable();
	}

	@Override
	public void onPause() {
		Log.d(TAG, "onPause");
		super.onPause();
		foregroundDispatcher.disable();
	}

}
