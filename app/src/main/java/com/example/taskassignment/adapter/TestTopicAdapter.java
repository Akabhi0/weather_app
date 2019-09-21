package com.example.taskassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskassignment.R;
import com.example.taskassignment.databinding.TestTopicAdapterBinding;

public class TestTopicAdapter extends RecyclerView.Adapter<TestTopicAdapter.ViewHolder> {
    private String[] topicName;
    private Context context;
    private onClickInterface onClickInterface;

    public TestTopicAdapter(String[] topicName) {
        this.topicName = topicName;
    }

    @Override
    public int getItemCount() {
        return topicName.length;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_topic_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getBinding().actvName.setText(topicName[position]);
        switch (position) {
            case 0:
                holder.getBinding().topic.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_calculator));
                break;
            case 1:
                holder.getBinding().topic.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_java));
                break;
            case 2:
                holder.getBinding().topic.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_android));
                break;

        }

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TestTopicAdapterBinding binding;

        public TestTopicAdapterBinding getBinding() {
            return binding;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

            binding.quizRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickInterface.onInfoCattleClicked(getAdapterPosition());
                }
            });

        }
    }

    public interface onClickInterface {
        void onInfoCattleClicked(int topicPosition);
    }

    public void onClickFunction(onClickInterface onClickInterface) {
        this.onClickInterface = onClickInterface;
    }
}
