package com.thinhlp.cocshopapp.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by anhkh0a on 7/8/17.
 */

public class RegisterError {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("messages")
    @Expose
    private List<String> messages = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

}
