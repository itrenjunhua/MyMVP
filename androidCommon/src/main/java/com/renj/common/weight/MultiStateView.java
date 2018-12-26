package com.renj.common.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.renj.common.R;

/**
 * View that contains 4 different states: Content, Error, Empty, and Loading.<br>
 * Each state has their own separate layout which can be shown/hidden by setting
 * the {@link ViewState} accordingly Every MultiStateView
 * <b><i>MUST</i></b> contain a content view. The content view is obtained from
 * whatever is inside of the tags of the view via its XML declaration
 */
public class MultiStateView extends FrameLayout {

    private static final int UNKNOWN_VIEW = -1;

    private static final int CONTENT_VIEW = 0;

    private static final int ERROR_VIEW = 1;

    private static final int EMPTY_VIEW = 2;

    private static final int LOADING_VIEW = 3;
    private static final int NETWORK_VIEW = 4;

    public enum ViewState {
        CONTENT, LOADING, EMPTY, ERROR, NETWORK_VIEW
    }

    private LayoutInflater mInflater;

    private View mContentView;

    private View mLoadingView;
    private View mNetworkView;
    private View mErrorView;

    private View mEmptyView;

    private ViewState mViewState = ViewState.CONTENT;

    public MultiStateView(Context context) {
        this(context, null);
    }

