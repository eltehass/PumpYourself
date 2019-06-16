package leo.com.pumpyourself

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Toast
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.android.synthetic.main.activity_main.*
import leo.com.pumpyourself.common.CameraEvent
import leo.com.pumpyourself.common.Constants.CAMERA_REQUEST
import leo.com.pumpyourself.common.Constants.MY_CAMERA_PERMISSION_CODE
import leo.com.pumpyourself.controllers.base.BaseController.Companion.TAB_GROUPS
import leo.com.pumpyourself.controllers.base.BaseController.Companion.TAB_MEAL
import leo.com.pumpyourself.controllers.base.BaseController.Companion.TAB_PROFILE
import leo.com.pumpyourself.controllers.base.BaseController.Companion.TAB_TRAININGS
import leo.com.pumpyourself.controllers.groups.GroupsController
import leo.com.pumpyourself.controllers.meal.MealController
import leo.com.pumpyourself.controllers.profile.ProfileController
import leo.com.pumpyourself.controllers.trainings.TrainingsController
import org.greenrobot.eventbus.EventBus
import java.util.*


class MainActivity : AppCompatActivity() {

  private val TAG = "MainActivity"

  // Remote Config keys
  private val LOADING_PHRASE_CONFIG_KEY = "loading_phrase"
  private val WELCOME_MESSAGE_KEY = "welcome_message"
  private val WELCOME_MESSAGE_CAPS_KEY = "welcome_message_caps"
  private var mFirebaseRemoteConfig: FirebaseRemoteConfig? = null

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


    // Get Remote Config instance.
    // [START get_remote_config_instance]
//    mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
    // [END get_remote_config_instance]

    // Create a Remote Config Setting to enable developer mode, which you can use to increase
    // the number of fetches available per hour during development. Also use Remote Config
    // Setting to set the minimum fetch interval.
    // [START enable_dev_mode]
//    val configSettings = FirebaseRemoteConfigSettings.Builder()
//      .setDeveloperModeEnabled(BuildConfig.DEBUG)
//      .setMinimumFetchIntervalInSeconds(3600)
//      .build()
//    mFirebaseRemoteConfig?.setConfigSettings(configSettings)
    // [END enable_dev_mode]

    // Set default Remote Config parameter values. An app uses the in-app default values, and
    // when you need to adjust those defaults, you set an updated value for only the values you
    // want to change in the Firebase console. See Best Practices in the README for more
    // information.
    // [START set_default_values]
//    mFirebaseRemoteConfig?.setDefaults(R.xml.remote_config_defaults)
    // [END set_default_values]

//    fetchWelcome()
  }

  /**
   * Fetch a welcome message from the Remote Config service, and then activate it.
   */
  private fun fetchWelcome() {
//    mWelcomeTextView.setText(mFirebaseRemoteConfig.getString(LOADING_PHRASE_CONFIG_KEY))

    // [START fetch_config_with_callback]
    mFirebaseRemoteConfig?.fetchAndActivate()
      ?.addOnCompleteListener(this) { task ->
        if (task.isSuccessful) {
          val updated = task.result!!
          Log.d("MainActivity", "Config params updated: $updated")
          Toast.makeText(
            this@MainActivity, "Fetch and activate succeeded",
            Toast.LENGTH_SHORT
          ).show()

        } else {
          Toast.makeText(
            this@MainActivity, "Fetch failed",
            Toast.LENGTH_SHORT
          ).show()
        }
      }
    // [END fetch_config_with_callback]
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

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode === MY_CAMERA_PERMISSION_CODE) {
      if (grantResults[0] === PackageManager.PERMISSION_GRANTED) {
        Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show()
        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)
      } else {
        Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
      }
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (resultCode != Activity.RESULT_OK) { return }
    val photo = data?.extras?.get("data") as Bitmap
    EventBus.getDefault().post(CameraEvent(photo))
  }
}