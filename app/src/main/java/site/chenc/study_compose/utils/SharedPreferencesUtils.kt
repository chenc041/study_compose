package site.chenc.study_compose.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import site.chenc.study_compose.R
import javax.inject.Inject

class SharedPreferencesUtils @Inject constructor(
   @ApplicationContext private val context: Context
) {
    private  val sharedPreferencesTag = context.resources.getString(R.string.shared_preferences_tag)
    private val sharedPreferences =
        context.getSharedPreferences(sharedPreferencesTag, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    /**
     * 保存字符串
     */
    fun saveStringValue(key: String, value: String) {
        editor.putString(key, value)
        editor.apply() // 或者使用 editor.commit() 如果需要同步操作
    }

    fun getStringValue(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun sableIntValue(key: String, value: Int) {
        editor.putInt(key, value)
        editor.apply() // 或者使用 editor.commit() 如果需要同步操作
    }

    fun getIntValue(key: String): Int? {
        return sharedPreferences.getInt(key, 0)
    }

    fun removeKey(key: String) {
        editor.remove(key)
        editor.apply()
    }
}