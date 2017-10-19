package com.vb.torahmate.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.vb.torahmate.R;

/**
 * Created by psoloveichik on 9/23/2016.
 */

public class ToastSubmitting extends Activity {

    AlertDialog dialog;

    static CountDownTimer timer = null;
    Toast toast;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // creating toast and setting properties
        toast = new Toast(this);
        TextView textView = new TextView(this);
        textView.setTextColor(Color.BLUE);
        textView.setBackgroundColor(Color.TRANSPARENT);
        textView.setTextSize(20);
        textView.setText(R.string.submitting);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

        toast.setView(textView);

        //    Toast Display tTime Settings

        // Create the CountDownTimer object and implement the 2 methods
        // show the toast in onTick() method  and cancel the toast in onFinish() method
        // it will show the toast for 20 seconds (20000 milliseconds 1st argument) with interval of 1 second(2nd argument)

        timer = new CountDownTimer(20000, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                toast.show();
            }
            public void onFinish()
            {
                toast.cancel();
            }

        }.start();

    }

    public void cancel() {
        timer.cancel();
    }
}
