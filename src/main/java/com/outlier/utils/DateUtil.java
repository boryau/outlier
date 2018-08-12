package com.outlier.utils;

import com.outlier.constants.GlobalConstants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String getCurrentDateString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GlobalConstants.DATE_FORMAT);
        LocalDateTime localDateTime = LocalDateTime.now();
        String time = formatter.format(localDateTime);
        return  time;
    }
}
