package org.msrv.android.widgets.optionsbar;

import android.view.LayoutInflater;
import android.view.View;


public interface OptionsBarAwareUI {
    public View getOptionsView(LayoutInflater inflater, View optionItem);
    public View[] getOptionItems(LayoutInflater inflater);
    public boolean isAlignedRight();
    public boolean onOptionSelected(View optionItem, View selectedOption);
    public LayoutInflater getLayoutInflater();
}
