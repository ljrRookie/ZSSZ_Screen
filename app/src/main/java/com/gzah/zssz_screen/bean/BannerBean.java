package com.gzah.zssz_screen.bean;

import java.util.List;

/**
 * Created by 林佳荣 on 2018/5/31.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class BannerBean {

    /**
     * code : 0
     * data : [{"image":"images//upload//2018-05-28//205650IMG_20180528_174043.jpg,images//upload//2018-05-28//753268IMG_20180528_174105.jpg","id":"2","time":"2018-05-31","state":"1","title":"1111","content":"3333"},{"image":"images//upload//2018-05-26//2136081527324464178.png,images//upload//2018-05-26//3218951527324464179.png,images//upload//2018-05-26//9584581527324464179.png","id":"3","time":"2018-05-31","state":"1","title":"阿甘可根据肌肤","content":"电话和大家风范绝对反弹"}]
     */

    private String code;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * image : images//upload//2018-05-28//205650IMG_20180528_174043.jpg,images//upload//2018-05-28//753268IMG_20180528_174105.jpg
         * id : 2
         * time : 2018-05-31
         * state : 1
         * title : 1111
         * content : 3333
         */

        private String image;
        private String id;
        private String time;
        private String state;
        private String title;
        private String content;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
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
    }
}
