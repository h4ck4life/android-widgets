package org.msrv.android.widgets;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hoojook.social.mobile.android.R;
import com.hoojook.social.mobile.android.fragments.HoojookOptionBarAwareUI;

public class OptionsBar extends LinearLayout {
    static final String TAG = OptionsBar.class.getName();
    static final String ANDROID_NS = "http://schemas.android.com/apk/res/android";
    static final String ATTR_LAYOUT_HEIGHT = "layout_height";

    LinearLayout filterOptions;
    LinearLayout optionBar;
    int layoutHeight = LinearLayout.LayoutParams.WRAP_CONTENT;

    public OptionsBar(Context context) {
        super(context);
    }

    public OptionsBar(Context context, AttributeSet set) {
        super(context, set);
        Log.d(TAG, "Entered OptionsBar(Context context, AttributeSet set)");
        if (set.getAttributeValue(ANDROID_NS, ATTR_LAYOUT_HEIGHT) != null) {
            layoutHeight = set
                    .getAttributeIntValue(ANDROID_NS, ATTR_LAYOUT_HEIGHT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        Log.d(TAG, "Exiting OptionsBar(Context context, AttributeSet set)");
    }

    @Override
    protected void onFinishInflate() {
        Log.d(TAG, "Entered onFinishInflate");
        super.onFinishInflate();
        ((Activity) getContext()).getLayoutInflater().inflate(R.layout.option_bar, this);
        filterOptions = (LinearLayout) findViewById(R.id.options);
        optionBar = (LinearLayout) findViewById(R.id.optionBar);
        if(layoutHeight != -1) {
            LayoutParams params = (LayoutParams) optionBar.getLayoutParams();
            params.height = layoutHeight;
            optionBar.setLayoutParams(params);
        }
        Log.d(TAG, "Exiting onFinishInflate");
    }

    public void refresh() {
        Log.d(TAG, "Entered refresh");
        HoojookOptionBarAwareUI activity = (HoojookOptionBarAwareUI) getContext();
        optionBar.removeAllViews();
        View[] optionItems = activity.getOptionItems(activity.getLayoutInflater());
        if (optionItems != null) {
            for (View option : optionItems) {
                optionBar.addView(option);
                option.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateOptionView(v);
                        filterOptions.setVisibility(filterOptions.getVisibility() == View.VISIBLE ? View.GONE
                                : View.VISIBLE);
                    }
                });
            }
        }
        if (optionBar.getChildCount() == 0) {
            optionBar.setVisibility(View.GONE);
        } else {
            optionBar.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "Exiting refresh");
    }

    private void updateOptionView(final View optionItem) {
        Log.d(TAG, "Entered updateOptionView");
        final HoojookOptionBarAwareUI activity = (HoojookOptionBarAwareUI) getContext();
        filterOptions.removeAllViews();
        if (optionItem != null) {
            View optionsView = activity.getOptionsView(activity.getLayoutInflater(), optionItem);
            if (optionsView != null) {
                if (optionsView instanceof ViewGroup) {
                    ViewGroup vg = (ViewGroup) optionsView;
                    for (int i = 0; i < vg.getChildCount(); i++) {
                        vg.getChildAt(i).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Log.d(TAG, "Entered optionItem#onClick");
                                filterOptions.setVisibility(filterOptions.getVisibility() == View.VISIBLE ? View.GONE
                                        : View.VISIBLE);
                                activity.onOptionSelected(optionItem, v);
                                Log.d(TAG, "Exiting optionItem#onClick");
                            }
                        });
                    }
                } else {
                    optionsView.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "Entered optionsView#onClick");
                            filterOptions.setVisibility(filterOptions.getVisibility() == View.VISIBLE ? View.GONE
                                    : View.VISIBLE);
                            activity.onOptionSelected(optionItem, v);
                            Log.d(TAG, "Exiting optionsView#onClick");
                        }
                    });
                }
                filterOptions.addView(optionsView);
            }
        }
        Log.d(TAG, "Exiting updateOptionView");
    }
}
