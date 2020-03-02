package com.example.collaborativeapp.Fragment;

import com.example.collaborativeapp.Notifications.Myresponse;
import com.example.collaborativeapp.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAqPPDaFg:APA91bFhEGyv5NMiHypxgVs-DnhJbSpRO1p1IVievSTbbf2z1DVBXAHZrdk_yYTttKufz49KmWaRNfToih_YPOmbAUy6TpWS4o8suQ9bl_kw50B_85Vj2TYHFduPZomEEFQwW_T8miLl"
            }
    )

    @POST("fcm/send")
    Call<Myresponse> sendNotification(@Body Sender body);
}
