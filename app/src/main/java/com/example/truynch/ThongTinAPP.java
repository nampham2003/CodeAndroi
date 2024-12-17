package com.example.truynch;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThongTinAPP extends AppCompatActivity {

    TextView txtThongTin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_tin_app);
        txtThongTin = findViewById(R.id.textviewthongtin);

        String thongtin = "Ứng dụng đuược phát hành bới nhóm 10 DC.CNTT.IT11 \n" +
                "Là một ứng dụng mới được phát triển qua môn học: 'Phát Triển Ứng Dụng cho Thiết Bị Di Động' \n" +
                "Mọi chi tiết hay góp ý có thể liên hệ với nhóm qua Gmail: buiquan9092003@gmail.com \n" +
                "Số Điện Thoại Liên Hệ: 0867993506 \n ";
        txtThongTin.setText(thongtin);
    }
}