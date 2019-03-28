package com.hon.conquer.ui.favorites;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hon.conquer.R;

/**
 * Created by Frank_Hon on 9/28/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class FavoritesActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        initFragments();
    }

    private void initFragments() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,new NewsFavoritesFragment())
                .commit();
    }
}
