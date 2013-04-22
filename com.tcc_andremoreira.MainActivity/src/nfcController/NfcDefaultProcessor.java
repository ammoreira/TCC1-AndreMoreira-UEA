package nfcController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

public class NfcDefaultProcessor extends Activity implements NFC {

	private static final String PWD = "pwd:";
	private static final String SSID = "ssid:";
	private static final String TAG = "NFC";
	private String networkSSID;
	private String networkPWD;

	private NdefRecord createNdefRecord(String text) throws UnsupportedEncodingException {

		byte[] payload = text.getBytes();

		NdefRecord ndefRecord = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, null, payload);
		return ndefRecord;
	}

	public String getSSID() {
		return networkSSID;
	}

	public String getPWD() {
		return networkPWD;
	}

	public void readNdefTag(Intent intent) {
		networkPWD = null;
		networkSSID = null;
		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
			Parcelable[] messages;
			try {
				messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
				for (Parcelable message : messages) {
					NdefMessage msg = (NdefMessage) message;
					for (NdefRecord record : msg.getRecords()) {
						String tagPayload = new String(record.getPayload());

						if (tagPayload.startsWith(SSID)) {
							Log.d(TAG, "Retriving Network SSID");
							networkSSID = tagPayload.replaceFirst(SSID, "");
						}

						if (tagPayload.startsWith(PWD)) {
							Log.d(TAG, "Retriving Network Password");
							networkPWD = tagPayload.replaceFirst(PWD, "");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.d(TAG, e.getMessage());
			}
		} else {
			Log.d(TAG, "UNEXPECTED ACTION - " + intent.getAction());
			Toast.makeText(this, "Unexpected Action - " + intent.getAction(), Toast.LENGTH_LONG).show();
		}
	}

	public void writeNdefTag(Tag tag, String ssid, String password) throws UnsupportedEncodingException, IOException,
			FormatException {

		if (ssid == null){
			throw new IllegalArgumentException();
		}

		if (ssid.isEmpty()){
			throw new IllegalArgumentException();
		}
		
		NdefRecord[] records = { createNdefRecord(ssid), createNdefRecord(password) };
		NdefMessage message = new NdefMessage(records);
		Ndef ndef = Ndef.get(tag);
		ndef.connect();
		ndef.writeNdefMessage(message);
		ndef.close();
	}
}
