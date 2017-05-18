package com.xilada.xldutils.widget;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

public class MyDatePickerDialog extends DatePickerDialog {

	public MyDatePickerDialog(Context context, int theme,
			OnDateSetListener listener, int year, int monthOfYear,
			int dayOfMonth) {
		super(context, theme, listener, year, monthOfYear, dayOfMonth);
	}

	public MyDatePickerDialog(Context context, OnDateSetListener callBack,
			int year, int monthOfYear, int dayOfMonth) {
		super(context, callBack, year, monthOfYear, dayOfMonth);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

}
