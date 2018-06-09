package com.main.hooker.hooker.entity;

import java.util.List;

public class Book {

    /**
     * code : 200
     * msg : success
     * data : [{"id":12,"title":"Hello, Hell","type":"Horror","desc":"wd","author_id":3,"cover_img":null,"items_count":null,"view_count":null,"favor_count":null,"score_count":null,"score_total":null,"create_time":"1970-01-01 08:00:00","update_time":"1970-01-01 08:00:00"},{"id":3,"title":"Test","type":"Horror","desc":"A scary Test","author_id":3,"cover_img":"no img","items_count":null,"view_count":0,"favor_count":1,"score_count":null,"score_total":null,"create_time":"1970-01-01 08:00:00","update_time":"1970-01-01 08:00:00"},{"id":2,"title":"Happiness","type":"Romance","desc":"A happy marriage","author_id":3,"cover_img":"no img again","items_count":5,"view_count":30,"favor_count":41,"score_count":null,"score_total":null,"create_time":"1970-01-01 08:00:00","update_time":"1970-01-01 08:00:00"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 12
         * title : Hello, Hell
         * type : Horror
         * desc : wd
         * author_id : 3
         * cover_img : null
         * items_count : null
         * view_count : null
         * favor_count : null
         * score_count : null
         * score_total : null
         * create_time : 1970-01-01 08:00:00
         * update_time : 1970-01-01 08:00:00
         */

        private int id;
        private String title;
        private String type;
        private String desc;
        private int author_id;
        private Object cover_img;
        private Object items_count;
        private Object view_count;
        private Object favor_count;
        private Object score_count;
        private Object score_total;
        private String create_time;
        private String update_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getAuthor_id() {
            return author_id;
        }

        public void setAuthor_id(int author_id) {
            this.author_id = author_id;
        }

        public Object getCover_img() {
            return cover_img;
        }

        public void setCover_img(Object cover_img) {
            this.cover_img = cover_img;
        }

        public Object getItems_count() {
            return items_count;
        }

        public void setItems_count(Object items_count) {
            this.items_count = items_count;
        }

        public Object getView_count() {
            return view_count;
        }

        public void setView_count(Object view_count) {
            this.view_count = view_count;
        }

        public Object getFavor_count() {
            return favor_count;
        }

        public void setFavor_count(Object favor_count) {
            this.favor_count = favor_count;
        }

        public Object getScore_count() {
            return score_count;
        }

        public void setScore_count(Object score_count) {
            this.score_count = score_count;
        }

        public Object getScore_total() {
            return score_total;
        }

        public void setScore_total(Object score_total) {
            this.score_total = score_total;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }
}
