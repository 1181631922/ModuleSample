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
import com.ripple.media.picker.config.MediaThemeConfig

/**
 * base
 */

open class RippleBaseActivity : AppCompatActivity() {

    protected var toolbar: Toolbar? = null
    protected var toolbarCenterTitle: TextView? = null
    protected var toolbarRightTitle: TextView? = null

    protected var themeConfig: MediaThemeConfig? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            themeConfig = if (intent.getSerializableExtra(MediaThemeConfig.THEME_CONFIG) != null) {
                intent.getSerializableExtra(MediaThemeConfig.THEME_CONFIG) as MediaThemeConfig
            } else {
                RippleMediaPick.getInstance().themeConfig
            }
            themeConfig?.let {
                window.statusBarColor = it.getStatusBarColor()
                if (it.isLight()) {
                    window.decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        toolbar = findViewById(R.id.toolbar)
        themeConfig = if (intent.getSerializableExtra(MediaThemeConfig.THEME_CONFIG) != null) {
            intent.getSerializableExtra(MediaThemeConfig.THEME_CONFIG) as MediaThemeConfig
        } else {
            RippleMediaPick.getInstance().themeConfig
        }

        toolbar?.let {
            setSupportActionBar(toolbar)
            it.title = ""
            themeConfig?.let { theme ->
                it.setNavigationIcon(theme.getNavigationIcon())
                it.setBackgroundColor(theme.getToolbarColor())
            }
            it.setNavigationOnClickListener {
                finish()
            }
        }

        toolbarCenterTitle = findViewById(R.id.toolbarCenterTitle)
        toolbarCenterTitle?.let {
            themeConfig?.let { theme ->
                it.setTextColor(theme.getToolbarCenterTitleColor())
            }
        }

        toolbarRightTitle = findViewById(R.id.toolbarRightTitle)
        toolbarRightTitle?.let {
            themeConfig?.let { theme ->
                it.setTextColor(theme.getToolbarRightTitleColor())
            }
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
