package leo.com.pumpyourself

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*
import leo.com.pumpyourself.controllers.BaseController.Companion.TAB_GROUPS
import leo.com.pumpyourself.controllers.BaseController.Companion.TAB_MEAL
import leo.com.pumpyourself.controllers.BaseController.Companion.TAB_PROFILE
import leo.com.pumpyourself.controllers.BaseController.Companion.TAB_TRAININGS
import leo.com.pumpyourself.controllers.groups.GroupsController
import leo.com.pumpyourself.controllers.meal.MealController
import leo.com.pumpyourself.controllers.profile.ProfileController
import leo.com.pumpyourself.controllers.trainings.TrainingsController
import java.util.*

class MainActivity : AppCompatActivity() {

  private val controllerStacks: HashMap<String, Stack<Fragment>> = hashMapOf()
  private var currentControllerTabName = ""
  private val onNavigationItemSelectedListener =
    BottomNavigationView.OnNavigationItemSelectedListener { item ->
      when (item.itemId) {
        R.id.navigation_meal -> {
          selectedTab(TAB_MEAL)
          return@OnNavigationItemSelectedListener true
        }

        R.id.navigation_trainings -> {
          selectedTab(TAB_TRAININGS)
          return@OnNavigationItemSelectedListener true
        }

        R.id.navigation_groups -> {
          selectedTab(TAB_GROUPS)
          return@OnNavigationItemSelectedListener true
        }

        R.id.navigation_profile -> {
          selectedTab(TAB_PROFILE)
          return@OnNavigationItemSelectedListener true
        }
      }

      false
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val toolbar = findViewById<Toolbar>(R.id.toolbar_actionbar)
    setSupportActionBar(toolbar)
    supportActionBar?.title = "My title"

    controllerStacks[TAB_MEAL] = Stack()
    controllerStacks[TAB_TRAININGS] = Stack()
    controllerStacks[TAB_GROUPS] = Stack()
    controllerStacks[TAB_PROFILE] = Stack()

    navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    navigation.selectedItemId = R.id.navigation_meal
  }

  fun showToolbar() {
    if (!supportActionBar?.isShowing!!) {
      supportActionBar?.show()
    }
  }

  fun hideToolbar() {
    if (supportActionBar?.isShowing!!) {
      supportActionBar?.hide()
    }
  }

  fun updateToolbarTitle(title: String) {
    supportActionBar?.title = title
  }

  private fun selectedTab(tabId: String) {
    currentControllerTabName = tabId

    if (controllerStacks[tabId]?.isEmpty()!!) {
      when (tabId) {
        TAB_MEAL -> pushFragments(tabId, MealController(), true)
        TAB_TRAININGS -> pushFragments(tabId, TrainingsController(), true)
        TAB_GROUPS -> pushFragments(tabId, GroupsController(), true)
        TAB_PROFILE -> pushFragments(tabId, ProfileController(), true)
      }
    } else {
      pushFragments(tabId, controllerStacks[tabId]?.lastElement()!!, false)
    }
  }

  fun pushFragments(tag: String, fragment: Fragment, shouldAdd: Boolean) {
    if (shouldAdd) { controllerStacks[tag]?.push(fragment) }
    supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container, fragment)
        .commit()

    supportActionBar?.setDisplayHomeAsUpEnabled(controllerStacks[currentControllerTabName]?.size!! != 1)
    supportActionBar?.setDisplayShowHomeEnabled(controllerStacks[currentControllerTabName]?.size!! != 1)
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }

  override fun onBackPressed() {
    if (controllerStacks[currentControllerTabName]?.size!! == 1) {
      finish()
      return
    }

    popFragments()
  }

  private fun popFragments() {
    val fragment = controllerStacks[currentControllerTabName]?.elementAt(controllerStacks[currentControllerTabName]?.size!! - 2)
    controllerStacks[currentControllerTabName]?.pop()
    supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container, fragment!!)
        .commit()

    supportActionBar?.setDisplayHomeAsUpEnabled(controllerStacks[currentControllerTabName]?.size!! != 1)
    supportActionBar?.setDisplayShowHomeEnabled(controllerStacks[currentControllerTabName]?.size!! != 1)
  }

}