package com.ducnv.moviehunt.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.ducnv.moviehunt.databinding.FragmentLoginBinding
import com.ducnv.moviehunt.ui.base.BaseFragment
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.data.local.pref.AppPrefs
import com.ducnv.moviehunt.data.model.Token
import com.ducnv.moviehunt.ui.home.HomeActivity
import com.ducnv.moviehunt.utils.hideKeyboardwithoutPopulate
import com.varunjohn1990.iosdialogs4android.IOSDialog
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment() : BaseFragment<FragmentLoginBinding, LoginViewModel>(), LoginListener {


    override val viewModel: LoginViewModel by viewModel()

    override val getLayoutId: Int = R.layout.fragment_login

    override fun setupView() {
        binding.listener = this
        clickOutSideFocus()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun clickOutSideFocus() {
        binding.container.setOnTouchListener { v, event ->
            if (binding.editTextEmail.isFocusable) editText_email.clearFocus()
            if (binding.editTextPassword.isFocusable) editText_password.clearFocus()
            v.hideKeyboardwithoutPopulate()
            false
        }
        binding.cardView.setOnTouchListener { v, event ->
            if (binding.editTextEmail.isFocusable) editText_email.clearFocus()
            if (binding.editTextPassword.isFocusable) editText_password.clearFocus()
            v.hideKeyboardwithoutPopulate()
            false
        }
    }

    override fun onLoginClicked() {

        if (confirmInput()) {
            viewModel.apply {
                val username = binding.editTextEmail.text.toString().trim()
                val password = binding.editTextPassword.text.toString().trim()
                Log.e("token1",getTokenId().toString())
                loadData(username, password)
            }

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
             fetchToken()

        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.apply {
            token.observe(viewLifecycleOwner, Observer {
                putTokenId(token.value?.request_token.toString())
                Log.e("token",it.request_token.toString())
            })
            login.observe(viewLifecycleOwner, Observer {
                if (it.success!!) {
                    //fetch Session and GuestSession
                    getTokenId()?.let { tokenId ->
                        fetchSession(tokenId)

                    }

                }

            })

            session.observe(viewLifecycleOwner, Observer {
                //goToHome
                putSessionId(it.session_id)
                val intent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            })
        }
    }

    private fun validateEmail(): Boolean {
        val email = binding.editTextEmail.text.toString().trim()
        if (email.isNullOrBlank()) {
            binding.editTextEmail.error = "Field can't be empty"
            return false
        }
        binding.editTextEmail.error = null
        return true
    }

    private fun validatePassword(): Boolean {
        val password = binding.editTextPassword.text.toString().trim()
        if (password.isNullOrBlank()) {
            binding.editTextPassword.error = "Field can't be empty"
            return false
        }
        binding.editTextPassword.error = null
        return true
    }

    private fun confirmInput(): Boolean {
        if (!validateEmail() || !validatePassword()) {
            return false
        }
        return true
    }

    override fun showDialog(message: String?): IOSDialog.Builder? {
        return super.showDialog(message)
    }
}