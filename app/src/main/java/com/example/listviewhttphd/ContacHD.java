package com.example.listviewhttphd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContacHD extends AppCompatActivity {
    TextView textNom, textCiu, textTel;
    ImageView btnsalir, marcar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contac_hd);
        android:setTitle("Información del Contacto");

        textNom = findViewById(R.id.nombrehd);
        textCiu = findViewById(R.id.ciudadhd);
        textTel = findViewById(R.id.telHD);
        marcar= findViewById(R.id.marcar);
        btnsalir = findViewById(R.id.salir);

        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        String tel = intent.getStringExtra("idtel");
        String ciudad = intent.getStringExtra("ciudad");
        textNom.setText(nombre);
        textCiu.setText(ciudad);
        textTel.setText(tel);

        marcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String telefono = tel;
                if(ActivityCompat.checkSelfPermission(ContacHD.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                    return;
                if(!TextUtils.isEmpty(telefono)) {
                    String dial = "tel:" + telefono;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                }else {
                    Toast.makeText(ContacHD.this, "Ingresa un telefono", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "LLAMADA CANCELADA", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}