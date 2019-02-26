package com.study.kotlin.user.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.InvokeParam
import com.jph.takephoto.model.TContextWrap
import com.jph.takephoto.model.TResult
import com.jph.takephoto.permission.InvokeListener
import com.jph.takephoto.permission.PermissionManager
import com.jph.takephoto.permission.PermissionManager.TPermissionType
import com.jph.takephoto.permission.TakePhotoInvocationHandler
import com.study.kotlin.user.utils.UserPrefsUtils
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ui.activity.BaseMvpActivity
import com.study.kotlin.base.utils.DateUtils
import com.study.kotlin.base.utils.GlideUtils
import com.study.kotlin.user.R
import com.study.kotlin.user.data.protocol.UserInfo
import com.study.kotlin.user.injection.component.DaggerUserComponent
import com.study.kotlin.user.presenter.UserInfoPresenter
import com.study.kotlin.user.presenter.view.UserInfoView
import kotlinx.android.synthetic.main.activity_user_info.*
import org.jetbrains.anko.toast
import java.io.File


class UserInfoActivity : BaseMvpActivity<UserInfoPresenter>(), UserInfoView, TakePhoto.TakeResultListener, InvokeListener {

    private lateinit var invokeParam: InvokeParam
    private lateinit var localImgFile: String
    private var takePhoto: TakePhoto? = null

    //private val uploadManager: UploadManager by lazy { UploadManager() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getTakePhoto().onCreate(savedInstanceState)

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

    private fun showAlertView() {

        AlertView.Builder().setContext(this)
            .setStyle(AlertView.Style.ActionSheet)
            .setTitle("上传头像")
            .setCancelText("取消")
            .setDestructive("拍照", "相册")
            .setOnItemClickListener(object : OnItemClickListener{
                override fun onItemClick(o: Any?, position: Int) {
                    if (position == 0) {
                        startTakePhoto()
                    }else{
                        startPickPicture()
                    }
                }

            })
            .build()
            .setCancelable(true)
            .show()

    }

    private fun startTakePhoto() {

        //允许压缩
        getTakePhoto().onEnableCompress(CompressConfig.ofDefaultConfig(), false)
        //拍照方式拾取照片
        getTakePhoto().onPickFromCapture(Uri.fromFile(createTempFile()))
    }


    private fun startPickPicture() {

        //允许压缩
        getTakePhoto().onEnableCompress(CompressConfig.ofDefaultConfig(), false)
        //从相册拾取照片
        getTakePhoto().onPickFromGallery()

    }


    override fun injectComponent() {
        DaggerUserComponent.builder()
            .activityComponent(activityComponent)
            .build()
            .inject(this)

        mPresenter.mView = this
    }

    //实现 take photo 相应的方法

    override fun takeSuccess(result: TResult) {
        Log.e("TakePhoto", result.image.originalPath)
        Log.e("TakePhoto", result.image.compressPath)

        localImgFile = result.image.compressPath

        //获取上传文件的凭证
        mPresenter.getUploadToken()

    }

    override fun takeCancel() {

    }

    override fun takeFail(result: TResult, msg: String) {
        Log.e("TakePhoto", msg)
    }

    override fun invoke(invokeParam: InvokeParam): PermissionManager.TPermissionType {
        val type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.method)
        if (TPermissionType.WAIT == type) {
            this.invokeParam = invokeParam
        }
        return type
    }

    private fun getTakePhoto(): TakePhoto {
        if (takePhoto == null) {
            takePhoto = TakePhotoInvocationHandler.of(this).bind(TakePhotoImpl(this, this)) as TakePhoto
        }

        return takePhoto as TakePhoto

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //以下代码为处理Android6.0、7.0动态权限所需
        val type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        getTakePhoto().onSaveInstanceState(outState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        getTakePhoto().onActivityResult(requestCode, resultCode, data)
    }


    private fun createTempFile():File {
        val tempFileName = "${DateUtils.curTime}.png"

        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            //外部存储
            File(Environment.getExternalStorageDirectory(), tempFileName)
        }else {
            //内部存储
            File(filesDir, tempFileName)
        }
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