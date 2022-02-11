package com.atn.remoteapiandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton = findViewById(R.id.apiMenu);
        floatingActionButton.setOnClickListener(v -> loadMenuDialog());

        replaceFragment(new ListFragment()); //default view
    }

    private void loadMenuDialog() {
        alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setCancelable(true);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.api_menu_dialog, null);
        view.findViewById(R.id.listLayout).setOnClickListener(v -> replaceFragment(new ListFragment()));
        view.findViewById(R.id.singleDataLV).setOnClickListener(v -> new ViewFragment());
        view.findViewById(R.id.insertDataLV).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("result", "insert");
            ViewFragment fragment = new ViewFragment();
            fragment.setArguments(bundle);
            replaceFragment(fragment);
        });
        view.findViewById(R.id.updateDataLV).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("result", "update");
            ViewFragment fragment = new ViewFragment();
            fragment.setArguments(bundle);
            replaceFragment(fragment);
        });
        view.findViewById(R.id.deleteDataLV).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("result", "delete");
            ViewFragment fragment = new ViewFragment();
            fragment.setArguments(bundle);
            replaceFragment(fragment);
        });
        alertDialog.setCancelable(true);
        alertDialog.setView(view);
        alertDialog.show();

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.frame_layout, fragment);
        ft.commit();
    }

}