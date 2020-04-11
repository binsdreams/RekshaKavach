package com.rekshakavach.tracker.ui.join

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rekshakavach.tracker.R
import com.rekshakavach.tracker.common.showSnackBar
import com.rekshakavach.tracker.di.vm.ViewModelProviderFactory
import com.rekshakavach.tracker.domain.entity.DataEntity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_user_details.*
import javax.inject.Inject


class UserDetailsFragment : DaggerFragment(){

    private lateinit var joinViewModel: JoinViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveUserDetailBtn.setOnClickListener{
            var fullname =   userName.text.toString()
            var emailAddress =   userEmail.text.toString()
            if(fullname.isEmpty()){
                userAdditionRoot.showSnackBar("error user name",R.color.snack_red)
            }else if(isValidEmail(emailAddress).not()){
                userAdditionRoot.showSnackBar("error email",R.color.snack_red)
            }else{
                progress(true)
                joinViewModel.saveUser(fullname,emailAddress)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        joinViewModel = ViewModelProvider(this, viewModelFactory).get(JoinViewModel::class.java)
        joinViewModel.otpResponseLive.observe(viewLifecycleOwner, Observer {
            when(it){
                is DataEntity.SUCCESS ->{
                    progress(false)
//                    startActivity(MainHomeActivity.getIntent(context!!))
//                    activity!!.finish()
                }
                is DataEntity.ERROR ->{
                    var error = it.error.message?:getString(R.string.sometingWentWrong)
                    loginRoot.showSnackBar(error,R.color.snack_red)
                }
            }
        })
    }

    private fun isValidEmail(target: String): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun progress(show :Boolean){
        userProgress.visibility = if(show) View.VISIBLE else View.GONE
        saveUserDetailBtn.text = if(show) "" else "next"
    }
}