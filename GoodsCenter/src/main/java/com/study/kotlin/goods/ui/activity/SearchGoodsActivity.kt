package com.study.kotlin.goods.ui.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.kotlin.base.adapter.BaseRecyclerViewAdapter
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ext.setVisible
import com.study.kotlin.base.ui.activity.BaseActivity
import com.study.kotlin.base.utils.AppPrefsUtils
import com.study.kotlin.goods.R
import com.study.kotlin.goods.data.common.GoodsConstant
import com.study.kotlin.goods.ui.adapter.SearchHistoryAdapter
import kotlinx.android.synthetic.main.activity_search_goods.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * 商品搜索页面
 */
class SearchGoodsActivity: BaseActivity(), View.OnClickListener {


    private lateinit var mHistoryAdapter: SearchHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search_goods)

        initView()
    }

    override fun onStart() {
        super.onStart()
        loadHistory()
    }

    private fun initView() {

        //返回按钮
        mLeftIv.onClick(this)
        //搜索按钮
        mSearchTv.onClick(this)
        //清空历史
        mClearBtn.onClick(this)

        mHistoryAdapter = SearchHistoryAdapter(this)
        mSearchHistoryRv.layoutManager = LinearLayoutManager(this)
        mSearchHistoryRv.adapter = mHistoryAdapter
        mHistoryAdapter.setOnItemClickListener(object: BaseRecyclerViewAdapter.OnItemClickListener<String> {

            override fun onItemClick(item: String, position: Int) {

                go2GoodsList(item)

            }

        })

    }

    private fun loadHistory() {

        //集合的方式存储历史记录
        val set = AppPrefsUtils.getStringSet(GoodsConstant.SP_SEARCH_HISTORY)
        mNoDataTv.setVisible(set.isEmpty())
        mDataView.setVisible(set.isNotEmpty())

        if (set.isNotEmpty()) {
            val list = mutableListOf<String>()
            list.addAll(set)
            //更新历史记录
            mHistoryAdapter.setData(list)
        }

    }

    override fun onClick(v: View) {

        when(v.id) {

            R.id.mLeftIv -> finish()

            R.id.mSearchTv -> {

                val keyword = mKeywordEt.text.toString()
                if (keyword.isEmpty()) {
                    toast("请输入搜索内容")
                    return
                }

                //保存历史记录，内部已经做了添加操作
                AppPrefsUtils.putStringSet(GoodsConstant.SP_SEARCH_HISTORY, setOf(keyword))

                //去商品列表页
                go2GoodsList(keyword)

            }

            //清空历史记录
            R.id.mClearBtn -> {

                AppPrefsUtils.remove(GoodsConstant.SP_SEARCH_HISTORY)

            }

        }
    }

    /**
     * 去商品列表页
     */
    private fun go2GoodsList(keyword: String) {

        startActivity<GoodsListActivity>(
            GoodsConstant.KEY_GOODS_KEYWORD to keyword,
            GoodsConstant.KEY_GOODS_LIST_TYPE to GoodsConstant.KEY_TYPE_SEARCH_KEYWORD)

    }
}