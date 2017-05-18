package com.xilada.xldutils.event;

public class EventTag {

    public static class What {
        public static final int START_CYCLING = 0x1001;//开始骑行
        public static final int UN_LOCKING = 0x1002;//正在开锁
        public static final int NOTICE_UN_LOCKING = 0x1003;//正在开锁收到锁打开通知
        public static final int NOTICE_CLOSE_AND_LOCK = 0x1004;//关锁通知
        public static final int NOTICE_UN_LOCKING_HOME = 0x1005;//开锁成功通知首页开锁
        public static final int FREEZE_USER_ACCOUNT = 0x1006;//账号禁用
        public static final int FREEZE_USER_STATUS = 0x1009;//账号认证状态
        public static final int CLEAR_SUCCESS = 0x1007;//清除缓存成功
        public static final int USER_OUT_LOGIN = 0x1008;//退出登录
    }
    public static class arg1 {


    }
    public static class arg2 {

    }
}
