package com.study.kotlin.base.common

import android.app.Activity
import java.util.*

/**
 * 管理 Activity 的单例
 *
 * 单例：私有的构造方法 + 伴生对象 + by lazy 实现
 */
class AppManager private constructor(){

    private val activityStack = Stack<Activity>()

    companion object {
        val instance: AppManager by lazy { AppManager() }
    }


    /**
     * 入栈
     */
    fun addActivity(activity: Activity) {

        activityStack.add(activity)

    }

    /**
     * 出栈
     */
    fun finishActivity(activity: Activity) {

        activity.finish()
        activityStack.remove(activity)

    }

    /**
     * 栈顶 Activity
     */
    fun topActivity(): Activity {

        return activityStack.lastElement()

    }



}