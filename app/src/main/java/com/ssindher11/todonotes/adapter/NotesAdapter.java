package com.ssindher11.todonotes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ssindher11.todonotes.R;
import com.ssindher11.todonotes.clicklisteners.ItemClickListener;
import com.ssindher11.todonotes.model.Notes;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private List<Notes> notesList;
    private ItemClickListener itemClickListener;

    public NotesAdapter(List<Notes> notesList, ItemClickListener itemClickListener) {
        this.notesList = notesList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_adapter_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Notes notes = notesList.get(position);
        final String title = notes.getTitle();
        final String description = notes.getDescription();

        holder.titleTV.setText(title);
        holder.descTV.setText(description);
        holder.itemView.setOnClickListener(view -> itemClickListener.onClick(notes));
    }


    @Override
    public int getItemCount() {
        return notesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTV, descTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.tv_title);
            descTV = itemView.findViewById(R.id.tv_description);
        }
    }
}
