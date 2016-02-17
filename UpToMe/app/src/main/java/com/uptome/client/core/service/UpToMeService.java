package com.uptome.client.core.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.uptome.client.core.log.ClientLog;

import java.util.LinkedList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * The main service.
 *
 * @author Vladimir Rybkin
 */
public class UpToMeService extends Service {

    /*
     * States
     */
    public final static int STATE_INIT = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_ERROR = 2;

    private int mState = STATE_INIT;

    /**
     * Tag for logging.
     */
    private static final String LOG_TAG = UpToMeService.class.getSimpleName();

    /**
     * The service name
     */
    public static final String SERVICE_NAME = UpToMeService.class.getName();

    /**
     * List of callbacks
     */
    private LinkedList<IUpToMeServiceBinder.IUpToMeServiceCallback> mCallbacks = new LinkedList<>();

    /**
     * Registration instance
     */
    private Registration mRegistration;

    /**
     * Self testing instance
     */
    private SelfTesting mSelfTesting;

    /**
     * Skills instance
     */
    private Skills mSkills;

    /**
     * Current course
     */
    private CurrentCourse mCurrentCourse;

    /**
     * Flag indicating that the service is destroyed
     */
    private boolean mDestroyed;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (mState != STATE_READY) {
            if (mState != STATE_INIT) {
                setState(STATE_INIT);
            }

            final Initializer initializer = new Initializer();
            Observable.create(initializer)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Object>() {
                        @Override
                        public void onCompleted() {
                            if (mDestroyed == false) {
                                mRegistration = initializer.mRegistration;
                                mSelfTesting = initializer.mSelfTesting;
                                mSkills = initializer.mSkills;
                                mCurrentCourse = initializer.mCurrentCourse;

                                setState(STATE_READY);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mDestroyed == false) {
                                setState(STATE_ERROR);
                            }
                        }

                        @Override
                        public void onNext(Object o) {
                        }
                    });
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        ClientLog.d(LOG_TAG, "Binding, intent=%s", intent);
        return new ServiceBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        ClientLog.d(LOG_TAG, "Unbinding, intent=%s", intent);
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCallbacks.clear();
        mDestroyed = true;
    }

    /**
     * Set the new service state.
     * @param newState New state value
     */
    private void setState(int newState) {
        int currentState = mState;
        ClientLog.d(LOG_TAG, "Set the new state, currentState=%d, newState=%d", currentState, newState);

        if (currentState == newState) {
            ClientLog.w(LOG_TAG, "Trying to set the same state, newState=%d", newState);
            return;
        }

        mState = newState;
        notifyAboutNewState(currentState, newState);
    }

    /**
     * Notify listeners about a new state.
     *
     * @param previousState
     * @param newState
     */
    private void notifyAboutNewState(int previousState, int newState) {
        for (IUpToMeServiceBinder.IUpToMeServiceCallback callback : mCallbacks) {
            callback.onNewServiceState(previousState, newState);
        }
    }

    /**
     * Service binder class.
     */
    private class ServiceBinder extends Binder implements IUpToMeServiceBinder {

        @Override
        public void registerCallback(IUpToMeServiceCallback callback) {
            if (mCallbacks.contains(callback) == false) {
                mCallbacks.add(callback);
            }
        }

        @Override
        public void unregisterCallback(IUpToMeServiceCallback callback) {
            mCallbacks.remove(callback);
        }

        @Override
        public int getState() {
            return mState;
        }

        @Override
        public IRegistration getRegistrationInterface() {
            return mRegistration;
        }

        @Override
        public ISelfTesting getSelfTestingInterface() {
            return mSelfTesting;
        }

        @Override
        public ISkills getSkillsInterface() {
            return mSkills;
        }

        @Override
        public ICurrentCourse getCurrentCourseInterface() {
            return mCurrentCourse;
        }
    }

    /**
     * Registration proxy
     */
    private static class Registration implements IRegistration {

        /**
         * Whether the registartion is completed
         */
        private boolean mIsCompleted;

        /**
         * Initialize the instance
         */
        public static Registration init(Context context) {
            ClientLog.d(LOG_TAG, "Start the registration proxy initializaation");
            Registration instance = new Registration();

            ClientLog.d(LOG_TAG, "The registration proxy initializaation completed successfully");
            return instance;
        }

        @Override
        public boolean isCompleted() {
            return mIsCompleted;
        }
    }

    /**
     * Self testing proxy
     */
    private static class SelfTesting implements ISelfTesting {

        /**
         * Whether the self-testing is completed
         */
        private boolean mIsCompleted;

        /**
         * Initialize the instance
         */
        public static SelfTesting init(Context context) {
            ClientLog.d(LOG_TAG, "Start the self-testing proxy initializaation");
            SelfTesting instance = new SelfTesting();

            ClientLog.d(LOG_TAG, "The self-testing proxy initializaation completed successfully");
            return instance;
        }

        @Override
        public boolean isCompleted() {
            return mIsCompleted;
        }
    }

    /**
     * Skills proxy
     */
    private static class Skills implements ISkills {

        /**
         * Initialize the instance
         */
        public static Skills init(Context context) {
            ClientLog.d(LOG_TAG, "Start the skills proxy initializaation");
            Skills skills = new Skills();

            ClientLog.d(LOG_TAG, "The skills proxy initializaation completed successfully");
            return skills;
        }
    }

    /**
     * The current course proxy
     */
    private static class CurrentCourse implements ICurrentCourse {

        /**
         * Initialize the instance
         */
        public static CurrentCourse init(Context context) {
            ClientLog.d(LOG_TAG, "Start the current course proxy initialization");

            ClientLog.d(LOG_TAG, "The skills proxy initializaation completed successfully");
            return null;
        }
    }

    /**
     * The service initializer
     */
    private class Initializer implements Observable.OnSubscribe<Object> {

        /**
         * Instances
         */
        Registration mRegistration;
        SelfTesting mSelfTesting;
        Skills mSkills;
        CurrentCourse mCurrentCourse;

        @Override
        public void call(Subscriber<? super Object> subscriber) {
            if (mDestroyed == true) {
                ClientLog.w(LOG_TAG, "Initialization failed, the service is destroyed");
                subscriber.onCompleted();
                return;
            }

            if (mState != STATE_INIT) {
                ClientLog.e(LOG_TAG, "Initialization failed, the service is not in the INIT state");
                subscriber.onCompleted();
                return;
            }

            boolean error = false;
            Exception exception = null;
            try {
                do {
                    mRegistration = Registration.init(UpToMeService.this);

                    if (mDestroyed == true) {
                        break;
                    }

                    mSelfTesting = SelfTesting.init(UpToMeService.this);

                    if (mDestroyed == true) {
                        break;
                    }

                    mSkills = Skills.init(UpToMeService.this);

                    if (mDestroyed == true) {
                        break;
                    }

                    mCurrentCourse = CurrentCourse.init(UpToMeService.this);
                } while (false);
            } catch (Exception ex) {
                ClientLog.e(LOG_TAG, ex);
                exception = ex;
                error = true;

                mRegistration = null;
                mSelfTesting = null;
                mSkills = null;
                mCurrentCourse = null;
            }

            if (error == false) {
                subscriber.onCompleted();
            } else {
                subscriber.onError(exception);
            }
        }
    }
}
