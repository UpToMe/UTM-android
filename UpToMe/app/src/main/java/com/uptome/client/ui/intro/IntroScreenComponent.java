package com.uptome.client.ui.intro;

import com.uptome.client.RootComponent;
import com.uptome.client.core.scopes.ViewScope;

import dagger.Component;

@ViewScope
@Component(dependencies = RootComponent.class, modules = IntroScreen.Module.class)
public interface IntroScreenComponent {

    void inject(IntroScreenView view);

    void inject(IntroScreen.Presenter presenter);
}
