package com.smartown.library.ui.base;

/**
 * Author:Tiger[https://github.com/KungFuBrother]
 * <p>
 * CreateTime:2016-10-20 13:24:29
 * <p>
 * Description:BasePresenter
 */
public class BasePresenter<V extends BaseView> {

    private V view;

    public BasePresenter(V view) {
        this.view = view;
    }

    protected V getView() {
        return view;
    }

}
