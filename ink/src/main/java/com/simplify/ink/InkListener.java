package com.simplify.ink;

public interface InkListener {
    /**
     * Callback method when the ink view has been cleared
     */
    void onInkClear();

    /**
     * Callback method when the ink view receives a touch event
     * (Will be fired multiple times during a signing)
     */
    void onInkDraw();
}
