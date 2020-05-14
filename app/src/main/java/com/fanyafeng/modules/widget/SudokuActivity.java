package com.fanyafeng.modules.widget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fanyafeng.modules.BaseActivity;
import com.fanyafeng.modules.R;

public class SudokuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);
    }
}
