package com.ymonnier.websocket.littleapp.utilities.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project websocket.
 * Package com.ymonnier.websocket.littleapp.utilities.date.
 * File DateFormatter.java.
 * Created by Ysee on 13/03/2017 - 15:42.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
public class DateFormatter {
    public static String dateTime() {
        Date dNow = new Date();
        SimpleDateFormat ft =
                new SimpleDateFormat("yyyy.MM.dd hh:mm");

        return ft.format(dNow);
    }
}
