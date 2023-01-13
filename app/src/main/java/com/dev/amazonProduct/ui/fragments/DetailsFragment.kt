package com.dev.amazonProduct.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.dev.amazonProduct.R
import com.dev.amazonProduct.adapters.ImageSlideAdapter
import com.dev.amazonProduct.ui.AmazonProductViewModel
import com.dev.amazonProduct.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_details.*
import me.relex.circleindicator.CircleIndicator

class DetailsFragment : Fragment(R.layout.fragment_details){
    lateinit var viewModel: AmazonProductViewModel
    private val args:DetailsFragmentArgs by navArgs()

    lateinit var viewPagerAdapter: ImageSlideAdapter
    lateinit var indicator: CircleIndicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= (activity as MainActivity).viewModel

        val productDetails = args.details

        tvProductNameValue.text = productDetails.name
        tvPriceValue.text       = productDetails.price
        tvUidValue.text         = productDetails.uid
        tvCreatedAtValue.text   = productDetails.created_at

        productDetails.image_urls.let{
            viewPagerAdapter = ImageSlideAdapter(requireContext(), it)
            viewpager.adapter = viewPagerAdapter
            indicator = requireView().findViewById(R.id.indicator) as CircleIndicator
            indicator.setViewPager(viewpager)
        }

        clAddToCart.setOnClickListener {
            Toast.makeText(activity, "${productDetails.name} Added to cart successfully", Toast.LENGTH_LONG).show()
        }
    }
}