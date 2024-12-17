package com.example.truynch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.truynch.database.DatabaseTruyenChu;
import com.example.truynch.model.Truyen;

public class Main_ThemTruyen extends AppCompatActivity {
    EditText editid,editten,editnoidung,edittacgia,editanh,edittrangthai;
    Button btnaddtruyen;
    DatabaseTruyenChu databaseTruyenChu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_them_truyen);


        editid = findViewById(R.id.edID);
        editten = findViewById(R.id.edTentruyen);
        editnoidung = findViewById(R.id.edNoidungsoluoc);
        edittacgia = findViewById(R.id.edTacgia);
        editanh = findViewById(R.id.edAnh);
        edittrangthai = findViewById(R.id.edTrangthai);
        btnaddtruyen = findViewById(R.id.btnadd_them);

        databaseTruyenChu = new DatabaseTruyenChu(this);

        btnaddtruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editid.getText().toString();
                String tentruyen = editten.getText().toString();
                String noidung = editnoidung.getText().toString();
                String tacgia = edittacgia.getText().toString();
                String trangthai = edittrangthai.getText().toString();
                String anh = editanh.getText().toString();

                Truyen truyen = CreatTruyen();

                if(tentruyen.equals("")||noidung.equals("")||tacgia.equals("")||anh.equals("")||trangthai.equals("")){
                    Toast.makeText(Main_ThemTruyen.this, "ban chua nhap day du thong tin truyen", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseTruyenChu.addTruyen(truyen);

                    Intent intent = new Intent(Main_ThemTruyen.this,Main_DangBai.class);
                    setResult(RESULT_OK);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }
    private Truyen CreatTruyen() {
        String id = editid.getText().toString();
        String tentruyen = editten.getText().toString();
        String noidung = editnoidung.getText().toString();
        String tacgia = edittacgia.getText().toString();
        String trangthai = edittrangthai.getText().toString();
        String anh = editanh.getText().toString();

        Intent intent = getIntent();
        int idtk = intent.getIntExtra("Id",0);
        Truyen truyen = new Truyen(id,tentruyen,noidung,tacgia,trangthai,anh,idtk);
        return truyen;
    }
}