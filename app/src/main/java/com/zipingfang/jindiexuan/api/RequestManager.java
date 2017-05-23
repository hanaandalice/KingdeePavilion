package com.zipingfang.jindiexuan.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.xilada.xldutils.network.HttpUtils;
import com.xilada.xldutils.utils.Toast;
import com.xilada.xldutils.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.sinata.util.DES;
import okhttp3.Call;

/**
 * Created by Administrator on 2016/7/13.
 */
public class RequestManager {
    public static final String SPLIT = "=";
    public static final String AND = "&";
    private static final String RESULT_CODE = "code";
    private static final String RESULT_DATA = "info";
    private static final String PARSER_ERROR = "数据解析错误！";//数据解析错误
    private static final String REQUEST_ERROR = "数据异常！";
    public static final String PAGE_SIZE = "15";//默认每页数据条数
    public static final String END_POINT = "http://oss-cn-shanghai.aliyuncs.com";
    public static final String OSS_URL = "http://rautumn.oss-cn-shanghai.aliyuncs.com/";
    public static final String BUCKET_NAME = "rautumn";
    private static class ParamsBuilder {

        private StringBuilder sb;

        private ParamsBuilder() {
            sb = new StringBuilder();
        }

        public static ParamsBuilder create() {
            return new ParamsBuilder();
        }

        public String build() {
            return sb.toString();
        }

        public ParamsBuilder append(String key, String value) {
            _append(key, value);
            return this;
        }
        public ParamsBuilder append(String key, int value) {
            _append(key, value);
            return this;
        }
        public ParamsBuilder append(String key, double value) {
            _append(key, value);
            return this;
        }

