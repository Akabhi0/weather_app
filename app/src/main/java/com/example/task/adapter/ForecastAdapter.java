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
import com.example.task.dataBase.tables.ForecastDateTable;
import com.example.task.databinding.ForecastAdapterBinding;
import com.example.task.view.ClimateShowActivity;

import java.util.ArrayList;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    private ArrayList<ForecastArrayTable> forecastTable;
    private ArrayList<ForecastDateTable> forecastDateTables;
    private ArrayList<ForecastAdapter> forecastAdapters;
    private ForecastDateTable forecastDateTable;
    private Context context;

    public ForecastAdapter(ClimateShowActivity climateShowActivity, ArrayList<ForecastArrayTable> forecastTable) {
        this.forecastTable = forecastTable;
        forecastDateTables = new ArrayList<>();
        forecastAdapters = new ArrayList<>();
        forecastDateTable = new ForecastDateTable();
        this.context = climateShowActivity;

        /**
         * We are doing re storage
         */
        for (int i = 0; i < forecastTable.size(); i++) {
            for (int j = 0; j < forecastTable.get(i).getForecastDateTables().size(); j++) {
                if (forecastTable.get(i).getForecastDateTables().get(j).getDate() == null) {
                    forecastDateTable = new ForecastDateTable();
                } else {
                    forecastDateTable.setDate(forecastTable.get(i)
                            .getForecastDateTables().get(j).getDate());
                    forecastDateTable.setDay(forecastTable.get(i)
                            .getForecastDateTables().get(j).getDay());
                    forecastDateTable.setDescription(forecastTable.get(i)
                            .getForecastDateTables().get(j).getDescription());
                    forecastDateTable.setIcon(forecastTable.get(i)
                            .getForecastDateTables().get(j).getIcon());
                    forecastDateTable.setTemp(forecastTable.get(i)
                            .getForecastDateTables().get(j).getTemp());
                    forecastDateTable.setTempMin(forecastTable.get(i)
                            .getForecastDateTables().get(j).getTempMin());
                    forecastDateTable.setTempMax(forecastTable.get(i)
                            .getForecastDateTables().get(j).getTempMax());
                    forecastDateTable.setCity(forecastTable.get(i)
                            .getForecastDateTables().get(i).getCity());
                    forecastDateTables.add(forecastDateTable);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return forecastDateTables.size();
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
                .load(Constant.PIC + forecastDateTables.get(position).getIcon() + Constant.FORMATE)
                .placeholder(R.drawable.ic_cloud_computing).
                into(holder.getBinding().acivIconMon);

        holder.getBinding().actvCityName.setText(forecastDateTables.get(position).getCity());
        holder.getBinding().actvDay.setText(forecastDateTables.get(position).getDay());
        holder.getBinding().actvDate.setText(forecastDateTables.get(position).getDate());
        holder.getBinding().actvTemp.setText(String.format(Constant.STRING_FORMATE, BasicFunction.getCelcius(forecastDateTables.get(position).getTemp())) + (char) 0x00B0);
        holder.getBinding().actvMaxTemp.setText(String.format(Constant.STRING_FORMATE, BasicFunction.getCelcius(forecastDateTables.get(position).getTempMax())) + (char) 0x00B0);
        holder.getBinding().actvMinTemp.setText(String.format(Constant.STRING_FORMATE, BasicFunction.getCelcius(forecastDateTables.get(position).getTempMin())) + (char) 0x00B0);
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
