package com.merlin.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.merlin.view.MSearchView;
import com.merlin.view.Util;

public class MainActivity extends AppCompatActivity {

    private final String tag = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MSearchView mSearchView = Util.findById(this, R.id.MSearchView);
        mSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(tag, "length=" + editable.toString().length());  //搜索
            }
        });

        mSearchView.addSearchListener(new MSearchView.SearchListener() {
            @Override
            public void onSearchClick(String key) {
                Log.i(tag, "searching");  //搜索
            }
        });

        mSearchView.addModeChangedListener(new MSearchView.ModeChangedListener() {
            @Override
            public void onExpanded(MSearchView searchView) {
                Log.i(tag, "onExpanded");  //展开
            }

            @Override
            public void onShrinked(MSearchView searchView) {
                Log.i(tag, "onShrinked");  //收缩
            }
        });

    }

}
