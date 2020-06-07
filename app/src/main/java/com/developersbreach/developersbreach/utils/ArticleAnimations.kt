package com.developersbreach.developersbreach.utils

import android.content.Context
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.developersbreach.developersbreach.R
import com.google.android.material.card.MaterialCardView
import kotlin.math.hypot


/**
 * @param rootView pass any view to this method for effect. This will create a circular reveal
 * effect for view from left side and bottom of the screen.
 * @see MainActivity for implementation.
 */
fun startBottomCircularEffect(rootView: View) {

    // get the left bottom corner for the clipping circle
    val cx = rootView.left
    val cy = rootView.bottom
    // get the final radius for the clipping circle
    val finalRadius =
        hypot(cx.toDouble(), cy.toDouble()).toFloat()
    // create the animator for this view (the start radius is zero)
    val animator = ViewAnimationUtils.createCircularReveal(
        rootView, cx, cy, 0f,
        finalRadius
    )
    // Starts the animation.
    animator.start()
}

/**
 * @param view needs to be called with a view to apply animation.
 * @see ArticleListFragment.onResume
 * @see ArticleFavoritesFragment.onResume
 * @see SearchArticleFragment.onResume
 */
fun startLinearAnimation(view: View) {
    // Set the vertical location to begin with.
    view.translationY = 800f
    // Call this object with an view which needs animation.
    val viewPropertyAnimator = view.animate()
    // This causes to animate the present view with given properties by cancelling existing.
    viewPropertyAnimator.translationY(0f)
    // Set duration for animation to take place on view.
    viewPropertyAnimator.duration = 300L
    // Starts the animation.
    viewPropertyAnimator.start()
}

/**
 * @param context  gets access for resources.
 * @param cardView this is the view which we are animating to.
 * @return returns the view with type of animation assigned to perform with.
 * @see FavoriteListBindingAdapter.bindDeleteFavoriteClickListener
 * @see R.anim.fragment_fade_exit
 */
fun fadeOutCardAnimation(
    context: Context?,
    cardView: MaterialCardView
): Animation? {
    val animFadeOut =
        AnimationUtils.loadAnimation(context, R.anim.card_fade_exit)
    cardView.startAnimation(animFadeOut)
    return animFadeOut
}