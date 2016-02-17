package com.uptome.client.ui.splash;

import android.os.Bundle;

import com.uptome.client.R;
import com.uptome.client.RootModule;
import com.uptome.client.core.engine.Engine;
import com.uptome.client.core.mortar.WithModule;
import com.uptome.client.ui.common.Layout;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.path.Path;
import mortar.ViewPresenter;
import mortar.dagger1support.ObjectGraphService;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Splash screen representer.
 *
 * @author Vladimir Rybkin
 */
@Layout(R.layout.splash_layout) @WithModule(SplashScreen.Module.class)
public class SplashScreen extends Path {

    @dagger.Module(injects = {SplashScreenView.class, SplashScreen.Presenter.class}, addsTo = RootModule.class)
    public static class Module {

    }

    @Singleton
    public static class Presenter extends ViewPresenter<SplashScreenView> {

        @Inject
        Engine mEngine;

        @Override
        public void onLoad(Bundle savedInstance) {
            super.onLoad(savedInstance);
            if (hasView() == false) {
                return;
            }

            final int DELAY_SECONDS = 3;
            ObjectGraphService.inject(getView().getContext(), this);
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
