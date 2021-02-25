package com.vjconnect.nihongoquiz;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class QuizDatabaseHelper extends SQLiteOpenHelper {
    static final private String DBNAME = "quizapp.sqlite";
    static final private int VERSION = 001;

    String[][] quizData = {
            // {"日本語", "正解", "選択肢１", "選択肢２", "選択肢３", "カテゴリー"}
            {"朝", "asa","Buổi sáng","Bạn bè","Nghỉ","Tình yêu","1"},
            {"家", "ie","Nhà","Điện","Tuần sau","Tình yêu, tình thương","1"},
            {"エアコン", "eakon","Máy điều hòa","Bà","Nghỉ","Ý tưởng","1"},
            {"お母さん", "okasan","Mẹ","Tuần trước","Anh trai","Bắt tay","1"},
            {"外国", "gaikoku","Nước ngoài","Tai nạn, sự cố","Con chó","Truy cập","1"},
            {"季節", "kisetsu","Mùa","Buổi chiều","Sinh viên, học sinh","Trọng âm","1"},
            {"空港", "kuko","Sân bay","Giày","Lớp học","Khắp nơi, đây đó ","1"},
            {"元気", "genki","Khoẻ mạnh","Lớp học","Giày","Thu thập","1"},
            {"高校", "koko","Trường trung học phổ thông","Sinh viên, học sinh","Buổi chiều","Địa chỉ","1"},
            {"魚", "sakana","Cá","Tiền bạc","Tai nạn, sự cố","Dọn dẹp","1"},
            {"時間", "jikan","Thời gian","Ga tàu, nhà ga","Tuần trước","Lời khuyên","1"},
            {"スーパー", "supa","Siêu thị","Con chó","Bà","Hố, hang, lỗ hỏng","1"},
            {"席", "seki","Ghế","Anh trai","Điện","Phần dư, phần còn lại","1"},
            {"外", "soto","Ở ngoài","Bố mẹ","Bạn bè","Bảng câu hỏi","1"},
            {"大学", "daigaku","Trường đại học","Tuần sau","Buổi sáng","Tinh nghịch, nghịch ngợm","1"},
            {"お父さん", "otosan","Bố","Nghỉ","Nhà","Việc nhà, việc nội trợ","1"},
            {"テレビ", "televi","Ti vi","E-mail","Máy điều hòa","Cho vay","1"},
            {"メール", "meru","E-mail","Ti vi","Mẹ","Con số","1"},
            {"休み", "yasumi","Nghỉ","Bố","Nước ngoài","Một hướng, một bên","1"},
            {"来週", "raishu","Tuần sau","Bố","Mùa","Danh mục, Catalog","1"},
            {"両親", "ryosin","Bố mẹ","Trường đại học","Sân bay","Giá trị","1"},
            {"兄", "ani","Anh trai","Ở ngoài","Khoẻ mạnh","Cắt","1"},
            {"犬", "inu","Con chó","Ghế","Trường trung học phổ thông","Hoạt động","1"},
            {"駅", "eki","Ga tàu, nhà ga","Siêu thị","Cá","Nhẫn nhịn, chịu đựng","1"},
            {"お金", "okane","Tiền bạc","Thời gian","Chúa Trời","Tình yêu","1"},
            {"学生", "gakusei","Sinh viên, học sinh","Bầu trời","Ý tưởng","Bắt tay","1"},
            {"教室", "kyoshitsu","Lớp học","Trường trung học phổ thông","Truy cập","Địa chỉ","1"},
            {"靴", "kutsu","Giày","Khoẻ mạnh","Thời gian","Chào mừng, nghênh đón","1"},
            {"午後", "gogo","Buổi chiều","Sân bay","Siêu thị","Du lịch","1"},
            {"事故", "jiko","Tai nạn, sự cố","Mùa","Ghế","Bạn bè","1"},
            {"先週", "sensyu","Tuần trước","Nước ngoài","Ở ngoài","Điện","1"},
            {"祖母", "sobo","Bà","Mẹ","Trường đại học","Tai nạn, sự cố", "1"},
            {"電気", "denki","Điện","Máy điều hòa","Bố","Giày","1"},
            {"友達", "tomodachi","Bạn bè","Nhà","Ti vi","Anh trai","1"},


            /*
            {"北海道", "札幌市", "長崎市", "福島市", "前橋市", "1"},
            {"青森県", "青森市", "広島市", "甲府市", "岡山市", "1"},
            {"岩手県", "盛岡市","大分市", "秋田市", "福岡市", "1"},
            {"宮城県", "仙台市", "水戸市", "岐阜市", "福井市", "1"},
            {"秋田県", "秋田市","横浜市", "鳥取市", "仙台市", "1"},
            {"山形県", "山形市","青森市", "山口市", "奈良市", "1"},
            {"福島県", "福島市", "盛岡市", "新宿区", "京都市", "1"},
            {"茨城県", "水戸市", "金沢市", "名古屋市", "奈良市", "2"},
            {"栃木県", "宇都宮市", "札幌市", "岡山市", "奈良市", "2"},
            {"群馬県", "前橋市", "福岡市", "松江市", "福井市", "2"},
            {"埼玉県", "さいたま市", "新潟市", "盛岡市", "水戸市", "2"},
            {"千葉県", "千葉市", "さいたま市", "横浜市", "松山市", "2"},
            {"東京都", "新宿区", "札幌市", "千葉市", "福島市", "2"},
            {"神奈川県", "横浜市", "宮崎市", "水戸市", "山口市", "2"},
            {"新潟県", "新潟市", "福島市", "前橋市", "大分市", "3"},
            {"富山県", "富山市", "山形市", "神戸市", "宇都宮市", "3"},
            {"石川県", "金沢市", "富山市", "熊本市", "山口市", "3"},
            {"福井県", "福井市", "長野市", "水戸市", "名古屋市", "3"},
            {"山梨県", "甲府市", "新潟市", "秋田市", "盛岡市", "3"},
            {"長野県", "長野市", "和歌山市", "那覇市", "山形市", "3"},
            {"岐阜県", "岐阜市", "千葉市", "京都市", "福井市", "3"},
            {"静岡県", "静岡市", "佐賀市", "宮崎市", "長崎市", "3"},
            {"愛知県", "名古屋市", "山口市", "秋田市", "長野市", "3"},
            {"三重県", "津市", "新宿区", "水戸市", "仙台市", "4"},
            {"滋賀県", "大津市", "鹿児島市", "福井市", "秋田市", "4"},
            {"京都府", "京都市", "甲府市", "大津市", "鹿児島市", "4"},
            {"大阪府", "大阪市", "さいたま市", "奈良市", "福島市", "4"},
            {"兵庫県", "神戸市", "宇都宮市", "大分市", "那覇市", "4"},
            {"奈良県", "奈良市", "甲府市", "千葉市", "広島市", "4"},
            {"和歌山県", "和歌山市", "盛岡市", "岐阜市", "金沢市", "4"},
            {"鳥取県", "鳥取市", "福井市", "那覇市", "徳島市", "5"},
            {"島根県", "松江市", "水戸市", "福島市", "大分市", "5"},
            {"岡山県", "岡山市", "大分市", "岐阜市", "鹿児島市", "5"},
            {"広島県", "広島市", "さいたま市", "長野市", "宮崎市", "5"},
            {"山口県", "山口市", "高知市", "大阪市", "水戸市", "5"},
            {"徳島県", "徳島市", "宇都宮市", "金沢市", "広島市", "5"},
            {"香川県", "高松市","富山市", "名古屋市", "鳥取市", "5"},
            {"愛媛県", "松山市", "山形市", "高松市", "奈良市", "5"},
            {"高知県", "高知市", "富山市", "松江市", "札幌市", "5"},
            {"福岡県", "福岡市", "高松市", "岡山市", "前橋市", "6"},
            {"佐賀県", "佐賀市", "秋田市", "水戸市", "富山市", "6"},
            {"長崎県", "長崎市", "松山市", "青森市", "大津市", "6"},
            {"熊本県", "熊本市", "名古屋市", "富山市", "千葉市", "6"},
            {"大分県", "大分市", "鳥取市", "津市", "甲府市", "6"},
            {"宮崎県", "宮崎市", "札幌市", "新潟市", "奈良市", "6"},
            {"鹿児島県", "鹿児島県", "和歌山市", "神戸市", "名古屋市", "6"},
            {"沖縄県", "那覇市", "熊本市", "高知市", "大津市", "6"}

             */
    };


    public QuizDatabaseHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // テーブルを作成
        db.execSQL("CREATE TABLE quiz (" +
                "question TEXT PRIMARY KEY, alphabet TXT, answer TXT, choice1 TEXT," +
                "choice2 TXT, choice3 TXT, category TXT)");

        // トランザクション開始
        db.beginTransaction();
        try {
            // SQLを準備
            SQLiteStatement sql = db.compileStatement(
                    "INSERT INTO quiz (question, alphabet, answer, choice1, choice2, choice3, category)" +
                            "VALUES(?, ?, ?, ?, ?, ?, ?)"
            );

            // クイズにデータを一行ずつ追加する
            for (int i=0; i < quizData.length; i++) {
                // Valueをセット
                // bindString(index, value)
                sql.bindString(1, quizData[i][0]); // 日本語
                sql.bindString(2, quizData[i][1]); // アルファベット
                sql.bindString(3, quizData[i][2]); // 正解
                sql.bindString(4, quizData[i][3]); // 選択肢１
                sql.bindString(5, quizData[i][4]); // 選択肢２
                sql.bindString(6, quizData[i][5]); // 選択肢３
                sql.bindString(7, quizData[i][6]); // カテゴリ

                sql.executeInsert();
            }

            // 成功
            db.setTransactionSuccessful();

        } catch (SQLException e) {
            // 失敗
            e.printStackTrace();

        } finally {
            // トランザクション終了
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS quiz");
            onCreate(db);
        }
    }
}
