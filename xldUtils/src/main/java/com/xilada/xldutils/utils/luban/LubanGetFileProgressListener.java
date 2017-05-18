package com.xilada.xldutils.utils.luban;

import java.io.File;

/**
 * Created by Administrator on 2017/3/1.
 */
public interface LubanGetFileProgressListener {
    void onStart();
    void onError(Throwable throwable);
    void onSuccess(File file);
}
