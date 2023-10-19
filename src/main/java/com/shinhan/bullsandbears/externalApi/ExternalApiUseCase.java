//package com.shinhan.bullsandbears.externalApi;
//
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ExternalApiUseCase {
//
//    public String get() {
//        return callApi();
//    }
//
//    private String callApi() {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .hostnameVerifier((hostname, session) -> {
//                    // if ("trustedhostname.com".equals(hostname)) {
//                    //     return true;
//                    // } else {
//                    //     return false;
//                    // }
//                    return true; // 모든 호스트를 허용하는 예제
//                })
//                .build();
//
//        Request request = new Request.Builder()
//                .url("https://gapi.shinhansec.com:8443/openapi/v1.0/strategy/market-issue")
//                .method("GET", null)
//                .addHeader("apiKey", "l7xxR7Fe0dP3i8KPZaPKpknI2vWrMeJfwDpk") // apiKey를 추가합니다.
//                .build();
//
//        try {
//            Response response = client.newCall(request).execute();
//            if (response.isSuccessful() && response.body() != null) {
//                return response.body().string();
//            } else {
//                System.err.println("Request failed: " + response.code());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "Request failed!";
//    }
//}
