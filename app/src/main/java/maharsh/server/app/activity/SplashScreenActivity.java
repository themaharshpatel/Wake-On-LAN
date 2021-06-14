package maharsh.server.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.Toast;

import maharsh.server.app.R;

public class SplashScreenActivity extends AppCompatActivity {
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar = findViewById(R.id.progressBar);

        new Handler(Looper.myLooper()).postDelayed(()->{

            Intent i = new Intent(getApplicationContext(),BaseFragmentActivity.class);
            startActivity(i);
            this.finish();
        },500);
    }
}