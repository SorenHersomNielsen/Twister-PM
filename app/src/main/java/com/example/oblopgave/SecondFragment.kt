package com.example.oblopgave

import Message.Message
import Message.MessageViewModel
import Message.TwisterMessageAdapter
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oblopgave.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private var firebaseViewModel: FirebaseViewModel = FirebaseViewModel()
    private val messageViewModel: MessageViewModel by activityViewModels()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         setHasOptionsMenu(true)
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            inflater.inflate(R.menu.menu_main, menu)
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.action_signout -> {
                    firebaseViewModel.SignOut()
                    findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messageViewModel.MessageLiveData.observe(viewLifecycleOwner) { message ->
            //Log.d("APPLE", "observer $books")
            binding.progressbar.visibility = View.GONE
            binding.recyclerView.visibility = if (message == null) View.GONE else View.VISIBLE
            if (message != null) {
                val adapter = TwisterMessageAdapter(message) { position ->

                }
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recyclerView.adapter = adapter
            }
        }

        messageViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            binding.textviewMessage.text = errorMessage
        }

        messageViewModel.reload()

        binding.swiperefresh.setOnRefreshListener {
            messageViewModel.reload()
            binding.swiperefresh.isRefreshing = false
        }

        binding.CreateMessage.setOnClickListener{
            val message: String = binding.WriteYourMessage.text.toString()
            val email = firebaseViewModel.Email

            val postmessage = Message(message,email)

            messageViewModel.add(postmessage)

            binding.swiperefresh.setOnRefreshListener {
                messageViewModel.reload()
                binding.swiperefresh.isRefreshing = false
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}