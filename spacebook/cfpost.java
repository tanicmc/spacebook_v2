package com.example.spacebook;

import java.util.HashMap;
import java.util.Map;

public class cfpost {

String topic,content,uid,fund,price;

    public cfpost() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public cfpost(String topic, String content, String uid, String fund,String price) {
        this.topic = topic;
        this.content = content;
        this.uid = uid;
        this.fund = fund;
        this.price = price;


    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public String getFund() {
        return fund;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("topic", topic);
        result.put("content", content);
        result.put("Funding", fund);
        result.put("Price",price);


        return result;
    }
}


