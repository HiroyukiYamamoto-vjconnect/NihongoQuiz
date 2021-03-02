package com.vjconnect.nihongoquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView countLabel;
    private TextView questionLabel;
    private TextView alphabetLabel;
    private Button answerBtn1;
    private Button answerBtn2;
    private Button answerBtn3;
    private Button answerBtn4;

    private String rightAnswer;
    private int rightAnswerCount;
    private int quizCount = 1;
    static private final int QUIZ_COUNT = 10;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();
    private SoundPlayer soundPlayer = null;

    private InterstitialAd mInterstitialAd;

/*
    String[][] quizData = {
            // {"都道府県名", "正解", "選択肢1", "選択肢2", "選択肢3"}
            {"北海道", "札幌市", "長崎市", "福島市", "前橋市"},
            {"青森県", "青森市", "広島市", "甲府市", "岡山市"},
            {"岩手県", "盛岡市","大分市", "秋田市", "福岡市"},
            {"宮城県", "仙台市", "水戸市", "岐阜市", "福井市"},
            {"秋田県", "秋田市","横浜市", "鳥取市", "仙台市"},
            {"山形県", "山形市","青森市", "山口市", "奈良市"},
            {"福島県", "福島市", "盛岡市", "新宿区", "京都市"},
            {"茨城県", "水戸市", "金沢市", "名古屋市", "奈良市"},
            {"栃木県", "宇都宮市", "札幌市", "岡山市", "奈良市"},
            {"群馬県", "前橋市", "福岡市", "松江市", "福井市"},
    };
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundPlayer = new SoundPlayer(this);

        countLabel = findViewById(R.id.countLabel);
        questionLabel = findViewById(R.id.questionLabel);
        alphabetLabel = findViewById(R.id.alphabetLabel);
        answerBtn1 = findViewById(R.id.answerBtn1);
        answerBtn2 = findViewById(R.id.answerBtn2);
        answerBtn3 = findViewById(R.id.answerBtn3);
        answerBtn4 = findViewById(R.id.answerBtn4);

        // StartActivityからクイズカテゴリを取得
        int quizCategory = getIntent().getIntExtra("QUIZ_CATEGORY", 0);
        Log.v("QUIZ_CATEGORY", quizCategory + "");

        QuizDatabaseHelper dbHelper = new QuizDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String table = "quiz";
        String[] column = {"*"};// 全てのカラム
        String selection ="category = ?"; // どのカテゴリのクイズを取得するか
        String[] selectionArgs = {String.valueOf(quizCategory)}; // カテゴリセット
        String orderBy = "RANDOM()"; // ランダムに取得
        String limit = String.valueOf(QUIZ_COUNT); // 何問取得するか

        Cursor cursor = null;

        try {
            if (quizCategory != 0) {
                // カテゴリが「全て」以外の場合
                cursor = db.query(table, column, selection, selectionArgs,
                        null, null, orderBy, limit);
            } else {
                // カテゴリが「全て」の場合
                cursor = db.query(table, column, null, null, null, null, orderBy, limit);
            }
            // quizArrayを作成
            while (cursor.moveToNext()) {
                ArrayList<String> tmpArray = new ArrayList<>();
                tmpArray.add(cursor.getString(0)); // 日本語名
                tmpArray.add(cursor.getString(1)); // アルファベット
                tmpArray.add(cursor.getString(2)); // 正解
                tmpArray.add(cursor.getString(3)); // 選択肢1
                tmpArray.add(cursor.getString(4)); // 選択肢2
                tmpArray.add(cursor.getString(5)); // 選択肢3
                Log.v("Yamama", "cursor.getString(0)" + cursor.getString(0));
                Log.v("Yamama", "cursor.getString(1)" + cursor.getString(1));
                Log.v("Yamama", "cursor.getString(2)" + cursor.getString(2));
                Log.v("Yamama", "cursor.getString(3)" + cursor.getString(3));
                Log.v("Yamama", "cursor.getString(4)" + cursor.getString(4));
                Log.v("Yamama", "cursor.getString(5)" + cursor.getString(5));
                quizArray.add(tmpArray);
            }
        } finally {
            // Cursorとデータベースを閉じる
            cursor.close();
            db.close();
        }

/*
        for (int i = 0; i<quizData.length; i++) {
            // 新しいArrayListを作成
            ArrayList<String> tmpArray = new ArrayList<>();

            // クイズデータを追加
            tmpArray.add(quizData[i][0]);   // 都道府県名
            tmpArray.add(quizData[i][1]);   // 正解
            tmpArray.add(quizData[i][2]);   // 選択肢1
            tmpArray.add(quizData[i][3]);   // 選択肢2
            tmpArray.add(quizData[i][4]);   // 選択肢3

            // tmpArrayをquizArrayに追加する
            quizArray.add(tmpArray);
        }

 */
        // インタースティシャル広告
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                showResultActivity();
            }
        });

        showNextQuiz();
    }

    public void showNextQuiz() {

        // クイズカウントラベルを更新
        countLabel.setText(getString(R.string.count_label, quizCount));

        // ランダムな数字を取得
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        // randomNumを使って、quizArrayからクイズを一つ取り出す
        ArrayList<String> quiz = quizArray.get(randomNum);

        // 問題文(日本語)を表示
        questionLabel.setText(quiz.get(0));

        // 問題文のアルファベットを表示
        alphabetLabel.setText(quiz.get(1));

        // 正解をrightAnswerにセット
        rightAnswer = quiz.get(2);

        // クイズ配列から問題文(都道府県名)を削除
        quiz.remove(0);
        // クイズ配列からアルファベットを削除
        quiz.remove(0);

        // 正解と選択肢3つをシャッフル
        Collections.shuffle(quiz);

        // 解答ボタンに正解と選択肢3つを表示
        answerBtn1.setText(quiz.get(0));
        answerBtn2.setText(quiz.get(1));
        answerBtn3.setText(quiz.get(2));
        answerBtn4.setText(quiz.get(3));

        // このクイズをquizArrayから削除
        quizArray.remove(randomNum);
    }

    public void checkAnswer(View view) {

        // どの解答ボタンが押されたか
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        String alertTitle;
        if (btnText.equals(rightAnswer)) {
            alertTitle = "正解!";
            rightAnswerCount++;
            soundPlayer.playCorrectSound();
        } else {
            alertTitle = "不正解...";
            soundPlayer.playWrongSound();
        }

        // ダイアログオブジェクトを作成
        DialogFragment dialogFragment = new answerDialogFragment();

        // ダイアログに「正解・不正解」を渡す
        Bundle args = new Bundle();
        args.putString("alertTitle", alertTitle);
        args.putString("rightAnswer", rightAnswer);
        dialogFragment.setArguments(args);

        // ダイアログの表示
        dialogFragment.show(getSupportFragmentManager(), "answer_dialog");
    }

    // 「OK」ボタンを押した時に呼ばれるメソッド
    public void okBtnClicked() {
        if (quizCount == QUIZ_COUNT) {
            // 結果画面へ移動
            if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                showResultActivity();
            }
        } else {
            quizCount++;
            showNextQuiz();
        }
    }

    public void showResultActivity() {
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }
}