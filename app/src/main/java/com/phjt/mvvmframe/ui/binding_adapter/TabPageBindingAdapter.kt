package com.phjt.mvvmframe.ui.binding_adapter

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.phjt.mvvmframe.ui.adapter.CommonViewPagerAdapter
import com.phjt.module_base.utils.log.LogUtils
import com.phjt.mvvmframe.R

/**
 * @author: gengyan
 * date:    2021/9/18 17:32
 * company: GY
 * description: 描述
 */

@BindingAdapter("initTabAndPage")
fun initTabAndPage(tabLayout: TabLayout, initTabAndPage: Boolean) {

    val count: Int = tabLayout.tabCount
    val title = mutableListOf<String>()

    (0..count).forEach { i ->
        tabLayout.getTabAt(i)?.let {
            title.add(it.text.toString())
        }
    }

    tabLayout.rootView.findViewById<ViewPager>(R.id.view_pager)?.apply {
        adapter = CommonViewPagerAdapter(false, title.toTypedArray())
        tabLayout.setupWithViewPager(this)
    }

}