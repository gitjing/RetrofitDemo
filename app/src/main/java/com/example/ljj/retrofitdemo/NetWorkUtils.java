package com.example.ljj.retrofitdemo;

import retrofit2.Retrofit;

/**
 * 作者：ljj
 * 日期：2016/7/21 13:42
 */
public class NetWorkUtils {

    public static Retrofit retrofit = null;

    public static Retrofit retrofit() {

        if (retrofit == null) {

            /**
             * 设置缓存
             */

            /**
             * 公共参数
             */


            /**
             * 设置头
             */


            /**
             * Log拦截器
             */

            /**
             * 设置cookie
             */


            /**
             *设置连接超时
             */


        }

        return retrofit;

    }


    //设置缓存
//    private static void cacheMethod() {
//        File cacheFile = new File();
//        final Cache cache = new Cache(cacheFile,1024*1024*50);
//
//        Interceptor cachIntercetor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                return null;
//            }
//        };
//
//    }

}
