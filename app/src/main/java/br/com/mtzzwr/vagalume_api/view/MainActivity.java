package br.com.mtzzwr.vagalume_api.view;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import br.com.mtzzwr.vagalume_api.R;
import br.com.mtzzwr.vagalume_api.fragments.ArtistaFragment;
import br.com.mtzzwr.vagalume_api.fragments.HomeFragment;
import br.com.mtzzwr.vagalume_api.fragments.LetrasFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView navigationView;
    ImageView gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.navigationView);
        gif = findViewById(R.id.gifHome);
        navigationView.setOnNavigationItemSelectedListener(this);

        Glide.with(this)
                .load("https://i.pinimg.com/originals/8e/aa/8f/8eaa8ffb622d3b229abc15d6879bc74c.gif")
                .into(gif);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_songs: {
                Fragment homeFragment = HomeFragment.newInstance();
                openFragment(homeFragment);
                break;
            }
            case R.id.navigation_albums: {
                Fragment letrasFragment = LetrasFragment.newInstance();
                openFragment(letrasFragment);
                break;
            }
            case R.id.navigation_artists: {
//                getSupportActionBar().setTitle("Artistas");
                Fragment artistasFragment = ArtistaFragment.newInstance();
                openFragment(artistasFragment);
                break;
            }
        }
        return true;
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
