package com.uptome.client.ui.splash;

import com.uptome.client.R;
import com.uptome.client.core.android.ActionBarOwner;
import com.uptome.client.core.engine.Engine;
import com.uptome.client.core.scopes.ViewScope;
import com.uptome.client.ui.common.Layout;
import com.uptome.client.ui.common.ViewPresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.Provides;
import flow.path.Path;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Splash screen representer.
 *
 * @author Vladimir Rybkin
 */
@Layout(R.layout.splash_layout)
public class SplashScreen extends Path {

    @dagger.Module
    public static class Module {

        @ViewScope
        @Provides
        SplashScreen.Presenter providePresenter() {
            return new Presenter();
        }
    }

    @ViewScope
    public static class Presenter extends ViewPresenter<SplashScreenView> {

        @Inject
        Engine mEngine;

        @Inject
        ActionBarOwner mActionBarOwner;

        @Inject
        public Presenter() {
            mEngine = null;
        }

        @Override
        public void takeView(SplashScreenView view) {
            super.takeView(view);
            if (hasView() == false) {
                return;
            }

            view.getSplashScreenComponent().inject(this);
            mActionBarOwner.setActionBarVisibility(false);


            final int DELAY_SECONDS = 3;
            Observable.empty().delay(DELAY_SECONDS, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Object>() {
                        @Override
                        public void onCompleted() {
                            if (hasView() == false) {
                                return;
                            }

                            mEngine.onSplashCompleted();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Object o) {

                        }
                    });
        }
    }
}
