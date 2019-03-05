package com.study.kotlin.goods.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.kennyc.view.MultiStateView
import com.kotlin.base.utils.YuanFenConverter
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ext.setVisible
import com.study.kotlin.base.ui.fragment.BaseMvpFragment
import com.study.kotlin.base.utils.AppPrefsUtils
import com.study.kotlin.goods.R
import com.study.kotlin.goods.data.common.GoodsConstant
import com.study.kotlin.goods.data.protocol.CartGoods
import com.study.kotlin.goods.event.CartAllCheckedEvent
import com.study.kotlin.goods.event.UpdateCartSizeEvent
import com.study.kotlin.goods.event.UpdateTotalPriceEvent
import com.study.kotlin.goods.injection.component.DaggerCartComponent
import com.study.kotlin.goods.presenter.CartListPresenter
import com.study.kotlin.goods.presenter.view.CartListView
import com.study.kotlin.goods.ui.adapter.CartGoodsAdapter
import com.study.kotlin.provider.common.ProviderConstant
import com.study.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.fragment_cart.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast

class CartListFragment: BaseMvpFragment<CartListPresenter>(), CartListView {

    private lateinit var mAdapter: CartGoodsAdapter
    private var mTotalPrice = 0L
    private var mIsEditing = false
    private val mDeleteCartIdList: MutableList<Int> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        EventBus.getDefault().register(this)

    }

    override fun onResume() {
        super.onResume()
        loadData() //每次进来刷新
    }

    override fun injectComponent() {
        DaggerCartComponent.builder()
            .activityComponent(activityComponent)
            .build()
            .inject(this)

        mPresenter.mView = this
    }

    private fun initView() {

        mAdapter = CartGoodsAdapter(mActivity)
        mCartGoodsRv.layoutManager = LinearLayoutManager(mActivity)
        mCartGoodsRv.adapter = mAdapter

        //全选/全不选
        mAllCheckedCb.onClick {
            for (item in mAdapter.dataList) {
                item.isSelected = mAllCheckedCb.isChecked
            }
            mAdapter.notifyDataSetChanged()

            updateTotalPrice()
        }

        //编辑
        mHeaderBar.getRightView().onClick {
            switchEditStatus()
        }

        //删除
        mDeleteBtn.onClick {
            deleteCartList()
        }

        //结算
        mSettleAccountsBtn.onClick {
            submitCart()
        }
    }

    //设置返回按钮是否可见
    public fun setBackVisible(visible: Boolean) {
        mHeaderBar.getLeftView().setVisible(visible)
    }

    private fun submitCart() {

        val submitCartList:List<CartGoods>  = mAdapter.dataList.filter { it.isSelected }
        if (submitCartList.isEmpty()) {
            mActivity.toast("请选择要结算的商品")
        }else{
            mPresenter.submitCart(submitCartList, mTotalPrice)
        }

    }

    override fun onSubmitCartResult(result: Int) {
        //mActivity.toast("订单 Id: $result")
        ARouter.getInstance()
            .build(RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
            .withInt(ProviderConstant.KEY_ORDER_ID, result)  //传递订单 id
            .navigation()
    }

    private fun deleteCartList(){

        mDeleteCartIdList.clear()

        mAdapter.dataList.filter { it.isSelected }.mapTo(mDeleteCartIdList){it.id}

        if (mDeleteCartIdList.isEmpty()) {
            mActivity.toast("请选择要删除的商品")
        }else {
            mPresenter.deleteCartList(mDeleteCartIdList)
        }
    }

    override fun onDeleteCartListResult(result: Boolean) {
        mActivity.toast("删除成功")

        //删除本地购物车数据
        mAdapter.dataList.removeAll {
            it.id in mDeleteCartIdList
        }
        mAdapter.notifyDataSetChanged()

        //没有数据展示空页面
        if (mAdapter.dataList.isEmpty()) {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
            mHeaderBar.getRightView().setVisible(false)
        }

        refreshCartCount(mAdapter.dataList.size)

    }

    /**
     * 切换编辑状态
     */
    private fun switchEditStatus(){

        mIsEditing = !mIsEditing
        mTotalPriceTv.setVisible(!mIsEditing) //编辑中不显示总价
        mSettleAccountsBtn.setVisible(!mIsEditing) //编辑中不显示结算按钮
        mDeleteBtn.setVisible(mIsEditing) //编辑中显示删除按钮
        mHeaderBar.getRightView().text = if (mIsEditing) getString(R.string.common_complete) else getString(R.string.common_edit)

    }

    private fun loadData() {

        mMultiStateView.viewState = MultiStateView.VIEW_STATE_LOADING
        mPresenter.getCartList()

    }

    override fun onGetCartListResult(result: MutableList<CartGoods>?) {
        if (result != null && result.isNotEmpty()){
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
            mAdapter.setData(result)
            resetSomething()
            mHeaderBar.getRightView().setVisible(true)
        }else{
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
            mHeaderBar.getRightView().setVisible(false)
        }

        refreshCartCount(result?.count() ?: 0)
    }

    //通知（首页、详情页等）更新购物车数量
    private fun refreshCartCount(count: Int) {
        AppPrefsUtils.putInt(GoodsConstant.SP_CART_COUNT, count)
        EventBus.getDefault().post(UpdateCartSizeEvent())
    }

    //是否全部选中的事件
    @Subscribe
    fun onCheckedAllEvent(event: CartAllCheckedEvent) {
        mAllCheckedCb.isChecked = event.isAllChecked

        updateTotalPrice()
    }

    //更新总价的事件
    @Subscribe
    fun onUpdateTotalPriceEvent(event: UpdateTotalPriceEvent) {
        updateTotalPrice()
    }

    /**
     * 更新总价
     */
    private fun updateTotalPrice() {

        mTotalPrice = mAdapter.dataList
            .filter { it.isSelected } //过滤选中的
            .map { it.goodsCount * it.goodsPrice } //变换 计算单个总价
            .sum() //求和

        mTotalPriceTv.text = "合计${YuanFenConverter.changeF2YWithUnit(mTotalPrice)}"
    }


    private fun resetSomething() {
        mAllCheckedCb.isChecked = false
        updateTotalPrice()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }
}