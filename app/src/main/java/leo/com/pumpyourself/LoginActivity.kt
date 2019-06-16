package leo.com.pumpyourself

import android.content.Intent
import android.os.Bundle
import android.support.transition.Fade
import android.support.transition.TransitionManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class LoginActivity : AppCompatActivity() {

    private var isLoginScreenState = false

    private lateinit var rootView: ViewGroup
    private lateinit var tvOperation: TextView
    private lateinit var etLogin: EditText
    private lateinit var etPassword: EditText
    private lateinit var etRepeatPassword: EditText
    private lateinit var dividerRepeatPassword: View

    private lateinit var btnAction: Button
    private lateinit var tvExtraAction: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//        FirebaseApp.initializeApp(this)


        rootView = findViewById(R.id.root_view)
        tvOperation = findViewById(R.id.tv_operation)
        etLogin = findViewById(R.id.et_login)
        etPassword = findViewById(R.id.et_password)
        etRepeatPassword = findViewById(R.id.et_repeat_password)
        dividerRepeatPassword = findViewById(R.id.view3)
        btnAction = findViewById(R.id.button_action)
        tvExtraAction = findViewById(R.id.tv_extra_action)

        tvExtraAction.setOnClickListener {
            val fade = Fade(Fade.IN)
            TransitionManager.beginDelayedTransition(rootView, fade)

            isLoginScreenState = !isLoginScreenState

            if (isLoginScreenState) {
                etRepeatPassword.visibility = View.GONE
                dividerRepeatPassword.visibility = View.GONE

                btnAction.text = "Sign in"
                tvExtraAction.text = "Sign up"
            } else {
                etRepeatPassword.visibility = View.VISIBLE
                dividerRepeatPassword.visibility = View.VISIBLE

                btnAction.text = "Sign up"
                tvExtraAction.text = "Sign in"
            }

            val anim = AlphaAnimation(1.0f, 0.0f).apply {
                duration = 350
                repeatCount = 1
                repeatMode = Animation.REVERSE
            }

            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationEnd(animation: Animation?) { }
                override fun onAnimationStart(animation: Animation?) { }
                override fun onAnimationRepeat(animation: Animation?) {
                    tvOperation.text = if (isLoginScreenState) "Sign in" else "Sign up"
                }
            })

            tvOperation.startAnimation(anim)
        }

        btnAction.setOnClickListener {
            // TODO add requests
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
