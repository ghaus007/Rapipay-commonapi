package com.rapipay.commonapi.validations;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateValidations  {

	public  Date validateAndParseDateJava7(String dateString, String dateFormat) {
        Date date = null;
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
	
}
