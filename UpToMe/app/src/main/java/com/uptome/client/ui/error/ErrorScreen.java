package com.uptome.client.ui.error;

import com.uptome.client.R;
import com.uptome.client.RootModule;
import com.uptome.client.core.engine.Engine;
import com.uptome.client.core.mortar.WithModule;
import com.uptome.client.ui.common.Layout;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.path.Path;
import mortar.ViewPresenter;

/**
 * A global error screen.
 *
 * @author Vladimir Rybkin
 */
@Layout(R.layout.error_layout) @WithModule(ErrorScreen.Module.class)
public class ErrorScreen extends Path {

    @dagger.Module(injects = {ErrorScreenView.class, ErrorScreen.Presenter.class}, addsTo = RootModule.class)
    public static class Module {

    }

    @Singleton
    public static class Presenter extends ViewPresenter<ErrorScreenView> {

        @Inject
        Engine mEngine;

    }
}
