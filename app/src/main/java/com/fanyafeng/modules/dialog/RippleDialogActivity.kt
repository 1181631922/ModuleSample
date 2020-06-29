package com.fanyafeng.modules.dialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.fanyafeng.modules.R
import com.ripple.dialog.callback.RippleDialogInterface
import com.ripple.dialog.widget.impl.RippleDialog
import com.ripple.dialog.widget.impl.RippleDialogFragment
import com.ripple.dialog.widget.impl.RippleToast
import kotlinx.android.synthetic.main.activity_ripple_dialog.*

class RippleDialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ripple_dialog)

        title = "dialog封装"

        initView()
        initData()
    }

    private fun initView() {
        btn1.setOnClickListener {
            val view =
                LayoutInflater.from(this).inflate(R.layout.ripple_dialog_layout_center_dialog, null)
            val dialog = RippleDialog(this, view)
            dialog.setCancel(false)
//            dialog.interceptBackPressed(true)
            dialog.setOnDismissListener(object : RippleDialogInterface.OnDismissListener {
                override fun onDismiss() {
                    println("弹窗消失")
                }

            })
            dialog.setOnBackPressListener(object : RippleDialogInterface.OnBackPressListener {
                override fun onBackPress() {
                    dialog.dismiss()
                }
            })
            dialog.showCenter()
        }

        btn2.setOnClickListener {
            val view =
                LayoutInflater.from(this).inflate(R.layout.ripple_dialog_layout_center_dialog, null)
            val dialog = RippleDialog(this, view)
            dialog.showBottom()
        }

        btn3.setOnClickListener {
            RippleToast.show(this, "toast提示")
        }

        btn4.setOnClickListener {
            val view =
                LayoutInflater.from(this).inflate(R.layout.ripple_dialog_layout_center_dialog, null)
            val dialog = RippleDialogFragment(this, view)
            dialog.show()
        }

        btn5.setOnClickListener {
            val view =
                LayoutInflater.from(this).inflate(R.layout.ripple_dialog_layout_center_dialog, null)
            val dialog = RippleDialogFragment(this, view)
            dialog.setOnDismissListener(object : RippleDialogInterface.OnDismissListener {
                override fun onDismiss() {
                    println("dialog消失")
                }
            })
            dialog.setOnActivityResult()
            dialog.interceptBackPressed(true)
            dialog.showBottom()
        }
    }

    private fun initData() {

    }

    private fun showFragmentDialog() {
        val dialogFragment = DialogFragment()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}