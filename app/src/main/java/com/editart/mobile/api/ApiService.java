package com.editart.mobile.api;

import com.editart.mobile.api.APIInterface;
import com.editart.mobile.retrofit.RetrofitClient;

public class ApiService {
    private static APIInterface apiInterface;

    public static APIInterface getApi() {
        if (apiInterface == null) {
            apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        }
        return apiInterface;
    }
}
