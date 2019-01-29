package com.study.kotlin.goods.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder
import com.kennyc.view.MultiStateView
import com.study.kotlin.base.adapter.BaseRecyclerViewAdapter
import com.study.kotlin.base.ui.activity.BaseMvpActivity
import com.study.kotlin.goods.R
import com.study.kotlin.goods.data.common.GoodsConstant
import com.study.kotlin.goods.data.protocol.Goods
import com.study.kotlin.goods.injection.component.DaggerGoodsComponent
import com.study.kotlin.goods.presenter.GoodsListPresenter
import com.study.kotlin.goods.presenter.view.GoodsListView
import com.study.kotlin.goods.ui.adapter.GoodsAdapter
import kotlinx.android.synthetic.main.activity_goods.*
import org.jetbrains.anko.startActivity

class GoodsListActivity: BaseMvpActivity<GoodsListPresenter>(), GoodsListView,
    BGARefreshLayout.BGARefreshLayoutDelegate {


    private lateinit var mGoodsAdapter: GoodsAdapter
    private var mCurrentPage: Int = 1
    private var mMaxPage: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_goods)

        initView()

        initRefreshLayout()

    }

    private fun initView() {

        mGoodsAdapter = GoodsAdapter(this)
        mGoodsRv.layoutManager = GridLayoutManager(this,2)
        mGoodsRv.adapter = mGoodsAdapter
        mGoodsAdapter.setOnItemClickListener(object: BaseRecyclerViewAdapter.OnItemClickListener<Goods> {

            override fun onItemClick(item: Goods, position: Int) {

                startActivity<GoodsDetailActivity>(GoodsConstant.KEY_GOODS_ID to item.id)

            }

        })

    }

    private fun initRefreshLayout() {

        mRefreshLayout.setDelegate(this)
        val viewHolder = BGANormalRefreshViewHolder(this, true)
        mRefreshLayout.setRefreshViewHolder(viewHolder)

        mRefreshLayout.beginRefreshing()

    }



    override fun injectComponent() {

        DaggerGoodsComponent.builder()
            .activityComponent(activityComponent)
            .build()
            .inject(this)

        mPresenter.mView = this

    }

    /**
     * 通过分类/关键字 加载商品列表
     */
    private fun loadData() {

        //关键字搜索
        if (intent.getIntExtra(GoodsConstant.KEY_GOODS_LIST_TYPE, 0) == GoodsConstant.KEY_TYPE_SEARCH_KEYWORD) {

            val keyword = intent.getStringExtra(GoodsConstant.KEY_GOODS_KEYWORD)
            mPresenter.getGoodsListByKeyword(keyword, mCurrentPage)

        }else { //分类搜索

            val categoryId = intent.getIntExtra(GoodsConstant.KEY_CATEGORY_ID, 0)
            mPresenter.getGoodsList(categoryId, mCurrentPage)
        }

    }

    override fun onError(msg: String) {
        super.onError(msg)
        mRefreshLayout.endRefreshing()
        mRefreshLayout.endLoadingMore()
    }

    override fun onGetGoodsListResult(result: MutableList<Goods>?) {


        if (result.isNullOrEmpty()) {

            //之前也没有加载过数据
            if (mGoodsAdapter.dataList.isEmpty()) {
                mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
            }

        }else {

            mMaxPage = result[0].maxPage

            if (mCurrentPage == 1) {

                mGoodsAdapter.setData(result)

            }else {

                mGoodsAdapter.addData(result)

            }

            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT

        }

        mRefreshLayout.endRefreshing()
        mRefreshLayout.endLoadingMore()

    }


    //是否允许加载更多
    override fun onBGARefreshLayoutBeginLoadingMore(refreshLayout: BGARefreshLayout?): Boolean {


        //没有更多了
        return if (mCurrentPage >= mMaxPage) {

           false

        }else {

            //加载更过
            mCurrentPage ++
            loadData()

            true

        }


    }

    //下拉刷新
    override fun onBGARefreshLayoutBeginRefreshing(refreshLayout: BGARefreshLayout?) {

        mCurrentPage = 1

        loadData()


    }
}