package tugoapps.com.sharing.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tugoapps.com.sharing.R;

public class PostAndAlbumActivity extends AppCompatActivity {

  String userid;
  TextView text_userid;
  SharedPreferences pref;
  Button post, album;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_post_and_album);
    post = findViewById(R.id.button_post);
    album = findViewById(R.id.button_album);
    pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

    userid = pref.getString("prefUserId", "");
    text_userid = findViewById(R.id.text_userid);

    text_userid.setText("UserID: " + userid);


    post.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent=new Intent(PostAndAlbumActivity.this,PostsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left );
      }
    });

    album.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent=new Intent(PostAndAlbumActivity.this,AlbumsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left );
      }
    });

  }

}
