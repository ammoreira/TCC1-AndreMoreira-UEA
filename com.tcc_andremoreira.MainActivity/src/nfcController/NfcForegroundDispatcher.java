package nfcController;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.util.Log;

public class NfcForegroundDispatcher implements ForegroundDispatcher {

	private static final String TAG = "NfcForegroundDispatcher";
	private final NfcAdapter nfcAdapter;
	private final PendingIntent nfcPendingIntent;
	private final Activity receivedActivity;

	public NfcForegroundDispatcher(Activity activity) {
		receivedActivity = activity;
		nfcAdapter = NfcAdapter.getDefaultAdapter(activity);
		nfcPendingIntent = PendingIntent.getActivity(activity, 0,
				new Intent(activity, activity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
	}

	public void enable() {
		Log.d(TAG, "enableForegroundMode");
		IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
		IntentFilter[] TagFilters = new IntentFilter[] { tagDetected };
		nfcAdapter.enableForegroundDispatch(receivedActivity, nfcPendingIntent, TagFilters, null);
	}

	public void disable() {
		Log.d(TAG, "disableForegroundMode");
		nfcAdapter.disableForegroundDispatch(receivedActivity);
	}

}
