package com.ripple.media.picker.config.impl

import android.graphics.Color
import com.ripple.media.picker.R
import com.ripple.media.picker.config.MediaThemeConfig

/**
 * Author: fanyafeng
 * Data: 2020/4/21 18:52
 * Email: fanyafeng@live.cn
 * Description:
 */
class ThemeConfig(builder: Builder) : MediaThemeConfig {

    private var isLight: Boolean = false

    private var statusBarColor = Color.parseColor("#3F51B5")

    private var toolbarColor = Color.parseColor("#303F9F")

    private var toolbarCenterTitleColor = Color.WHITE

    private var toolbarRightTitleColor = Color.WHITE

    private var navigationIcon = R.drawable.go_back_left_arrow

    init {
        isLight = builder.isLight
        statusBarColor = builder.statusBarColor
        toolbarColor = builder.toolbarColor
        toolbarCenterTitleColor = builder.toolbarCenterTitleColor
        toolbarRightTitleColor = builder.toolbarRightTitleColor
        navigationIcon = builder.navigationIcon
    }

    override fun isLight(): Boolean {
        return isLight
    }

    override fun setStatusBarColor(): Int {
        return statusBarColor
    }

    override fun setToolbarColor(): Int {
        return toolbarColor
    }

    override fun setToolbarCenterTitleColor(): Int {
        return toolbarCenterTitleColor
    }

    override fun setToolbarRightTitleColor(): Int {
        return toolbarRightTitleColor
    }

    override fun setNavigationIcon(): Int {
        return navigationIcon
    }

    class Builder {
        var isLight: Boolean = false
            private set(value) {
                field = value
            }

        var statusBarColor = Color.parseColor("#3F51B5")
            private set(value) {
                field = value
            }

        var toolbarColor = Color.parseColor("#303F9F")
            private set(value) {
                field = value
            }

        var toolbarCenterTitleColor = Color.WHITE
            private set(value) {
                field = value
            }

        var toolbarRightTitleColor = Color.WHITE
            private set(value) {
                field = value
            }

        var navigationIcon = R.drawable.go_back_left_arrow
            private set(value) {
                field = value
            }

        fun setLight(isLight: Boolean): Builder {
            this.isLight = isLight
            return this
        }

        fun setStatusBarColor(statusBarColor: Int): Builder {
            this.statusBarColor = statusBarColor
            return this
        }

        fun setToolbarColor(toolbarColor: Int): Builder {
            this.toolbarColor = toolbarColor
            return this
        }

        fun setToolbarCenterTitleColor(toolbarCenterTitleColor: Int): Builder {
            this.toolbarCenterTitleColor = toolbarCenterTitleColor
            return this
        }

        fun setToolbarRightTitleColor(toolbarRightTitleColor: Int): Builder {
            this.toolbarRightTitleColor = toolbarRightTitleColor
            return this
        }

        fun setNavigationIcon(navigationIcon: Int): Builder {
            this.navigationIcon = navigationIcon
            return this
        }

        fun build(): ThemeConfig {
            return ThemeConfig(this)
        }

        fun create(): ThemeConfig {
            return Builder().setLight(false)
                .setStatusBarColor(Color.parseColor("#3F51B5"))
                .setToolbarColor(Color.parseColor("#303F9F"))
                .setToolbarCenterTitleColor(Color.WHITE)
                .setToolbarRightTitleColor(Color.WHITE)
                .setNavigationIcon(R.drawable.go_back_left_arrow)
                .build()
        }
    }

}