package com.jermyn.hima.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 🙏🏻 GOD BLESS MY CODE ！
 *
 * @author CHCNAV BDZH Jermyn on 2022/3/26
 */
public class TinyTools {
    public static String convertTimestamp2Date(Long timestamp, String pattern) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(new Date(timestamp));
    }
}
