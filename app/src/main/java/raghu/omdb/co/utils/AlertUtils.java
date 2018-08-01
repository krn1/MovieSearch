package raghu.omdb.co.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import raghu.omdb.co.R;
public class AlertUtils {
    public static void displayError(Activity context, String errorMsg) {
        if (context == null || context.isFinishing()) {
            //no-op
            return;
        }

        if (errorMsg == null || errorMsg.isEmpty() || errorMsg.equalsIgnoreCase("unavailable")) {
            errorMsg = context.getString(R.string.generic_network_error);
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context)
                .setMessage(errorMsg)
                .setPositiveButton("Okay",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                context.onBackPressed();
                            }
                        });

        alertDialogBuilder.create().show();
    }

}