package com.xilada.xldutils.network;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.xilada.xldutils.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 使用的第三方okhttp，对其进行简单的封装
 * Created by LiaoXiang on 2015/11/18.
 */
public class HttpUtils {

    private static HttpUtils mInstance;
    private OkHttpClient mOkHttpClient;


    private Handler mDelivery;
    private static final String ERROR="获取数据失败！";
    private static final String CODE="showapi_res_code";
    private static final String DATA="showapi_res_body";
    private static final int defaultTimeOut  = 20;//秒
    private static boolean isSecret = false;
    Gson mGson;

    private HttpUtils() {
        mOkHttpClient = new OkHttpClient();
//        OkHttpClient.Builder mOkHttpClientBuilder = new OkHttpClient.Builder();
        //cookie enabled
//        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mDelivery = new Handler(Looper.getMainLooper());
//        mOkHttpClient = mOkHttpClientBuilder.build();
        mGson = new Gson();
    }

    public static HttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (HttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new HttpUtils();
                }
            }
        }
        return mInstance;
    }
    /**
     * 同步的Get请求
     *
     * @param url url
     * @return Response 请求
     */
    private Response _getAsyn(String url){
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        Response execute = null;
        try {
            execute = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return execute;
    }

    private void _getAsyn(String url,Map<String,String> headers,ResultCallback callback){
        Request.Builder builder = new Request.Builder();
        Set<Map.Entry<String, String>> entries = headers.entrySet();

        for (Map.Entry<String, String> entry : entries) {
            builder.addHeader(entry.getKey(),entry.getValue());
        }

        builder.url(url);
        final Request request = builder.build();
        Call call = mOkHttpClient.newCall(request);
        deliveryResult(callback, request,defaultTimeOut);
    }
    /**
     * 同步的Get请求
     *
     * @param url url
     * @return 字符串
     */
    private String _getAsString(String url) {
        Response execute = _getAsyn(url);
        try {
            return execute.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 同步的Post请求
     *
     * @param url url
     * @param params post的参数
     * @return response
     */
    private Response _post(String url, Map<String,Object> params) throws IOException {
        Request request = buildPostRequest(url, params);
        Response response = mOkHttpClient.newCall(request).execute();
        return response;
    }

    /**
     * 同步的Post请求
     *
     * @param url url
     * @param params post的参数
     * @return 字符串
     */
    private String _postAsString(String url, Map<String,Object> params) throws IOException {
        Response response = _post(url, params);
        return response.body().string();
    }

    /**
     * 异步的post请求
     *
     * @param url url
     * @param callback 回调
     * @param params 参数
     */
    private void _postAsyn(String url, final ResultCallback callback, Map<String, Object> params) {
//        Log.d("tag","----表单---"+url+params);
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request,defaultTimeOut);
    }

    /**
     * 异步基于post的文件上传
     *
     * @param url url
     * @param callback 回调
     * @param files 文件数组
     * @param fileKeys 文件名
     * @throws IOException
     */
    private void _postAsyn(String url, Map<String,String> params, ResultCallback callback, File[] files, String[] fileKeys){
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        deliveryResult(callback, request,defaultTimeOut);
    }

    private void _postAsyn(String url, Map<String,String> params, ResultCallback callback, File[] files, String[] fileKeys,int timeOut){
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        deliveryResult(callback, request,timeOut);
    }
    /**
     * 异步基于post的文件上传，单文件不带参数上传
     *
     * @param url url
     * @param callback 回调
     * @param file 文件
     * @param fileKey 文件名
     * @throws IOException
     */
    private void _postAsyn(String url, ResultCallback callback, File file, String fileKey) {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        deliveryResult(callback, request,defaultTimeOut);
    }

    private void _postAsyn(String url, ResultCallback callback, File file, String fileKey, Map<String,String> params){
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        deliveryResult(callback, request,defaultTimeOut);
    }
    private void _postAsynFormData(String url, ResultCallback callback, File[] files, String[] fileKeys, Map<String,String> params){
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        deliveryResult(callback, request,defaultTimeOut);
    }

    /**
     * 异步下载文件
     *
     * @param url url
     * @param destFileDir 本地文件存储的文件夹
     * @param callback 回调
     */
    private void _downloadAsyn(final String url, final String destFileDir, final ResultCallback callback) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                sendFailedStringCallback(call, e.getMessage(), callback);
            }

            @Override
            public void onResponse(Call call,Response response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(destFileDir, getFileName(url));
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    sendSuccessResultCallback(file.getAbsolutePath(), callback);
                } catch (IOException e) {
                    sendFailedStringCallback(call, e.getMessage(), callback);
                } finally {
                    try {
                        if (is != null) is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null) fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    //*************对外公布的方法************
    public static Response getAsyn(String url)  {
        return getInstance()._getAsyn(url);
    }

    public static String getAsString(String url){
        return getInstance()._getAsString(url);
    }

    public static void postAsyn(String url, Map<String, Object> params ,final ResultCallback callback) {
//        Log.d("tag","------访问的url--->"+url);
        getInstance()._postAsyn(url, callback, params);
    }

    public static void postAsyn(String url, Map<String ,String> params, ResultCallback callback, File[] files, String[] fileKeys,int timeOut){
        getInstance()._postAsyn(url, params, callback, files, fileKeys,timeOut);
    }

    public static void postAsyn(String url, Map<String,String> params, ResultCallback callback, File file, String fileKey) {
        getInstance()._postAsyn(url, callback, file, fileKey, params);
    }

    public static void downloadAsyn(String url, String destDir, ResultCallback callback) {
        getInstance()._downloadAsyn(url, destDir, callback);
    }

    //****************************
    private Request buildMultipartFormRequest(String url, File[] files,
                                              String[] fileKeys, Map<String,String> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
//        MultipartBuilder builder = new MultipartBuilder()
//                .type(MultipartBuilder.FORM);
        builder.setType(MultipartBody.FORM);
        Set<Map.Entry<String, String>> entries = params.entrySet();

        for (Map.Entry<String, String> entry : entries) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                    RequestBody.create(null, entry.getValue()));
        }

        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //TODO 根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }
    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
    private static final String SESSION_KEY = "Set-Cookie";
    private static final String mSessionKey = "JSESSIONID";
    private Map<String, String> mSessions = new HashMap<>();
    private void deliveryResult(final ResultCallback callback, final Request request,int timeOut) {
        if (timeOut != defaultTimeOut){
            mOkHttpClient = mOkHttpClient.newBuilder().connectTimeout(timeOut,TimeUnit.SECONDS).build();
        }
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Utils.systemErr(e.getMessage());
                sendResultCallback(callback);
                sendFailedStringCallback(call, ERROR, callback);
            }
            @Override
            public void onResponse(Call call,final Response response) {
                try {
                Log.d("tag","----------str---->"+request.toString());
                    if (callback.mType == String.class) {
                        final String string = response.body().string();
                        Utils.systemErr(string);
                        sendResultCallback(callback);
                        sendSuccessResultCallback(string, callback);
                    } else if (callback.mType == JsonReader.class){
                        if (response.body().charStream()!=null) {
                            JsonReader jsonReader = new JsonReader(response.body().charStream());
                            sendSuccessResultCallback(jsonReader, callback);
                        }else {
                            sendFailedStringCallback(call, ERROR, callback);
                        }
                    }else if(callback.mType == JSONObject.class){
                        final String string = response.body().string();
                        Utils.systemErr(string);
                        sendResultCallback(callback);
                        JSONObject json = new JSONObject(string);
                        int code = json.optInt(CODE,-1);
                        if (code == 0) {
                            JSONObject data=json.optJSONObject(DATA);
                            if (data==null) {
                                sendFailedStringCallback(call,ERROR , callback);
                                return;
                            }
                            sendSuccessResultCallback(data, callback);
                        }else {
                            sendFailedStringCallback(call, ERROR, callback);
                        }
                    }else {
                        final String string = response.body().string();
                        Object o = mGson.fromJson(string, callback.mType);
                        sendSuccessResultCallback(o, callback);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    sendFailedStringCallback(call, e.getMessage(), callback);
                }catch (JSONException e){//json解析错误
                    e.printStackTrace();
                    sendFailedStringCallback(call, e.getMessage(), callback);
                }
            }
        });
    }

    private void sendFailedStringCallback(final Call call, final String e, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onError(call, e);
            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(object);
                }
            }
        });
    }

    private void sendResultCallback(final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResult();
                }
            }
        });
    }

    private Request buildPostRequest(String url, Map<String,Object> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        FormBody.Builder builder = new FormBody.Builder();
        Set<Map.Entry<String, Object>> entries = params.entrySet();

        for (Map.Entry<String, Object> entry : entries) {
            builder.add(entry.getKey(), entry.getValue().toString());
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }
    public static abstract class ResultCallback<T> {
        Type mType;
        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }
        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return parameterized.getActualTypeArguments()[0];
        }

        /**
         * 网络请求失败
         * @param call 请求
         * @param e 错误信息
         */
        public void onError(Call call, String e){

        }
        /**
         * 网络请求成功
         * @param response 数据
         */
        public abstract void onResponse(T response);

        /**
         * 网络请求完成，可统一处理一些事件，不管成功与否都会进入这个方法
         *
         */
        public void onResult(){

        }
    }
}


