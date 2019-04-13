package com.cortasys.note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cortasys.note.db.entity.Note;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes = new ArrayList<>();

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                    LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.txtTitle.setText(note.getTitle());
        holder.txtShortDesc.setText(note.getShortDesc());
        holder.txtDesc.setText(note.getLongDesc());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> list) {
        this.notes = list;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position) {
        return notes.get(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle, txtDesc, txtShortDesc;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtShortDesc = itemView.findViewById(R.id.txtShortDesc);
            txtDesc = itemView.findViewById(R.id.txtDesc);
        }
    }
}
