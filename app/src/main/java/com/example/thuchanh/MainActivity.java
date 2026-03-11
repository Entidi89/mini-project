package com.example.thuchanh;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RoomAdapter.OnRoomClickListener {

    private List<Room> roomList;
    private RoomAdapter adapter;
    private RecyclerView rvRooms;
    private FloatingActionButton fabAddRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        roomList = new ArrayList<>();
        // Pre-populate with some data for testing
        roomList.add(new Room("1", "Room 101", 1500.0, "Available", "", ""));
        roomList.add(new Room("2", "Room 102", 2000.0, "Rented", "John Doe", "0123456789"));

        rvRooms = findViewById(R.id.rvRooms);
        fabAddRoom = findViewById(R.id.fabAddRoom);

        adapter = new RoomAdapter(roomList, this);
        rvRooms.setLayoutManager(new LinearLayoutManager(this));
        rvRooms.setAdapter(adapter);

        fabAddRoom.setOnClickListener(v -> showRoomDialog(null, -1));
    }

    private void showRoomDialog(Room room, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_room, null);
        builder.setView(dialogView);

        EditText etRoomId = dialogView.findViewById(R.id.etRoomId);
        EditText etRoomName = dialogView.findViewById(R.id.etRoomName);
        EditText etPrice = dialogView.findViewById(R.id.etPrice);
        Spinner spnStatus = dialogView.findViewById(R.id.spnStatus);
        EditText etTenantName = dialogView.findViewById(R.id.etTenantName);
        EditText etPhoneNumber = dialogView.findViewById(R.id.etPhoneNumber);

        // Setup Spinner Adapter for both Add and Edit
        ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(this,
                R.array.room_status_array, android.R.layout.simple_spinner_item);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStatus.setAdapter(adapterStatus);

        if (room != null) {
            builder.setTitle("Edit Room");
            etRoomId.setText(room.getId());
            etRoomId.setEnabled(false);
            etRoomName.setText(room.getName());
            etPrice.setText(String.valueOf(room.getPrice()));
            etTenantName.setText(room.getTenantName());
            etPhoneNumber.setText(room.getPhoneNumber());
            
            int spinnerPosition = adapterStatus.getPosition(room.getStatus());
            spnStatus.setSelection(spinnerPosition);
        } else {
            builder.setTitle("Add New Room");
        }

        builder.setPositiveButton("Save", (dialog, which) -> {
            String id = etRoomId.getText().toString().trim();
            String name = etRoomName.getText().toString().trim();
            String priceStr = etPrice.getText().toString().trim();
            String status = spnStatus.getSelectedItem().toString();
            String tenantName = etTenantName.getText().toString().trim();
            String phoneNumber = etPhoneNumber.getText().toString().trim();

            if (TextUtils.isEmpty(id) || TextUtils.isEmpty(name) || TextUtils.isEmpty(priceStr)) {
                Toast.makeText(MainActivity.this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Invalid price", Toast.LENGTH_SHORT).show();
                return;
            }

            if (room == null) {
                // Create
                Room newRoom = new Room(id, name, price, status, tenantName, phoneNumber);
                roomList.add(newRoom);
                adapter.notifyItemInserted(roomList.size() - 1);
            } else {
                // Update
                room.setName(name);
                room.setPrice(price);
                room.setStatus(status);
                room.setTenantName(tenantName);
                room.setPhoneNumber(phoneNumber);
                adapter.notifyItemChanged(position);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    @Override
    public void onRoomClick(Room room, int position) {
        showRoomDialog(room, position);
    }

    @Override
    public void onRoomLongClick(Room room, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Room")
                .setMessage("Are you sure you want to delete this room?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    roomList.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, roomList.size());
                })
                .setNegativeButton("No", null)
                .show();
    }
}
