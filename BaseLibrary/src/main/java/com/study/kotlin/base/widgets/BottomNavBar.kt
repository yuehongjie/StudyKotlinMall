package com.study.kotlin.base.widgets

import android.content.Context
import android.util.AttributeSet
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.ashokvarma.bottomnavigation.ShapeBadgeItem
import com.ashokvarma.bottomnavigation.TextBadgeItem
import com.study.kotlin.base.R

class BottomNavBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationBar(context, attrs, defStyleAttr) {

    private val mCarBadge: TextBadgeItem
    private val mMsgBadge: ShapeBadgeItem

    init {

        //首页
        val homeItem = BottomNavigationItem(R.drawable.btn_nav_home_press, resources.getString(R.string.nav_bar_home))
            .setActiveColorResource(R.color.common_blue)
            .setInactiveIconResource(R.drawable.btn_nav_home_normal) //未选中时的图标
            .setInActiveColorResource(R.color.text_normal) //未选中时的颜色

        //分类
        val categoryItem = BottomNavigationItem(R.drawable.btn_nav_category_press, resources.getString(R.string.nav_bar_category))
            .setActiveColorResource(R.color.common_blue)
            .setInactiveIconResource(R.drawable.btn_nav_category_normal) //未选中时的图标
            .setInActiveColorResource(R.color.text_normal) //未选中时的颜色

        //购物车
        val carItem = BottomNavigationItem(R.drawable.btn_nav_cart_press, resources.getString(R.string.nav_bar_cart))
            .setActiveColorResource(R.color.common_blue)
            .setInactiveIconResource(R.drawable.btn_nav_cart_normal) //未选中时的图标
            .setInActiveColorResource(R.color.text_normal) //未选中时的颜色

        //设置提醒标记
        mCarBadge = TextBadgeItem()
        mCarBadge.hide()
        carItem.setBadgeItem(mCarBadge)

        //消息
        val messageItem = BottomNavigationItem(R.drawable.btn_nav_msg_press, resources.getString(R.string.nav_bar_msg))
            .setActiveColorResource(R.color.common_blue)
            .setInactiveIconResource(R.drawable.btn_nav_msg_normal) //未选中时的图标
            .setInActiveColorResource(R.color.text_normal) //未选中时的颜色

        //消息提醒
        mMsgBadge = ShapeBadgeItem()
        mMsgBadge.setShape(ShapeBadgeItem.SHAPE_OVAL) //圆形
        mMsgBadge.setSizeInDp(context, 8, 8)
        mMsgBadge.hide()
        messageItem.setBadgeItem(mMsgBadge)

        //我的
        val meItem = BottomNavigationItem(R.drawable.btn_nav_user_press, resources.getString(R.string.nav_bar_user))
            .setActiveColorResource(R.color.common_blue)
            .setInactiveIconResource(R.drawable.btn_nav_user_normal) //未选中时的图标
            .setInActiveColorResource(R.color.text_normal) //未选中时的颜色

        //设置导航栏样式
        this.setMode(MODE_SHIFTING)
            //.setMode(MODE_FIXED)
            .setBackgroundStyle(BACKGROUND_STYLE_STATIC)
            .setBarBackgroundColor(R.color.common_white)

        //添加 item
        this.addItem(homeItem)
            .addItem(categoryItem)
            .addItem(carItem)
            .addItem(messageItem)
            .addItem(meItem)
            .setFirstSelectedPosition(0) //选中第一个
            .initialise()

    }

    /**
     * 设置购物车商品的数量
     * 0: 隐藏
     */
    fun checkCarBadgeCount(count: Int) {
        if (count <= 0) {
            mCarBadge.hide()
        }else {
            mCarBadge.show()
            if (count > 99) {
                mCarBadge.setText("...")
            }else{
                mCarBadge.setText("$count")
            }

        }
    }

    /**
     * 消息红点是否显示
     */
    fun checkMsgBadgeVisible(visible: Boolean) {

        if (visible) {
            mMsgBadge.show()
        }else {
            mMsgBadge.hide()
        }

    }

}