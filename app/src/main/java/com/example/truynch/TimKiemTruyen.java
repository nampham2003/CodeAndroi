package com.example.truynch;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truynch.adapter.TruyenAdapter;
import com.example.truynch.database.DatabaseTruyenChu;
import com.example.truynch.model.Truyen;

import java.util.ArrayList;

public class TimKiemTruyen extends AppCompatActivity {

    private EditText editTextSearch;
    private ListView listViewResults;
    private DatabaseTruyenChu databaseTruyenChu;
    private TruyenAdapter adapter; // Sử dụng adapter tùy chỉnh
    private ArrayList<Truyen> searchResults; // Danh sách truyện tìm kiếm
    private ArrayList<Truyen> truyens; // Danh sách tất cả truyện


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem_truyen);

        editTextSearch = findViewById(R.id.timkiem);
        listViewResults = findViewById(R.id.listviewtimkiem);
        databaseTruyenChu = new DatabaseTruyenChu(this);

        // Khởi tạo danh sách truyện

        loadTruyens();

        // Khởi tạo adapter cho ListView


        // Xử lý sự kiện tìm kiếm
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        // Xử lý sự kiện click vào một truyện
        listViewResults.setOnItemClickListener((adapterView, view, i, l)->{
            Truyen selectedTruyen = truyens.get(i);
            Intent intent = new Intent(TimKiemTruyen.this, MainActivity2.class);
            intent.putExtra("ID_TRUYEN", selectedTruyen.getIdTruyen());
            startActivity(intent);
        });
    }
    private  void filter(String text){
        searchResults.clear();
        ArrayList<Truyen> filteredList = new ArrayList<>();
        for(Truyen item: truyens){
            if(item.getTenTruyen().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
                searchResults.add(item);

            }
        }
        adapter.filterList(filteredList);
    }

    private void loadTruyens() {
        truyens = new ArrayList<>();
        searchResults = new ArrayList<>();
        Cursor cursor = databaseTruyenChu.getDataTruyen();
        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String tentruyen = cursor.getString(1);
            String noidungsoluoc = cursor.getString(2);
            String tacgia = cursor.getString(3);
            String trangthai = cursor.getString(5);
            String hinhanh = cursor.getString(4);
            int id_tk = cursor.getInt(6);

            truyens.add(new Truyen(id,tentruyen,noidungsoluoc,tacgia,trangthai,hinhanh,id_tk));
            adapter = new TruyenAdapter(getApplicationContext(),truyens);
            listViewResults.setAdapter(adapter);
            searchResults.add(new Truyen(id,tentruyen,noidungsoluoc,tacgia,hinhanh,trangthai,id_tk));
        }
        cursor.moveToFirst();
        cursor.close();
    }
}
