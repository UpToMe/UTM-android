package com.uptome.client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.uptome.client.core.GsonParceler;
import com.uptome.client.core.engine.Engine;
import com.uptome.client.core.engine.IEngine;
import com.uptome.client.ui.main.MainFrameLayout;
import com.uptome.client.ui.splash.SplashScreen;

import javax.inject.Inject;

import flow.Flow;
import flow.FlowDelegate;
import flow.History;
import flow.StateParceler;
import flow.path.Path;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;
import mortar.dagger1support.ObjectGraphService;

import static mortar.MortarScope.buildChild;
import static mortar.MortarScope.findChild;

/**
 * The main activity.
 *
 * @author Vladimir Rybkin
 */
public class MainActivity extends AppCompatActivity
        implements Flow.Dispatcher, Engine.IEngineCallbacks {

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

    @Override
    public Object getSystemService(@NonNull String name) {
        MortarScope activityScope = findChild(getApplicationContext(), getScopeName());

        if (activityScope == null) {
            activityScope = buildChild(getApplicationContext())
                    .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                    .build(getScopeName());
        }

        if (mFlowDelegate != null) {
            Object flowService = mFlowDelegate.getSystemService(name);
            if (flowService != null) return flowService;
        }

        return activityScope.hasService(name) ? activityScope.getService(name) : super.getSystemService(name);
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
        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mMainFrameLayout = (MainFrameLayout) findViewById(R.id.container);

        // inject dependencies
        ObjectGraphService.inject(this, this);
        mEngine.takeView(this);

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
        BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState);
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
        mEngine.dropView(this);

        if (isFinishing()) {
            MortarScope activityScope = findChild(getApplicationContext(), getScopeName());
            if (activityScope != null) {
                activityScope.destroy();
            }
        }

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
        Path newScreen = traversal.destination.top();
        mMainFrameLayout.dispatch(traversal, callback);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onEngineNewState(int previousState, int newState) {
        switch (newState) {
            case IEngine.STATE_INIT:
                break;

            case IEngine.STATE_REGISTRATION:
                break;

            case IEngine.STATE_SELF_TEST:
                break;

            case IEngine.STATE_SKILLS:
                break;

            case IEngine.STATE_COURSE:
                break;

            case IEngine.STATE_ERROR:
                break;

            default:
                throw new IllegalArgumentException("Unexpected state value " + Integer.toString(newState));
        }
    }
}
