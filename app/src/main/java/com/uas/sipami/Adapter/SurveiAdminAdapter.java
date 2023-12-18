package com.uas.sipami.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uas.sipami.Model.SurveiDto;
import com.uas.sipami.R;

import java.util.List;

public class SurveiAdminAdapter extends RecyclerView.Adapter<SurveiAdminAdapter.SurveiViewHolder> {

    private List<SurveiDto> surveiList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(SurveiDto survei);
    }

    public SurveiAdminAdapter(List<SurveiDto> surveiList, OnItemClickListener listener) {
        this.surveiList = surveiList;
        this.listener = listener;
    }

    // Tambahkan metode setData untuk memperbarui dataset
    public void setData(List<SurveiDto> newData) {
        surveiList.clear();
        surveiList.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SurveiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daftar_survei_admin, parent, false);
        return new SurveiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SurveiViewHolder holder, int position) {
        SurveiDto survei = surveiList.get(position);

        // Set data to views
        holder.textViewNamaSurvei.setText(survei.getNamaSurvei());
        holder.textViewKodeSurvei.setText("(" + survei.getKodeSurvei() + ")");
        String tanggalMulai = survei.getTanggalMulai();
        String tanggalSelesai = survei.getTanggalSelesai();
        holder.textViewTanggal.setText("Tanggal: " + tanggalMulai + " - " + tanggalSelesai);

        // Set click listener for the "Detail Survei" button
        holder.buttonDetailSurvei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(survei);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return surveiList.size();
    }

    // ViewHolder class
    public static class SurveiViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNamaSurvei, textViewKodeSurvei, textViewTanggal;
        Button buttonDetailSurvei;

        public SurveiViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize views
            textViewNamaSurvei = itemView.findViewById(R.id.textViewNamaSurvei);
            textViewKodeSurvei = itemView.findViewById(R.id.textViewKodeSurvei);
            textViewTanggal = itemView.findViewById(R.id.textViewTanggal);
            buttonDetailSurvei = itemView.findViewById(R.id.buttonDetailSurvei);
        }
    }
}
