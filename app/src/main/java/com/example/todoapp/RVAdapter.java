package com.example.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.databinding.EachRvBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RVAdapter extends ListAdapter<TODO,RVAdapter.ViewHolder> {

    public RVAdapter(MainActivity mainActivity){
        super(CALLBACK);
    }

    private static final DiffUtil.ItemCallback<TODO> CALLBACK = new DiffUtil.ItemCallback<TODO>() {
        @Override
        public boolean areItemsTheSame(@NonNull TODO oldItem, @NonNull TODO newItem) {
            return oldItem.getId()== newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TODO oldItem, @NonNull TODO newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getDisplay().equals(newItem.getDisplay());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_rv,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TODO todo = getItem(position);
        holder.binding.titleRv.setText(todo.getTitle());
        holder.binding.dispRv.setText(todo.getDisplay());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        holder.binding.dateTextView.setText(dateFormat.format(todo.getDate()));
    }

    public TODO getTodo(int position){
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        EachRvBinding binding;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding = EachRvBinding.bind(itemView);
        }
    }
}
