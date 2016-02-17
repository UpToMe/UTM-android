package com.uptome.client.core.service;

import rx.Observable;
import rx.Subscriber;

/**
 * The service connection observable.
 *
 * @author Vladimir Rybkin
 */
public class UpToMeServiceObservable implements Observable.OnSubscribe<UpToMeServiceClient> {

    /**
     * A service client instance.
     */
    private final UpToMeServiceClient mUpToMeServiceClient;

    /**
     * Constructor.
     * @param client A service client
     */
    public UpToMeServiceObservable(UpToMeServiceClient client) {
        mUpToMeServiceClient = client;
    }

    @Override
    public void call(final Subscriber<? super UpToMeServiceClient> subscriber) {
        boolean connected = mUpToMeServiceClient.isConnectedToService(new UpToMeServiceClient.IConnectionListener() {
            @Override
            public void onConnected() {
                subscriber.onNext(mUpToMeServiceClient);
                subscriber.onCompleted();
            }

            @Override
            public void onConnectionError() {
                subscriber.onError(null);
            }
        });

        if (connected == true) {
            subscriber.onNext(mUpToMeServiceClient);
            subscriber.onCompleted();
        }
    }
}
