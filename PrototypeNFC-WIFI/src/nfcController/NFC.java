package nfcController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.Tag;

public interface NFC {

	void readNdefTag(Intent intent);

	void writeNdefTag(Tag tag, String ssid, String password) throws UnsupportedEncodingException, IOException,
	FormatException;

	String getSSID();

	String getPWD();

}
