package com.janfranco.targetfragmentexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.janfranco.targetfragmentexample.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("reqKey") { requestKey, bundle ->
            binding.tvMainTitle.text = bundle.getString("bundleKey")
        }

        binding.btnMainNext.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToSecondFragment()
            )
        }
    }
}
