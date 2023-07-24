package com.fintech.crudsql;

import static android.view.View.generateViewId;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fintech.crudsql.db.DBSource;
import com.fintech.crudsql.model.Barang;

public class TambahActivity extends AppCompatActivity implements View.OnClickListener {

    Button simpan, batal; // Added the "batal" Button here
    EditText edNama, edHarga, edMerk;

    DBSource dbSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        edNama = findViewById(R.id.ed_nama);
        edHarga = findViewById(R.id.ed_harga);
        edMerk = findViewById(R.id.ed_merk);

        simpan = findViewById(R.id.btn_simpan);
        simpan.setOnClickListener(this);

        dbSource = new DBSource(this);
        dbSource.open();

        // batal action
        batal = findViewById(R.id.btn_batal);
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View view) {
        String nama = null;
        String merk = null;
        String harga = null;

        Barang barang = null;

        if(TextUtils.isEmpty(edNama.getText().toString().trim())
                && TextUtils.isEmpty(edHarga.getText().toString().trim())
                && TextUtils.isEmpty(edMerk.getText().toString().trim())
        ){

            edNama.setError("Mohon Isi Data");
            edHarga.setError("Mohon Isi Data");
            edMerk.setError("Mohon Isi Data");

        } else if(TextUtils.isEmpty(edNama.getText().toString().trim())) {
            edNama.setError("Mohon Isi Nama");

        } else if(TextUtils.isEmpty(edHarga.getText().toString().trim())) {
            edHarga.setError("Mohon Isi Harga");

        } else if(TextUtils.isEmpty(edMerk.getText().toString().trim())) {
            edMerk.setError("Mohon Isi Merk");

        } else {
            nama = edNama.getText().toString().trim();
            harga = edHarga.getText().toString().trim();
            merk = edMerk.getText().toString().trim();

            if (view.getId() == R.id.btn_simpan) {
                barang = dbSource.createBarang(nama, merk, harga);
                Toast.makeText(this, "Berhasil menambah "+nama, Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbSource.close();
    }
}
