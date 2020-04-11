package com.rekshakavach.tracker.base

import android.view.View
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.layout_action.*


open class BaseActivity : DaggerAppCompatActivity() {

    fun initAction(backBtn: Boolean, titleResId: Int, cartBtn: Boolean) {
        if (titleResId != 0) {
            action_title?.setText(titleResId)
        }

        action_backBtn?.setOnClickListener {
            onBackPressed();
        }
        action_backBtn?.visibility = if (backBtn) View.VISIBLE else View.GONE
    }

}
