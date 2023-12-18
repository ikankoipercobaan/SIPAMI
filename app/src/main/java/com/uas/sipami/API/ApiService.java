package com.uas.sipami.API;

import com.uas.sipami.Model.LoginRequest;
import com.uas.sipami.Model.LoginResponse;
import com.uas.sipami.Model.ProgresDto;
import com.uas.sipami.Model.RegisterRequest;
import com.uas.sipami.Model.RegisterResponse;
import com.uas.sipami.Model.SurveiDto;
import com.uas.sipami.Model.UpdatePasswordResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest LoginRequest);

    @POST("/register")
    Call<RegisterResponse> register(@Body RegisterRequest RegisterRequest);

    @POST("/survei")
    Call<SurveiDto> createSurvey(
            @Header("Authorization") String token,
            @Body SurveiDto surveiDto
    );

    @GET("/survei")
    Call<List<SurveiDto>> getSurveyAdmin(
            @Header("Authorization") String token
    );
    @POST("/progres")
    Call<ProgresDto> createProgres(
            @Header("Authorization") String token,
            @Body ProgresDto progres
    );
    @GET("survei/{kodeSurvei}") // Sesuaikan dengan path endpoint yang sesuai di API Anda
    Call<SurveiDto> getDetailSurveiByKode(
            @Header("Authorization") String token,
            @Path("kodeSurvei") String kodeSurvei
    );
    @PUT("survei/{kodeSurvei}")
    Call<SurveiDto> updateSurvei(
            @Header("Authorization") String token,
            @Path("kodeSurvei") String kodeSurvei,
            @Body SurveiDto updatedSurvei
    );
    @GET("users")
    Call<List<RegisterRequest>> getAllUser(
            @Header("Authorization") String token
    );

    @GET("user")
    Call<RegisterRequest> getUserByNiknip(
            @Header("Authorization") String token,
            @Query("nikNip") String nikNip
    );

    @GET("/progres/by-mitra-survei")
    Call<List<ProgresDto>> getDataGrafik(
            @Header("Authorization") String token,
            @Query("nikNip") String nikNip,
            @Query("kodeSurvei") String kodeSurvei
    );

    @POST("/update-password")
    Call<UpdatePasswordResponse> updatePassword(
            @Header("Authorization") String token,
            @Query("nikNip") String nikNip,
            @Query("currentPassword") String currentPassword,
            @Query("newPassword") String newPassword
    );
    @PUT("/user")
    Call<RegisterRequest> updateUser(
            @Header("Authorization") String token,
            @Query("nikNip") String nikNip,
            @Body RegisterRequest registerRequest
    );

}
