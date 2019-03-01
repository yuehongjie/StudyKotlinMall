package com.study.kotlin.mall.ui.activity


import android.os.Bundle
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.study.kotlin.base.ui.activity.BaseActivity
import com.study.kotlin.base.ui.fragment.BaseFragment
import com.study.kotlin.base.utils.AppPrefsUtils
import com.study.kotlin.goods.data.common.GoodsConstant
import com.study.kotlin.goods.event.UpdateCartSizeEvent
import com.study.kotlin.goods.ui.fragment.CartListFragment
import com.study.kotlin.goods.ui.fragment.CategoryFragment
import com.study.kotlin.mall.R
import com.study.kotlin.mall.ui.fragment.HomeFragment
import com.study.kotlin.mall.ui.fragment.MeFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {

    //懒加载 Fragment
    private val mHomeFragment:HomeFragment by lazy { HomeFragment() }   //首页
    private val mCateFragment: CategoryFragment by lazy { CategoryFragment() }   //分类
    private val mCarFragment:CartListFragment by lazy { CartListFragment()}    //购物车
    private val mMsgFragment:HomeFragment by lazy { HomeFragment() }    //消息
    private val mMeFragment:MeFragment by lazy { MeFragment() }         //我

    //当前选中的 Fragment
    private var mCurrentFragment:BaseFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBottomNavBar()
        switchFragment(mHomeFragment)
        loadData()

        EventBus.getDefault().register(this)
    }

    private fun loadData() {

        //2秒后，显示购物车、消息 ,不知道为啥一进来就设置的话没有效果
        Observable.timer(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                setCartSize()
            }

    }
    private fun initView() {

    }

    private fun initBottomNavBar() {

        mBottomNavBar.setTabSelectedListener(object: BottomNavigationBar.OnTabSelectedListener{
            override fun onTabReselected(position: Int) {
            }

            override fun onTabUnselected(position: Int) {
            }

            override fun onTabSelected(position: Int) {

                when(position) {

                    0 -> switchFragment(mHomeFragment) //切换首页
                    1 -> switchFragment(mCateFragment) //切换分类
                    2 -> switchFragment(mCarFragment) //切换购物车
                    3 -> switchFragment(mMsgFragment) //切换消息
                    4 -> switchFragment(mMeFragment) //切换我的

                }


            }

        })

    }

    /**
     * 切换 Fragment
     */
    private fun switchFragment(fragment: BaseFragment) {

        if (fragment == mCurrentFragment) return

        val transaction = supportFragmentManager.beginTransaction()

        //初次进来时没有已选中的 Fragment，所以需要判断
        if (mCurrentFragment != null) {
            //隐藏当前选中的 Fragment
            transaction.hide(mCurrentFragment as BaseFragment)
        }

        if (!fragment.isAdded) {
            transaction.add(R.id.mContainer, fragment)
        }

        transaction.show(fragment)
        transaction.commit()

        mCurrentFragment = fragment

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: UpdateCartSizeEvent) {

        setCartSize()
    }

    private fun setCartSize() {

        mBottomNavBar.checkCarBadgeCount(AppPrefsUtils.getInt(GoodsConstant.SP_CART_COUNT))
        
    }




    override fun onDestroy() {
        super.onDestroy()

        EventBus.getDefault().unregister(this)
    }
}
