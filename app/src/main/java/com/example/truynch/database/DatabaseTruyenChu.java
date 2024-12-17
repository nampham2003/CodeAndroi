package com.example.truynch.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.truynch.model.TaiKhoan;
import com.example.truynch.model.Truyen;

public class DatabaseTruyenChu extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TruyenChu.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và cột
    private static final String TABLE_TAIKHOAN = "TaiKhoan";
    private static final String ID_TAI_KHOAN = "id";
    private static final String TEN_TAI_KHOAN = "tenTaiKhoan";
    private static final String MAT_KHAU = "matKhau";
    private static final String EMAIL = "email";
    private static final String PHAN_QUYEN = "phanQuyen";

    private static final String TABLE_TRUYEN = "truyen";
    private static final String ID_TRUYEN = "idtruyen";
    private static final String TEN_TRUYEN = "tieude";
    private static final String NOI_DUNGSOLUOC = "noidungsoluoc";
    private static final String IMAGE = "anh";
    private static final String TACGIA = "nguoiviet";
    private static final String TRANGTHAI = "trangthaihh";

    private static final String TABLE_CHAPTER = "sotap";
    private static final String CHUONG = "taptruyen";
    private static final String NOI_DUNGCHAPTER = "noidungchapter";

    public DatabaseTruyenChu(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLQueryTaiKhoan = "CREATE TABLE " + TABLE_TAIKHOAN + " (" +
                ID_TAI_KHOAN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TEN_TAI_KHOAN + " TEXT UNIQUE, " +
                MAT_KHAU + " TEXT, " +
                EMAIL + " TEXT, " +
                PHAN_QUYEN + " INTEGER)";

        String SQLQueryTruyen = "CREATE TABLE " + TABLE_TRUYEN + " (" +
                ID_TRUYEN + " TEXT PRIMARY KEY, " +
                TEN_TRUYEN + " TEXT, " +
                NOI_DUNGSOLUOC + " TEXT, " +
                TACGIA + " TEXT, " +
                IMAGE + " TEXT, " +
                TRANGTHAI + " TEXT, " +
                ID_TAI_KHOAN + " INTEGER, " +
                "FOREIGN KEY (" + ID_TAI_KHOAN + ") REFERENCES " + TABLE_TAIKHOAN + "(" + ID_TAI_KHOAN + "))";

        String SQLQueryChapter = "CREATE TABLE " + TABLE_CHAPTER + " (" +
                ID_TRUYEN + " TEXT, " +
                CHUONG + " TEXT, " +
                NOI_DUNGCHAPTER + " TEXT, " +
                "FOREIGN KEY (" + ID_TRUYEN + ") REFERENCES " + TABLE_TRUYEN + "(" + ID_TRUYEN + "))";

        db.execSQL(SQLQueryTaiKhoan);
        db.execSQL(SQLQueryTruyen);
        db.execSQL(SQLQueryChapter);

        // Thêm tài khoản mẫu
        db.execSQL("INSERT INTO " + TABLE_TAIKHOAN + " VALUES (null, 'admin', 'admin', 'admin@gmail.com', 2)");
        db.execSQL("INSERT INTO " + TABLE_TAIKHOAN + " VALUES (null, 'nam', '123', 'nam@gmail.com', 2)");

        // Thêm truyện mẫu
        db.execSQL("INSERT INTO " + TABLE_TRUYEN + " VALUES ('DLDL1', 'Đấu La đại lục 1', 'Đấu La Đại Lục là một bộ phim hoạt hình Trung Quốc, chuyển thể từ tiểu thuyết cùng tên của tác giả Đường Gia Tam Thiếu. Câu chuyện xoay quanh Đường Tam...','Đường Gia Tam Thiếu', 'https://yymedia.codeprime.net/media/ogimg/202109/ddbaa7fe74.jpg', 'Chưa Hoàn Thành', 1)");
        db.execSQL("INSERT INTO " + TABLE_TRUYEN + " VALUES ('TGHM', 'Thế Giới Hoàn Mỹ', 'Thế Giới Hoàn Mỹ là một bộ phim hoạt hình Trung Quốc, chuyển thể từ tiểu thuyết cùng tên của tác giả Thần Đông. Câu chuyện xoay quanh Thạch Hạo...','Thần Đông', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSYC2LoNxKnbmx8W-bmPBH1zI5t6IaUBEVXw&s', 'Chưa Hoàn Thành', 1)");

        // Thêm chương mẫu
        db.execSQL("INSERT INTO " + TABLE_CHAPTER + " VALUES ('DLDL1', '1', 'Ba Thục còn có mỹ danh Thiên Phủ Chi Quốc, trong đó nổi danh nhất chính là Đường Môn\n" +
                "\n" +
                "Đường Môn là một thần bí địa phương, rất nhiều người chỉ biết đó là một địa điểm giữa sườn núi, mà đỉnh núi nơi Đường Môn tọa lạc lại có một cái tên làm kẻ khác đảm chiến kinh tâm - Quỷ Kiến Sầu.\n" +
                "\n" +
                "Từ đỉnh Quỷ Kiến Sầu mà ném ra một hòn đá, phải đến 19 mới nghe được tiếng vang của hòn đá va chạm dưới chân núi, có thể thấy được núi cao thế nào, cũng bởi vì mười chín giây này, vượt qua mười tám tầng địa ngục một bậc, nên mới có cái tên này.\n" +
                "\n" +
                "Một gã hôi y (áo xám) thanh niên đang đứng trên đỉnh núi Quỷ Kiến Sầu, gió núi mãnh liệt không làm thân thể hắn di động chút nào, từ trên ngực hắn ngực có một chữ Đường lớn có thể nhận ra, hắn đến từ Đường Môn, áo xám đại biểu là Đường Môn ngoại môn đệ tử.\n" +
                "\n" +
                "Hắn năm nay hai mươi chín tuổi, xuất sanh không lâu thì tiến vào Đường Môn, trong ngoại môn bài danh đệ tam, bởi vậy ngoại môn đệ tử xưng hắn một tiếng Tam Thiếu. Đương nhiên, tới miệng nội môn đệ tử miệng, tựu biến thành Đường Tam.\n" +
                "\n" +
                "Đường Môn từ khi thành lập thì bắt đầu chia làm nội ngoại nhị môn, ngoại môn đệ tử đều là ngoại tính (họ khác) hoặc được thụ dư (ban cho) Đường tính (họ Đường), mà nội môn là Đường Môn trực hệ gia tộc truyền thừa.\n" +
                "\n" +
                "Lúc này, vẻ mặt Đường Tam rất phong phú, khi thì cười, khi thì khóc, nhưng vô luận thế nào, đều không thể che dấu hưng phấn phát ra từ nội tâm.\n" +
                "\n" +
                "Hai mươi chín năm, hai mươi chín năm trước hắn được ngoại môn trưởng lão Đường Lam thái gia nhặt về Đường Môn từ lúc còn nằm trong tã, Đường Môn chính là nhà hắn, mà Đường Môn ám khí chính là tất cả của hắn.\n')");
        db.execSQL("INSERT INTO " + TABLE_CHAPTER + " VALUES ('DLDL1', '2', 'Ở thế giới này. Thợ rèn có thể nói là một trong những nghề thấp hèn nhất. Bởi vì đặc thù nguyên nhân nào đó, thế giới này đỉnh cấp vũ khí cũng không phải do thợ rèn làm ra.\n" +
                "\n" +
                "Nhưng là, thợ rèn duy nhất trong thôn, vốn nhà Đường Tam không nghèo khó như vậy. nhưng là, về điểm này, thu nhập ít ỏi hầu hết đều......\n" +
                "\n" +
                "Vừa vào đến cửa, Đường Tam đã ngửi thấy được mùi cơm, đó cũng không phải là Đường Hạo vì hắn chuẩn bị, mà là hắn chuẩn bị vì Đường Hạo.\n" +
                "\n" +
                "Từ lúc mới bốn tuổi, Đường Tam thân cao vẫn chưa cao bằng táo thai (bệ bếp), nấu cơm cũng là nhiệm vụ của hắn mỗi ngày. Cho dù là phải đứng trên ghế mới có thể tới mặt bếp.\n" +
                "\n" +
                "Cũng không phải Đường Hạo yêu cầu hắn làm như vậy, mà là bởi vì không như vậy, Đường Tam cơ hồ không lúc nào có thể ăn no.')");
        db.execSQL("INSERT INTO " + TABLE_CHAPTER + " VALUES ('TGHM', '1', 'Đêm đã về khuya, bóng tối phủ kín khắp nơi, bao trùm lên cảnh vật. Thế nhưng trong núi lại chẳng hề yên tĩnh, tiếng mãnh thú rít gào rung động cả non sông, cây cối rung lên, lá bay tán loạn.\n" +
                "\n" +
                "Chốn rừng núi mênh mang là nơi hoạt động của vô số mãnh thú hồng hoang cùng những chủng tộc sót lại từ thời Thái Cổ. Tiếng kêu đáng sợ của muôn loài rống lên trong bóng tối, khiến mặt đất như muốn nứt toác ra.\n" +
                "\n" +
                "Từ trong dãy núi trông xa thấy có thấp thoáng một quầng sáng nhu hòa, tựa như một ngọn nến lập lòe dưới màn đêm đen vô tận, lẩn khuất giữa muôn trùng núi, ánh sáng dường như có thể vụt tắt bất cứ lúc nào. Bạn đang xem truyện được sao chép tại: TruyenFull.vn\n" +
                "\n" +
                "Đến gần hơn, có thể thấy rõ ở đó có một nửa thân cây khô khổng lồ, đường kính thân cây ước chừng hơn chục mét, toàn thân cháy đen, ngoại trừ một nửa thân cây này, chỉ còn lại một cành cây yếu ớt nhưng lại tỏa ra sức sống. Lá cây lung linh như được khắc từ lục ngọc, tán phát từng đốm sáng nhu hòa bao trùm lấy cả một thôn làng.\n" +
                "\n" +
                "Nói một cách chính xác thì đây là một thân cây sét đánh, rất nhiều năm về trước nó từng gặp phải một trận sét thông thiên, vòm cây um tùm cùng sức sống tràn trề của gốc liễu già đã bị sấm sét phá hủy. Nay chỉ còn lại một đoạn gốc cao chừng tám chín mét trồi lên mặt đất, đường kính rộng kinh người, cành liễu duy nhất sót lại kia trông như sợi xích thần bằng mây lục bích, hào quang ngập tràn bao trùm che chở cho cả thôn làng, khiến mảnh đất này trở nên mông lung như một vùng tiên thổ, trong chốn đại hoang cảm giác vô cùng thần bí.')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAPTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRUYEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAIKHOAN);
        onCreate(db);
    }

    public void addTaiKhoan(TaiKhoan taiKhoan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEN_TAI_KHOAN, taiKhoan.getmTenTaiKhoan());
        values.put(MAT_KHAU, taiKhoan.getmMatKhau());
        values.put(EMAIL, taiKhoan.getmEmail());
        values.put(PHAN_QUYEN, taiKhoan.getmPhanQuyen());
        db.insert(TABLE_TAIKHOAN, null, values);
        db.close();
    }

    public Cursor getDataTaiKhoan() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TAIKHOAN, null);
    }
    public TaiKhoan getAdminAccount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery("SELECT * FROM TaiKhoan WHERE PhanQuyen = 2", null); // Giả sử quyền admin là 1
        if (cursor != null && cursor.moveToFirst()) {
            TaiKhoan admin = new TaiKhoan(
                    cursor.getString(1),  // Tên tài khoản
                    cursor.getString(2),  // Email
                    cursor.getString(3),  // Mật khẩu
                    cursor.getInt(4)      // Quyền
            );
            cursor.close();
            return admin;
        }
        return null; // Nếu không có tài khoản admin
    }

    public boolean isTaiKhoanExist(String taiKhoan) {
        Cursor cursor = null;
        try {
            cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_TAIKHOAN + " WHERE " + TEN_TAI_KHOAN + " = ?", new String[]{taiKhoan});
            return cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
//    public void updateTaiKhoan(TaiKhoan taiKhoan) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(TEN_TAI_KHOAN, taiKhoan.getmTenTaiKhoan());
//        values.put(MAT_KHAU, taiKhoan.getmMatKhau());
//        values.put(EMAIL, taiKhoan.getmEmail());
//        values.put(PHAN_QUYEN, taiKhoan.getmPhanQuyen());
//        // Thực hiện cập nhật
//        db.update(TABLE_TAIKHOAN, values, "ID = ?", new String[]{String.valueOf(taiKhoan.getmId())});
//        db.close();
//    }
public boolean updateTaiKhoan(int id, String tenTaiKhoan, String matKhau, String email, int quyen) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(TEN_TAI_KHOAN, tenTaiKhoan);
    values.put(MAT_KHAU, matKhau);
    values.put(EMAIL, email);
    values.put(PHAN_QUYEN, quyen);

    long result = db.update(TABLE_TAIKHOAN, values, ID_TAI_KHOAN + " = ?", new String[]{String.valueOf(id)});
    return result != -1; // Trả về true nếu cập nhật thành công
}
    public Cursor getKhachById(String idKhach) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_TAIKHOAN +" WHERE "+ ID_TAI_KHOAN+ " = ?", new String[]{idKhach});

    }
    public Cursor getTKById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_TAIKHOAN, null, ID_TAI_KHOAN+" = ?", new String[]{String.valueOf(id)}, null, null, null);
    }

    public boolean updateKhach(int idKhach, String tenKhach, String matKhau, String email) {
        // Mở cơ sở dữ liệu
        SQLiteDatabase db = this.getWritableDatabase();

        // Tạo đối tượng ContentValues để lưu dữ liệu cập nhật
        ContentValues values = new ContentValues();
        values.put(TEN_TAI_KHOAN, tenKhach);
        values.put(MAT_KHAU, matKhau);
        values.put(EMAIL, email);

        // Cập nhật thông tin khách hàng trong cơ sở dữ liệu
        int rowsAffected = db.update(TABLE_TAIKHOAN, values, ID_TAI_KHOAN +" = ?", new String[]{String.valueOf(idKhach)});

        // Đóng cơ sở dữ liệu
        db.close();

        // Trả về true nếu có ít nhất một hàng bị ảnh hưởng (cập nhật thành công)
        return rowsAffected > 0;
    }
    public boolean updatetkadmin(int id, String tenTaiKhoan, String matKhau, String email, int phanQuyen) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEN_TAI_KHOAN, tenTaiKhoan);
        values.put(MAT_KHAU, matKhau);
        values.put(EMAIL, email);
        values.put(PHAN_QUYEN, phanQuyen);

        // Cập nhật hàng
        return db.update(TABLE_TAIKHOAN, values, ID_TAI_KHOAN + " = ?", new String[]{String.valueOf(id)}) > 0;
    }


    public void deleteTaiKhoan(int taiKhoanId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Xóa tài khoản
        int result = db.delete(TABLE_TAIKHOAN, ID_TAI_KHOAN + " = ?", new String[]{String.valueOf(taiKhoanId)});

        // Kiểm tra nếu xóa thành công
        if (result > 0) {
            Log.d("DeleteTaiKhoan", "Xóa thành công tài khoản có ID: " + taiKhoanId);
        } else {
            Log.d("DeleteTaiKhoan", "Xóa không thành công tài khoản có ID: " + taiKhoanId);
        }

        db.close();
    }
    public void deleteRelatedData(int taiKhoanId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Giả sử bạn có các bảng như "truyen", "chapter" và "taiKhoan"
        // Chỉ cần xóa tài khoản mà không cần xóa các liên quan khác
        db.execSQL("DELETE FROM taiKhoan WHERE id = ?", new Object[]{taiKhoanId});

        // Nếu có liên kết nhưng không cần xóa dữ liệu liên quan, chỉ cần làm trống chúng hoặc không thực hiện gì
        // Ví dụ, bạn có thể muốn làm sạch thông tin trong bảng "truyen" hoặc "chapter" mà không cần xóa hoàn toàn.
        // Thêm mã cho bất kỳ bảng nào bạn muốn xử lý tùy theo yêu cầu cụ thể của bạn.

        db.close(); // Đóng cơ sở dữ liệu
    }

    //Lấy tin mới nhất
    public Cursor getDataTruyen1(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_TRUYEN+" ORDER BY "+ID_TRUYEN+" DESC LIMIT 3" , null );
        return res;
    }
    //Lấy tất cả truyện
    public Cursor getDataTruyen() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TRUYEN, null);
    }



    public Cursor getTruyenById(String idTruyen) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TRUYEN + " WHERE " + ID_TRUYEN + " = ?";
        return db.rawQuery(query, new String[]{idTruyen});
    }

    public Cursor getChaptersById(String idTruyen) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CHAPTER + " WHERE " + ID_TRUYEN + " = ?";
        return db.rawQuery(query, new String[]{idTruyen});
    }

    public Cursor getChapterByIdAndTruyen(String truyenId, String chuongId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + TABLE_CHAPTER + "." + ID_TRUYEN + ", " +
                        TABLE_CHAPTER + "." + CHUONG + ", " +
                        TABLE_CHAPTER + "." + NOI_DUNGCHAPTER + " " +
                        "FROM " + TABLE_CHAPTER + " " +
                        "INNER JOIN " + TABLE_TRUYEN + " " +
                        "ON " + TABLE_TRUYEN + "." + ID_TRUYEN + " = " + TABLE_CHAPTER + "." + ID_TRUYEN + " " +
                        "WHERE " + TABLE_CHAPTER + "." + ID_TRUYEN + " = ? AND " +
                        TABLE_CHAPTER + "." + CHUONG + " = ?",
                new String[]{truyenId, chuongId});
    }

    // Thêm truyện
    public void addTruyen(Truyen truyen) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_TRUYEN,truyen.getIdTruyen());
        values.put(TEN_TRUYEN,truyen.getTenTruyen());
        values.put(NOI_DUNGSOLUOC,truyen.getNoidungsoluoc());
        values.put(TACGIA,truyen.getTacGia());
        values.put(IMAGE,truyen.getHinhAnh());
        values.put(TRANGTHAI,truyen.getTrangThai());
        db.insert(TABLE_TRUYEN, null, values);
        db.insert(TABLE_TRUYEN,null,values);
        db.close();
        Log.e("ADD TRUYEN","THANH CONG");
    }

    // Sửa truyện
    public void updateTruyen(String idTruyen, String tenTruyen, String noiDungSoluoc, String tacGia, String anh, String trangThai) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEN_TRUYEN, tenTruyen);
        values.put(NOI_DUNGSOLUOC, noiDungSoluoc);
        values.put(TACGIA, tacGia);
        values.put(IMAGE, anh);
        values.put(TRANGTHAI, trangThai);
        db.update(TABLE_TRUYEN, values, ID_TRUYEN + " = ?", new String[]{idTruyen});
        db.close();
    }

    // Xóa truyện
    public void deleteTruyen(String idTruyen) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRUYEN, ID_TRUYEN + " = ?", new String[]{idTruyen});
        db.delete(TABLE_CHAPTER, ID_TRUYEN + " = ?", new String[]{idTruyen}); // Xóa chương liên quan
        db.close();
    }

    //Thêm Chương
    public boolean addChapter(String idTruyen, String chuong, String noiDungChapter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_TRUYEN, idTruyen);
        values.put(CHUONG, chuong);
        values.put(NOI_DUNGCHAPTER, noiDungChapter);

        long result = db.insert(TABLE_CHAPTER, null, values);
        db.close();

        return result != -1; // Trả về true nếu thêm thành công
    }

    // Xóa chương
    public boolean deleteChapter(String idTruyen, String chuong) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_CHAPTER, ID_TRUYEN + " = ? AND " + CHUONG + " = ?", new String[]{idTruyen, chuong});
        db.close();
        return result > 0; // Trả về true nếu có ít nhất một dòng bị xóa
    }

    public boolean EditTruyen(String idTruyen, String tenTruyen, String noidung, String tacgia, String trangthai, String hinhanh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEN_TRUYEN, tenTruyen);
        values.put(NOI_DUNGSOLUOC, noidung);
        values.put(TACGIA, tacgia);
        values.put(IMAGE, hinhanh);
        values.put(TRANGTHAI, trangthai);

        // Cập nhật thông tin truyện trong database theo idtruyen
        long result = db.update("truyen", values, "idtruyen=?", new String[]{idTruyen}); // Sửa lại thành "idtruyen"

        // Kiểm tra nếu cập nhật thành công
        db.close();
        return result != -1;
    }

    public boolean updateChapter(String idTruyen, String chuong, String noiDungChapter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOI_DUNGCHAPTER, noiDungChapter); // New content for the chapter

        // Update chapter content based on idTruyen and chapter number
        int result = db.update(TABLE_CHAPTER, values, ID_TRUYEN + " = ? AND " + CHUONG + " = ?", new String[]{idTruyen, chuong});
        db.close();

        return result > 0; // Return true if update is successful
    }
}
