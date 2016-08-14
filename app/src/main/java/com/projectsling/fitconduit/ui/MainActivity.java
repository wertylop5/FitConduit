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

public class MainActivity extends AppCompatActivity
        implements WireCreatorDialog.OnWireCreateListener, WireEditorDialog.OnWireEditListener,
WireMenuDialog.StartEditListener, WireDeleteDialog.OnWireDeleteListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    FragmentManager mFragmentManager;
    RelativeLayout mRelativeLayout;
    private String mWireChoiceFragmentTag = "WireChoiceTag";
    private String mResultsFragmentTag = "ResultsTag";

    //WireCreatorDialog calls this
    @Override
    public void onWireCreate(String name, int amount) {
        Log.v(LOG_TAG, "Got new wire " + name + " and amount of " + amount);
        WireChoiceFragment frag =
                (WireChoiceFragment) mFragmentManager.findFragmentByTag(mWireChoiceFragmentTag);
        frag.createWire(name, amount);
    }

    @Override
    public void onWireEdit(String newWire, int amount, int position) {
        Log.v(LOG_TAG, "Got edit wire name " + newWire +
                " new amount " + amount + " at position " + position);
        WireChoiceFragment frag =
                (WireChoiceFragment) mFragmentManager.findFragmentByTag(mWireChoiceFragmentTag);
        frag.editWire(newWire, amount, position);
    }

    @Override
    public void onWireDelete(int wireListPosition) {
        WireChoiceFragment frag =
                (WireChoiceFragment) mFragmentManager.findFragmentByTag(mWireChoiceFragmentTag);
        frag.deleteWire(wireListPosition);
    }

    //Shows the wire edit dialog
    @Override
    public void startEdit(int wireListPosition) {
        Log.v(LOG_TAG, "Starting edit dialog");

        WireChoiceFragment frag =
                (WireChoiceFragment) mFragmentManager.findFragmentByTag(mWireChoiceFragmentTag);
        frag.startEditDialog(wireListPosition);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.mainActivityContainer);

        mFragmentManager = getSupportFragmentManager();

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);

        if(savedInstanceState == null) {
            //Log.v(LOG_TAG, "null");

            //Holds the json for each wire
            //Should be in form of
            /*
            * {
            *   "name":<string>,
            *   "od":<double>
            * }
            * */
            ArrayList<JSONObject> list = new ArrayList<>();
            try {
                list.add(new JSONObject("{\"name\":\"uno\", \"od\":1.0}"));
                list.add(new JSONObject("{\"name\":\"dos\", \"od\":2.0}"));
                list.add(new JSONObject("{\"name\":\"tres\", \"od\":3.0}"));
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSON exception", e);
            }

            WireChoiceFragment frag = WireChoiceFragment.newInstance(list);
            //Add the wire choice fragment
            mFragmentManager.beginTransaction()
                    .add(R.id.mainActivityContainer, frag, mWireChoiceFragmentTag)
                    .add(R.id.mainActivityContainer, new ResultsFragment(), mResultsFragmentTag)
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.v(LOG_TAG, "Destroyed");
    }

    /*@Override
    protected void onSaveInstanceState(Bundle outState) {
        if (!outState.containsKey("keepFrag")) {
            outState.putBoolean("keepFrag", true);
        }
    }*/

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
