package org.samir.simplecounter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CounterItemAdapter extends RecyclerView.Adapter<CounterItemAdapter.ViewHolder>{
    private ArrayList<CounterItem> counterItems = new ArrayList<>();
    private Context mContext;

    public interface UpdateMainUI{
        void setEmptyVisiblity(boolean b);
    }

    public CounterItemAdapter(Context mContext){
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_counter,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtCounterName.setText(counterItems.get(position).getName());
        String countString = "Count: " + counterItems.get(position).getCount();
        holder.txtCount.setText(countString);
        holder.btnDelete.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                    .setTitle("Delete")
                    .setMessage("This will delete: " + counterItems.get(position).getName())
                    .setNegativeButton("Cancel",null)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Utils.getInstance(mContext).deleteCounter(counterItems.get(position),CounterItemAdapter.this);
                        }
                    });
            builder.create().show();

        });
        holder.imgPlusButton.setOnClickListener(v->{
            if(counterItems.get(position).getCount() < Integer.MAX_VALUE){
                Utils.getInstance(mContext).incrementCount(counterItems.get(position),this);
            }
        });
        holder.imgMinusButton.setOnClickListener(v->{
            if(counterItems.get(position).getCount() > 0){
                Utils.getInstance(mContext).decrementCount(counterItems.get(position),this);
            }
        });
    }

    @Override
    public int getItemCount() {
        return counterItems.size();
    }

    public void setCounterItems(ArrayList<CounterItem> counterItems){
        this.counterItems = counterItems;
        MainActivity activity = (MainActivity) mContext;
        if(counterItems.size() > 0){
            activity.setEmptyVisiblity(false);
        }else{
            activity.setEmptyVisiblity(true);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtCounterName,txtCount;
        private Button btnDelete;
        private ImageView imgMinusButton,imgPlusButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCounterName = itemView.findViewById(R.id.txtCounterName);
            txtCount = itemView.findViewById(R.id.txtCount);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            imgMinusButton = itemView.findViewById(R.id.imgMinusButton);
            imgPlusButton = itemView.findViewById(R.id.imgPlusButton);
        }
    }
}
