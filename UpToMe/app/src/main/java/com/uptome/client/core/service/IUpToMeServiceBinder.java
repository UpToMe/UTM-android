package com.uptome.client.core.service;

/**
 * The main service binder interface.
 *
 * @author Vladrimir Rybkin
 */
public interface IUpToMeServiceBinder {

    int STATE_INIT = UpToMeService.STATE_INIT;
    int STATE_READY = UpToMeService.STATE_READY;
    int STATE_ERROR = UpToMeService.STATE_ERROR;

    /**
     * The service callback interface
     */
    interface IUpToMeServiceCallback {

        /**
         * On new service state.
         *
         * @param previousState
         * @param newState
         */
        void onNewServiceState(int previousState, int newState);
    }

    /**
     * Register a service callback.
     *
     * @param callback
     */
    void registerCallback(IUpToMeServiceCallback callback);

    /**
     * Unregister a service callback.
     *
     * @param callback
     */
    void unregisterCallback(IUpToMeServiceCallback callback);

    /**
     * Get the service state.
     * @return value
     */
    int getState();

    /**
     * Get a registration interface.
     *
     * @return instance
     */
    IRegistration getRegistrationInterface();

    /**
     * Get the selftesting interface.
     *
     * @return instance
     */
    ISelfTesting getSelfTestingInterface();

    /**
     * Get the skills interface.
     *
     * @return instance
     */
    ISkills getSkillsInterface();

    /**
     * Get the current course interface.
     *
     * @return instance or null
     */
    ICurrentCourse getCurrentCourseInterface();
}
