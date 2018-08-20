package app.first.my.registroabc.Business;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class DialogMethods {
    public static void showInformationDialog(Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setCancelable(false);
        dialog.setMessage(message);
        if(onClickListener == null){
            dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
        }else{
            dialog.setPositiveButton("Ok",onClickListener);
        }

        if(!((Activity)context).isFinishing()){
            //dialog.show();
            dialog.show();
        }
    }

    public static void showErrorDialog(Context context, String message, String log) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Ocurrio un error");
        dialog.setCancelable(false);
        dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.setMessage(message);
        dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        if(!((Activity)context).isFinishing()){
            dialog.show();
        }
    }
}
