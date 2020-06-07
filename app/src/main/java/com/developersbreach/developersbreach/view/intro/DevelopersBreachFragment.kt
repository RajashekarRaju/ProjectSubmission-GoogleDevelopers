package com.developersbreach.developersbreach.view.intro

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.developersbreach.developersbreach.databinding.FragmentDevelopersBreachBinding


/**
 * A simple [Fragment] subclass.
 */
class DevelopersBreachFragment : Fragment() {

    private lateinit var binding: FragmentDevelopersBreachBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDevelopersBreachBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        object : CountDownTimer(750, 1000) {
            /**
             * Callback fired on regular interval.
             *
             * @param millisUntilFinished The amount of time until finished.
             */
            override fun onTick(millisUntilFinished: Long) {}

            /**
             * Callback fired when the time is up.
             */
            override fun onFinish() {
                val directions: NavDirections =
                    DevelopersBreachFragmentDirections.IntroToArticlesFragment()
                Navigation.findNavController(binding.root)
                    .navigate(directions)
            }
        }.start()
    }
}