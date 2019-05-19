package leo.com.pumpyourself.controllers.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import leo.com.pumpyourself.MainActivity
import leo.com.pumpyourself.network.AndroidCoroutineContextElement
import leo.com.pumpyourself.network.doCoroutineWork

abstract class BaseController<LayoutClassBinding : ViewDataBinding> : Fragment() {

  lateinit var mainActivity: MainActivity
  abstract var binding: LayoutClassBinding

  private var coroutineJob = Job()
  private val coroutineScope = CoroutineScope(Dispatchers.Main + coroutineJob)

  companion object {
    const val TAB_MEAL = "tab_meal"
    const val TAB_TRAININGS = "tab_trainings"
    const val TAB_GROUPS = "tab_groups"
    const val TAB_PROFILE = "tab_profile"
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    mainActivity = activity as MainActivity
    mainActivity.updateToolbarTitle(getTitle())
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initController()
  }

  fun show(stackTab: String, fragment: Fragment) {
    mainActivity.pushFragments(stackTab, fragment, true)
  }

  fun <P> asyncSafe(doOnAsyncBlock: suspend CoroutineScope.() -> P) {
    doCoroutineWork(doOnAsyncBlock, coroutineScope, AndroidCoroutineContextElement)
  }

  open fun initController() {}

  abstract fun getLayoutId(): Int

  abstract fun getTitle(): String

}