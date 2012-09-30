package org.msrv.android.widgets.lazybinding.fragment;


public interface LazyBindingFragment {
    public void startLoading();
    public void refreshContent(int arg);
    
    public interface DataChangeListener {
        public void onChangeStart(LazyBindingFragment fragment, String message);
        public void onSuccess(LazyBindingFragment fragment, String message);
        public void onError(LazyBindingFragment fragment, Integer errorCode, String errorMessage);
    }
}
