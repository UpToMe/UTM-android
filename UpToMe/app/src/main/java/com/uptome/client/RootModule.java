package com.uptome.client;

/**
 * The root Mortar module.
 *
 * @author Vladimir Rybkin
 */

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uptome.client.core.PreferencesHelper;
import com.uptome.client.core.android.ActionBarOwner;
import com.uptome.client.core.engine.Engine;
import com.uptome.client.core.model.ICurrentCourse;
import com.uptome.client.core.model.IRegistration;
import com.uptome.client.core.model.ISelfTesting;
import com.uptome.client.core.model.ISkills;
import com.uptome.client.core.scopes.PerApp;

import dagger.Module;
import dagger.Provides;

@Module
public class RootModule {

    private final Context mContext;

    /**
     * Constructor.
     * @param context Root context
     */
    public RootModule(Context context) {
        mContext = context;
    }

    @Provides
    @PerApp
    Context provideContext() {
        return mContext;
    }

    @Provides
    @PerApp
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @PerApp
    Engine provideEngine(Context context) {
        return new Engine(context);
    }

    @Provides
    @PerApp
    PreferencesHelper providePreferences(Context context) {
        return new PreferencesHelper(context);
    }

    @Provides
    @PerApp
    ActionBarOwner getActionBarOwner() {
        return new ActionBarOwner();
    }

    @Provides
    @PerApp
    IRegistration getRegistration(Engine engine) {
        return engine.getRegistrationInterface();
    }

    @Provides
    @PerApp
    ISelfTesting getSelfTesting(Engine engine) {
        return engine.getSelfTestingInterface();
    }

    @Provides
    @PerApp
    ISkills getSkills(Engine engine) {
        return engine.getSkillsInterface();
    }

    @Provides
    @PerApp
    ICurrentCourse getCurrentCourse(Engine engine) {
        return engine.getCurrentCourseInterface();
    }
}
