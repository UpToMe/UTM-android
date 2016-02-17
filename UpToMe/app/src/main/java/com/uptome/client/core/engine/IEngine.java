package com.uptome.client.core.engine;

/**
 * Engine interface
 *
 * @author Vladimir Rybkin
 */
public interface IEngine {

    /*
     * States
     */
    int STATE_UNKNOWN = -1;
    int STATE_INIT = 0;
    int STATE_REGISTRATION = 1;
    int STATE_SELF_TEST = 2;
    int STATE_SKILLS = 3;
    int STATE_COURSE = 4;
    int STATE_ERROR = 5;

    /**
     * Get the engine state.
     *
     * @return value
     */
    int getState();
}
