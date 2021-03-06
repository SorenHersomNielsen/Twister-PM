package com.example.oblopgave

import Comment.CommentViewModel
import Message.Message
import Message.MessageViewModel
import Message.TwisterMessageAdapter
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oblopgave.databinding.FragmentSecondBinding



/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val firebaseViewModel: FirebaseViewModel by activityViewModels()
    private val messageViewModel: MessageViewModel by activityViewModels()
    private val messageid: CommentViewModel by activityViewModels()
    private val args: SecondFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var messageId: Int = 0


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

        binding.CreateMessage.setOnClickListener {
            val message: String = binding.WriteYourMessage.text.toString()
            val email: String = args.email
            Log.d("APPLE", email + message)

            val postmessage = Message(message, email)
            Log.d("Apple", "post $postmessage")

            messageViewModel.add(postmessage)

        }

        binding.deletemessage.setOnClickListener {
            val id: Int = binding.ID.text.toString().toInt()
            val email: String = args.email

            messageViewModel.delete(id)
        }

        binding.SeeComment.setOnClickListener{
            Log.d("apple", "moving to third fragment")
            messageId = binding.ID.text.toString().toInt()
            messageid.messageId = messageId
            Log.d("apple",messageid.messageId.toString())

            val action = SecondFragmentDirections.actionSecondFragmentToThirdFragment(args.email, messageId)

            findNavController().navigate(action)
            //findNavController().navigate(R.id.action_SecondFragment_to_thirdFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




