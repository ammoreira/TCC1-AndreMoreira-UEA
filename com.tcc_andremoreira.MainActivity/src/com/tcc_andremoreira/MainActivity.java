package com.tcc_andremoreira;

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
import android.view.Menu;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String ssidNotFound = "Network SSID not found";
	private NFC nfc;
	private ForegroundDispatcher foregroundDispatcher;
	private static final String TAG = "MainActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		foregroundDispatcher = new NfcForegroundDispatcher(this);
		nfc = new NfcDefaultProcessor();

	}

	@Override
	public void onNewIntent(Intent intent) {

		RadioButton readBtn = (RadioButton) this.findViewById(R.id.radioReadTag);

		if (readBtn.isChecked()) {
			String text;
			nfc.readNdefTag(intent);
			if (nfc.getSSID() != null) {
				text = nfc.getSSID() + " - " + nfc.getPWD();
			} else {
				text = ssidNotFound;
			}
			Toast.makeText(this, text, Toast.LENGTH_LONG).show();

		} else {
			Tag newTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			EditText editSsid = (EditText) this.findViewById(R.id.editSSID);
			EditText editPwd = (EditText) this.findViewById(R.id.editPWD);
			try {
				nfc.writeNdefTag(newTag, editSsid.getText().toString(), editPwd.getText().toString());
				Toast.makeText(this, "Tag written Successfully", Toast.LENGTH_LONG).show();

			} catch (UnsupportedEncodingException e) {
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			} catch (FormatException e) {
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
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
