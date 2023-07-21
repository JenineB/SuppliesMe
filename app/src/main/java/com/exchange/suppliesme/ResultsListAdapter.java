
package com.exchange.suppliesme;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResultsListAdapter extends RecyclerView.Adapter<ResultsListAdapter.ViewHolder> {

    String data[];
    Context context;


    public ResultsListAdapter(Context context, String[] data){
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ResultsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create a new view
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_user,parent,false);
        /*CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user, parent, false);*/
        //wrap it in a ViewHolder
        ViewHolder viewHolder = new ViewHolder(view);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ResultsListAdapter.ViewHolder holder, int position) {

        holder.textView1.setText(data[position]);
        /*viewHolder.textView1.setText(animalNames[position]);*/
    }

    @Override
    public int getItemCount() {
    return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView1;
        /*TextView textView2;*/

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.text1);
            /*textView2 = (TextView) card.findViewById(R.id.text2);*/
        }
    }
}

