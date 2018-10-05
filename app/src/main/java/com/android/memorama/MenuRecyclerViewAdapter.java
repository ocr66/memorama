package com.android.memorama;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 */

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.MenuViewHolder> {

    List<MemoryMenu> memoryMenus;
    private Context context;

    public MenuRecyclerViewAdapter(){}

    public MenuRecyclerViewAdapter(List<MemoryMenu> memoryMenus, Context context){
        this.memoryMenus = memoryMenus;
        this.context = context;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.memory_selection_layout,
                parent, false);
        MenuViewHolder menuViewHolder = new MenuViewHolder(v);
        return menuViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuViewHolder holder, final int position) {
        holder.name.setText(memoryMenus.get(position).name);
        holder.picture.setImageResource(memoryMenus.get(position).imgSource);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Game.class);
                i.putExtra("Selected", memoryMenus.get(position).getName().toString());
                holder.cardView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return memoryMenus.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView name;
        ImageView picture;

        public MenuViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.memoryCardView);
            name = (TextView)itemView.findViewById(R.id.memory_name);
            picture = (ImageView)itemView.findViewById(R.id.memory_icon);

        }


    }
}
