package com.study.kotlin.base.ui.activity

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
import com.jph.takephoto.permission.TakePhotoInvocationHandler
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.utils.DateUtils
import java.io.File


/**
 * 一个继承自 BaseMvpActivity 的，用来上传头像的基类 Activity
 */
open abstract class BaseTakePhotoActivity<T: BasePresenter<*>>: BaseMvpActivity<T>(), TakePhoto.TakeResultListener,
    InvokeListener {

    private var takePhoto: TakePhoto? = null
    private lateinit var invokeParam: InvokeParam

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getTakePhoto().onCreate(savedInstanceState)
    }

    /**
     * 上传图片的弹窗（默认标题是上传头像）
     */
    fun showAlertView(title: String = "上传头像") {

        AlertView.Builder().setContext(this)
            .setStyle(AlertView.Style.ActionSheet)
            .setTitle(title)
            .setCancelText("取消")
            .setDestructive("拍照", "相册")
            .setOnItemClickListener(object : OnItemClickListener {
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

    /**
     * 拍照
     */
    private fun startTakePhoto() {

        //允许压缩
        getTakePhoto().onEnableCompress(CompressConfig.ofDefaultConfig(), false)
        //拍照方式拾取照片
        getTakePhoto().onPickFromCapture(Uri.fromFile(createTempFile()))
    }

    private fun createTempFile(): File {
        val tempFileName = "${DateUtils.curTime}.png"

        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            //外部存储
            File(Environment.getExternalStorageDirectory(), tempFileName)
        }else {
            //内部存储
            File(filesDir, tempFileName)
        }
    }


    /**
     * 从相册拾取
     */
    private fun startPickPicture() {

        //允许压缩
        getTakePhoto().onEnableCompress(CompressConfig.ofDefaultConfig(), false)
        //从相册拾取照片
        getTakePhoto().onPickFromGallery()

    }

    // TakePhoto 实例
    private fun getTakePhoto(): TakePhoto {
        if (takePhoto == null) {
            takePhoto = TakePhotoInvocationHandler.of(this).bind(TakePhotoImpl(this, this)) as TakePhoto
        }

        return takePhoto as TakePhoto

    }

    //实现 take photo 相应的方法
    //拾取图片成功
    override fun takeSuccess(result: TResult) {
        Log.e("TakePhoto", result.image.originalPath)
        Log.e("TakePhoto", result.image.compressPath)

        //拾取成功后，由子类实现具体的业务逻辑

    }

    override fun takeCancel() {

    }

    override fun takeFail(result: TResult, msg: String) {
        Log.e("TakePhoto", msg)
    }


    /**
     * 权限的申请
     */
    override fun invoke(invokeParam: InvokeParam): PermissionManager.TPermissionType {
        val type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.method)
        if (PermissionManager.TPermissionType.WAIT == type) {
            this.invokeParam = invokeParam
        }
        return type
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

    //回调
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        getTakePhoto().onActivityResult(requestCode, resultCode, data)
    }
}