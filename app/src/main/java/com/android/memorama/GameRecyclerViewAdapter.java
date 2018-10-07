package com.android.memorama;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Handler;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by o.lopez.cienfuegos on 10/4/2018.
 */

public class GameRecyclerViewAdapter extends RecyclerView.Adapter<GameRecyclerViewAdapter.GameViewHolder> {

    List<Card> cardList, cardListCopy;
    Context context;
    Integer [] currentGame, imageCard;

    //-1 = hidden, 0 = shown, 1 = found
    Integer [] selected = {-1, -1, -1,
            -1, -1, -1,
            -1, -1, -1,
            -1, -1, -1};

    int cardsOpened, card1, card2, totalFound = 0;


    public GameRecyclerViewAdapter(){}

    public GameRecyclerViewAdapter(List<Card> cardList, Integer[] currentGame, Context context){
        //Las cartas "boca abajo"
        this.cardList = cardList;
        //El contexto de la actividad
        this.context = context;
        //Las cartas revueltas
        this.currentGame = currentGame;

        this.cardListCopy = new ArrayList<Card>();

        //imageCard = new Integer[16];
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
        holder.card.setImageResource(cardList.get(position).getImageSource());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Posicion " + position, Toast.LENGTH_SHORT).show();
                //Logica del memorama
                //"Abre" la tarjeta seleccionada
                if(selected[position] == -1) {
                    //updateData(position);
                    holder.card.setImageResource(currentGame[position]);
                    if (verifyTwoCardsSelected(position)) {
                        cardsOpened = 0;
                        if (!validateMatchingCards()) {
                            //undoLastTwoCards();

                            //Card card = new Card(R.drawable.card_back);
                            //Ultima carta seleccionada
                            //holder.card.setImageResource(R.drawable.card_back);

                            /*try{
                                Thread.sleep(1500);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }*/
                            notifyItemChanged(card1);
                            notifyItemChanged(card2);
                            selected[card1] = -1;
                            selected[card2] = -1;
                            card1 = card2 = 0;
                        } else {
                            selected[card1] = 1;
                            selected[card2] = 1;
                            card1 = card2 = 0;

                            if (totalFound == 6){
                                atGameEnd(v);

                            }
                        }
                    }
                }
            }
        });
    }

    private void updateData(int position){
        cardListCopy.clear();
        cardListCopy.addAll(cardList);
        cardList.clear();
        for(int i = 0; i < currentGame.length; i++){
            if(i != position) {
                cardList.add(cardListCopy.get(i));
            }else{
                Card card = new Card(currentGame[i]);
                cardList.add(card);
            }
        }
        notifyDataSetChanged();
    }

    private void undoLastTwoCards(){
        cardListCopy.clear();
        cardListCopy.addAll(cardList);
        cardList.clear();
        Card c = new Card(R.drawable.card_back);
        for(int i = 0; i < currentGame.length; i++){
            if(i == card1 || i == card2) {
                cardList.add(c);
            }else{
                cardList.add(cardListCopy.get(i));
            }
        }
        notifyDataSetChanged();
    }

    private boolean verifyTwoCardsSelected(int position){
        cardsOpened++;

        if(cardsOpened == 2 && position != card1){
            card2 = position;
            //notifyItemChanged(card1);
            //notifyItemChanged(card2);
            return true;
        }else if(cardsOpened == 1) {
            card1 = position;
            return false;
        }else{
            cardsOpened--;
            return false;
        }
    }

    private boolean validateMatchingCards(){
        if(currentGame[card1].intValue() == (currentGame[card2].intValue())){
            //cardsOpened = 0;
            totalFound++;
            return true;
        }else{
            //cardsOpened = 0;
            return false;
        }
    }

    public void atGameEnd(View v){
        /*final Dialog dialogs = new Dialog(context, R.style.AppTheme);
        dialogs.setContentView(R.layout.game_completed);
        dialogs.getWindow().getAttributes().windowAnimations = R.style.AppTheme;
        ImageButton replay = (ImageButton) dialogs.findViewById(R.id.replay);
        ImageButton back = (ImageButton) dialogs.findViewById(R.id.back);

        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
            }
        });
        dialogs.show();*/

        LayoutInflater inflater = (LayoutInflater)v.getContext().
                getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.game_completed, null);
        int width = RelativeLayout.LayoutParams.MATCH_PARENT;
        int height = RelativeLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        ImageButton replay = (ImageButton) popupView.findViewById(R.id.replay);
        ImageButton back = (ImageButton)popupView.findViewById(R.id.back);
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shuffleArray(currentGame);
                notifyDataSetChanged();
                for(int i = 0; i < selected.length; i++) {
                    selected[i] = -1;
                }
                popupWindow.dismiss();
                totalFound = 0;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).finish();
            }
        });
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
