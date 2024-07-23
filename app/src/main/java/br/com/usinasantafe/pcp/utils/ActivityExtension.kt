package br.com.usinasantafe.pcp.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.databinding.LayoutBotoesBinding
import br.com.usinasantafe.pcp.databinding.LayoutBotoesSAtualBinding

fun showToast(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Fragment.showGenericAlertDialog(message: String, context: Context, callback: () -> Unit) {
    AlertDialog.Builder(context).apply {
        setMessage(message)
        setPositiveButton(getString(R.string.texto_padrao_ok)) { _, _ ->
            callback.invoke()
        }
    }.show()
}

fun Fragment.showGenericAlertDialog(message: String, context: Context): AlertDialog {
    val alert = AlertDialog.Builder(context).apply {
        setMessage(message)
        setPositiveButton(getString(R.string.texto_padrao_ok)) { dialog, _ ->
            dialog.dismiss()
        }
    }.show()
    return alert
}

fun showGenericAlertDialogCheck(message: String, context: Context, callback: () -> Unit) {
    AlertDialog.Builder(context)
        .setMessage(message)
        .setPositiveButton("SIM") { _, _ ->
            callback.invoke()
        }
        .setNegativeButton("NÃO", null)
        .create()
        .show()
}

fun setListenerButtonsCPF(editText: EditText) {
    if (editText.text.length < 15) {
        if ((editText.text.length == 3) || (editText.text.length == 7)) {
            editText.setText("${editText.text}.")
        }
        if ((editText.text.length == 11)) {
            editText.setText("${editText.text}-")
        }
    } else {
        editText.setText(editText.text.substring(0, editText.text.length - 1))
    }
}

fun setListenerButtonsGeneric(layoutBotoesBinding: LayoutBotoesBinding, editText: EditText, operation: ((editText: EditText) -> Unit)? = null) {
    with(layoutBotoesBinding) {
        buttonNum0.setOnClickListener {
            editText.setText("${editText.text}" + buttonNum0.text)
            operation?.invoke(editText)
        }
        buttonNum1.setOnClickListener {
            editText.setText("${editText.text}" + buttonNum1.text)
            operation?.invoke(editText)
        }
        buttonNum2.setOnClickListener {
            editText.setText("${editText.text}" + buttonNum2.text)
            operation?.invoke(editText)
        }
        buttonNum3.setOnClickListener {
            editText.setText("${editText.text}" + buttonNum3.text)
            operation?.invoke(editText)
        }
        buttonNum4.setOnClickListener {
            editText.setText("${editText.text}" + buttonNum4.text)
            operation?.invoke(editText)
        }
        buttonNum5.setOnClickListener {
            editText.setText("${editText.text}" + buttonNum5.text)
            operation?.invoke(editText)
        }
        buttonNum6.setOnClickListener {
            editText.setText("${editText.text}" + buttonNum6.text)
            operation?.invoke(editText)
        }
        buttonNum7.setOnClickListener {
            editText.setText("${editText.text}" + buttonNum7.text)
            operation?.invoke(editText)
        }
        buttonNum8.setOnClickListener {
            editText.setText("${editText.text}" + buttonNum8.text)
            operation?.invoke(editText)
        }
        buttonNum9.setOnClickListener {
            editText.setText("${editText.text}" + buttonNum9.text)
            operation?.invoke(editText)
        }
        buttonCancPadrao.setOnClickListener {
            if (editText.text.isNotEmpty()) {
                editText.setText(editText.text.substring(0, editText.text.length - 1))
            }
        }
    }
}

fun setListenerButtonsGenericSUpdate(
    layoutBotoesSAtualBinding: LayoutBotoesSAtualBinding,
    editText: EditText
) {
    with(layoutBotoesSAtualBinding) {
        buttonNum0.setOnClickListener {
            editText.setText("${editText.text}" + buttonNum0.text)
        }
        buttonNum1.setOnClickListener {
            editText.setText("${editText.text}" + buttonNum1.text)
        }
        buttonNum2.setOnClickListener {
            editText.setText("${editText.text}" + buttonNum2.text)
        }
        buttonNum3.setOnClickListener {
            editText.setText("${editText.text}" + buttonNum3.text)
        }
        buttonNum4.setOnClickListener {
            editText.setText("${editText.text}" + buttonNum4.text)
        }
        buttonNum5.setOnClickListener {
            editText.setText("${editText.text}" + buttonNum5.text)
        }
        buttonNum6.setOnClickListener {
            editText.setText("${editText.text}" + buttonNum6.text)
        }
        buttonNum7.setOnClickListener {
            editText.setText("${editText.text}" + buttonNum7.text)
        }
        buttonNum8.setOnClickListener {
            editText.setText("${editText.text}" + buttonNum8.text)
        }
        buttonNum9.setOnClickListener {
            editText.setText("${editText.text}" + buttonNum9.text)
        }
        buttonCancPadrao.setOnClickListener {
            if (editText.text.isNotEmpty()) {
                editText.setText(editText.text.substring(0, editText.text.length - 1))
            }
        }
    }
}

fun Activity.hideKeyboard() {
    val imm: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view: View? = currentFocus
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun AppCompatActivity.replaceFragment(@IdRes id: Int, fragment: Fragment) {
    if (supportFragmentManager.findFragmentById(id) == null) {
        supportFragmentManager.beginTransaction().apply {
            add(id, fragment)
            commit()
        }
    } else {
        supportFragmentManager.beginTransaction().apply {
            replace(id, fragment)
            addToBackStack(null)
            commit()
        }
    }
    hideKeyboard()
}

fun Fragment.onBackPressed(callback: () -> Unit) {
    val funReturn: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            callback.invoke()
        }
    }
    requireActivity().onBackPressedDispatcher.addCallback(
        this, funReturn
    )
}
