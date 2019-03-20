package com.study.kotlin.user.ui.activity


import android.os.Bundle
import android.util.Log
import com.jph.takephoto.model.TResult
import com.study.kotlin.user.utils.UserPrefsUtils
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ui.activity.BaseTakePhotoActivity
import com.study.kotlin.base.utils.GlideUtils
import com.study.kotlin.user.R
import com.study.kotlin.user.data.protocol.UserInfo
import com.study.kotlin.user.injection.component.DaggerUserComponent
import com.study.kotlin.user.presenter.UserInfoPresenter
import com.study.kotlin.user.presenter.view.UserInfoView
import kotlinx.android.synthetic.main.activity_user_info.*
import org.jetbrains.anko.toast


class UserInfoActivity : BaseTakePhotoActivity<UserInfoPresenter>(), UserInfoView {

    private lateinit var localImgFile: String


    //private val uploadManager: UploadManager by lazy { UploadManager() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_user_info)

        initView()

        initData()

    }

    private fun initView() {

        //上传头像
        mUserIconView.onClick {
            showAlertView()
        }

        //保存用户信息
        mHeaderBar.getRightView().onClick {
            saveUserInfo()
        }

    }

    private fun initData() {

        //头像
        val localSavedIcon = UserPrefsUtils.getLocalSavedIcon()
        GlideUtils.loadImageCenterCrop(this, localSavedIcon, R.drawable.icon_default_user, mUserIconIv)

        //昵称
        mUserNameEt.setText(UserPrefsUtils.getUserName())
        //性别
        val userGender = UserPrefsUtils.getUserGender()
        if ("0" == userGender) { // boy
            mGenderMaleRb.isChecked = true
        }else{ // girl
            mGenderFemaleRb.isChecked = true
        }
        //手机号
        mUserMobileTv.text = UserPrefsUtils.getUserMobile()
        //签名
        mUserSignEt.setText(UserPrefsUtils.getUserSign())

    }


    override fun injectComponent() {
        DaggerUserComponent.builder()
            .activityComponent(activityComponent)
            .build()
            .inject(this)

        mPresenter.mView = this
    }


    /**
     * 成功获取头像后，处理业务逻辑
     */
    override fun takeSuccess(result: TResult) {
        Log.e("TakePhoto", result.image.originalPath)
        Log.e("TakePhoto", result.image.compressPath)

        localImgFile = result.image.compressPath

        //获取上传文件的凭证
        mPresenter.getUploadToken()

    }



    //获取上传凭证成功，开始上传头像
    override fun onGetUploadTokenResult(result: String) {

        Log.e("UploadToken", result)

        //七牛云已经上传不了了，就保存到本地，加载本地图片吧

        UserPrefsUtils.saveIconToLocal(localImgFile)
        GlideUtils.loadImage(this, localImgFile, mUserIconIv)


//        uploadManager.put(localImgFile, null, result, object : UpCompletionHandler{
//
//            override fun complete(key: String?, info: ResponseInfo?, response: JSONObject?) {
//
//                if (info!= null && info.isOK) {
//
//                    //上传头像成功
//                    val remoteAvatar = BaseConstant.IMAGE_SERVER_ADDRESS + response?.get("hash")
//
//                }
//
//            }
//
//        }, null)

    }


    private fun saveUserInfo() {

        mPresenter.editUser(
            UserPrefsUtils.getLocalSavedIcon(),
            mUserNameEt.text.toString(),
            if (mGenderMaleRb.isChecked) "0" else "1",
            mUserSignEt.text.toString()
        )

    }

    override fun onEditUserResult(result: UserInfo) {

        toast("个人信息保存成功")
        UserPrefsUtils.putUserInfo(result)

    }

}