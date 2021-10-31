package com.example.oblopgave

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.oblopgave.databinding.FragmentThridBinding



/**
 * A simple [Fragment] subclass.
 * Use the [ThridFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThridFragment : Fragment() {

    private var _binding: FragmentThridBinding? = null
    private var firebaseViewModel: FirebaseViewModel = FirebaseViewModel()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
         _binding = FragmentThridBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_signout -> {
                firebaseViewModel.SignOut()
                findNavController().navigate(R.id.action_thridFragment_to_FirstFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.GetBack.setOnClickListener{
            findNavController().navigate(R.id.action_thridFragment_to_SecondFragment2)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}