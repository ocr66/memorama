package com.android.memorama;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List <MemoryMenu> memoryMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.menuRecyclerView);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        memoryMenu = new ArrayList<MemoryMenu>();

        agregarItemsMenu();

        MenuRecyclerViewAdapter menuRecyclerViewAdapter =
                new MenuRecyclerViewAdapter(memoryMenu, getApplicationContext());
        recyclerView.setAdapter(menuRecyclerViewAdapter);

    }

    private void agregarItemsMenu(){
        MemoryMenu mMonsters = new MemoryMenu();
        MemoryMenu mFrozen = new MemoryMenu();
        MemoryMenu mTangled = new MemoryMenu();

        mMonsters.setName(getString(R.string.monsters));
        mMonsters.setImgSource(R.drawable.monsters_menu);
        memoryMenu.add(mMonsters);

        mTangled.setName(getString(R.string.tangled));
        mTangled.setImgSource(R.drawable.enredados_menu);
        memoryMenu.add(mTangled);

        mFrozen.setName(getString(R.string.frozen));
        mFrozen.setImgSource(R.drawable.frozen_menu);
        memoryMenu.add(mFrozen);

    }
}
