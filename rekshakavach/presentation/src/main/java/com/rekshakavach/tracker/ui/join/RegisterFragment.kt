package com.rekshakavach.tracker.ui.join

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
import kotlinx.android.synthetic.main.fragment_register.*
import javax.inject.Inject

class RegisterFragment : DaggerFragment(){

    lateinit var joinViewModel: JoinViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpBtn.setOnClickListener{
            registerProgress.visibility =View.VISIBLE
            signUpBtn.text = ""
        }
        navigateToLogin.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_login)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        joinViewModel = ViewModelProvider(this, viewModelFactory).get(JoinViewModel::class.java)
        joinViewModel.otpValidateLive.observe(viewLifecycleOwner, Observer {
            when(it){
                is DataEntity.SUCCESS ->{
                    if(it.data?.data?.token.isNullOrEmpty()){
                        var error = it.data?.message?:getString(R.string.sometingWentWrong)
                        registerRoot.showSnackBar(error,R.color.snack_red)
                    }else{
                    }
                }
                is DataEntity.ERROR ->{
                    var error = it.error.message?:getString(R.string.sometingWentWrong)
                    registerRoot.showSnackBar(error,R.color.snack_red)
                }
            }
        })
    }

    private fun dismissProgress(){
        registerProgress.visibility =View.GONE
        signUpBtn.text = getString(R.string.signuup)
    }
}