package com.example.oblopgave

import Comment.CommentAdapter
import Comment.CommentViewModel
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oblopgave.databinding.FragmentSecondBinding
import com.example.oblopgave.databinding.FragmentThirdBinding


/**
 * A simple [Fragment] subclass.
 * Use the [ThirdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null
    private val firebaseViewModel: FirebaseViewModel by activityViewModels()
    private val commentViewModel: CommentViewModel by activityViewModels()

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_signout -> {
                firebaseViewModel.SignOut()
                findNavController().navigate(R.id.action_thirdFragment_to_FirstFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commentViewModel.commentLiveData.observe(viewLifecycleOwner){ comment ->
            binding.progressbar.visibility  = View.GONE
            binding.recyclerView.visibility = if (comment == null) View.GONE else View.VISIBLE
            if (comment != null) {
                val adapter = CommentAdapter(comment) { position ->
                }
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recyclerView.adapter = adapter
            }
        }
        commentViewModel.errorCommentLiveData.observe(viewLifecycleOwner) { errorcomment ->
            binding.Comment.text = errorcomment
        }



        binding.swiperefresh.setOnClickListener{
            binding.swiperefresh.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}