package com.example.newsapi.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapi.R
import com.example.newsapi.databinding.FragmentDetailBinding
import com.google.android.material.transition.MaterialSharedAxis
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {

    private val args by navArgs<DetailFragmentArgs>()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var webView: WebView
    private lateinit var loadingView: View
    private lateinit var topAppBar: androidx.appcompat.widget.Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        setupSharedAxisTransitions()
        (activity as AppCompatActivity).supportActionBar?.hide()

//        webView = binding.webViewDetail
//        loadingView = binding.spinkitDetail
        topAppBar = binding.topAppBar

        return binding.root
    }

    private fun setupSharedAxisTransitions() {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        setupWebView()
        setupAppBar()
        setupInit()
    }

    private fun setupInit() {
        binding.apply {
            judulNewsDetail.text = args.newsTitle
            authorNewsDetail.text = args.newsAuthor
            descriptionNewsDetail.text = args.newsDescription
            contentNewsDetail.text = args.newsContent

            Picasso.get()
                .load(args.newsImage)
                .into(ivNewsDetail)
        }
    }

//    private fun setupWebView() {
//        webView.settings.javaScriptEnabled
//        webView.webViewClient = object : WebViewClient() {
//            override fun onPageFinished(view: WebView?, url: String?) {
//                super.onPageFinished(view, url)
//                loadingView.visibility = View.GONE
//                webView.visibility = View.VISIBLE
//            }
//        }
//        webView.loadUrl(args.newsUrl.toString())
//    }

    private fun setupAppBar() {
        topAppBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_listFragment)
        }

        topAppBar.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.share_button -> {
                    shareText()
                    true
                }

                else -> false
            }
        }
    }

    private fun shareText() {
        val shareText = "Check out this news!! \n  ${args.newsUrl}"
        val sharingIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }

        val chooserIntent = Intent.createChooser(sharingIntent, "Share via")
        if (sharingIntent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(chooserIntent)
        } else {
            Toast.makeText(requireContext(), "No apps can handle this action", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
