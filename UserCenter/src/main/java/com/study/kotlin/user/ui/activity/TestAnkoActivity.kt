package com.study.kotlin.user.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class TestAnkoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_test_anko)

        // 使用 Anko 硬编码布局
        verticalLayout {
            padding = 30
            editText {
                hint = "输入用户名"
                textSize = 16f
            }
            editText {
                hint = "输入密码"
                textSize = 16f
            }

            button{
                text = "测试"
                onClick {
                    toast("登陆成功")
                }
            }
        }

        toast("id : ${intent.getIntExtra("id", -1)}")

    }
}
