package com.uptome.client.ui.registration;

import com.uptome.client.RootComponent;
import com.uptome.client.core.scopes.ViewScope;

import dagger.Component;

@ViewScope
@Component(dependencies = RootComponent.class, modules = RegistrationScreen.Module.class)
public interface RegistrationScreenComponent {

    void inject(RegistrationScreenView view);

    void inject(RegistrationScreen.Presenter presenter);
}
