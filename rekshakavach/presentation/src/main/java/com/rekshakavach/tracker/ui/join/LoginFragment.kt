package com.rekshakavach.tracker.ui.join

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.rekshakavach.tracker.R
import com.rekshakavach.tracker.common.showSnackBar
import com.rekshakavach.tracker.di.vm.ViewModelProviderFactory
import com.rekshakavach.tracker.domain.entity.DataEntity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

const val KEY_ARGUMENT_CODE = "country_code"
const val KEY_ARGUMENT_PHONE = "phoneNumber"
class LoginFragment : DaggerFragment(){

    private lateinit var joinViewModel: JoinViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signinBtn.setOnClickListener {
            var phone = phoneNumberEntryField.text.toString()
            var password = passwordEt.text.toString()
            if (phone.isNullOrEmpty() or (phone.length < 9)) {
                loginRoot.showSnackBar(getString(R.string.errorPhone),
                    R.color.snack_red
                )
            } else if (password.isNullOrEmpty()) {
            loginRoot.showSnackBar(getString(R.string.error_password),
                R.color.snack_red
            )
        } else {
                signinBtn.isEnabled = false
                loginProgress.visibility = View.VISIBLE
                signinBtn.text = ""
//                joinViewModel.generateOtp(country, phone)
            }
        }

        navigateToRegister.setOnClickListener{
            var controller =NavHostFragment.findNavController(this)
            controller.navigate(R.id.action_register)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        joinViewModel = ViewModelProvider(this, viewModelFactory).get(JoinViewModel::class.java)
        joinViewModel.otpResponseLive.observe(viewLifecycleOwner, Observer {
            when(it){
                is DataEntity.SUCCESS ->{

                }
                is DataEntity.ERROR ->{
                    var error = it.error.message?:getString(R.string.sometingWentWrong)
                    loginRoot.showSnackBar(error,R.color.snack_red)
                }
            }
            dismissProgress()
        })

        splashRoot.postDelayed({
            if(joinViewModel.isUserLoggedIn()) {
             //todo
            }else{
                splashRoot.animate()
                    .alpha(0f)
                    .setDuration(1000)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            splashRoot.visibility = View.GONE
                        }
                    })
            }
        },1000
        )
    }

    private fun dismissProgress(){
        signinBtn.isEnabled = true
        loginProgress.visibility =View.GONE
        signinBtn.text = getString(R.string.signin)
    }

}