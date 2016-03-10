package com.uptome.client.ui.splash;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.uptome.client.RootComponent;
import com.uptome.client.UpToMeApplication;

import javax.inject.Inject;


/**
 * Splash screen view.
 *
 * @author Vladimir Rybkin
 */
public class SplashScreenView extends FrameLayout {

    @Inject SplashScreen.Presenter mPresenter;

    /**
     * Screen component
     */
    private SplashScreenComponent mSplashScreenComponent;

    public SplashScreenView(Context context) {
        super(context);
        init(context);
    }

    public SplashScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SplashScreenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Initialize
     * @param context
     */
    protected void init(Context context) {
        RootComponent root = (RootComponent) context.getApplicationContext().getSystemService(UpToMeApplication.ROOT_NAME);
        mSplashScreenComponent = DaggerSplashScreenComponent.builder().rootComponent(root).module(new SplashScreen.Module()).build();
        mSplashScreenComponent.inject(this);
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mPresenter.takeView(this);
    }


    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mPresenter.dropView(this);
    }

    /**
     * Get the screen component.
     *
     * @return instance
     */
    public SplashScreenComponent getSplashScreenComponent() {
        return mSplashScreenComponent;
    }
}
