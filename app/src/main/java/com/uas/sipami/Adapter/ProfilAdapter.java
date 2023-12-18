package com.uas.sipami.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.uas.sipami.Controller.UserDetailActivity;
import com.uas.sipami.Model.RegisterRequest;
import com.uas.sipami.R; // Replace with your actual R class import

import java.util.List;

// ProfilAdapter.java
// ProfilAdapter.java
public class ProfilAdapter extends RecyclerView.Adapter<ProfilAdapter.ProfilViewHolder> {

    private List<RegisterRequest> profilList;
    private Context context;
    private ItemClickListener itemClickListener;
    private String token;
    private String kodeSurvei;

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public ProfilAdapter(Context context, ItemClickListener itemClickListener, String token, String kodeSurvei) {
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.token = token;
        this.kodeSurvei=kodeSurvei;
    }

    public void setProfilList(List<RegisterRequest> profilList) {
        this.profilList = profilList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProfilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_mitra, parent, false);
        return new ProfilViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilViewHolder holder, int position) {
        RegisterRequest profil = profilList.get(position);

        // Set data to views
        holder.nameTextView.setText("Nama: " + profil.getName());
        holder.nikTextView.setText("Nik/Nip: " + profil.getNikNip());

        // Set click listener for the button
        holder.detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gunakan holder.getAdapterPosition() untuk mendapatkan posisi yang benar
                int clickedPosition = holder.getAdapterPosition();

                // Handle button click (you can implement this as needed)
                if (itemClickListener != null && clickedPosition != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(clickedPosition);

                    // Pindah ke UserDetailActivity saat tombol "Detail" diklik
                    Intent intent = new Intent(context, UserDetailActivity.class);

                    // Kirim data yang diperlukan ke UserDetailActivity
//                    intent.putExtra("USER_ID", profil.getUserId());
                    intent.putExtra("NIKNIP", profil.getNikNip());
                    intent.putExtra("TOKEN", token); // Kirim token
                    intent.putExtra("KODE_SURVEI", kodeSurvei); // Kirim token

                    context.startActivity(intent);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return profilList == null ? 0 : profilList.size();
    }

    public static class ProfilViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView nikTextView;
        Button detailButton;

        public ProfilViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            nikTextView = itemView.findViewById(R.id.nikTextView);
            detailButton = itemView.findViewById(R.id.detailButton);
        }
    }
}
