package com.uptome.client;

import android.app.Application;

import com.uptome.client.core.service.UpToMeServiceClient;

import dagger.ObjectGraph;
import mortar.MortarScope;
import mortar.dagger1support.ObjectGraphService;

/**
 * The application class.
 *
 * @author Vladimir Rybkin
 */
public class UpToMeApplication extends Application {

    public final static String LOG_TAG = UpToMeApplication.class.getSimpleName();

    /**
     * Mortar lib root scope
     */
    private MortarScope mRootScope;

    @Override
    public void onCreate() {
        super.onCreate();
        mRootScope = MortarScope.buildRootScope()
                .withService(ObjectGraphService.SERVICE_NAME, ObjectGraph.create(new RootModule()))
                .withService(UpToMeServiceClient.SERVICE_NAME, new UpToMeServiceClient(this, LOG_TAG))
                .build(LOG_TAG);

    }

    @Override
    public Object getSystemService(String name) {
        return mRootScope.hasService(name) ? mRootScope.getService(name) : super.getSystemService(name);
    }
}