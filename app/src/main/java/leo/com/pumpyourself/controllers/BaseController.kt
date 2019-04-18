package leo.com.pumpyourself.controllers

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import leo.com.pumpyourself.MainActivity

abstract class BaseController<LayoutClassBinding : ViewDataBinding> : Fragment() {

  lateinit var mainActivity: MainActivity

  companion object {
    const val TAB_MEAL = "tab_meal"
    const val TAB_TRAININGS = "tab_trainings"
    const val TAB_GROUPS = "tab_groups"
    const val TAB_PROFILE = "tab_profile"
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val binding: LayoutClassBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
    setDataForView(binding)
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    mainActivity = activity as MainActivity
    mainActivity.updateToolbarTitle(getTitle())
  }

  fun show(stackTab: String, fragment: Fragment) {
    mainActivity.pushFragments(stackTab, fragment, true)
  }

  abstract fun setDataForView(binding: LayoutClassBinding)

  abstract fun getLayoutId(): Int

  abstract fun getTitle(): String

}