package com.study.kotlin.base.injection.module

import android.app.Activity
import dagger.Module
import dagger.Provides

/**
 * Activity 级别的 Module
 */

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    fun providesActivity(): Activity {
        return activity
    }

}