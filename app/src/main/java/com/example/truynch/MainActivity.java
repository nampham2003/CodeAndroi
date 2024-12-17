package com.example.truynch;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.truynch.adapter.AdapterThongTin;
import com.example.truynch.adapter.TruyenAdapter;
import com.example.truynch.database.DatabaseTruyenChu;
import com.example.truynch.model.TaiKhoan;
import com.example.truynch.model.Truyen;
import com.example.truynch.model.chuyenmuc;
import com.example.truynch.adapter.adapterchuyenmuc;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_MAINADMIN = 1;
    private static final int REQUEST_CODE_EDIT = 1;

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    ListView listView, listviewThongtin;
    DrawerLayout drawerLayout;

    String email;
    String tentaikhoan;

    ArrayList<chuyenmuc> chuyenmucArrayList;
    ArrayList<TaiKhoan> taiKhoanArrayList;

    AdapterThongTin AdapterThongTin;
    adapterchuyenmuc adapterchuyenmuc;

    private ListView listViewNew;
    private ArrayList<Truyen> truyenList;
    private TruyenAdapter truyenAdapter;
    private DatabaseTruyenChu databaseTruyenChu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewNew = findViewById(R.id.listviewNew);
        truyenList = new ArrayList<>();
        databaseTruyenChu = new DatabaseTruyenChu(this);

        Intent intentpq = getIntent();
        int i = intentpq.getIntExtra("phanq", 0);  // Quyền
        int idd = intentpq.getIntExtra("idd", 0);  // ID người dùng
        email = intentpq.getStringExtra("email");
        tentaikhoan = intentpq.getStringExtra("tentaikhoan");

        // Lấy dữ liệu từ database
        loadData();
        AnhXa();

        // Thiết lập Action Bar
        ActionBar();
        // Thiết lập ViewFlipper
        ActionViewFlipper();

        // Thiết lập adapter cho danh sách truyện
        truyenAdapter = new TruyenAdapter(this, truyenList);
        listViewNew.setAdapter(truyenAdapter);

        // Xử lý sự kiện click vào từng mục trong danh sách truyện
        listViewNew.setOnItemClickListener((adapterView, view, position, l) -> {
            Truyen selectedTruyen = truyenList.get(position);
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("ID_TRUYEN", selectedTruyen.getIdTruyen());
            startActivity(intent);
        });

        // Khởi tạo adapter cho chuyên mục
        adapterchuyenmuc = new adapterchuyenmuc(this, R.layout.chuyen_muc, chuyenmucArrayList);
        listView.setAdapter(adapterchuyenmuc);

