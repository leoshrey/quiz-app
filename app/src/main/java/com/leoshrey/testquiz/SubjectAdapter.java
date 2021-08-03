package com.leoshrey.testquiz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>{

    Context context;
    ArrayList<SubjectModel> subjectModels;

    public SubjectAdapter(Context context, ArrayList<SubjectModel> subjectModels){
        this.context = context;
        this. subjectModels = subjectModels;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subject_category,null);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        SubjectModel model = subjectModels.get(position);
        holder.textView.setText(model.getSubjectName());
        Glide.with(context).load(model.getSubjectImage()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuizActivity.class);
                intent.putExtra("subjectId", model.getSubjectId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectModels.size();
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.subjectCategory);
        }
    }
}
