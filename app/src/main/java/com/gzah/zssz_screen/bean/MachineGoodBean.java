package com.gzah.zssz_screen.bean;

import java.util.List;

/**
 * Created by 林佳荣 on 2018/3/29.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class MachineGoodBean {
    /**
     * code : 0
     * data : [{"image":"assets/image/market_portrait.png","number":"1","price":"3.5","introduce":"","name":"百岁泉","id":"28","stock":"77","price1":"0.04","sales_volume":"0"},{"image":"assets/image/a2.jpg","number":"1","price":"3.6","introduce":"","name":"辣子鸡","id":"53","stock":"10","price1":"0.01","sales_volume":"0"},{"image":"assets/image/a2.jpg","number":"1","price":"3.6","introduce":"","name":"辣子鸡","id":"52","stock":"10","price1":"0.01","sales_volume":"0"},{"image":"assets/image/a2.jpg","number":"1","price":"3.6","introduce":"","name":"辣子鸡","id":"51","stock":"9","price1":"0.01","sales_volume":"1"},{"image":"assets/image/a2.jpg","number":"1","price":"3.6","introduce":"","name":"辣子鸡","id":"50","stock":"9","price1":"0.01","sales_volume":"1"},{"image":"assets/image/market_portrait.png","number":"1","price":"3.5","introduce":"","name":"百岁泉","id":"49","stock":"10","price1":"0.04","sales_volume":"0"},{"image":"assets/image/market_portrait.png","number":"1","price":"3.5","introduce":"","name":"百岁泉","id":"48","stock":"10","price1":"0.04","sales_volume":"0"},{"image":"assets/image/market_portrait.png","number":"1","price":"3.5","introduce":"","name":"百岁泉","id":"47","stock":"10","price1":"0.04","sales_volume":"0"},{"image":"assets/image/market_portrait.png","number":"1","price":"3.5","introduce":"","name":"百岁泉","id":"46","stock":"10","price1":"0.04","sales_volume":"0"},{"image":"assets/image/market_portrait.png","number":"1","price":"3.5","introduce":"","name":"百岁泉","id":"45","stock":"10","price1":"0.04","sales_volume":"0"},{"image":"assets/image/market_portrait.png","number":"1","price":"3.5","introduce":"","name":"百岁泉","id":"44","stock":"10","price1":"0.04","sales_volume":"0"},{"image":"assets/image/market_portrait.png","number":"1","price":"3.5","introduce":"","name":"百岁泉","id":"42","stock":"10","price1":"0.04","sales_volume":"0"},{"image":"images/upload/2018-05-26/1527324691245.png","number":"0","price":"10.0","introduce":"124214","name":"猪肉","id":"35","stock":"40","price1":"1000.0","sales_volume":"20"},{"image":"images/upload/2018-05-26/1527324683186.png","number":"0","price":"1.0","introduce":"21321","name":"222222","id":"34","stock":"44","price1":"1.0","sales_volume":"6"},{"image":"assets/image/a1.jpg","number":"1","price":"4.5","introduce":"","name":"果粒橙","id":"33","stock":"100","price1":"0.02","sales_volume":"0"},{"image":"assets/image/a2.jpg","number":"1","price":"3.6","introduce":"","name":"辣子鸡","id":"54","stock":"10","price1":"0.01","sales_volume":"0"},{"image":"assets/image/market_portrait.png","number":"1","price":"3.5","introduce":"","name":"百岁泉","id":"43","stock":"10","price1":"0.04","sales_volume":"0"}]
     * count : 17
     */

    private String code;
    private int count;
    private List<MachineGoodBean.DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<MachineGoodBean.DataBean> getData() {
        return data;
    }

    public void setData(List<MachineGoodBean.DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * image : assets/image/market_portrait.png
         * number : 1
         * price : 3.5
         * introduce :
         * name : 百岁泉
         * id : 28
         * stock : 77
         * price1 : 0.04
         * sales_volume : 0
         */

        private String image;
        private String number;
        private String price;
        private String introduce;
        private String name;
        private String id;
        private String stock;
        private String price1;
        private String sales_volume;
        private int count =0;

        @Override
        public String toString() {
            return "DataBean{" +
                    "image='" + image + '\'' +
                    ", number='" + number + '\'' +
                    ", price='" + price + '\'' +
                    ", introduce='" + introduce + '\'' +
                    ", name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    ", stock='" + stock + '\'' +
                    ", price1='" + price1 + '\'' +
                    ", sales_volume='" + sales_volume + '\'' +
                    ", count=" + count +
                    '}';
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getPrice1() {
            return price1;
        }

        public void setPrice1(String price1) {
            this.price1 = price1;
        }

        public String getSales_volume() {
            return sales_volume;
        }

        public void setSales_volume(String sales_volume) {
            this.sales_volume = sales_volume;
        }
    }

}
