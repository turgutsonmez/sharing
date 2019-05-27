package tugoapps.com.sharing.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import tugoapps.com.sharing.R;

public class NewPostActivity extends AppCompatActivity {

  EditText editText_title, editText_body;
  Button button_save;
  SharedPreferences pref;
  String userid;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_post);

    pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
    userid = pref.getString("prefUserId", "");
    editText_title = findViewById(R.id.editText_title);
    editText_body = findViewById(R.id.editText_body);
    button_save = findViewById(R.id.button_save);



    button_save.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        if (!TextUtils.isEmpty(editText_title.getText().toString()) && !TextUtils.isEmpty(editText_body.getText().toString())){
          SharedPreferences.Editor editor = pref.edit();
          editor.putString("prefPostTitle", editText_title.getText().toString());
          editor.putString("prefPostBody", editText_body.getText().toString());
          editor.apply();
          startActivity(new Intent(NewPostActivity.this, PostsActivity.class));
          finish();
        }
      }
    });
  }


  @Override
  public void onBackPressed() {
    super.onBackPressed();
    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
  }
}

