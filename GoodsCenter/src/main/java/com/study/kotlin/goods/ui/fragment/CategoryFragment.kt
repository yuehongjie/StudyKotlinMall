package com.study.kotlin.goods.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import com.study.kotlin.base.adapter.BaseRecyclerViewAdapter
import com.study.kotlin.base.ui.fragment.BaseMvpFragment
import com.study.kotlin.goods.R
import com.study.kotlin.goods.data.common.GoodsConstant
import com.study.kotlin.goods.data.protocol.Category
import com.study.kotlin.goods.injection.component.DaggerCategoryComponent
import com.study.kotlin.goods.presenter.CategoryPresenter
import com.study.kotlin.goods.presenter.view.CategoryView
import com.study.kotlin.goods.ui.activity.GoodsListActivity
import com.study.kotlin.goods.ui.adapter.SecondCategoryAdapter
import com.study.kotlin.goods.ui.adapter.TopCategoryAdapter
import kotlinx.android.synthetic.main.fragment_category.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.view

/**
 * 分类
 */
class CategoryFragment: BaseMvpFragment<CategoryPresenter>(), CategoryView {

    private lateinit var mTopAdapter: TopCategoryAdapter
    private lateinit var mSecondAdapter: SecondCategoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        loadData()

    }


    override fun injectComponent() {
        DaggerCategoryComponent.builder()
            .activityComponent(activityComponent)
            .build()
            .inject(this)

        mPresenter.mView = this
    }

    private fun initView() {

        //一级菜单
        mTopAdapter = TopCategoryAdapter(mActivity)
        mTopCategoryRv.layoutManager = LinearLayoutManager(context)
        mTopCategoryRv.adapter = mTopAdapter
        //设置一级菜单点击事件
        mTopAdapter.setOnItemClickListener(object: BaseRecyclerViewAdapter.OnItemClickListener<Category> {
            override fun onItemClick(item: Category, position: Int) {

                onTopItemClick(item, position)

            }

        })

        //二级菜单
        mSecondAdapter = SecondCategoryAdapter(mActivity)
        mSecondCategoryRv.layoutManager = GridLayoutManager(context, 3)
        mSecondCategoryRv.adapter = mSecondAdapter
        mSecondAdapter.setOnItemClickListener(object: BaseRecyclerViewAdapter.OnItemClickListener<Category> {

            override fun onItemClick(item: Category, position: Int) {
                onSecondItemClick(item, position)
            }

        })
    }

    //点击了一级菜单
    private fun onTopItemClick(item: Category, position: Int) {

        for (category in mTopAdapter.dataList) {
            category.isSelected = category.id == item.id
        }
        mTopAdapter.notifyDataSetChanged()

        //加载二级菜单
        loadData(item.id)

    }

    //点击了二级菜单
    private fun onSecondItemClick(item: Category, position: Int) {

        mActivity.startActivity<GoodsListActivity>(GoodsConstant.KEY_CATEGORY_ID to item.id)

    }

    /**
     * 加载分类
     * parentId = 0 则为加载一级分类，其他为加载二级分类
     */
    private fun loadData(parentId: Int = 0) {

        if (parentId != 0) {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_LOADING
        }

        mPresenter.getCategory(parentId)

    }

    /**
     * 获取商品分类
     */
    override fun onGetCategoryResult(result: MutableList<Category>?) {

        result?.let {

            //一级分类
            if (result[0].parentId == 0) {

                //默认选中第一个
                result[0].isSelected = true

                mTopAdapter.setData(result)

                //加载第一个的二级分类
                loadData(result[0].id)

                //Log.e("Top","一级菜单")

            }
            //二级分类
            else {

                mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
                mSecondAdapter.setData(result)

                //Log.e("Second","二级菜单")

            }

        }

        if (result.isNullOrEmpty()) {

            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY

        }

    }
}