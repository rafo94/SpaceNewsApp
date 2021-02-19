package com.example.spacenewsapp.dialog

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.spacenewsapp.BuildConfig
import com.example.spacenewsapp.R
import com.example.spacenewsapp.util.Constants.Companion.PROFILE_URL
import com.example.spacenewsapp.util.Constants.Companion.PROJECT_URL
import com.example.spacenewsapp.util.facebookIntent
import com.example.spacenewsapp.util.linkedinIntent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_app_info.*

class AppInfoBottomSheet : BottomSheetDialogFragment() {

    companion object {
        const val TAG_APP_INFO_DIALOG = "AppInfoBottomSheet"
        fun newInstance(): AppInfoBottomSheet = AppInfoBottomSheet()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(requireContext()).inflate(R.layout.dialog_app_info, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileImage.load(PROFILE_URL)
        version.text = "Version ${BuildConfig.VERSION_NAME}"
        clickHere.text = PROJECT_URL
        Linkify.addLinks(clickHere, Linkify.WEB_URLS)

        facebook.setOnClickListener { startActivity(facebookIntent(requireActivity().packageManager)) }
        linkedin.setOnClickListener { startActivity(linkedinIntent(requireActivity().packageManager)) }
    }

    override fun onResume() {
        super.onResume()
        val bottomSheet = (view?.parent as View)
        bottomSheet.backgroundTintMode = PorterDuff.Mode.CLEAR
        bottomSheet.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
    }
}