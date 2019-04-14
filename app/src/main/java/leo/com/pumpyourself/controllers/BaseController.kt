package leo.com.pumpyourself.controllers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import leo.com.pumpyourself.MainActivity

abstract class BaseController : Fragment() {

  companion object {
    const val TAB_MEAL = "tab_meal"
    const val TAB_TRAININGS = "tab_trainings"
    const val TAB_GROUPS = "tab_groups"
    const val TAB_PROFILE = "tab_profile"
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(getLayoutId(), container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    (activity as MainActivity).updateToolbarTitle(getTitle())
  }

  fun show(stackTab: String, fragment: Fragment) {
    (activity as MainActivity).pushFragments(stackTab, fragment, true)
  }

  abstract fun getLayoutId(): Int

  abstract fun getTitle(): String

}