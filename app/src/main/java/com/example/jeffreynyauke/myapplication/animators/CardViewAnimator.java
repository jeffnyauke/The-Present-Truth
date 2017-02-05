package com.example.jeffreynyauke.myapplication.animators;

import android.view.View;

/**
 * CardView animation logic abstraction
 *
 * @author Jorge Castillo Pérez
 */
public interface CardViewAnimator {
    
    void animateCardIn(View cardView);
    
    void animateCardOut(View cardView);
    
    void attachCardViewAnimationListener(CardViewAnimationListener listener);
}
