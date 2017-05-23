package com.zipingfang.jindiexuan.module_home.model;

import java.util.List;

/**
 *
 * Created by Administrator on 2017/5/23.
 */

public class HomeModel {
    private List<LunboBean> lunbo;
    private List<ZixunBean> zixun;
    private List<CateBean> cate;

    public List<LunboBean> getLunbo() {
        return lunbo;
    }

    public void setLunbo(List<LunboBean> lunbo) {
        this.lunbo = lunbo;
    }
    public List<ZixunBean> getZixun() {
        return zixun;
    }
    public void setZixun(List<ZixunBean> zixun) {
        this.zixun = zixun;
    }
    public List<CateBean> getCate() {
        return cate;
    }

    public void setCate(List<CateBean> cate) {
        this.cate = cate;
    }
    public static class LunboBean {
        /**
         * id : 1
         * title : 测试
         * content : 测试测试测试
         * url :
         * pic : /data/upload/images/2017-05-23/111.png
         */
        private String id;
        private String title;
        private String content;
        private String url;
        private String pic;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }

    public static class ZixunBean {
        /**
         * id : 1
         * title : 测试
         * content : 测试测试测试测试
         * pic : /data/upload/images/2017-05-23/111.png
         * create_time : 1495504503
         */

        private String id;
        private String title;
        private String content;
        private String pic;
        private String create_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }

    public static class CateBean {
        /**
         * cate_id : -1
         * cate_name : 热销
         */
        private int cate_id;
        private String cate_name;

        public int getCate_id() {
            return cate_id;
        }
        public void setCate_id(int cate_id) {
            this.cate_id = cate_id;
        }

        public String getCate_name() {
            return cate_name;
        }

        public void setCate_name(String cate_name) {
            this.cate_name = cate_name;
        }
    }
}
