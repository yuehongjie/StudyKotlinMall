package com.study.kotlin.user.data.protocol

/**
 * 用户的数据模型：
 * id:          用户 id
 * userIcon:    用户头像
 * userName:    用户昵称
 * userGender:  用户性别
 * userMobile:  用户手机号
 * userSign:    用户签名
 *
 */
data class UserInfo(val id: String,
                    val userIcon: String,
                    val userName: String,
                    val userGender: String,
                    val userMobile: String,
                    val userSign: String)