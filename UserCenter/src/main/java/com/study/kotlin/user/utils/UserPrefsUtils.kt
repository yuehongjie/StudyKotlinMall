package com.study.kotlin.user.utils

import com.study.kotlin.provider.common.ProviderConstant
import com.study.kotlin.base.common.BaseConstant
import com.study.kotlin.base.utils.AppPrefsUtils
import com.study.kotlin.user.data.protocol.UserInfo

/*
    本地存储用户相关信息
 */
object UserPrefsUtils {

    /*
        退出登录时，传入null,清空存储
     */
    fun putUserInfo(userInfo: UserInfo?) {
        AppPrefsUtils.putString(BaseConstant.KEY_SP_TOKEN, userInfo?.id ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_ICON, userInfo?.userIcon ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_NAME, userInfo?.userName ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_MOBILE, userInfo?.userMobile ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_GENDER, userInfo?.userGender ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_SIGN, userInfo?.userSign ?: "")
    }

    fun getUserToken(): String {
        return AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN)
    }

    fun getUserName(): String {
        return AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
    }

    fun getUserMobile(): String {
        return AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_MOBILE)
    }

    fun getUserGender(): String {
        return AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_GENDER)
    }

    fun getUserSign(): String {
        return AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN)
    }
    /**
     * 获取当前登录的用户的头像
     */
    fun getLocalSavedIcon():String {

        val currUserId = AppPrefsUtils.getString(ProviderConstant.KEY_SP_TOKEN)

        return AppPrefsUtils.getString("icon_$currUserId")
    }

    /**
     * 保存/更新当前登录的用户的头像路径
     */
    fun saveIconToLocal(localIcon: String) {

        val currUserId = AppPrefsUtils.getString(ProviderConstant.KEY_SP_TOKEN)

        return AppPrefsUtils.putString("icon_$currUserId", localIcon)

    }
}
