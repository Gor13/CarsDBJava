package com.hardzei.carsdbjava.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.hardzei.carsdbjava.R;
import com.hardzei.carsdbjava.db.Car;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<Car> cars = new ArrayList<>();

    public void setCars(List<Car> cars) {
        this.cars = cars;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        holder.modelTV.setText(cars.get(position).getModel());
        holder.yearTV.setText(String.valueOf(cars.get(position).getYear()));
        holder.priceTV.setText(String.valueOf(cars.get(position).getPrice()));
        holder.colorTV.setText(cars.get(position).getColor());
        holder.mileageTV.setText(String.valueOf(cars.get(position).getMileage()));
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    class CarViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.modelTV)
        TextView modelTV;
        @Nullable
        @BindView(R.id.yearTV)
        TextView yearTV;
        @Nullable
        @BindView(R.id.priceTV)
        TextView priceTV;
        @Nullable
        @BindView(R.id.colorTV)
        TextView colorTV;
        @Nullable
        @BindView(R.id.mileageTV)
        TextView mileageTV;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
