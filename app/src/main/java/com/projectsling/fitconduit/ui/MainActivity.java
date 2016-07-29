package com.projectsling.fitconduit.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.projectsling.fitconduit.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    FragmentManager mFragmentManager;
    RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.mainActivityContainer);

        if(savedInstanceState == null) {
            //Log.v(TAG, "null");
            Toolbar mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
            setSupportActionBar(mainToolbar);

            //Add the wire choice fragment
            mFragmentManager = getSupportFragmentManager();
            mFragmentManager.beginTransaction()
                    .add(R.id.mainActivityContainer, new WireChoiceFragment())
                    .add(R.id.mainActivityContainer, new ResultsFragment())
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
        Log.v(TAG, test.toString(4));
    }

    public void openAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}
