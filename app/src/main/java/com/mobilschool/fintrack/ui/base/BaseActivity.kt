package com.mobilschool.fintrack.ui.base

import dagger.android.support.DaggerAppCompatActivity
import com.arellomobile.mvp.MvpDelegate
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.mobilschool.fintrack.ui.main.MainPresenter
import javax.inject.Inject


open class BaseActivity: DaggerAppCompatActivity() {
    private var mMvpDelegate: MvpDelegate<out BaseActivity>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getMvpDelegate().onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        getMvpDelegate().onAttach()
    }

    override fun onResume() {
        super.onResume()

        getMvpDelegate().onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        getMvpDelegate().onSaveInstanceState(outState)
        getMvpDelegate().onDetach()
    }

    override fun onStop() {
        super.onStop()

        getMvpDelegate().onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()

        getMvpDelegate().onDestroyView()

        if (isFinishing) {
            getMvpDelegate().onDestroy()
        }
    }

    /**
     * @return The [MvpDelegate] being used by this Activity.
     */
    fun getMvpDelegate(): MvpDelegate<*> {
        if (mMvpDelegate == null) {
            mMvpDelegate = MvpDelegate<BaseActivity>(this)
        }
        return mMvpDelegate as MvpDelegate<*>
    }

}