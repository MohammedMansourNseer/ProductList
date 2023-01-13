package com.dev.amazonProduct.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.amazonProduct.R
import com.dev.amazonProduct.adapters.ListAdapter
import com.dev.amazonProduct.ui.MainActivity
import com.dev.amazonProduct.ui.AmazonProductViewModel
import com.dev.amazonProduct.utils.Resource
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment(R.layout.fragment_list) {
    lateinit var viewModel: AmazonProductViewModel
    lateinit var listAdapter: ListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= (activity as MainActivity).viewModel

        setupRecyclerView()

        listAdapter.setOnItemClickListener {
            val bundle= Bundle().apply{
                putSerializable("details", it)
        }
            findNavController().navigate(
                R.id.action_listFragment_to_detailsFragment,
                bundle
            )
        }
        viewModel.amazonProduct.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let { listResponse ->
                        listAdapter.differ.submitList(listResponse.results.toList())
                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let { message->
                        Log.e("productError", "An error occured: $message" )
                        Toast.makeText(activity, "An error occurred: $message", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }
        })
    }

    private fun setupRecyclerView(){
        listAdapter= ListAdapter()
        rvProductList.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
    private fun hideProgressBar(){
        paginationProgressBar.visibility= View.INVISIBLE
        isLoading= false
    }
    private fun showProgressBar(){
        paginationProgressBar.visibility= View.VISIBLE
        isLoading= true
    }

    var isLoading= false
}