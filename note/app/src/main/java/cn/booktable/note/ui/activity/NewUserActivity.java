package cn.booktable.note.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import cn.booktable.note.R;

public class NewUserActivity extends AppCompatActivity {

    public static final String EXTRA_RES_TITLE = "com.example.android.wordlistsql.title";
    public static final String EXTRA_RES_DETAIL = "com.example.android.wordlistsql.detail";

    private EditText mEditTitleView;
    private EditText mEditDetailView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        mEditTitleView = findViewById(R.id.edit_title);
        mEditDetailView=findViewById(R.id.edit_detail);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditTitleView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String title = mEditTitleView.getText().toString();
                    String detail = mEditDetailView.getText().toString();
                    replyIntent.putExtra(EXTRA_RES_TITLE, title);
                    replyIntent.putExtra(EXTRA_RES_DETAIL, detail);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}