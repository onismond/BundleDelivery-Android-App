package com.bundlegh.bundledelivery.data.network

import com.bundlegh.bundledelivery.data.network.responses.*
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MyApi {

    @FormUrlEncoded
    @POST("delivery/profile")
    suspend fun loadProfile(
        @Field("token") token: String,
    ) : Response<ProfileResponse>

    @FormUrlEncoded
    @POST("orders")
    suspend fun getNewOrders(
        @Field("token") token: String,
    ) : Response<OrderResponse>

    @FormUrlEncoded
    @POST("transactions")
    suspend fun getTransactions(
        @Field("token") token: String,
        @Field("tran_ids") tran_ids: String,
    ) : Response<TransactionResponse>

    @FormUrlEncoded
    @POST("order/accept")
    suspend fun acceptOrder(
        @Field("token") token: String,
        @Field("order_id") order_id: String,
        @Field("amount") amount: String,
    ) : Response<AcceptOrderResponse>

    @FormUrlEncoded
    @POST("order/completed")
    suspend fun completedOrder(
        @Field("token") token: String,
        @Field("order_id") tran_ids: String,
    ) : Response<AcceptOrderResponse>

    @FormUrlEncoded
    @POST("user/payouts")
    suspend fun loadPayouts(
        @Field("token") token: String,
    ) : Response<PayoutResponse>

    @FormUrlEncoded
    @POST("delivery/login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("register")
    suspend fun userSignup(
        @Field("name") name: String,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ) : Response<AuthResponse>

    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : MyApi{
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://192.168.13.21/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}