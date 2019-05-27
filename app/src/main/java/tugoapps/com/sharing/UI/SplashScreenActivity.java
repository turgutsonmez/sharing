package tugoapps.com.sharing.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tugoapps.com.sharing.R;

public class SplashScreenActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_screen);
    Intent intent = new Intent(this, UserActivity.class);
    startActivity(intent);
    finish();
  }
}
