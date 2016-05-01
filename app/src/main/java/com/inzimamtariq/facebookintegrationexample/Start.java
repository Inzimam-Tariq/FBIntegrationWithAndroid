package com.inzimamtariq.facebookintegrationexample;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.content.pm.PackageManager.GET_SIGNATURES;

public class Start extends AppCompatActivity {


    EditText fieldHashKey;
    Button btn_keyhash, nxt;
    EditText getPackage;
    String pkgName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        getPackage = (EditText) findViewById(R.id.et_keyHash);
        fieldHashKey = (EditText) findViewById(R.id.resultField);
        btn_keyhash= (Button) findViewById(R.id.btn_keyhash);
        nxt  = (Button) findViewById(R.id.btn_next);

        btn_keyhash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pkgName = getPackage.getText().toString();
                printHahKey();
            }
        });

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }


    /**
     * Method for printing Hash key to logcat and TextView
     */
    void printHahKey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    pkgName,
                    GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));

                fieldHashKey.setVisibility(View.VISIBLE);
                fieldHashKey.setText(Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}
