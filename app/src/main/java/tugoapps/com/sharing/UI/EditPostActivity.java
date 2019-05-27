package tugoapps.com.sharing.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import tugoapps.com.sharing.R;

public class EditPostActivity extends AppCompatActivity {

  EditText edit_title, edit_body;
  Button editButton_update;
  SharedPreferences pref;
  String title, body;
  int id;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_post);

    edit_title = findViewById(R.id.edit_title);
    edit_body = findViewById(R.id.edit_body);
    editButton_update = findViewById(R.id.editButton_update);

    pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
    id = pref.getInt("prefEditPostId", 0);
    title = pref.getString("prefEditPostTitle", "");
    body = pref.getString("prefEditPostBody", "");

    edit_title.setText(title);
    edit_body.setText(body);

    editButton_update.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        updatePost();
      }
    });
  }

  public void updatePost() {
    SharedPreferences.Editor editor = pref.edit();
    editor.putString("prefEditPostTitle", edit_title.getText().toString());
    editor.putString("prefEditPostBody", edit_body.getText().toString());
    editor.apply();
    startActivity(new Intent(EditPostActivity.this, PostsActivity.class));
    finish();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
  }

}
