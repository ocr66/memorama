package com.android.memorama;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.nio.channels.GatheringByteChannel;
import java.util.List;

/**
 * Created by o.lopez.cienfuegos on 10/4/2018.
 */

public class GameRecyclerViewAdapter extends RecyclerView.Adapter<GameRecyclerViewAdapter.GameViewHolder> {

    List<Card> cardList;
    private Context context;
    Integer [] currentGame, imageCard;

    //-1 = hidden, 0 = shown, 1 = found
    Integer [] selected = {-1, -1, -1,
            -1, -1, -1,
            -1, -1, -1,
            -1, -1, -1};

    int cardsOpened, card1, card2 = 0;

    public GameRecyclerViewAdapter(){}

    public GameRecyclerViewAdapter(List<Card> cardList, Integer[] currentGame, Context context){
        this.cardList = cardList;
        this.context = context;
        this.currentGame = currentGame;

        imageCard = new Integer[16];
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        GameViewHolder gameViewHolder = new GameViewHolder(v);
        return gameViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final GameViewHolder holder, final int position) {
        holder.card.setImageResource(cardList.get(position).imageSource);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Posicion " + position, Toast.LENGTH_SHORT).show();
                //Logica del memorama
                //"Abre" la tarjeta seleccionada
                if(selected[position] == -1) {
                    holder.card.setImageResource(currentGame[position]);
                    //notifyItemChanged(position);
                /*try{
                    wait(2000);
                }catch (Exception e){

                }*/
                    //Array para revisar las tarjetas abiertas, 0 = abierta
                    //selected[position] = 0;
                    if (verifyTwoCardsSelected(position)) {
                        //Toast.makeText(context, "Dos cartas abiertas", Toast.LENGTH_SHORT).show();
                        //notifyItemChanged(position);
                        cardsOpened = 0;
                        if (!validateMatchingCards()) {
                            //Card card = new Card(R.drawable.card_back);
                            //Ultima carta seleccionada
                            //holder.card.setImageResource(R.drawable.card_back);
                            //cardList.set(card1, card);
                            //notifyDataSetChanged();
                            notifyItemChanged(card1);
                            notifyItemChanged(card2);
                            selected[card1] = -1;
                            selected[card2] = -1;
                            card1 = card2 = 0;
                        } else {
                            //Toast.makeText(context, "Encontradas!", Toast.LENGTH_SHORT).show();
                            selected[card1] = 1;
                            selected[card2] = 1;
                            card1 = card2 = 0;
                        }
                    }
                }
            }
        });
    }

    /*private boolean verifyTwoCardsSelected(int position){
        cardsOpened++;
        //if(position== 0 ){
        //    cardsOpened++;
        //}
        //if (position == 2 ){
        //    cardsOpened = 0;
        //    notifyDataSetChanged();
        //}
        notifyDataSetChanged();

        if(cardsOpened == 2){
            card2 = position;
            return true;
        }else if(cardsOpened == 1){
            card1 = position;
            return false;
        }
        return false;
    }*/

    private boolean verifyTwoCardsSelected(int position){
        cardsOpened++;

        if(cardsOpened == 2){
            card2 = position;
            //notifyItemChanged(card1);
            //notifyItemChanged(card2);
            return true;
        }else if(cardsOpened == 1) {
            card1 = position;
            return false;
        }
        return false;
    }

    private boolean validateMatchingCards(){
        if(currentGame[card1].intValue() == (currentGame[card2].intValue())){
            //cardsOpened = 0;
            return true;
        }else{
            //cardsOpened = 0;
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class GameViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView card;


        public GameViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.gameCardView);
            card = itemView.findViewById(R.id.card);
        }

        public void setCard(Integer imageSource){
            card.setImageResource(imageSource);
        }
    }

}
