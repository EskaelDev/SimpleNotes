package com.example.tomek.simplenotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

public class PinActivity extends AppCompatActivity {


    private static final String TAG = "pinActivity";
    EditText pinText1, pinText2, pinText3, pinText4;
    ImageView circ1, circ2, circ3, circ4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        pinText1 = findViewById(R.id.pinText1);
        pinText2 = findViewById(R.id.pinText2);
        pinText3 = findViewById(R.id.pinText3);
        pinText4 = findViewById(R.id.pinText4);

        circ1 = findViewById(R.id.imageview_circle1);
        circ2 = findViewById(R.id.imageview_circle2);
        circ3 = findViewById(R.id.imageview_circle3);
        circ4 = findViewById(R.id.imageview_circle4);

        pinText1.requestFocus();
        pinText1.setInputType(InputType.TYPE_CLASS_NUMBER);
        pinText1.setFocusableInTouchMode(true);

        pinText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, " onTextChanged");

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, " afterTextChanged");
                circ1.setImageResource(R.drawable.oval_fill);

                pinText2.requestFocus();
                pinText2.setInputType(InputType.TYPE_CLASS_NUMBER);
                pinText2.setFocusableInTouchMode(true);

            }
        });

        pinText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, " onTextChanged");

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, " afterTextChanged");
                circ2.setImageResource(R.drawable.oval_fill);

                pinText3.requestFocus();
                pinText3.setInputType(InputType.TYPE_CLASS_NUMBER);
                pinText3.setFocusableInTouchMode(true);

            }
        });
        pinText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, " onTextChanged");

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, " afterTextChanged");
                circ3.setImageResource(R.drawable.oval_fill);

                pinText4.requestFocus();
                pinText4.setInputType(InputType.TYPE_CLASS_NUMBER);
                pinText4.setFocusableInTouchMode(true);

            }
        });

        pinText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, " onTextChanged");

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, " afterTextChanged");
                circ4.setImageResource(R.drawable.oval_fill);

            }
        });
    }
}