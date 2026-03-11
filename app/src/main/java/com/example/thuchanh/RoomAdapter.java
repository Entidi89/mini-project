package com.example.thuchanh;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<Room> roomList;
    private OnRoomClickListener listener;

    public interface OnRoomClickListener {
        void onRoomClick(Room room, int position);
        void onRoomLongClick(Room room, int position);
    }

    public RoomAdapter(List<Room> roomList, OnRoomClickListener listener) {
        this.roomList = roomList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.tvRoomName.setText(room.getName());
        holder.tvRoomPrice.setText("Price: $" + room.getPrice());
        holder.tvRoomStatus.setText("Status: " + room.getStatus());

        if ("Available".equalsIgnoreCase(room.getStatus())) {
            holder.tvRoomStatus.setTextColor(Color.GREEN);
            holder.tvTenantInfo.setVisibility(View.GONE);
        } else {
            holder.tvRoomStatus.setTextColor(Color.RED);
            holder.tvTenantInfo.setVisibility(View.VISIBLE);
            holder.tvTenantInfo.setText("Tenant: " + room.getTenantName() + " (" + room.getPhoneNumber() + ")");
        }

        holder.itemView.setOnClickListener(v -> listener.onRoomClick(room, position));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onRoomLongClick(room, position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomName, tvRoomPrice, tvRoomStatus, tvTenantInfo;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvRoomPrice = itemView.findViewById(R.id.tvRoomPrice);
            tvRoomStatus = itemView.findViewById(R.id.tvRoomStatus);
            tvTenantInfo = itemView.findViewById(R.id.tvTenantInfo);
        }
    }
}
