package sg.just4fun.tgasdk.tga.mvp;

import androidx.annotation.NonNull;

import sg.just4fun.tgasdk.tga.base.BaseFragment;


/**
 * Created by Lrony on 18-4-10.
 */
public abstract class MvpFragment<P extends MvpPresenter> extends BaseFragment implements MvpView {

    private P mPresenter;

    @Override
    public void loading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void error() {

    }

    @Override
    public void empty() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter = null;
        }
    }

    @NonNull
    public abstract P createPresenter();

    /**
     * Subclass can get the bound presenter by calling this method.
     *
     * 子类通过调用该方法，获得绑定的presenter
     *
     * @return Bound presenter
     */
    protected P getPresenter() {
        if (mPresenter == null) {
            mPresenter = createPresenter();
            mPresenter.attachView(this);
        }
        return mPresenter;
    }
}
