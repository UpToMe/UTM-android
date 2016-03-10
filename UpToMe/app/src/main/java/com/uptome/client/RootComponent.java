package com.uptome.client;

import com.uptome.client.core.android.ActionBarOwner;
import com.uptome.client.core.engine.Engine;
import com.uptome.client.core.model.IRegistration;
import com.uptome.client.core.scopes.PerApp;

import dagger.Component;

/**
 * The root component.
 *
 * @author Vladimir Rybkin
 */
@PerApp
@Component(modules = RootModule.class)
public interface RootComponent {

    void inject(MainActivity activity);

    void inject(Engine engine);

    void inject(ActionBarOwner actionBarOwner);

    Engine provideEngine();

    ActionBarOwner provideActionBarOwner();

    IRegistration provideRegistration();
}
