package com.gx.utils;

import java.util.UUID;


public class UUIDUtil {

   //UUID 是 通用唯一识别码（Universally Unique Identifier）的缩写
   public static String uuid() {
      return UUID.randomUUID().toString().replace("-", "");
   }

}