        public ParamsBuilder append(String key, float value) {
            _append(key, value);
            return this;
        }
        public ParamsBuilder append(String key, long value) {
            _append(key, value);
            return this;
        }
        /*
        * 拿key值进行拼接
        * */
        private void _append(String key, Object value) {
            if (value instanceof String) {
                if ("null".equals(value) || TextUtils.isEmpty(value.toString())) {
                    value = "";
                }
            }
            if (sb.length() == 0) {
                sb.append(key);
                sb.append(SPLIT);
                sb.append(value);
            } else {
                sb.append(AND);
                sb.append(key);
                sb.append(SPLIT);
                sb.append(value);
            }
        }
    }
    /**
     * 加密请求
     * @param keyValue 请求值
     * @param callback 回调
     */
    private static final String TAG = "RequestManager";
    private static void secretRequest(String field,Map<String,Object> keyValue, final HttpUtils.ResultCallback<ResultData> callback) {

//        //加密
//        Map<String, String> params = new HashMap<>();
//        Set<String> keySet = keyValue.keySet();
//        String next;
//        StringBuilder builder=new StringBuilder();
//        for (int i = 0,len=keySet.size(); i < len; i++) {
//            next = keySet.iterator().next();
//            if (i<len-1) {
//                builder.append(next+"="+keyValue.get(next)+"&");
//            }else{
//                builder.append(next+"="+keyValue.get(next));
//            }
//        }
//        params.put("key",DES.decryptDES(builder.toString()));
        HttpUtils.postAsyn(Api.BASE_URL+field, keyValue, new HttpUtils.ResultCallback<String>() {
            @Override
            public void onResult() {
                super.onResult();
                callback.onResult();
            }
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: --->"+response.toString());
                ResultData resultData = new ResultData();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.optInt(RESULT_CODE, -1);
                    String message = jsonObject.optString("msg");
                    resultData.setCode(code);
                    resultData.setMessage(message);
                    if (code == 1) {
                        String data = jsonObject.optString(RESULT_DATA);
                        if (!TextUtils.isEmpty(data)) {
//                            String unDecryptData = DES.decryptDES(data);
                            String unDecryptData =data;
                            resultData.setData(unDecryptData);
                            if (unDecryptData.startsWith("{")) {
                                resultData.setData(new JSONObject(unDecryptData));
                            } else if (unDecryptData.startsWith("[")) {
                                resultData.setData(new JSONArray(unDecryptData));
                            }
                        }
                        callback.onResponse(resultData);
                    } else {
                        callback.onError(null, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onError(null, PARSER_ERROR);
                }
            }
            @Override
            public void onError(Call call, String e) {
                super.onError(call, e);
                callback.onError(call, REQUEST_ERROR + e);
            }
        });
    }
    /**
     * 加密请求
     * @param keyValue 请求值
     * @param callback 回调
     */
    private static void postHttpSecret(Map<String,Object> keyValue, String url, final HttpUtils.ResultCallback<ResultData> callback) {
//        //加密
//        Map<String, String> params = new HashMap<>();
//        Set<String> keySet = keyValue.keySet();
//        String next;
//        StringBuilder builder=new StringBuilder();
//        for (int i = 0,len=keySet.size(); i < len; i++) {
//            next = keySet.iterator().next();
//            if (i<len-1) {
//                builder.append(next+"="+keyValue.get(next)+"&");
//            }else{
//                builder.append(next+"="+keyValue.get(next));
//            }
//        }
//        params.put("key",DES.decryptDES(builder.toString()));
        HttpUtils.postAsyn(url, keyValue, new HttpUtils.ResultCallback<String>() {
            @Override
            public void onResult() {
                super.onResult();
                callback.onResult();
            }
            @Override
            public void onResponse(String response) {
                ResultData resultData = new ResultData();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.optInt(RESULT_CODE, -1);
                    String message = jsonObject.optString("message");
                    resultData.setCode(code);
                    resultData.setMessage(message);
                    if (code == 0) {
                        String data = jsonObject.optString(RESULT_DATA);
                        if (!TextUtils.isEmpty(data)) {
                            String unDecryptData = DES.decryptDES(data);
                            resultData.setData(unDecryptData);
                            if (unDecryptData.startsWith("{")) {
                                resultData.setData(new JSONObject(unDecryptData));
                            } else if (unDecryptData.startsWith("[")) {
                                resultData.setData(new JSONArray(unDecryptData));
                            }
                        }
                        callback.onResponse(resultData);
                    } else {
                        callback.onError(null, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onError(null, PARSER_ERROR);
                }

            }

            @Override
            public void onError(Call call, String e) {
                super.onError(call, e);
                callback.onError(call, REQUEST_ERROR + e);
            }
        });
    }
    /**
     * 加密带文件请求
     * @param keyValue 请求值
     * @param callback 回调
     */
    private static void secretFileRequest(Map<String,String> keyValue, File[] file, String[] fileKey, final HttpUtils.ResultCallback<ResultData> callback, int timeOut) {
//        Map<String, String> params = new HashMap<>();
//        params.put("key", DES.encryptDES(keyValue));
        HttpUtils.postAsyn(Api.BASE_URL, keyValue, new HttpUtils.ResultCallback<String>() {
            @Override
            public void onResult() {
                super.onResult();
                callback.onResult();
            }
            @Override
            public void onResponse(String response) {
                ResultData resultData = new ResultData();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.optInt(RESULT_CODE, -1);
                    String message = jsonObject.optString("message");
                    resultData.setCode(code);
                    resultData.setMessage(message);
                    if (code == 0) {
                        String data = jsonObject.optString(RESULT_DATA);
                        if (!TextUtils.isEmpty(data)) {
                            String desData = DES.decryptDES(data);
                            resultData.setData(new JSONObject(desData));
                        }
                        callback.onResponse(resultData);
                    } else {
                        callback.onError(null, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onError(null, PARSER_ERROR);
                }
            }
            @Override
            public void onError(Call call, String e) {
                super.onError(call, e);
                callback.onError(call, REQUEST_ERROR + e);
            }
        }, file, fileKey, timeOut);
    }
    /**
     * 上传文件到aliyun
     */
    public static void uploadClientFile(Context context, String fileName, File file, final HttpUtils.ResultCallback<ResultData> callback) {
        // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAIK4xR3rHP7PmZ", "mFYuVlWVbMjWuPyOdJdIxnfSVWQS08");
        final OSS oss = new OSSClient(context.getApplicationContext(), END_POINT, credentialProvider);
        final String bucketName = "rautumn";
        final String objectKey =fileName + System.currentTimeMillis() + "." + getFileSuffix(file);
        // 构造上传请求
        final PutObjectRequest put = new PutObjectRequest(bucketName, objectKey, file.getPath());
        try {
            ResultData resultData = new ResultData();
            oss.putObject(put);
            String url = oss.presignPublicObjectURL(bucketName, objectKey);
            resultData.setMessage(url);
            callback.onResponse(resultData);
            callback.onResult();
        } catch (Exception e) {
            e.printStackTrace();
            callback.onError(null, e.toString());
            callback.onResult();
        }
    }
    //    /**
//     * OSS上传文件到aliyun
//     */
    public static void uploadFile(Context context, String fileName, File file, final HttpUtils.ResultCallback<ResultData> callback) {
        // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAIK4xR3rHP7PmZ", "mFYuVlWVbMjWuPyOdJdIxnfSVWQS08");
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(30 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(30 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        final OSS oss = new OSSClient(context.getApplicationContext(), END_POINT, credentialProvider,conf);

        final String bucketName = "rautumn";
        final String objectKey =fileName + System.currentTimeMillis() + "." + getFileSuffix(file);
        // 构造上传请求
        final PutObjectRequest put = new PutObjectRequest(bucketName, objectKey, file.getPath());

//        try {
//            ResultData resultData = new ResultData();
//            oss.putObject(put);
//            String url = oss.presignPublicObjectURL(bucketName, objectKey);
//            resultData.setMessage(url);
//            callback.onResponse(resultData);
//            callback.onResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//            callback.onError(null, e.toString());
//            callback.onResult();
//        }
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d(TAG, "onProgress: ---------->"+currentSize+"--------->"+totalSize);
            }
        });
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                ResultData resultData = new ResultData();
                resultData.setMessage(OSS_URL+request.getObjectKey());
                callback.onResponse(resultData);
                callback.onResult();
            }
            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                Log.d(TAG, "onFailure: ------>"+request.getObjectKey());
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    callback.onError(null,clientExcepion.getMessage());
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e(TAG, serviceException.getErrorCode());
                    Log.e(TAG, serviceException.getRequestId());
                    Log.e(TAG, serviceException.getHostId());
                    Log.e(TAG, serviceException.getRawMessage());
                    callback.onError(null,serviceException.getRawMessage());
                }
                callback.onResult();
            }
        });
    }
    //    /**
