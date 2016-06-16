package com.nao20010128nao.Wisecraft.misc.compat;
import android.content.Context;
import android.support.v7.app.AlertDialog;

public class AppCompatAlertDialog extends AlertDialog
{
	public AppCompatAlertDialog(Context ctx){
		super(ctx);
	}
	public AppCompatAlertDialog(Context ctx,int attr){
		super(ctx,attr);
	}
}
