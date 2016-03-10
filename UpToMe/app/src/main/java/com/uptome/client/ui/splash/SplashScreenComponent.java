package com.uptome.client.ui.splash;

import com.uptome.client.RootComponent;
import com.uptome.client.core.scopes.ViewScope;

import dagger.Component;

@ViewScope
@Component(dependencies = RootComponent.class, modules = SplashScreen.Module.class)
public interface SplashScreenComponent {

    void inject(SplashScreenView view);

    void inject(SplashScreen.Presenter presenter);
}
