package com.study.kotlin.goods.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import com.kotlin.base.utils.YuanFenConverter
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ui.fragment.BaseMvpFragment
import com.study.kotlin.goods.R
import com.study.kotlin.goods.data.protocol.CartGoods
import com.study.kotlin.goods.event.CartAllCheckedEvent
import com.study.kotlin.goods.event.UpdateTotalPriceEvent
import com.study.kotlin.goods.injection.component.DaggerCartComponent
import com.study.kotlin.goods.presenter.CartListPresenter
import com.study.kotlin.goods.presenter.view.CartListView
import com.study.kotlin.goods.ui.adapter.CartGoodsAdapter
import kotlinx.android.synthetic.main.fragment_cart.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class CartListFragment: BaseMvpFragment<CartListPresenter>(), CartListView {

    private lateinit var mAdapter: CartGoodsAdapter
    private var mTotalPrice = 0L

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        loadData()
        EventBus.getDefault().register(this)

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
    }

    private fun loadData() {

        mMultiStateView.viewState = MultiStateView.VIEW_STATE_LOADING
        mPresenter.getCartList()

    }

    override fun onGetCartListResult(result: MutableList<CartGoods>?) {
        if (result != null && result.isNotEmpty()){
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
            mAdapter.setData(result)
        }else{
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }
}