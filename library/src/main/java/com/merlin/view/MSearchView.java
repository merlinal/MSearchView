package com.merlin.view;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by zal on 16/11/23.
 */
public class MSearchView extends LinearLayout {

    public MSearchView(Context context) {
        this(context, null);
    }

    public MSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr);
    }

    private Activity context;
    private ImageView searchImg;
    private ImageView deleteImg;
    private EditText inputEdit;

    private boolean isSearchMode = false;
    private boolean isExpanded = false;
    private int maxWidth = 0;
    private int fromColor;
    private int toColor;
    private long duration;
    private int searchShrinkIcon, searchExpandIcon;

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (context instanceof Activity) {
            this.context = (Activity) context;
        }
        initData(context);
        initView(context);
        initAttr(attrs, defStyleAttr, 0);
    }

    public boolean isExpanded() {
        return isSearchMode;
    }

    public MSearchView setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth - Util.dp2px(getContext(), 40);
        checkMode(true);
        return this;
    }

    public MSearchView setExpanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
        checkMode(true);
        return this;
    }

    public MSearchView setSearchIcon(int... resIds) {
        if (resIds != null && resIds.length > 0) {
            if (resIds.length == 1) {
                searchShrinkIcon = searchExpandIcon = resIds[0];
            } else {
                searchShrinkIcon = resIds[0];
                searchExpandIcon = resIds[1];
            }
        }
        checkMode(false);
        return this;
    }

    public MSearchView setClearIcon(int resId) {
        deleteImg.setImageResource(resId);
        return this;
    }

    public MSearchView setTextColor(int color) {
        inputEdit.setTextColor(color);
        return this;
    }

    public MSearchView setHintColor(int color) {
        inputEdit.setHintTextColor(color);
        return this;
    }

    public MSearchView setTextSize(float size) {
        inputEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        return this;
    }

    public MSearchView setHint(CharSequence hint) {
        inputEdit.setHint(hint);
        return this;
    }

    public MSearchView setToColor(int toColor) {
        this.toColor = toColor;
        return this;
    }

    public MSearchView setFromColor(int fromColor) {
        this.fromColor = fromColor;
        return this;
    }

    private void checkMode(boolean isReset) {
        if (isExpanded) {
            searchImg.setImageResource(searchExpandIcon);
            if (isReset) {
                duration = 0;
                isSearchMode = false;
                modeSwitch();
            }
        } else {
            searchImg.setImageResource(searchShrinkIcon);
        }
    }

    private void initAttr(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MSearchView, defStyleAttr, defStyleRes);
        if (ta != null) {
            fromColor = ta.getColor(R.styleable.MSearchView_fromColor, fromColor);
            toColor = ta.getColor(R.styleable.MSearchView_toColor, toColor);
            searchShrinkIcon = ta.getResourceId(R.styleable.MSearchView_searchShrinkIcon, -1);
            searchExpandIcon = ta.getResourceId(R.styleable.MSearchView_searchExpandIcon, -1);

            setFromColor(fromColor).setToColor(toColor).setSearchIcon(searchShrinkIcon, searchExpandIcon)
                    .setClearIcon(ta.getResourceId(R.styleable.MSearchView_clearIcon, -1))
                    .setTextColor(ta.getColor(R.styleable.MSearchView_textColor, -1))
                    .setTextSize(ta.getDimension(R.styleable.MSearchView_textSize, 14))
                    .setHintColor(ta.getColor(R.styleable.MSearchView_hintColor, -1))
                    .setHint(ta.getString(R.styleable.MSearchView_hint))
                    .setExpanded(ta.getBoolean(R.styleable.MSearchView_expanded, false));

            ta.recycle();
        }
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_search_custom, this);
        searchImg = Util.findById(this, R.id.search_search);
        deleteImg = Util.findById(this, R.id.search_delete);
        inputEdit = Util.findById(this, R.id.search_input);

        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    if (isSearchMode) {
                        modeSwitch();
                        return true;
                    }
                }
                return false;
            }
        });
        searchImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSearchMode) {
                    if (searchListener != null) {
                        searchListener.onSearchClick(inputEdit.getText().toString());
                    }
                } else {
                    modeSwitch();
                }
            }
        });

        deleteImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = inputEdit.getText().toString().trim();
                if (input.length() > 0) {
                    inputEdit.setText("");
                } else {
                    if (!isExpanded) {
                        modeSwitch();
                    }
                }
            }
        });
    }

    private void modeSwitch() {
        if (isSearchMode) {
            statusChange(0, toColor, fromColor, duration);
        } else {
            if (maxWidth < Util.dp2px(getContext(), 80)) {
                maxWidth = context.getResources().getDisplayMetrics().widthPixels - searchImg.getWidth() - Util.dp2px(getContext(), 8);
            }
            statusChange(maxWidth, fromColor, toColor, duration);
        }
    }

    private void statusChange(int targetWidth, int fromColor, int toColor, long duration) {
        isSearchMode = !isSearchMode;
        //改变输入框宽度
        ObjectAnimator anim = ObjectAnimator.ofInt(new ViewWrapper(inputEdit), "width", targetWidth).setDuration(duration);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if (!isSearchMode) {
                    inputEdit.setFocusableInTouchMode(false);
                    deleteImg.setVisibility(View.GONE);
                    searchImg.setImageResource(searchShrinkIcon);
                    Util.hideSoftInput(context, inputEdit);
                    if (modeChangedListener != null) {
                        modeChangedListener.onShrinked(MSearchView.this);
                    }
                } else {
                    inputEdit.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (isSearchMode) {
                    inputEdit.setFocusableInTouchMode(true);
                    inputEdit.requestFocus();
                    deleteImg.setVisibility(View.VISIBLE);
                    searchImg.setImageResource(searchExpandIcon);
                    Util.showSoftInput(context);
                    if (modeChangedListener != null) {
                        modeChangedListener.onExpanded(MSearchView.this);
                    }
                } else {
                    inputEdit.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        anim.start();

        //改变背景色
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setBackgroundColor((Integer) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimator.setDuration(duration * 2);
        valueAnimator.start();
    }

    /**
     * 初始化属性
     *
     * @param context
     */
    private void initData(Context context) {
        fromColor = 0xffffffff;
        toColor = 0x00ffffff;
        duration = 300l;
    }

    private SearchListener searchListener;

    private ModeChangedListener modeChangedListener;

    /**
     * 输入监听
     *
     * @param textWatcher
     */
    public void addTextChangedListener(TextWatcher textWatcher) {
        inputEdit.addTextChangedListener(textWatcher);
    }

    /**
     * 搜索
     *
     * @param searchListener
     */
    public void addSearchListener(SearchListener searchListener) {
        this.searchListener = searchListener;
    }

    /**
     * View状态切换
     *
     * @param modeChangedListener
     */
    public void addModeChangedListener(ModeChangedListener modeChangedListener) {
        this.modeChangedListener = modeChangedListener;
    }

    public interface SearchListener {
        void onSearchClick(String key);
    }

    public interface ModeChangedListener {
        void onExpanded(MSearchView searchView);

        void onShrinked(MSearchView searchView);
    }

}
