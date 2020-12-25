package com.motorcycle.exception;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {

        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);

        Object timestamp = errorAttributes.get("timestamp");

        if (timestamp == null) {
            errorAttributes.put("timestamp", dateFormat.format(OffsetDateTime.now()));
        } else {
            errorAttributes.put("timestamp", dateFormat.format(timestamp));
        }

        errorAttributes.put("motorcycle version", "1.1");

        return errorAttributes;
    }
}
