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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements WireCreatorDialog.OnWireCreateListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    FragmentManager mFragmentManager;
    RelativeLayout mRelativeLayout;
    private String mWireChoiceFragmentTag = "WireChoiceTag";
    private String mResultsFragmentTag = "ResultsTag";

    //WireCreatorDialog calls this
    @Override
    public void onWireCreate(String name, int amount) {
        Log.v(LOG_TAG, "Got " + name + " and amount of " + amount);
        WireChoiceFragment frag =
                (WireChoiceFragment) mFragmentManager.findFragmentByTag(mWireChoiceFragmentTag);
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

            ArrayList<JSONObject> list = new ArrayList<>();
            try {
                list.add(new JSONObject("{\"name\":\"uno\", \"od\":1}"));
                list.add(new JSONObject("{\"name\":\"dos\", \"od\":2}"));
                list.add(new JSONObject("{\"name\":\"tres\", \"od\":3}"));
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSON exception", e);
            }

            WireChoiceFragment frag = WireChoiceFragment.newInstance(list);

            //Add the wire choice fragment
            mFragmentManager = getSupportFragmentManager();
            mFragmentManager.beginTransaction()
                    .add(R.id.mainActivityContainer, frag, mWireChoiceFragmentTag)
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

    public void openAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}
