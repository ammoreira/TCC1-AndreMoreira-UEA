package com.example.tccprototipov1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class NFCListActivity extends FragmentActivity
        implements NFCListFragment.Callbacks {

    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_list);

        if (findViewById(R.id.nfc_detail_container) != null) {
            mTwoPane = true;
            ((NFCListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.nfc_list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(NFCDetailFragment.ARG_ITEM_ID, id);
            NFCDetailFragment fragment = new NFCDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nfc_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, NFCDetailActivity.class);
            detailIntent.putExtra(NFCDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
