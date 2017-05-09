package my.util.app.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import my.util.app.R;

/**
 * Created by labattula on 09/05/17.
 */

public class UtilDialog {

    /**
     * One Info the layout must contain the OK (R.id.ok) TextView
     * <p>
     * One TextView for displaying the Info
     *
     * @param ctx
     * @param layoutResource
     */
    public static void showSubmitDialog(Context ctx, int layoutResource, int infoMessage, View.OnClickListener submitClick) {
        final Dialog dialog = new Dialog(ctx);
        View view = LayoutInflater.from(ctx).inflate(layoutResource, null);

        //Submit Ok
        ((TextView) view.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //Information message
        ((TextView) view.findViewById(R.id.submit_text)).setText(infoMessage);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * Show AlertDialog
     *
     * @param ctx
     */
    public static void showDialog(Context ctx) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.create().show();
    }

    public static void showDialog(final Context ctx, int message, int layoutResourcse, final int positiveText, final int negativeText) {
        new AlertDialog.Builder(ctx)
                .setMessage(message)
                .setView(layoutResourcse)
                .setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.showShortToast(ctx, ctx.getString(positiveText));
                    }
                })
                .setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.showShortToast(ctx, ctx.getString(negativeText));
                    }
                }).show();
    }

    /**
     * Dismissable Dialog display
     *
     * @param ctx
     * @param layoutResource
     * @param infoMessage
     */
    public static void showDialog(Context ctx, int layoutResource, int infoMessage) {
        final Dialog dialog = new Dialog(ctx);
        View view = LayoutInflater.from(ctx).inflate(layoutResource, null);

        //Submit Ok
        ((TextView) view.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //Information message
        ((TextView) view.findViewById(R.id.submit_text)).setText(infoMessage);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * One Info the layout must contain the OK (R.id.ok) TextView
     * <p>
     * One TextView for displaying the Info
     *
     * @param layoutResource
     */
    public static void showSubmitDialog(Context ctx, int layoutResource, int infoMessage, final boolean isDismissable,
                                 final View.OnClickListener submitClick) {
        final Dialog dialog = new Dialog(ctx);
        View view = LayoutInflater.from(ctx).inflate(layoutResource, null);

        //Submit Ok
        ((TextView) view.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDismissable) {
                    dialog.dismiss();
                } else {
                    submitClick.onClick(v);
                }
            }
        });

        //Information message
        ((TextView) view.findViewById(R.id.submit_text)).setText(infoMessage);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

}
