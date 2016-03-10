package com.uptome.client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.uptome.client.core.GsonParceler;
import com.uptome.client.core.android.ActionBarOwner;
import com.uptome.client.core.engine.Engine;
import com.uptome.client.core.engine.IEngine;
import com.uptome.client.ui.error.ErrorScreen;
import com.uptome.client.ui.intro.IntroScreen;
import com.uptome.client.ui.main.MainFrameLayout;
import com.uptome.client.ui.registration.RegistrationScreen;
import com.uptome.client.ui.splash.SplashScreen;

import javax.inject.Inject;

import flow.Flow;
import flow.FlowDelegate;
import flow.History;
import flow.StateParceler;

/**
 * The main activity.
 *
 * @author Vladimir Rybkin
 */
public class MainActivity extends AppCompatActivity
        implements Flow.Dispatcher, Engine.IEngineCallbacks, ActionBarOwner.ActionBarOwnerCallback {

    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    /**
     * Flow delegate instance
     */
    private flow.FlowDelegate mFlowDelegate;

    /**
     * Main container
     */
    private MainFrameLayout mMainFrameLayout;

    /**
     * The main engine
     */
    @Inject
    Engine mEngine;

    /**
     * Action bar
     */
    @Inject
    ActionBarOwner mActionBarOwner;

    @Override
    public Object getSystemService(@NonNull String name) {
        if (mFlowDelegate != null) {
            Object flowService = mFlowDelegate.getSystemService(name);
            if (flowService != null) return flowService;
        }

        return super.getSystemService(name);
    }

    /**
     * Get the activity scope name.
     *
     * @return name
     */
    private String getScopeName() {
        return getClass().getName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mMainFrameLayout = (MainFrameLayout) findViewById(R.id.container);

        // inject dependencies
        RootComponent root = (RootComponent) getApplicationContext().getSystemService(UpToMeApplication.ROOT_NAME);
        root.inject(this);

        mEngine.attachCallbacks(this);
        mActionBarOwner.attachCallbacks(this);

        // initialize the Flow
        StateParceler parceler = new GsonParceler(new Gson());
        History defaultHistory = History.single(new SplashScreen());
        FlowDelegate.NonConfigurationInstance nonConfig =
                (FlowDelegate.NonConfigurationInstance) getLastNonConfigurationInstance();
        mFlowDelegate = FlowDelegate.onCreate(nonConfig, getIntent(), savedInstanceState, parceler, defaultHistory, this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mFlowDelegate.onNewIntent(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mFlowDelegate.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFlowDelegate.onResume();
    }

    @Override
    protected void onPause() {
        mFlowDelegate.onPause();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (!mFlowDelegate.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        mEngine.destroy();
        mActionBarOwner.destroy();

        super.onDestroy();

        mMainFrameLayout = null;
        mFlowDelegate = null;
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void dispatch(Flow.Traversal traversal, Flow.TraversalCallback callback) {
        mMainFrameLayout.dispatch(traversal, callback);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setActionBarVisibility(boolean visible) {
        ActionBar actionBar = getSupportActionBar();
        if (visible == true && actionBar.isShowing() == false) {
            actionBar.show();
        } else if (visible == false && actionBar.isShowing() == true) {
            actionBar.hide();
        }
    }

    @Override
    public void onEngineNewState(int previousState, int newState) {
        switch (newState) {
            case IEngine.STATE_INIT:
                break;

            case IEngine.STATE_INTRO:
                Flow.get(this).setHistory(History.single(new IntroScreen()), Flow.Direction.REPLACE);
                break;

            case IEngine.STATE_REGISTRATION:
                int index = mEngine.getRegistrationInterface().getNextQuestionIndex(-1);
                int nextIndex = mEngine.getRegistrationInterface().getNextQuestionIndex(index);
                Flow.get(this).setHistory(History.single(new RegistrationScreen(index, nextIndex)), Flow.Direction.REPLACE);
                break;

            case IEngine.STATE_SELF_TEST:
                break;

            case IEngine.STATE_SKILLS:
                break;

            case IEngine.STATE_COURSE:
                break;

            case IEngine.STATE_ERROR:
                Flow.get(this).set(new ErrorScreen());
                break;

            default:
                throw new IllegalArgumentException("Unexpected state value " + Integer.toString(newState));
        }
    }
}
