package com.uptome.client.core.engine;

import android.content.Context;
import android.os.Bundle;

import com.uptome.client.core.log.ClientLog;
import com.uptome.client.core.service.ICurrentCourse;
import com.uptome.client.core.service.IRegistration;
import com.uptome.client.core.service.ISelfTesting;
import com.uptome.client.core.service.IUpToMeServiceBinder;
import com.uptome.client.core.service.UpToMeServiceClient;
import com.uptome.client.core.service.UpToMeServiceObservable;

import mortar.MortarScope;
import mortar.Presenter;
import mortar.bundler.BundleService;
import rx.Observable;
import rx.functions.Action1;

/**
 * The core engine.
 *
 * @author Vladimir Rybkin
 */
public class Engine extends Presenter<Engine.IEngineCallbacks> implements IEngine, IUpToMeServiceBinder.IUpToMeServiceCallback {

    private static final String LOG_TAG = Engine.class.getSimpleName();

    /*
     * Extras
     */
    private static final String EXTRA_SPLASH_COMPLETED = Engine.class.getSimpleName() + "#EXTRA_SPLASH_COMPLETED";

    /**
     * Connection to the service
     */
    private UpToMeServiceClient mUpToMeServiceClient;

    /**
     * Flag indicating that the splash screen has been completed
     */
    private boolean mSplashCompleted;

    /**
     * A pending state
     */
    private int mPendingState = IEngine.STATE_UNKNOWN;

    /**
     * State
     */
    private int mState = STATE_UNKNOWN;

    public interface IEngineCallbacks {

        /**
         * Get the current context.
         * @return context
         */
        Context getContext();

        /**
         * On a new engine state
         *
         * @param previousState
         * @param newState
         */
        void onEngineNewState(int previousState, int newState);
    }

    @Override
    protected BundleService extractBundleService(IEngineCallbacks callbacks) {
        return BundleService.getBundleService(callbacks.getContext());
    }

    @Override
    protected void onEnterScope(MortarScope scope) {
        ClientLog.d(LOG_TAG, "Enter scope");

        mUpToMeServiceClient = MortarScope.getScope(getView().getContext().getApplicationContext()).getService(UpToMeServiceClient.SERVICE_NAME);
        Observable.create(new UpToMeServiceObservable(mUpToMeServiceClient))
                .subscribe(new Action1<UpToMeServiceClient>() {
                               @Override
                               public void call(UpToMeServiceClient upToMeServiceClient) {
                                   if (hasView() == false) {
                                       return;
                                   }

                                   mUpToMeServiceClient.registerCallback(Engine.this);
                                   boolean serviceReady = mUpToMeServiceClient.getState() == IUpToMeServiceBinder.STATE_READY;
                                   if (serviceReady == true) {
                                       setState(STATE_INIT);
                                       onServiceReady();
                                   } else {
                                       boolean error = mUpToMeServiceClient.getState() == IUpToMeServiceBinder.STATE_ERROR;

                                       if (error == true) {
                                           setState(STATE_ERROR);
                                       } else {
                                           setState(STATE_INIT);
                                       }
                                   }
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                setState(STATE_ERROR);
                            }
                        });
    }

    @Override
    public void onNewServiceState(int previousState, int newState) {
        ClientLog.d(LOG_TAG, "On a new service state, previousState=%d new=%d", previousState, newState);

        if (newState == IUpToMeServiceBinder.STATE_READY) {
            onServiceReady();
        } else if (newState == IUpToMeServiceBinder.STATE_ERROR) {
            setState(STATE_ERROR);
        } else {
            ClientLog.e(LOG_TAG, "Unexpected new service state=%d", newState);
        }
    }

    /**
     * On the service ready event.
     */
    private void onServiceReady() {
        do {
            IRegistration registration = mUpToMeServiceClient.getRegistrationInterface();
            if (registration.isCompleted() == false) {
                setState(STATE_REGISTRATION);
                break;
            }

            ISelfTesting selfTesting = mUpToMeServiceClient.getSelfTestingInterface();
            if (selfTesting.isCompleted() == false) {
                setState(STATE_SELF_TEST);
                break;
            }

            ICurrentCourse currentCourse = mUpToMeServiceClient.getCurrentCourseInterface();
            if (currentCourse != null) {
                setState(STATE_COURSE);
                break;
            }

            setState(STATE_SKILLS);
        } while (false);
    }

    /**
     * Set the new state.
     *
     * @param newState New state value
     */
    private void setState(int newState) {
        int currentState = mState;
        ClientLog.d(LOG_TAG, "Set a new state, current=%d new=%d", currentState, newState);

        if (currentState == newState) {
            ClientLog.w(LOG_TAG, "The same state transition, canceled");
            return;
        }

        if (mSplashCompleted == false && (newState != IEngine.STATE_INIT && newState != IEngine.STATE_ERROR)) {
            mPendingState = newState;
            ClientLog.w(LOG_TAG, "Waiting for the splash, new pending state value=", mPendingState);
            return;
        }

        switch (newState) {
            case STATE_INIT:
                break;

            case STATE_REGISTRATION:
                break;

            case STATE_SELF_TEST:
                break;

            case STATE_SKILLS:
                break;

            case STATE_COURSE:
                break;

            case STATE_ERROR:
                break;

            default:
                throw new IllegalArgumentException("Unexpected state value " + Integer.toString(newState));
        }

        mState = newState;
        notifyStateChanged(currentState, newState);
    }

    /**
     * Notify the listeners about a new state.
     * @param previousState
     * @param newState
     */
    private void notifyStateChanged(int previousState, int newState) {
        IEngineCallbacks callbacks = getView();

        if (callbacks != null) {
            callbacks.onEngineNewState(previousState, newState);
        }
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        ClientLog.d(LOG_TAG, "Load scope");
        if (savedInstanceState != null) {
            mSplashCompleted = savedInstanceState.getBoolean(EXTRA_SPLASH_COMPLETED);
        }
    }

    @Override
    protected void onSave(Bundle outState) {
        ClientLog.d(LOG_TAG, "Save scope");
        outState.putBoolean(EXTRA_SPLASH_COMPLETED, mSplashCompleted);
    }

    @Override
    protected void onExitScope() {
        ClientLog.d(LOG_TAG, "Exit scope");
        if (mUpToMeServiceClient.isConnectedToService() == true) {
            mUpToMeServiceClient.unregisterCallback(this);
        }
    }

    @Override
    public int getState() {
        return mState;
    }

    /**
     * Notification that the splash screen has been completed
     */
    public void onSplashCompleted() {
        if (mSplashCompleted == false) {
            mSplashCompleted = true;

            if (mPendingState != IEngine.STATE_UNKNOWN) {
                setState(mPendingState);
                mPendingState = IEngine.STATE_UNKNOWN;
            }
        } else {
            ClientLog.w(LOG_TAG, "onSplashCompleted() called when the mSplashCompleted is true");
        }
    }
}
