package com.uptome.client.ui.error;

import com.uptome.client.ui.common.ViewPresenter;

import javax.inject.Singleton;

import flow.path.Path;

/**
 * A global error screen.
 *
 * @author Vladimir Rybkin
 */
//@Layout(R.layout.error_layout) @WithModule(ErrorScreen.Module.class)
public class ErrorScreen extends Path {

    @Singleton
    public static class Presenter extends ViewPresenter<ErrorScreenView> {

  //      @Inject
//        Engine mEngine;

    }
}
