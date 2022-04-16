package edu.bluejack21_2.guk.adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.bluejack21_2.guk.R;
import edu.bluejack21_2.guk.model.History;

public abstract class HistoryAdapter<T extends History> extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private Context context;
    private ArrayList<T> list;
    protected boolean isUser = false;

    public HistoryAdapter(Context context, ArrayList<T> list) {
        this.context = context;
        this.list = list;
    }

    public HistoryAdapter(Context context, ArrayList<T> list, boolean isUser) {
        this.context = context;
        this.list = list;
        this.isUser = isUser;
    }

    @NonNull
    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_template,parent,false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {
        T curr = list.get(position);

        setDescriptionText(curr, holder.descriptionTxt);

        if(!isUser){
            holder.viewProofBtn.setOnClickListener(view -> {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                showViewDialog(curr, context, dialog);
            });
            if(curr.getStatus() == 0){
                holder.approveBtn.setOnClickListener(view -> {
                    changeStatus(context, curr, true);
                });
                holder.rejectBtn.setOnClickListener(view -> {
                    changeStatus(context, curr, false);
                });
            } else {
                holder.approveBtn.setVisibility(View.GONE);
                holder.rejectBtn.setVisibility(View.GONE);

                int marginInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, context.getResources().getDisplayMetrics());

                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.leftTxt.getLayoutParams();
                params.setMargins(0, 0, marginInDp, 0);

                holder.leftTxt.setLayoutParams(params);
            }
        } else {
            holder.action.setVisibility(View.GONE);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.leftTxt.getLayoutParams();
            params.setMargins(0, 0, 0, 0);

            holder.leftTxt.setLayoutParams(params);
        }

        int color = R.color.white;
        if(curr.getStatus() == 1){
            color = R.color.success;
        } else if(curr.getStatus() == 2){
            color = R.color.danger_light;
        }
        holder.background.setCardBackgroundColor(context.getColor(color));
    }

    protected abstract void changeStatus(Context context, T curr, boolean isApproved);

    protected abstract void showViewDialog(T curr, Context context, Dialog dialog);

    protected abstract void setDescriptionText(T curr, TextView descriptionTxt);

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        CardView background;
        TextView descriptionTxt;
        ImageView viewProofBtn;
        ImageView approveBtn, rejectBtn;

        ViewGroup leftTxt, action;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            background = itemView.findViewById(R.id.list_card_template);
            descriptionTxt = itemView.findViewById(R.id.list_description_txt);
            viewProofBtn = itemView.findViewById(R.id.list_view_btn);
            approveBtn = itemView.findViewById(R.id.list_approve_btn);
            rejectBtn = itemView.findViewById(R.id.list_reject_btn);

            leftTxt = itemView.findViewById(R.id.list_description_container);
            action = itemView.findViewById(R.id.list_action);
        }
    }
}
