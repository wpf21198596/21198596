package com.fh.shop.api.utils;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IdUtil {
    public static String buildId(){
        DateTimeFormatter yyyyMMddHHmm = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        return LocalDateTime.now().format(yyyyMMddHHmm)+IdWorker.getIdStr();
    }
}
