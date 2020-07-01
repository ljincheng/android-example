package cn.booktable.uikit.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;

import cn.booktable.uikit.R;

public class BottomNavigationView extends com.google.android.material.bottomnavigation.BottomNavigationView {

    public BottomNavigationView(Context context) {
        this(context,null);
    }

    public BottomNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };

        int[] colors = new int[]{getResources().getColor(R.color.uikit_color_on_primary),
                getResources().getColor(R.color.uikit_color_secondary)
        };
        ColorStateList csl = new ColorStateList(states, colors);
        this.setItemTextColor(csl);
        this.setItemIconTintList(csl);
    }
}
