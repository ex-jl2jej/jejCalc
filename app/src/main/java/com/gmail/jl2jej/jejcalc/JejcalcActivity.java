package com.gmail.jl2jej.jejcalc;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.Display;
import android.view.WindowManager;

import android.widget.EditText;
import android.widget.Button;
//import android.text.Editable;


import com.gmail.jl2jej.jejcalc.Parser;

import java.util.ArrayList;

public class JejcalcActivity extends Activity {
    EditText input;
    Parser par;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        Log.d("NOTE", "density: "+ metrics.density );
        Log.d("NOTE", "density dpi: "+ metrics.densityDpi );
        Log.d("NOTE", "scaledDensity: "+ metrics.scaledDensity );
        Log.d("NOTE", "width pixcels: "+ metrics.widthPixels );
        Log.d("NOTE", "height pixecls: "+ metrics.heightPixels );

        setContentView(R.layout.activity_jejcalc);

        input = (EditText)findViewById(R.id.editText1);
        input.setTypeface(Typeface.MONOSPACE);
        if( metrics.widthPixels >= 640 ) {
            par = new Parser(false, true);
        } else {
            par = new Parser(false, false);
        }

        ButtonClickListener bcl = new ButtonClickListener();

        Button clearButton = (Button)findViewById(R.id.clearScreen);
        clearButton.setOnClickListener( bcl );

        Button clearAllButton = (Button)findViewById(R.id.clearAll);
        clearAllButton.setOnClickListener( bcl );

        Button calcThisLine = (Button)findViewById(R.id.calc);
        calcThisLine.setOnClickListener( bcl );
    }

    class ButtonClickListener implements OnClickListener {
        public void onClick(View v) {
            if( v.getId() == R.id.clearScreen ) {
                input.setText("");
            } else if( v.getId() == R.id.clearAll ) {
                input.setText("");
                par.clearAllMemory();
            } else if( v.getId() == R.id.calc ) {
                calcAndResultSet(false);
            }
        }
    }

    private void calcAndResultSet(boolean returnKey)
    {
        String text = input.getText().toString();
        String[] lines = text.split("\n", 0);
        int cursorPos = input.getSelectionStart();
        int lineLen = 0;
        int lineNum = 0;
        ArrayList<String> lineArray = new ArrayList<String>();

        for (int i = 0 ; i < lines.length ; i++) {
            lineArray.add(lines[i]);
        }

        for (lineNum = 0 ; lineArray.size() > lineNum ; lineNum++ ) {
            lineLen += lineArray.get(lineNum).length() + 1;
            if (lineLen > cursorPos ) {
                break;
            }
        }

        if (returnKey == true) {
            if (lineLen > cursorPos) {
                return;
            } else {
                lineNum--;
            }
        }

        //String result = textStr + "\n" + par.doYyparse( textStr.substring(scp,ecp) );
        String result = par.doYyparse( lineArray.get(lineNum) );
        String[] resultLines = result.split("\n", 0);
        for (int i = 0 ; i < resultLines.length ; i++) {
            if (lineArray.size() <= lineNum + 1 + i) {
                lineArray.add(resultLines[i]);
            } else {
                lineArray.set(lineNum + 1 + i, resultLines[i]);
            }
        }

        String inputResult = "";
        for (int i = 0 ; i < lineArray.size() ; i++) {
            inputResult += lineArray.get(i) + "\n";
            if (i==lineNum+resultLines.length) {
                cursorPos = inputResult.length();
            }
        }
        input.setText(inputResult);
        input.setSelection(cursorPos);
        Log.i("POSITION", "Position = " + Integer.toString(cursorPos) );
        Log.i("KeyUp", "ENTER");
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if( keyCode == KeyEvent.KEYCODE_ENTER ) {
            calcAndResultSet(true);
        } else if (keyCode == KeyEvent.KEYCODE_DEL) {
            Log.i( "KeyUp", "DEL");
        } else {
            Log.i("KeyUp", "keycode = " + keyCode );
        }
        return super.onKeyUp(keyCode, event);
    }
}
