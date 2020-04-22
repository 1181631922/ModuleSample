package com.ripple.media.picker.base

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.ripple.media.picker.R
import com.ripple.media.picker.RippleMediaPick

/**
 * base
 */

open class RippleBaseActivity : AppCompatActivity() {

    protected var toolbar: Toolbar? = null
    protected var toolbarCenterTitle: TextView? = null
    protected var toolbarRightTitle: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            val themeConfig = RippleMediaPick.getInstance().themeConfig
            window.statusBarColor = themeConfig.getStatusBarColor()
            if (themeConfig.isLight()) {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            }
        }
    }

    override fun onResume() {
        super.onResume()
        toolbar = findViewById(R.id.toolbar)
        val themeConfig = RippleMediaPick.getInstance().themeConfig

        toolbar?.let {
            setSupportActionBar(toolbar)
            toolbar!!.title = ""
            toolbar!!.setNavigationIcon(themeConfig.getNavigationIcon())
            toolbar!!.setBackgroundColor(themeConfig.getToolbarColor())
            toolbar!!.setNavigationOnClickListener {
                finish()
            }
        }

        toolbarCenterTitle = findViewById(R.id.toolbarCenterTitle)
        toolbarCenterTitle?.let {
            toolbarCenterTitle!!.setTextColor(themeConfig.getToolbarCenterTitleColor())
        }

        toolbarRightTitle = findViewById(R.id.toolbarRightTitle)
        toolbarRightTitle?.let {
            toolbarRightTitle!!.setTextColor(themeConfig.getToolbarRightTitleColor())
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        RippleMediaPick.getInstance().onRestoreInstanceState(savedInstanceState)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        RippleMediaPick.getInstance().onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }
}
