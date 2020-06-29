package com.ripple.dialog.widget

import android.content.Intent
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentManager
import com.ripple.dialog.callback.RippleDialogInterface


/**
 * Author: fanyafeng
 * Data: 2020/6/23 16:05
 * Email: fanyafeng@live.cn
 * Description: 抽象FragmentDialog的统一行为
 */

interface IRippleDialogFragment : IRippleBaseDialog {

    companion object {
        const val RIPPLE_DIALOG_FRAGMENT_TAG = "ripple_dialog_fragment_tag"
    }

    /**
     * activity回调结果
     *
     * android原有的dialog并不支持
     * 只有在DialogFragment才有，因为DialogFragment是继承的Fragment
     */
    fun setOnActivityResult(onActivityResult: RippleDialogInterface.OnActivityResult)

    /**
     * Return the identifier this fragment is known by.  This is either
     * the android:id value supplied in a layout or the container view ID
     * supplied when adding the fragment.
     */
    fun getId(): Int

    /**
     * Display the dialog, adding the fragment to the given FragmentManager.  This
     * is a convenience for explicitly creating a transaction, adding the
     * fragment to it with the given tag, and {@link FragmentTransaction#commit() committing} it.
     * This does <em>not</em> add the transaction to the fragment back stack.  When the fragment
     * is dismissed, a new transaction will be executed to remove it from
     * the activity.
     * @param manager The FragmentManager this fragment will be added to.
     * @param tag The tag for this fragment, as per
     * {@link FragmentTransaction#add(Fragment, String) FragmentTransaction.add}.
     */
    fun show(manager: FragmentManager, tag: String)

}