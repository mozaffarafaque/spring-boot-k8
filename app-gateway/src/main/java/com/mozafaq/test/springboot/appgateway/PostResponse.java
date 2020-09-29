package com.mozafaq.test.springboot.appgateway;

public class PostResponse<T> {

    private long id;
    private T content;

    public PostResponse(long id, T content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
