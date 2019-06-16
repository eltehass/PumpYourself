package leo.com.pumpyourself.common

import android.content.Context
import android.content.Context.MODE_PRIVATE

object AccountManager {

    private const val USER_ID_PREFERENCE = "user_id_preference"
    private const val USER_ID_KEY = "user_id_key"

    private var userId: Int = -1

    fun setId(userId: Int, context: Context) {
        this.userId = userId

        val editor = context.getSharedPreferences(USER_ID_PREFERENCE, MODE_PRIVATE).edit()
        editor.putInt(USER_ID_KEY, userId)
        editor.apply()
    }

    fun getId(context: Context): Int {
        val prefs = context.getSharedPreferences(USER_ID_PREFERENCE, MODE_PRIVATE)
        return prefs.getInt(USER_ID_KEY, -1)
    }

    fun logOut(context: Context) {
        val editor = context.getSharedPreferences(USER_ID_PREFERENCE, MODE_PRIVATE).edit()
        editor.putInt(USER_ID_KEY, -1)
        editor.apply()
    }

}