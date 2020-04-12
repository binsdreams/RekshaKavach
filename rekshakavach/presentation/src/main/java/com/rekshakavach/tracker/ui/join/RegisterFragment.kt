package com.rekshakavach.tracker.ui.join

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.rekshakavach.tracker.R
import com.rekshakavach.tracker.common.showSnackBar
import com.rekshakavach.tracker.data.common.parseDateToServerFormat
import com.rekshakavach.tracker.di.vm.ViewModelProviderFactory
import com.rekshakavach.tracker.domain.entity.DataEntity
import com.rekshakavach.tracker.domain.entity.UserInfoEntity
import com.rekshakavach.tracker.ui.home.HomeActivity
import com.rekshakavach.tracker.ui.picker.DatePickerFragment
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.*
import javax.inject.Inject

class RegisterFragment : DaggerFragment(){

    lateinit var joinViewModel: JoinViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private var sex ="Male"
    private var locationLive = MutableLiveData<Location>()
    private var addressLive = MutableLiveData<String>()
    private var location :Location ? =null
    private var address :String ? =null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpBtn.setOnClickListener{
            register()
        }
        navigateToLogin.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_login)
        }
        woman.setColorFilter(
            ContextCompat.getColor(context!!, R.color.color_lightgray),
            android.graphics.PorterDuff.Mode.SRC_IN
        );
        genderSwitch.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                sex = "Male"
                man.clearColorFilter()
                woman.setColorFilter(
                    ContextCompat.getColor(context!!, R.color.color_lightgray),
                    android.graphics.PorterDuff.Mode.SRC_IN
                );
            } else {
                sex = "Female"
                woman.clearColorFilter()
                man.setColorFilter(
                    ContextCompat.getColor(context!!, R.color.color_lightgray),
                    android.graphics.PorterDuff.Mode.SRC_IN
                );
            }
        }

        dobEt.setOnClickListener{
            showDatePicker(joinViewModel.getDob().time)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        joinViewModel = ViewModelProvider(this, viewModelFactory).get(JoinViewModel::class.java)
        joinViewModel.initToday()
        joinViewModel.userResponseLive.observe(viewLifecycleOwner, Observer {
            dismissProgress()
            when(it){
                is DataEntity.SUCCESS ->{
                    startActivity(HomeActivity.getIntent(context!!))
                    activity!!.finish()
                }
                is DataEntity.ERROR ->{
                    var error = it.error.message?:getString(R.string.sometingWentWrong)
                    registerRoot.showSnackBar(error,R.color.snack_red)
                }
            }
        })

        (activity!! as JoinPhoneActivity).requestForLocationAndHandlePermission(locationLive,addressLive)
        locationLive.observe(viewLifecycleOwner, Observer {
            location = it
        })
        addressLive.observe(viewLifecycleOwner, Observer {
            address = it
        })
    }


    private fun register(){
        var fullname =fullnameEt.text.toString()
        var dob =dobEt.text.toString()
        var phone =userEmail.text.toString()
        var password =passwordEt.text.toString()
        if(fullname.isEmpty() || dob.isEmpty() || phone.isEmpty() || password.isEmpty()){
            registerRoot.showSnackBar(getString(R.string.allfileds_mandet),R.color.snack_red)
        }else{
            registerProgress.visibility =View.VISIBLE
            signUpBtn.text = ""
            joinViewModel.register(UserInfoEntity(
                name = fullname,
                dob = parseDateToServerFormat(joinViewModel.getDob()),
                phone = phone,
                sex = sex,
                address = address,
                registered_date = parseDateToServerFormat(Date())
            ))
        }
    }

    private fun showDatePicker(milliseconds :Long){
        val c = Calendar.getInstance()
        c.timeInMillis = milliseconds
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        DatePickerFragment().display(
            manager = childFragmentManager,
            tag = "DIALOG_TAG",
            calendar = Calendar.getInstance().apply { set(year, month, day) },
            callback = object : DatePickerFragment.Callback {
                override fun onDateSelected(calendar: Calendar) {
                    joinViewModel.setDob(calendar.time)
                    dobEt.text = joinViewModel.getDobStr()
                }
            })
    }

    private fun dismissProgress(){
        registerProgress.visibility =View.GONE
        signUpBtn.text = getString(R.string.signuup)
    }
}