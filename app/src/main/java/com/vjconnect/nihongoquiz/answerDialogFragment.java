package com.vjconnect.nihongoquiz;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

public class answerDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // MainActivityから値を受け取る
        String alertTitle = Objects.requireNonNull(getArguments().getString("alertTitle"));
        String rightAnswer = getArguments().getString("rightAnswer");

        // ダイアログを作成
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Objects.requireNonNull(getActivity()));

        // ダイアログの設定
        builder.setTitle(alertTitle)
                .setMessage("答え:" + rightAnswer)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity) Objects.requireNonNull(getActivity())).okBtnClicked();
                    }
                });

        // ダイアログを作成
        Dialog dialog = builder.create();

        // 「次の問題」ボタンを押す以外ではダイアログを閉じないようにする
        setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }
}
