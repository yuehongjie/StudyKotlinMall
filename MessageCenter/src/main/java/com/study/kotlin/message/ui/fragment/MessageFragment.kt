package com.study.kotlin.message.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import com.study.kotlin.base.ui.fragment.BaseMvpFragment
import com.study.kotlin.message.R
import com.study.kotlin.message.data.protocol.Message
import com.study.kotlin.message.event.MessageBadgeEvent
import com.study.kotlin.message.injection.component.DaggerMessageComponent
import com.study.kotlin.message.presenter.MessagePresenter
import com.study.kotlin.message.presenter.view.MessageView
import com.study.kotlin.message.ui.adapter.MessageAdapter
import kotlinx.android.synthetic.main.fragment_message.*
import org.greenrobot.eventbus.EventBus

class MessageFragment: BaseMvpFragment<MessagePresenter>(), MessageView {

    lateinit var mAdapter: MessageAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun injectComponent() {
        DaggerMessageComponent.builder()
            .activityComponent(activityComponent)
            .build()
            .inject(this)

        mPresenter.mView = this
    }

    private fun initView() {
        mMessageRv.layoutManager = LinearLayoutManager(mActivity)
        mAdapter = MessageAdapter(mActivity)
        mMessageRv.adapter = mAdapter

    }

    override fun onStart() {
        super.onStart()

        mPresenter.getMessageList()

    }

    /**
     * 获取消息后的回调
     */
    override fun onGetMessageListResult(result: MutableList<Message>?) {
        if (result != null && result.isNotEmpty()) {

            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT

        }else {
            //无消息
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            EventBus.getDefault().post(MessageBadgeEvent(false)) //消息页面显示的时候，发送事件，消除底部红点
        }
    }
}