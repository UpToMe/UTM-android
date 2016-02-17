package com.uptome.client.core.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.uptome.client.core.log.ClientLog;

import java.util.LinkedList;

import mortar.MortarScope;
import mortar.Scoped;

/**
 * A client to the main service.
 *
 * @author Vladimir Rybkin
 */
public class UpToMeServiceClient implements Scoped, IUpToMeServiceBinder {

    public static final String SERVICE_NAME = UpToMeService.SERVICE_NAME;

    /**
     * Tag for logging.
     */
    protected final String mLogTag;

    /**
     * Context to use
     */
    private final Context mContext;

    /**
     * Flag indicating that the client is waiting connection
     */
    private boolean mWaitingConnection;

    /**
     * Service binder
     */
    protected IUpToMeServiceBinder mServiceBinder;

    /**
     * The list of connection listeners.
     */
    private LinkedList<IConnectionListener> mConnectionListeners = new LinkedList<>();

    /**
     * Connection listener interface.
     */
    public interface IConnectionListener {

        /**
         * On connection notifications
         */
        void onConnected();

        /**
         * On connection error notification
         */
        void onConnectionError();
    }

    /**
     * Constructor.
     * @param context Context to use
     * @param ownerLogTag Owner log tag
     */
    public UpToMeServiceClient(Context context, String ownerLogTag) {
        mContext = context;
        mLogTag = UpToMeServiceClient.class.getSimpleName() + "[" + ownerLogTag + "]";
    }

    /**
     * Do playback service binding.
     *
     * @return true if service is going to be bound, false otherwise
     */
    public boolean connect() {
        boolean result = true;

        if (isConnectedToService() == false && mWaitingConnection == false) {
            ClientLog.d(mLogTag, "initiate service binding");
            mWaitingConnection = true;
            mContext.startService(new Intent(mContext, UpToMeService.class));
            result = mContext.bindService(new Intent(mContext, UpToMeService.class), mConnection, Context.BIND_AUTO_CREATE);
        }

        return result;
    }

    /**
     * Unbind the client from the service.
     */
    public void disconnect() {
        if (isConnectedToService() == true) {
            ClientLog.d(mLogTag, "initiate service unbinding");

            mContext.unbindService(mConnection);
            mServiceBinder = null;
        } else {
            mWaitingConnection = false;
        }
    }

    /**
     * On service connected.
     */
    protected void onConnected() {
    }

    /**
     * Check whether the manager is connected to the download service.
     *
     * @return true if manager is connected, false otherwise
     */
    public boolean isConnectedToService() {
        return mServiceBinder != null;
    }

    /**
     * Check whether the connection is established, if not the conenct will be called and the listener will be added to the pool of listeners and notified once the connection is established.
     * @param listener Listener to add if connection is not established, otherwise ignored
     * @return true if the client is connected, false otherwise
     */
    public boolean isConnectedToService(IConnectionListener listener) {
        boolean res = isConnectedToService();

        if (res == false) {
            if (listener != null) {
                mConnectionListeners.add(listener);
            }

            if (mWaitingConnection == false) {
                connect();
            }
        }

        return res;
    }

    /**
     * Connection to the download service
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        /*
         * (non-Javadoc)
         * @see android.content.ServiceConnection#onServiceConnected(android.content.ComponentName, android.os.IBinder)
         */
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ClientLog.d(mLogTag, "service bound successfully");

            if (mWaitingConnection == true) {
                mServiceBinder = (IUpToMeServiceBinder) service;
                mWaitingConnection = false;
                onConnected();

                for (IConnectionListener listener : mConnectionListeners) {
                    listener.onConnected();
                }
            } else {
                Log.d(mLogTag, "disconnect was called while the connection establishing");
                mWaitingConnection = false;
                mContext.unbindService(this);

                for (IConnectionListener listener : mConnectionListeners) {
                    listener.onConnectionError();
                }
            }

            mConnectionListeners.clear();
        }

        /*
         * (non-Javadoc)
         * @see android.content.ServiceConnection#onServiceDisconnected(android.content.ComponentName)
         */
        @Override
        public void onServiceDisconnected(ComponentName className) {
            ClientLog.d(mLogTag, "service unbound successfully");
        }
    };

    @Override
    public void onEnterScope(MortarScope scope) {
        connect();
    }

    @Override
    public void onExitScope() {
        disconnect();
    }

    /**
     * Ensure that conention is established.
     * @param info Info string
     */
    private void ensureConnection(String info) {
        if (isConnectedToService() == false) {
            throw new IllegalStateException("Trying to call the service without connection (info+" + info);
        }
    }

    @Override
    public void registerCallback(IUpToMeServiceCallback callback) {
        ensureConnection("registerCallback");
        mServiceBinder.registerCallback(callback);
    }

    @Override
    public void unregisterCallback(IUpToMeServiceCallback callback) {
        ensureConnection("unregisterCallback");
        mServiceBinder.unregisterCallback(callback);
    }

    @Override
    public int getState() {
        ensureConnection("getState");
        return mServiceBinder.getState();
    }

    @Override
    public IRegistration getRegistrationInterface() {
        ensureConnection("getRegistrationInterface");
        return mServiceBinder.getRegistrationInterface();
    }

    @Override
    public ISelfTesting getSelfTestingInterface() {
        ensureConnection("getSelfTestingInterface");
        return mServiceBinder.getSelfTestingInterface();
    }

    @Override
    public ISkills getSkillsInterface() {
        ensureConnection("getSkillsInterface");
        return mServiceBinder.getSkillsInterface();
    }

    @Override
    public ICurrentCourse getCurrentCourseInterface() {
        ensureConnection("getCurrentCourseInterface");
        return mServiceBinder.getCurrentCourseInterface();
    }
}
