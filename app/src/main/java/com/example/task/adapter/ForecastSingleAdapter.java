package com.example.task.adapter;

import android.annotation.SuppressLint;
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
import com.example.task.dataBase.tables.ForecastSingleTable;
import com.example.task.databinding.ForecastSingleAdapterBinding;

import java.util.List;

public class ForecastSingleAdapter extends RecyclerView.Adapter<ForecastSingleAdapter.ViewHolder> {
    private List<ForecastSingleTable> forecastSingleTables;
    private Context context;

    public ForecastSingleAdapter(List<ForecastSingleTable> forecastSingleTables) {
        this.forecastSingleTables = forecastSingleTables;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_single_adapter, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(Constant.PIC + forecastSingleTables.get(0).getSingleArrayTables().get(position).getIcon() + Constant.FORMATE)
                .placeholder(R.drawable.ic_cloud_computing).
                into(holder.getBinding().acivIconMon);

        holder.getBinding().setForecast(forecastSingleTables.get(0).getSingleArrayTables().get(position));
        holder.getBinding().actvMaxTemp.setText(String.format(Constant.STRING_FORMATE,
                BasicFunction.getCelcius(forecastSingleTables.get(0).getSingleArrayTables().get(position).getTempMax()))
                + (char) 0x00B0);
        holder.getBinding().actvMinTemp.setText(String.format(Constant.STRING_FORMATE,
                BasicFunction.getCelcius(forecastSingleTables.get(0).getSingleArrayTables().get(position).getTempMin()))
                + (char) 0x00B0);
    }

    @Override
    public int getItemCount() {
        return forecastSingleTables.get(0).getSingleArrayTables().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ForecastSingleAdapterBinding binding;

        public ForecastSingleAdapterBinding getBinding() {
            return binding;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
