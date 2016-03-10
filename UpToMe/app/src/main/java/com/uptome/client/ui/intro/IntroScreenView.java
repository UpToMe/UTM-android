package com.uptome.client.ui.intro;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.uptome.client.R;
import com.uptome.client.RootComponent;
import com.uptome.client.UpToMeApplication;
import com.uptome.client.ui.splash.SplashScreenComponent;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Intro screen view.
 */
public class IntroScreenView extends FrameLayout {

    @Inject IntroScreen.Presenter mPresenter;

    /**
     * Screen component
     */
    private IntroScreenComponent mIntroScreenComponent;

    public IntroScreenView(Context context) {
        super(context);
        init(context);
    }

    public IntroScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IntroScreenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Initialize
     * @param context
     */
    protected void init(Context context) {
        RootComponent root = (RootComponent) context.getApplicationContext().getSystemService(UpToMeApplication.ROOT_NAME);
        mIntroScreenComponent = DaggerIntroScreenComponent.builder().rootComponent(root).module(new IntroScreen.Module()).build();
        mIntroScreenComponent.inject(this);
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mPresenter.takeView(this);
    }

    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mPresenter.dropView(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        RxView.clicks(findViewById(R.id.btn_ok)).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mPresenter.onGotIt();
            }
        });
    }

    /**
     * Get the screen component.
     *
     * @return instance
     */
    public IntroScreenComponent getIntroScreenComponent() {
        return mIntroScreenComponent;
    }
}
