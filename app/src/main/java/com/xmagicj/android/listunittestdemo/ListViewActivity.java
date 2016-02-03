package com.xmagicj.android.listunittestdemo;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Only for Test Sample
 * <p/>
 * Main Activity The main display area, and provide context
 *
 * @author Mumu
 */
public class ListViewActivity extends Activity {
    /**
     * ListView
     */
    private ListView mListView;
    /**
     * Adapter extends BaseAdapter
     */
    private ListViewAdapter mListViewAdapter;
    /**
     * Shared preferences key: Holds listview position. Must not be negative.
     */
    private static final String PREF_LISTVIEW_POS = "listview_pos_demo";
    /**
     * Magic constant to indicate that no value is stored for PREF_LISTVIEW_POS.
     */
    private static final int PREF_LISTVIEW_VALUE_ISNULL = -1;
    /**
     * Handle to default shared preferences for this activity.
     */
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get handle to default shared preferences for this activity
        mPrefs = PreferenceManager.getDefaultSharedPreferences(ListViewActivity.this);
        // Get handle to listview
        mListView = (ListView) findViewById(R.id.lv_main);
        mListViewAdapter = new ListViewAdapter(ListViewActivity.this);
        // List bundled with Adapter
        mListView.setAdapter(mListViewAdapter);
        mListView.setItemsCanFocus(true);
        // Read in a sample value, if it's not set.
        int selection = mPrefs.getInt(PREF_LISTVIEW_POS, PREF_LISTVIEW_VALUE_ISNULL);
        if (selection != PREF_LISTVIEW_VALUE_ISNULL) {
            mListView.setSelection(selection);
            mListViewAdapter.setSelectedIndex(selection);
        }
        // The following is used to test the item click event
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                mListViewAdapter.setSelectedIndex(position);
                mListViewAdapter.notifyDataSetChanged();
                mPrefs.edit().putInt(PREF_LISTVIEW_POS, position).apply();
                int count = position + 1;
                Toast.makeText(ListViewActivity.this, "Select " + count, Toast.LENGTH_SHORT).show();
            }
        });

        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPrefs.edit().putInt(PREF_LISTVIEW_POS, position).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mPrefs.edit().remove(PREF_LISTVIEW_POS).apply();
            }
        });
    }
}
