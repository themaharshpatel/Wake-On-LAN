package maharsh.server.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import maharsh.server.app.R;

public class BaseFragmentActivity extends AppCompatActivity {

    NavController navController;
    MaterialToolbar toolbar;
    public static FloatingActionButton addFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);
        navController = NavHostFragment.findNavController((NavHostFragment) Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.fragment_container)));
        toolbar = findViewById(R.id.top_AppBar);
        addFab = findViewById(R.id.add_server_fab);

        setSupportActionBar(toolbar);
        NavigationUI.setupActionBarWithNavController(this,navController);

        new Handler(Looper.myLooper()).post(()->{
           navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
               if(destination.getId() == R.id.homeFragment)
               {
                   toolbar.getMenu().clear();
                   toolbar.inflateMenu(R.menu.profile_menu);
                   addFab.show();
               }
               else
               {
                   toolbar.getMenu().clear();
                   addFab.hide();
               }
           });
        });
        toolbar.setOnMenuItemClickListener(item ->{
            if(item.getItemId()==R.id.menu_profile_settings)
                navController.navigate(R.id.action_homeFragment_to_settingsFragment);
            return false;
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        View view = getCurrentFocus();
        if (view == null) view = new View(this);
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        return navController.navigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.profile_menu,menu);
        return true;

    }
}