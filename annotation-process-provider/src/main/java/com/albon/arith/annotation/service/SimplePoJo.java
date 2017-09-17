package com.albon.arith.annotation.service;

import com.albon.arith.annotation.procecssor.AutoParse;

import java.util.List;

/**
 * Created by albon on 2017/9/17.
 */
public class SimplePoJo {

    @AutoParse(key = "first")
    private Integer first;
    @AutoParse(key = "second")
    private String second;
    @AutoParse(key = "third")
    private List<String> third;

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public List<String> getThird() {
        return third;
    }

    public void setThird(List<String> third) {
        this.third = third;
    }
}
