package edu.bluejack21_2.guk.util;

import android.app.Activity;

public class ActivityHelper {
    public static void refreshActivity(Activity ctx) {
        ctx.finish();
        ctx.overridePendingTransition(0, 0);
        ctx.startActivity(ctx.getIntent());
        ctx.overridePendingTransition(0, 0);
    }
}
