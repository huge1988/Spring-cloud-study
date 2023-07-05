package com.huge.provider.entity;

import lombok.Data;

/**
 * @author huge1988
 * @date 2023/6/28 - 14:28
 */
@Data
public class Dept {

    private Integer id;

    private String name;

    private String remark;

    private String service;

    public Dept() {
    }

    public Dept(Integer id, String name, String remark,String service) {
        this.id = id;
        this.name = name;
        this.remark = remark;
        this.service = service;
    }
}

