package com.uptome.client;

import android.app.Application;

/**
 * The application class.
 *
 * @author Vladimir Rybkin
 */
public class UpToMeApplication extends Application {

    public static final String ROOT_NAME = "daggerservice";

    /**
     * Mortar lib root scope
     */
    private RootComponent mRootComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mRootComponent = DaggerRootComponent.builder().rootModule(new RootModule(this)).build();
    }

    @Override
    public Object getSystemService(String name) {
        return name.equals(ROOT_NAME) ? mRootComponent : super.getSystemService(name);
    }
}