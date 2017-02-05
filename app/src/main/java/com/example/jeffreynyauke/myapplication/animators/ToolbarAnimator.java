package com.example.jeffreynyauke.myapplication.animators;

import android.view.View;

/**
 * Abstract toolbar animators from view logic
 *
 * @author Jorge Castillo Pérez
 */
public interface ToolbarAnimator {
    
    void hideInstantToolbar(final View toolbarView);
    
    void showDelayedSmoothToolbar(final View toolbarView);
    
    void showSmoothToolbar(final View toolbarView);
    
    void hideSmoothToolbar(final View toolbarView);
    
    void attachToolbarAnimationListener(ToolbarAnimationListener listener);
}
