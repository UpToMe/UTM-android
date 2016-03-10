package com.uptome.client.ui.common;

import flow.path.Path;

/**
 * View interface extension for views that want to apply the path object associated with it.
 *
 * @author Vladimir Rybkin
 */
public interface IPathApply {

    /**
     * The method is called as soon as the view is inflated.
     * @param to The "to" object
     */
    void applyPath(Path to);
}
