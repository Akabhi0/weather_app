package com.example.task.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.task.BasicUtality.BasicFunction;
import com.example.task.BasicUtality.Constant;
import com.example.task.R;
import com.example.task.dataBase.tables.ForecastArrayTable;
import com.example.task.databinding.ForecastAdapterBinding;
import com.example.task.view.ClimateShowActivity;

import java.util.ArrayList;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    private ArrayList<ForecastArrayTable> forecastTable;

    private Context context;

    public ForecastAdapter(ClimateShowActivity climateShowActivity, ArrayList<ForecastArrayTable> forecastTable) {
        this.forecastTable = forecastTable;
        this.context = climateShowActivity;
    }

    @Override
    public int getItemCount() {
        return forecastTable.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(Constant.PIC + forecastTable.get(position).getIcon() + Constant.FORMATE)
                .placeholder(R.drawable.ic_cloud_computing).
                into(holder.getBinding().acivIconMon);

        holder.getBinding().setModel(forecastTable.get(position));
        holder.getBinding().actvTemp.setText(String.format(Constant.STRING_FORMATE, BasicFunction.getCelcius(forecastTable.get(position).getTemp())) + (char) 0x00B0);
        holder.getBinding().actvMaxTemp.setText(String.format(Constant.STRING_FORMATE, BasicFunction.getCelcius(forecastTable.get(position).getTempMax())) + (char) 0x00B0);
        holder.getBinding().actvMinTemp.setText(String.format(Constant.STRING_FORMATE, BasicFunction.getCelcius(forecastTable.get(position).getTempMin())) + (char) 0x00B0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ForecastAdapterBinding binding;

        public ForecastAdapterBinding getBinding() {
            return binding;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

}
