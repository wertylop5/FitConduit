package com.projectsling.fitconduit.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.projectsling.fitconduit.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements WireCreatorDialog.OnWireCreateListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    FragmentManager mFragmentManager;
    RelativeLayout mRelativeLayout;
    private String mWireChoiceFragmentTag = "WireChoiceTag";
    private String mResultsFragmentTag = "ResultsTag";

    @Override
    public void onWireCreate(String name, int amount) {
        Log.v(LOG_TAG, "Got " + name + " and amount of " + amount);
        WireChoiceFragment frag = (WireChoiceFragment) mFragmentManager.findFragmentByTag(mWireChoiceFragmentTag);
        frag.createWire(name, amount);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.mainActivityContainer);

        if(savedInstanceState == null) {
            //Log.v(LOG_TAG, "null");
            Toolbar mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
            setSupportActionBar(mainToolbar);

            //Add the wire choice fragment
            mFragmentManager = getSupportFragmentManager();
            mFragmentManager.beginTransaction()
                    .add(R.id.mainActivityContainer, new WireChoiceFragment(), mWireChoiceFragmentTag)
                    .add(R.id.mainActivityContainer, new ResultsFragment(), mResultsFragmentTag)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                openAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void doThing(JSONObject test) throws JSONException{
        Log.v(LOG_TAG, test.toString(4));
    }

    public void openAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}
