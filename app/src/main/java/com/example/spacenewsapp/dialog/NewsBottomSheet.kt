package com.example.spacenewsapp.dialog

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.entities.response.News
import com.example.spacenewsapp.R
import com.example.spacenewsapp.util.convertDate
import com.example.spacenewsapp.util.openWebLink
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_news_more.*

class NewsBottomSheet(private val news: News) : BottomSheetDialogFragment() {

    companion object {
        const val TAG_NEWS_DIALOG = "NewsBottomSheet"
        fun newInstance(news: News): NewsBottomSheet = NewsBottomSheet(news)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(requireContext()).inflate(R.layout.dialog_news_more, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsDialogTitle.text = news.title
        newsDialogImage.load(news.imageUrl)
        newsDialogDesc.text = news.summary
        publishedDate.text = "Published in ${news.publishedAt.convertDate()} by ${news.newsSite}"

        openUrl.setOnClickListener { news.url.openWebLink(requireContext()) }
        cancelDialog.setOnClickListener { dismiss() }
    }

    override fun onResume() {
        super.onResume()
        val bottomSheet = (view?.parent as View)
        bottomSheet.backgroundTintMode = PorterDuff.Mode.CLEAR
        bottomSheet.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
    }
}