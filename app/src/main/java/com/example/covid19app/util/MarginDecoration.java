package com.example.covid19app.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;

public class MarginDecoration extends RecyclerView.ItemDecoration {
    private int margin;

    private boolean isTopBottom = false;

    /**
     * Instantiates a new Margin decoration.
     *
     * @param context the context
     * @param margin  the margin
     */
    public MarginDecoration(Context context, int margin) {
        this.margin = convertDpToPixel(context, margin);
        this.isTopBottom = false;
    }

    /**
     * Instantiates a new Margin decoration.
     *
     * @param context     the context
     * @param margin      the margin
     * @param isTopBottom the is top bottom
     */
    public MarginDecoration(Context context, int margin, boolean isTopBottom) {
        this.margin = convertDpToPixel(context, margin);
        this.isTopBottom = isTopBottom;
    }

    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int position = parent.getChildAdapterPosition(view);
        boolean isLast = position == (state.getItemCount() - 1);

        if (layoutManager instanceof GridLayoutManager) {
            outRect.set(margin, margin, margin, margin);
        } else {
            if (isTopBottom)
                outRect.set(0, margin, 0, margin);
            else {
                if (position == 0) {
                    outRect.set(margin, margin, margin, (margin / 2));
                } else if (isLast) {
                    outRect.set(margin, (margin / 2), margin, margin);
                } else {
                    outRect.set(margin, (margin / 2), margin, (margin / 2));
                }
            }
        }
    }
    public static int convertDpToPixel(Context context, float dp) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float px = dp * (dm.densityDpi / 160f);
        return (int) px;
    }
}
