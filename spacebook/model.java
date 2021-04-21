package com.example.spacebook;

public class model
{
    String Funding,content,topic,uid,price;
    model()
    {

    }


    public model(String Funding, String content, String topic, String uid, String price) {
        this.Funding = Funding;
        this.content = content;
        this.topic = topic;
        this.uid = uid;
        this.price = price;

    }

    public String getFunding() {
        return Funding;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setFunding(String Funding) {
        this.Funding = Funding;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
