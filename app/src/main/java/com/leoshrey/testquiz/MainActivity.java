package com.leoshrey.testquiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import me.ibrahimsn.lib.OnItemReselectedListener;
import me.ibrahimsn.lib.OnItemSelectedListener;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    com.leoshrey.testquiz.databinding.ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.leoshrey.testquiz.databinding.ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.content.getId(), new HomeFragment());
        transaction.commit();

        binding.bottombar.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public boolean onItemSelect(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                switch (i){
                    case 0:
                        transaction.replace(binding.content.getId(), new HomeFragment());
                        transaction.commit();
                        break;
                    case 1:
                        transaction.replace(binding.content.getId(), new LeaderboardFragment());
                        transaction.commit();
                        break;
                    case 2:
                        transaction.replace(binding.content.getId(), new ProfileFragment());
                        transaction.commit();
                        break;
                }
                return false;
            }
        });

    }
}