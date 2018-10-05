package com.android.memorama;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by o.lopez.cienfuegos on 10/4/2018.
 */

public class Game extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private List<Card> cardList;
    Intent intent;
    String gameTopic;
    Integer [] currentGame = new Integer[12];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory_game);

        recyclerView = findViewById(R.id.gameRecyclerView);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        intent = getIntent();
        gameTopic = intent.getStringExtra("Selected");

        cardList = new ArrayList<Card>();

        agregarCartas(gameTopic);

        GameRecyclerViewAdapter gAdapter = new GameRecyclerViewAdapter(cardList, currentGame, getApplicationContext());
        recyclerView.setAdapter(gAdapter);

    }

    private void agregarCartas(String gameTopic){

        //Seleccionar las cartas a usar
        switch (gameTopic){
            case "Monsters":
                currentGame = addMonsters();
                break;
            case "Enredados":
                currentGame = addTangled();
                break;
            case "Frozen":
                currentGame = addFrozen();
                break;
        }

        //Mezclar las cartas
        shuffleArray(currentGame);

        //Agregar cartas a la lista
        for(int i = 0; i < currentGame.length; i++){
            Card card = new Card(R.drawable.card_back);
            cardList.add(card);
        }


    }

    private Integer[] addMonsters(){
        Integer [] cards = {
                R.drawable.sullivan,
                R.drawable.mike,
                R.drawable.boo,
                R.drawable.celia,
                R.drawable.randall,
                R.drawable.waternoose,
                R.drawable.sullivan,
                R.drawable.mike,
                R.drawable.boo,
                R.drawable.celia,
                R.drawable.randall,
                R.drawable.waternoose};
        return cards;
    }

    private Integer [] addTangled(){
        Integer [] cards = {
                R.drawable.rapunzel,
                R.drawable.flynn,
                R.drawable.maximus,
                R.drawable.gothel,
                R.drawable.pascal,
                R.drawable.stabbington,
                R.drawable.rapunzel,
                R.drawable.flynn,
                R.drawable.maximus,
                R.drawable.gothel,
                R.drawable.pascal,
                R.drawable.stabbington,
            };

        return cards;
    }

    private Integer[] addFrozen(){
        Integer [] cards = {
                R.drawable.anna,
                R.drawable.elsa,
                R.drawable.sven,
                R.drawable.olaf,
                R.drawable.kristoff,
                R.drawable.hans,
                R.drawable.anna,
                R.drawable.elsa,
                R.drawable.sven,
                R.drawable.olaf,
                R.drawable.kristoff,
                R.drawable.hans,
        };

        return cards;
    }

    public void shuffleArray(Integer[] array) {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            if (index != i)
            {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
    }
}
