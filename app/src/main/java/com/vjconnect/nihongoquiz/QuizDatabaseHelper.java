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
            // {"都道府県名", "正解", "選択肢１", "選択肢２", "選択肢３", "カテゴリー"}
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
    };


    public QuizDatabaseHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // テーブルを作成
        db.execSQL("CREATE TABLE quiz (" +
                "question TEXT PRIMARY KEY, answer TXT, choice1 TEXT," +
                "choice2 TXT, choice3 TXT, category TXT)");

        // トランザクション開始
        db.beginTransaction();
        try {
            // SQLを準備
            SQLiteStatement sql = db.compileStatement(
                    "INSERT INTO quiz (question, answer, choice1, choice2, choice3, category)" +
                            "VALUES(?, ?, ?, ?, ?, ?)"
            );

            // クイズにデータを一行ずつ追加する
            for (int i=0; i < quizData.length; i++) {
                // Valueをセット
                // bindString(index, value)
                sql.bindString(1, quizData[i][0]); // 都道府県名
                sql.bindString(2, quizData[i][1]); // 正解
                sql.bindString(3, quizData[i][2]); // 選択肢１
                sql.bindString(4, quizData[i][3]); // 選択肢２
                sql.bindString(5, quizData[i][4]); // 選択肢３
                sql.bindString(6, quizData[i][5]); // カテゴリ

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
