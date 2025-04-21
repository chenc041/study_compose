package site.chenc.study_compose.utils

import android.content.Context
import site.chenc.study_compose.AppConfig

/**
 * 本地轻度数据存储
 */
class SharedPreferencesUtils(
    context: Context
) {
    private val sharedPreferences =
        context.getSharedPreferences(
            AppConfig.DEFAULT_SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    private val editor = sharedPreferences.edit()

    /**
     * 保存字符串
     */
    fun saveStringValue(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringValue(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun saveIntValue(key: String, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    fun getIntValue(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun removeKey(key: String) {
        editor.remove(key)
        editor.apply()
    }
}