//     * OSS上传文件到aliyun
//     */
    public static OSSAsyncTask uploadVideoFile(Context context, String fileName, File file, final HttpUtils.ResultCallback<ResultData> callback) {
        // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAIK4xR3rHP7PmZ", "mFYuVlWVbMjWuPyOdJdIxnfSVWQS08");
        final OSS oss = new OSSClient(context.getApplicationContext(), END_POINT, credentialProvider);
        final String bucketName = "rautumn";
        final String objectKey =fileName + System.currentTimeMillis() + "." + getFileSuffix(file);
        // 构造上传请求
        final PutObjectRequest put = new PutObjectRequest(bucketName, objectKey, file.getPath());
//        try {
//            ResultData resultData = new ResultData();
//            oss.putObject(put);
//            String url = oss.presignPublicObjectURL(bucketName, objectKey);
//            resultData.setMessage(url);
//            callback.onResponse(resultData);
//            callback.onResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//            callback.onError(null, e.toString());
//            callback.onResult();
//        }
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {

            }
        });
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                ResultData resultData = new ResultData();
                resultData.setMessage(OSS_URL+request.getObjectKey());
                callback.onResponse(resultData);
                callback.onResult();
            }
            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    callback.onError(null,clientExcepion.getMessage());
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e(TAG, serviceException.getErrorCode());
                    Log.e(TAG, serviceException.getRequestId());
                    Log.e(TAG, serviceException.getHostId());
                    Log.e(TAG, serviceException.getRawMessage());
                    callback.onError(null,serviceException.getRawMessage());
                }
                callback.onResult();
            }
        });
        return task;
    }
    public  interface UpLoadFileListener{
        void onProgress(long currentSize, long totalSize);
        void onSuccess(String url);
        void onFailure(String requestStr, String Code);
    }
    //    /**
    //     * OSS上传文件到aliyun
    //     */
    public static void uploadFile(Context context, String fileName, File file, final UpLoadFileListener upLoadFileListener) {
        // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
        if (file ==null&&file.exists()){
            Toast.create(context).show("图片错误");
            return;
        }
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAIK4xR3rHP7PmZ", "mFYuVlWVbMjWuPyOdJdIxnfSVWQS08");
        final OSS oss = new OSSClient(context.getApplicationContext(), END_POINT, credentialProvider);
        final String bucketName = "rautumn";
        final String objectKey =fileName + System.currentTimeMillis() + "." + getFileSuffix(file);
        // 构造上传请求
        final PutObjectRequest put = new PutObjectRequest(bucketName, objectKey, file.getPath());
//        try {
//            ResultData resultData = new ResultData();
//            oss.putObject(put);
//            String url = oss.presignPublicObjectURL(bucketName, objectKey);
//            resultData.setMessage(url);
//            callback.onResponse(resultData);
//            callback.onResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//            callback.onError(null, e.toString());
//            callback.onResult();
//        }
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                upLoadFileListener.onProgress(currentSize,totalSize);
            }
        });
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                ResultData resultData = new ResultData();
                upLoadFileListener.onSuccess(OSS_URL+request.getObjectKey());
            }
            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();

                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e(TAG, serviceException.getErrorCode());
                    Log.e(TAG, serviceException.getRequestId());
                    Log.e(TAG, serviceException.getHostId());
                    Log.e(TAG, serviceException.getRawMessage());
                }
                upLoadFileListener.onFailure(clientExcepion.getMessage(),serviceException.getRawMessage());
            }
        });
    }

    /**
     * 获取文件后缀
     *
     * @param file 文件
     * @return 文件后缀
     */
    public static String getFileSuffix(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     phone:string
     手机号
     code:string
     验证码
     password:string
     密码
     repassword:string
     重复密码

     注册
     *  @param callback 回调
     */
    public static void register(String phone, String code,String password, String repassword,final HttpUtils.ResultCallback<ResultData> callback) {
        /*final String request = ParamsBuilder.create()
                .append("action", action)
                .append("targetId", targetId)
                .build();*/
        Map<String,Object> map=new HashMap<>();
        map.put("phone",phone);
        map.put("code",code);
        map.put("password",password);
        map.put("repassword",repassword);
        secretRequest("/Index/register",map, callback);
    }

    /**
     phone:string
     手机号

     获取验证码
     *  @param callback 回调
     */
    public static void getMsg(String phone,final HttpUtils.ResultCallback<ResultData> callback) {
        /*final String request = ParamsBuilder.create()
                .append("action", action)
                .append("targetId", targetId)
                .build();*/
        Map<String,Object> map=new HashMap<>();
        map.put("phone",phone);
        secretRequest("/Index/getMsg",map, callback);
    }
    /**
     phone:string
     手机号
     passowrd:string
     密码

     登录
     *  @param callback 回调
     */
    public static void login(String phone,String password,final HttpUtils.ResultCallback<ResultData> callback) {
        /*final String request = ParamsBuilder.create()
                .append("action", action)
                .append("targetId", targetId)
                .build();*/
        Map<String,Object> map=new HashMap<>();
        map.put("phone",phone);
        map.put("password",password);
        secretRequest("/Index/login/",map, callback);
    }

    /**
     phone:string
     手机号
     code:string
     验证码
     passowrd:string
     密码
     repsd:string
     重复密码

     忘记密码
     *  @param callback 回调
     */
    public static void forget(String phone,String code,String passowrd,String repsd,final HttpUtils.ResultCallback<ResultData> callback) {
        /*final String request = ParamsBuilder.create()
                .append("action", action)
                .append("targetId", targetId)
                .build();*/
        Map<String,Object> map=new HashMap<>();
        map.put("phone",phone);
        map.put("code",code);
        map.put("passowrd",passowrd);
        map.put("repsd",repsd);
        secretRequest("/Index/forget",map, callback);
    }

    /**
     phone:string
     手机号
     code:string
     验证码
     passowrd:string
     密码
     repsd:string
     重复密码

     忘记密码
     *  @param callback 回调
     */
    public static void getHome(final HttpUtils.ResultCallback<ResultData> callback) {
        /*final String request = ParamsBuilder.create()
                .append("action", action)
                .append("targetId", targetId)
                .build();*/
        Map<String,Object> map=new HashMap<>();
        secretRequest("/Index/getHome",map, callback);
    }
    /**
     cate_id:string-1
     分类id
     page:string1
     页数

     商品分类
     *  @param callback 回调
     */
    public static void getCateGoods(String cate_id,int page,final HttpUtils.ResultCallback<ResultData> callback) {
        /*final String request = ParamsBuilder.create()
                .append("action", action)
                .append("targetId", targetId)
                .build();*/
        Map<String,Object> map=new HashMap<>();
        map.put("cate_id",cate_id);
        map.put("page",page+"");

        secretRequest("/Index/getCateGoods",map, callback);
    }
}