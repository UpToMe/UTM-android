package com.uptome.client.ui.intro;

import com.uptome.client.R;
import com.uptome.client.core.android.ActionBarOwner;
import com.uptome.client.core.engine.Engine;
import com.uptome.client.core.scopes.ViewScope;
import com.uptome.client.ui.common.Layout;
import com.uptome.client.ui.common.ViewPresenter;
import com.uptome.client.ui.splash.SplashScreenView;

import javax.inject.Inject;

import dagger.Provides;
import flow.path.Path;

/**
 * Intro screen representer.
 *
 * @author Vladimir Rybkin
 */
@Layout(R.layout.intro_layout)
public class IntroScreen extends Path {

    @dagger.Module
    public static class Module {
        @ViewScope
        @Provides
        IntroScreen.Presenter providePresenter() {
            return new Presenter();
        }
    }

    @ViewScope
    public static class Presenter extends ViewPresenter<IntroScreenView> {
        @Inject
        Engine mEngine;

        @Inject
        ActionBarOwner mActionBarOwner;

        @Override
        public void takeView(IntroScreenView view) {
            super.takeView(view);
            if (hasView() == false) {
                return;
            }

            view.getIntroScreenComponent().inject(this);

            mActionBarOwner.setActionBarVisibility(false);
        }

        /**
         * On button click
         */
        public void onGotIt() {
            mEngine.onIntroCompleted();
        }
    }
}