// Xử lý khi click vào item trong ListView chuyên mục
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Log.d("MainActivity", "Clicked item position in categories: " + position); // Log vị trí click
            switch (position) {
                case 0:  // Đăng Bài Mới
                    if (i == 2) {
                        Intent intent = new Intent(MainActivity.this, Main_DangBai.class);
                        intent.putExtra("Id", idd);
                        startActivityForResult(intent,REQUEST_CODE_MAINADMIN);
                    } else {
                        Toast.makeText(MainActivity.this, "Bạn không có quyền", Toast.LENGTH_SHORT).show();
                        Log.e("MainActivity", "Bạn không có quyền đăng bài mới");
                    }
                    break;
                case 1:  // Thông Tin App
                    Intent intentThongTin = new Intent(MainActivity.this, ThongTinAPP.class);
                    startActivity(intentThongTin);
                    break;
                case 2:  // Đăng Xuất
                    finish();  // Thoát ứng dụng
                    break;
                default:
                    Log.d("MainActivity", "Unknown position: " + position);
                    break;
            }
        });
        // Thêm vào onCreate
        listviewThongtin.setOnItemClickListener((parent, view, position, id) -> {
            // Kiểm tra phân quyền
            if (i == 2) {  // Giả sử i = 2 là phân quyền admin
                Intent intentAdmin = new Intent(MainActivity.this, TK_Admin.class);
                intentAdmin.putExtra("Id", idd);
                startActivityForResult(intentAdmin, REQUEST_CODE_MAINADMIN);
            } else {  // Nếu không phải admin, chuyển đến TK_Khach
                Intent intentKhach = new Intent(MainActivity.this, TK_Khach.class);
                intentKhach.putExtra("Id", idd);
                startActivityForResult(intentKhach, REQUEST_CODE_MAINADMIN);
            }
        });

    }

    // Tạo Action Bar với toolbar
    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
    }

    // Thiết lập ViewFlipper để hiển thị quảng cáo
    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://cdn.9pay.vn/tin-tuc/blobid1691048380301-1691048393.jpg");
        mangquangcao.add("https://cdn2.fptshop.com.vn/unsafe/1920x0/filters:quality(100)/2024_1_12_638406604133075424_hoat-hinh-trung-quoc.jpg");

        for (String url : mangquangcao) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(url).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }

        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);

        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);

        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        navigationView = findViewById(R.id.navigationview);
        listView = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerlayout);
        listviewThongtin = findViewById(R.id.listviewThongTin);

        // Thông tin tài khoản
        taiKhoanArrayList = new ArrayList<>();
        taiKhoanArrayList.add(new TaiKhoan(tentaikhoan, email));
        AdapterThongTin = new AdapterThongTin(this, R.layout.nav_thongtinkh, taiKhoanArrayList);
        listviewThongtin.setAdapter(AdapterThongTin);

        // Chuyên mục
        chuyenmucArrayList = new ArrayList<>();
        chuyenmucArrayList.add(new chuyenmuc("Đăng Bài Mới", R.drawable.ic_post));
        chuyenmucArrayList.add(new chuyenmuc("Thông tin", R.drawable.ic_face));
        chuyenmucArrayList.add(new chuyenmuc("Đăng xuất", R.drawable.ic_login));
    }

    // Tạo menu tìm kiếm trên action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    // Xử lý sự kiện khi chọn mục trong menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu1) {
            Intent intent = new Intent(this, TimKiemTruyen.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Load dữ liệu từ database
    private void loadData() {
        Cursor cursor = databaseTruyenChu.getDataTruyen();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Truyen truyen = new Truyen(
                        cursor.getString(0),  // ID
                        cursor.getString(1),  // Tên Truyện
                        cursor.getString(2), // noi dung
                        cursor.getString(3),  // Tác Giả
                        cursor.getString(5),  // Trạng Thái
                        cursor.getString(4),// anh
                        cursor.getInt(6) // id_tk
                );
                truyenList.add(truyen);
                Log.d("MainActivity", "Loaded Truyen: " + truyen.getTenTruyen()); // Log để kiểm tra dữ liệu
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_MAINADMIN && resultCode == RESULT_OK) {
            // Gọi hàm để cập nhật dữ liệu
            reloadData(); // Tải lại dữ liệu ở MainActivity
        }
        else if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            // Gọi hàm để cập nhật dữ liệu
            reloadData(); // Tải lại dữ liệu ở MainActivity
        }

    }

    private void reloadData() {
        truyenList.clear();

        // Lấy dữ liệu từ database
        Cursor cursor1 = databaseTruyenChu.getDataTruyen();
        while (cursor1.moveToNext()) {
            String id = cursor1.getString(0);
            String tentruyen = cursor1.getString(1);
            String noidungsoluoc = cursor1.getString(2);
            String tacgia = cursor1.getString(3);
            String anh = cursor1.getString(5);
            String trangthai = cursor1.getString(4);
            int id_tk = cursor1.getInt(6);

            // Thêm truyện vào danh sách
            truyenList.add(new Truyen(id, tentruyen, noidungsoluoc, tacgia, anh, trangthai, id_tk));
        }

        // Đóng cursor sau khi sử dụng
        cursor1.close();

        // Cập nhật giao diện
        truyenAdapter.notifyDataSetChanged();
    }
}
