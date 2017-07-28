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
        int ecp = input.getSelectionStart();
        String textStr;

        if( returnKey == true ) {
            text = text.substring(0,ecp-1)+ text.substring(ecp);
            ecp = ecp - 1;
        }
        ecp = text.indexOf("\n", ecp );
        if( ecp < 0 ) {
            ecp = text.length();
        }
        textStr = text.substring(0, ecp);


        int scp = textStr.lastIndexOf("\n");
        if( scp < 0 ) {
            scp = 0;
        }

        //String result = textStr + "\n" + par.doYyparse( textStr.substring(scp,ecp) );
        String result = par.doYyparse( textStr.substring(scp,ecp) );
        int ncr = 0;
        int ccp = 0;
        while((ccp = result.indexOf("\n",ccp+1)) > 0 ) {
            ++ncr;
        }
        String textEndStr = text.substring(ecp);
        if( textEndStr.length() > 1 ) {
            int ncp = 0;
            while((--ncr) >= 0 ) {
                ncp = textEndStr.indexOf("\n",ncp+1);
            }
            if( ncp > 0 ) {
                textEndStr = textEndStr.substring(ncp+1);
            }
        }
        if( textEndStr.startsWith("\n") == true ) {
            input.setText( textStr + result + textEndStr );
            input.setSelection((textStr + result).length()+1);
        } else {
            input.setText( textStr + "\n" + result + textEndStr );
            input.setSelection((textStr + "\n" + result).length());
        }
        Log.v("POSITION", "Position = " + ecp );
        Log.v("KeyUp", "ENTER");
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if( keyCode == KeyEvent.KEYCODE_ENTER ) {
            calcAndResultSet(true);
        } else if (keyCode == KeyEvent.KEYCODE_DEL) {
            Log.v( "KeyUp", "DEL");
        } else {
            Log.v("KeyUp", "keycode = " + keyCode );
        }
        return super.onKeyUp(keyCode, event);
    }
}
