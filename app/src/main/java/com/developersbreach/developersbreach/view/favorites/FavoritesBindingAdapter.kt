package com.developersbreach.developersbreach.view.favorites

import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.developersbreach.developersbreach.R
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.utils.capitalizeWord
import com.developersbreach.developersbreach.utils.fadeOutCardAnimation
import com.developersbreach.developersbreach.utils.isNetworkConnected
import com.developersbreach.developersbreach.utils.showSnackBar
import com.developersbreach.developersbreach.viewModel.FavoritesViewModel
import com.google.android.material.card.MaterialCardView


@BindingAdapter("bindFavoriteToDetailListener", "bindFavoritesFragment")
fun MaterialCardView.setFavoriteToDetailClickListener(
    article: Articles,
    fragment: FavoritesFragment
) {
    this.setOnClickListener { view ->

        if (isNetworkConnected(this.context)) {
            val direction: NavDirections =
                FavoritesFragmentDirections.FavoritesToDetailFragment(article, false)
            Navigation.findNavController(view).navigate(direction)
        } else {
            showSnackBar(
                this.context.getString(R.string.no_internet_connection),
                fragment.requireActivity()
            )
        }

    }
}


@BindingAdapter("bindFavoriteItemBanner")
fun ImageView.setFavoriteItemBanner(
    imageUrl: String
) {
    Glide.with(this.context).load(imageUrl).into(this)
}


@BindingAdapter("bindFavoriteItemTitle")
fun TextView.setFavoriteItemTitle(
    title: String
) {
    this.text = capitalizeWord(title)
}


@BindingAdapter(
    "bindFavoriteFragmentModel", "bindFavoriteViewModel",
    "bindItemCardView", "bindImageViewFragment"
)
fun ImageView.setFavoriteFragmentModel(
    article: Articles,
    viewModel: FavoritesViewModel,
    cardView: MaterialCardView,
    fragment: FavoritesFragment
) {
    let { imageView ->
        imageView.setImageResource(R.drawable.ic_favorite_remove)

        imageView.setOnClickListener {

            // Create a simple fade out animation to let user know the selected article has been
            // removed after clicking the imageView.
            val animation: Animation = fadeOutCardAnimation(imageView.context, cardView)!!
            deleteAfterAnimation(
                animation,
                viewModel,
                article,
                fragment,
                imageView
            )
        }
    }
}

fun deleteAfterAnimation(
    cardFadeOutAnimation: Animation,
    viewModel: FavoritesViewModel,
    article: Articles,
    fragment: FavoritesFragment,
    imageView: ImageView
) {
    // Set appropriate duration to animate.
    cardFadeOutAnimation.duration = 500L
    // Attach a listener and perform delete operation.
    cardFadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
        /**
         * At this point we reached to finish animation. So,
         * 1. Delete article
         * 2. Get window for current fragment being displayed.
         * 3. Show snackBar message while data is being deleted by user.
         *
         *
         * Notifies the end of the animation. This callback is not invoked
         * for animations with repeat count set to INFINITE.
         *
         * @param animation The animation which reached its end.
         */
        override fun onAnimationEnd(animation: Animation) {
            viewModel.deleteArticle(article)
            val message =
                imageView.context.getString(R.string.snackbar_removed_favorite_message)
            showSnackBar(message, fragment.requireActivity())
        }

        /**
         *
         * Notifies the start of the animation.
         *
         * @param animation The started animation.
         */
        override fun onAnimationStart(animation: Animation) {}

        /**
         *
         * Notifies the repetition of the animation.
         *
         * @param animation The animation which was repeated.
         */
        override fun onAnimationRepeat(animation: Animation) {}
    })
}