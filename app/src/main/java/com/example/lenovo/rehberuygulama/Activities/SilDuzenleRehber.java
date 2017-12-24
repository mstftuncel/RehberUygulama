package com.example.lenovo.rehberuygulama.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lenovo.rehberuygulama.Database.Database;
import com.example.lenovo.rehberuygulama.Model.Kisi;
import com.example.lenovo.rehberuygulama.R;

public class SilDuzenleRehber extends AppCompatActivity {

    EditText advesoyad;
    EditText email;
    EditText telno;
    Spinner cinsiyet;
    Button duzenle;
    Button sil;

    private ArrayAdapter<String> cinsiyetAdapter;
    private String[] cinsiyetdeger = {"KADIN","ERKEK"};
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sil_duzenle_rehber);

        advesoyad = (EditText)findViewById(R.id.etAdveSoyad);
        email = (EditText)findViewById(R.id.etEmail);
        telno = (EditText)findViewById(R.id.etTelNo);
        cinsiyet = (Spinner)findViewById(R.id.spCinsiyet);
        duzenle = (Button)findViewById(R.id.btnDuzenle);
        sil = (Button)findViewById(R.id.btnSil);

        cinsiyetAdapter = new ArrayAdapter<String>(SilDuzenleRehber.this,android.R.layout.simple_spinner_item,cinsiyetdeger);
        cinsiyetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cinsiyet.setAdapter(cinsiyetAdapter);

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");

        Database db = new Database(SilDuzenleRehber.this);
        Kisi kisi = new Kisi();
        kisi = db.KisiGetir(id);

        advesoyad.setText(kisi.getAdveSoyad());
        email.setText(kisi.getEmail());
        telno.setText(kisi.getTelNo());

        if(kisi.getCinsiyet().equals("KADIN")){
            cinsiyet.setSelection(0);
        }
        else{
            cinsiyet.setSelection(1);
        }



        duzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database db = new Database(SilDuzenleRehber.this);
                Kisi kisi = new Kisi();
                kisi.setId(id);
                kisi.setAdveSoyad(advesoyad.getText().toString());
                kisi.setEmail(email.getText().toString());
                kisi.setTelNo(telno.getText().toString());
                kisi.setCinsiyet(cinsiyet.getSelectedItem().toString());
                db.KisiGuncelle(kisi);

                Toast.makeText(SilDuzenleRehber.this,"Kaydınız Güncellenmiştir.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(SilDuzenleRehber.this);
                ad.setTitle("Uyarı");
                ad.setMessage("Kaydınız Silinecek.Emin misiniz?");

                ad.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Database db = new Database(SilDuzenleRehber.this);
                        db.KisiSil(id);
                        finish();
                    }
                });

                ad.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                Dialog dialog = ad.create();
                dialog.show();

            }
        });
    }
}