    public MultiStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MultiStateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mInflater = LayoutInflater.from(getContext());
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.MultiStateView);

        int loadingViewResId = a.getResourceId(
                R.styleable.MultiStateView_msv_loadingView, -1);
        if (loadingViewResId > -1) {
            mLoadingView = mInflater.inflate(loadingViewResId, this, false);
            addView(mLoadingView, mLoadingView.getLayoutParams());
        }

        int emptyViewResId = a.getResourceId(
                R.styleable.MultiStateView_msv_emptyView, -1);
        if (emptyViewResId > -1) {
            mEmptyView = mInflater.inflate(emptyViewResId, this, false);
            addView(mEmptyView, mEmptyView.getLayoutParams());
        }

        int errorViewResId = a.getResourceId(
                R.styleable.MultiStateView_msv_errorView, -1);
        if (errorViewResId > -1) {
            mErrorView = mInflater.inflate(errorViewResId, this, false);
            addView(mErrorView, mErrorView.getLayoutParams());
        }
        int networkViewResId = a.getResourceId(
                R.styleable.MultiStateView_msv_networkView, -1);
        if (networkViewResId > -1) {
            mNetworkView = mInflater.inflate(networkViewResId, this, false);
            addView(mNetworkView, mNetworkView.getLayoutParams());
        }
        int viewState = a.getInt(R.styleable.MultiStateView_msv_viewState,
                UNKNOWN_VIEW);
        if (viewState != UNKNOWN_VIEW) {
            switch (viewState) {
                case CONTENT_VIEW:
                    mViewState = ViewState.CONTENT;
                    break;

                case ERROR_VIEW:
                    mViewState = ViewState.EMPTY;
                    break;

                case EMPTY_VIEW:
                    mViewState = ViewState.EMPTY;
                    break;

                case LOADING_VIEW:
                    mViewState = ViewState.LOADING;
                    break;
                case NETWORK_VIEW:
                    mViewState = ViewState.NETWORK_VIEW;
                    break;
            }
        }

        a.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mContentView == null)
            throw new IllegalArgumentException("Content view is not defined");
        setView();
    }

    /*
     * All of the addView methods have been overridden so that it can obtain the
     * content view via XML It is NOT recommended to notifyMessageAdd views into
     * MultiStateView via the addView methods, but rather use any of the
     * setViewForState methods to set views for their given ViewState
     * accordingly
     */
    @Override
    public void addView(View child) {
        if (isValidContentView(child))
            mContentView = child;
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        if (isValidContentView(child))
            mContentView = child;
        super.addView(child, index);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (isValidContentView(child))
            mContentView = child;
        super.addView(child, index, params);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (isValidContentView(child))
            mContentView = child;
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height) {
        if (isValidContentView(child))
            mContentView = child;
        super.addView(child, width, height);
    }

    @Override
    protected boolean addViewInLayout(View child, int index,
                                      ViewGroup.LayoutParams params) {
        if (isValidContentView(child))
            mContentView = child;
        return super.addViewInLayout(child, index, params);
    }

    @Override
    protected boolean addViewInLayout(View child, int index,
                                      ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        if (isValidContentView(child))
            mContentView = child;
        return super
                .addViewInLayout(child, index, params, preventRequestLayout);
    }

    /**
     * Returns the {@link View} associated with the
     * {@link com.renj.common.weight.MultiStateView.ViewState}
     *
     * @param state The {@link com.renj.common.weight.MultiStateView.ViewState} with to
     *              return the view for
     * @return The {@link View} associated with the
     * {@link com.renj.common.weight.MultiStateView.ViewState}, null if no view
     * is present
     */
    public View getView(ViewState state) {
        switch (state) {
            case LOADING:
                return mLoadingView;

            case CONTENT:
                return mContentView;

            case EMPTY:
                return mEmptyView;

            case ERROR:
                return mErrorView;

            case NETWORK_VIEW:
                return mNetworkView;

            default:
                return null;
        }
    }

    /**
     * Returns the current {@link com.renj.common.weight.MultiStateView.ViewState}
     *
     * @return
     */
    public ViewState getViewState() {
        return mViewState;
    }

    /**
     * Sets the current {@link com.renj.common.weight.MultiStateView.ViewState}
     *
     * @param state The {@link com.renj.common.weight.MultiStateView.ViewState} to set
     *              {@link MultiStateView} to
     */
    public void setViewState(ViewState state) {
        if (state != mViewState) {
            mViewState = state;
            setView();
        }
    }

    /**
     * Shows the {@link View} based on the
     * {@link com.renj.common.weight.MultiStateView.ViewState}
     */
    private void setView() {
        switch (mViewState) {
            case LOADING:
                if (mLoadingView == null) {
                    throw new NullPointerException("Loading View");
                }

                mLoadingView.setVisibility(View.VISIBLE);
                if (mContentView != null)
                    mContentView.setVisibility(View.GONE);
                if (mErrorView != null)
                    mErrorView.setVisibility(View.GONE);
                if (mEmptyView != null)
                    mEmptyView.setVisibility(View.GONE);
                if (mNetworkView != null)
                    mNetworkView.setVisibility(View.GONE);
                break;

            case EMPTY:
                if (mEmptyView == null) {
                    throw new NullPointerException("Empty View");
                }

                mEmptyView.setVisibility(View.VISIBLE);
                if (mLoadingView != null)
                    mLoadingView.setVisibility(View.GONE);
                if (mErrorView != null)
                    mErrorView.setVisibility(View.GONE);
                if (mContentView != null)
                    mContentView.setVisibility(View.GONE);
                if (mNetworkView != null)
                    mNetworkView.setVisibility(View.GONE);
                break;

            case ERROR:
                if (mErrorView == null) {
                    throw new NullPointerException("Error View");
                }

                mErrorView.setVisibility(View.VISIBLE);
                if (mLoadingView != null)
                    mLoadingView.setVisibility(View.GONE);
                if (mContentView != null)
                    mContentView.setVisibility(View.GONE);
                if (mEmptyView != null)
                    mEmptyView.setVisibility(View.GONE);
                if (mNetworkView != null)
                    mNetworkView.setVisibility(View.GONE);
                break;

            case NETWORK_VIEW:
                if (mNetworkView == null) {
                    throw new NullPointerException("Error View");
                }

                mNetworkView.setVisibility(View.VISIBLE);
                if (mLoadingView != null)
                    mLoadingView.setVisibility(View.GONE);
                if (mContentView != null)
                    mContentView.setVisibility(View.GONE);
                if (mEmptyView != null)
                    mEmptyView.setVisibility(View.GONE);
                if (mErrorView != null)
                    mErrorView.setVisibility(View.GONE);
                break;

            case CONTENT:
            default:
                if (mContentView == null) {
                    throw new NullPointerException("Content View");
                }

                mContentView.setVisibility(View.VISIBLE);
                if (mLoadingView != null)
                    mLoadingView.setVisibility(View.GONE);
                if (mErrorView != null)
                    mErrorView.setVisibility(View.GONE);
                if (mEmptyView != null)
                    mEmptyView.setVisibility(View.GONE);
                if (mNetworkView != null)
                    mNetworkView.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * Checks if the given {@link View} is valid for the Content View
     *
     * @param view The {@link View} to check
     * @return
     */
    private boolean isValidContentView(View view) {
        if (mContentView != null && mContentView != view) {
            return false;
        }

        return view != mLoadingView && view != mErrorView && view != mEmptyView
                && view != mNetworkView;
    }

    /**
     * Sets the view for the given view state
     *
     * @param view          The {@link View} to use
     * @param state         The {@link com.renj.common.weight.MultiStateView.ViewState}to set
     * @param switchToState If the {@link com.renj.common.weight.MultiStateView.ViewState} should
     *                      be switched to
     */
    public void setViewForState(View view, ViewState state,
                                boolean switchToState) {
        switch (state) {
            case LOADING:
                if (mLoadingView != null)
                    removeView(mLoadingView);
                mLoadingView = view;
                addView(mLoadingView);
                break;

            case EMPTY:
                if (mEmptyView != null)
                    removeView(mEmptyView);
                mEmptyView = view;
                addView(mEmptyView);
                break;

            case ERROR:
                if (mErrorView != null)
                    removeView(mErrorView);
                mErrorView = view;
                addView(mErrorView);
                break;

            case CONTENT:
                if (mContentView != null)
                    removeView(mContentView);
                mContentView = view;
                addView(mContentView);
                break;
            case NETWORK_VIEW:
                if (mNetworkView != null)
                    removeView(mNetworkView);
                mNetworkView = view;
                addView(mNetworkView);
                break;
        }

        if (switchToState)
            setViewState(state);
    }

    /**
     * Sets the {@link View} for the given
     * {@link com.renj.common.weight.MultiStateView.ViewState}
     *
     * @param view  The {@link View} to use
     * @param state The {@link com.renj.common.weight.MultiStateView.ViewState} to set
     */
    public void setViewForState(View view, ViewState state) {
        setViewForState(view, state, false);
    }

    /**
     * Sets the {@link View} for the given
     * {@link com.renj.common.weight.MultiStateView.ViewState}
     *
     * @param layoutRes     Layout resource id
     * @param state         The {@link com.renj.common.weight.MultiStateView.ViewState} to set
     * @param switchToState If the {@link com.renj.common.weight.MultiStateView.ViewState} should
     *                      be switched to
     */
    public void setViewForState(int layoutRes, ViewState state,
                                boolean switchToState) {
        if (mInflater == null)
            mInflater = LayoutInflater.from(getContext());
        View view = mInflater.inflate(layoutRes, this, false);
        setViewForState(view, state, switchToState);
    }

    /**
     * Sets the {@link View} for the given
     * {@link com.renj.common.weight.MultiStateView.ViewState}
     *
     * @param layoutRes Layout resource id
     * @param state     The {@link View} state to set
     */
    public void setViewForState(int layoutRes, ViewState state) {
        setViewForState(layoutRes, state, false);
    }
}
