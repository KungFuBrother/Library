package com.smartown.demo;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.smartown.adapter.SimpleTextAdapter;
import com.smartown.demo.entity.SimpleListItem;
import com.smartown.library.ui.base.BaseFragment;
import com.smartown.library.ui.utils.JumpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Tiger
 * <p>
 * CrateTime:2016-11-02 17:08
 * <p>
 * Description:
 */
public class SimpleListFragment extends BaseFragment {

    private RecyclerView recyclerView;

    private List<SimpleListItem> items;
    private SimpleTextAdapter adapter;

    @Override
    protected void init() {
        items = new ArrayList<>();
        items.add(new SimpleListItem("IndicatorView", IndicatorViewTestFragment.class));
        items.add(new SimpleListItem("IndicatorView", IndicatorViewTestFragment.class));
        items.add(new SimpleListItem("IndicatorView", IndicatorViewTestFragment.class));
        items.add(new SimpleListItem("IndicatorView", IndicatorViewTestFragment.class));
        items.add(new SimpleListItem("IndicatorView", IndicatorViewTestFragment.class));
        items.add(new SimpleListItem("IndicatorView", IndicatorViewTestFragment.class));
        items.add(new SimpleListItem("IndicatorView", IndicatorViewTestFragment.class));
        adapter = new SimpleTextAdapter(getActivity(),
                new SimpleTextAdapter.AdapterHelper(new SimpleTextAdapter.AdapterHelper.ItemValues(16, Color.BLACK)) {
                    @Override
                    public int getSize() {
                        return items.size();
                    }

                    @Override
                    public String getItem(int position) {
                        return items.get(position).getName();
                    }

                    @Override
                    public void onClick(int position) {
                        JumpUtils.jump(getActivity(), items.get(position).getName(), items.get(position).getaClass());
                    }
                });
        findViews(R.layout.fragment_simple_list);
    }

    @Override
    protected void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.simple_list);
    }

    @Override
    protected void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new SimpleTextAdapter.ItemDecoration(Color.parseColor("#10000000")));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void registerViews() {

    }
}
