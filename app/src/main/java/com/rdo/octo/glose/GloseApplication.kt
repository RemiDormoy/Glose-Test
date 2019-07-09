package com.rdo.octo.glose

import android.app.Application
import android.content.Context

class GloseApplication : Application() {


    companion object {
        lateinit var CONTEXT: Context

    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = this
    }
}