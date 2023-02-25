package com.gx.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.*;

import java.lang.reflect.Type;

@TableName("tbl_book")
//lombok 自动写setter等方法
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String name;
    private String type;
    private String description;
    @Version   //乐观锁
    private Integer version;
    
}
