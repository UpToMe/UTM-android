package com.uptome.client;

/**
 * The root Mortar module.
 *
 * @author Vladimir Rybkin
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uptome.client.core.engine.Engine;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = { MainActivity.class },
        library = true)
public class RootModule {

    @Provides
    @Singleton
    Gson provideGson() {
            return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    Engine provideEngine() {
        return new Engine();
    }
}
