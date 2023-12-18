package com.uas.sipami.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uas.sipami.API.ApiClient;
import com.uas.sipami.API.ApiService;
import com.uas.sipami.Model.ProgresDto;
import com.uas.sipami.Model.SurveiDto;
import com.uas.sipami.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveiMitraAdapter extends RecyclerView.Adapter<SurveiMitraAdapter.SurveiViewHolder> {

    private List<SurveiDto> surveiList;
    private OnItemClickListener listener;
    private Context context;
    private String token;
    private String nikNip;

    private ApiService apiService;

    public void setTokenAndNikNip(String token, String nikNip) {
        this.token = token;
        this.nikNip = nikNip;
    }

    public interface OnItemClickListener {
        void onItemClick(SurveiDto survei);
    }

    public SurveiMitraAdapter(List<SurveiDto> surveiList, OnItemClickListener listener, Context context) {
        this.surveiList = surveiList;
        this.listener = listener;
        this.context = context;
    }

    public void setData(List<SurveiDto> newData) {
        surveiList.clear();
        surveiList.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SurveiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daftar_survei_mitra, parent, false);
        return new SurveiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SurveiViewHolder holder, int position) {
        SurveiDto survei = surveiList.get(position);

        holder.textViewNamaSurvei.setText(survei.getNamaSurvei());
        holder.textViewKodeSurvei.setText("(" + survei.getKodeSurvei() + ")");
        String tanggalMulai = survei.getTanggalMulai();
        String tanggalSelesai = survei.getTanggalSelesai();
        holder.textViewTanggal.setText("Tanggal: " + tanggalMulai + " - " + tanggalSelesai);

        holder.buttonKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleKirimButtonClick(holder, survei);
            }
        });
    }

    private void handleKirimButtonClick(SurveiViewHolder holder, SurveiDto survei) {
        String tanggal = getCurrentDate();
        String kodesurvei = survei.getKodeSurvei();
        int jumlahCacah = Integer.parseInt(holder.editTextNumber.getText().toString());
        sendProgressDataToApi(nikNip, tanggal, kodesurvei, jumlahCacah);
    }

    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    private void sendProgressDataToApi(String nikNip, String tanggal, String kodesurvei, int jumlahCacah) {
        ApiService apiService = ApiClient.createService(ApiService.class);
        ProgresDto progresDto = new ProgresDto(nikNip, kodesurvei, tanggal, jumlahCacah);
        Toast.makeText(context, "niknip : "+nikNip, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "tanggal : "+tanggal, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "kodesurvei : "+kodesurvei, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "jumlah cacah  : "+jumlahCacah, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "token : "+token, Toast.LENGTH_SHORT).show();

        Call<ProgresDto> call = apiService.createProgres(token, progresDto);
        call.enqueue(new Callback<ProgresDto>() {
            @Override
            public void onResponse(Call<ProgresDto> call, Response<ProgresDto> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Data berhasil dikirim", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Gagal mengirim data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProgresDto> call, Throwable t) {
                Toast.makeText(context, "Kesalahan jaringan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return surveiList.size();
    }

    public static class SurveiViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNamaSurvei, textViewKodeSurvei, textViewTanggal;
        Button buttonKirim;
        EditText editTextNumber;

        public SurveiViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNamaSurvei = itemView.findViewById(R.id.textViewNamaSurvei);
            textViewKodeSurvei = itemView.findViewById(R.id.textViewKodeSurvei);
            textViewTanggal = itemView.findViewById(R.id.textViewTanggal);
            buttonKirim = itemView.findViewById(R.id.buttonKirim);
            editTextNumber = itemView.findViewById(R.id.editTextNumber);
        }
    }
}

