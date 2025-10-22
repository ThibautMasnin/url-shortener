package com.pct.urlshortener.util;

import org.springframework.stereotype.Component;

@Component
public class Base62Encoder {

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String encode(Long number) {
        StringBuilder codeBuilder = new StringBuilder();

        if (number == 0) {
            return "0";
        }

        while (number > 0) {
            char nextChar = BASE62.charAt((int) (number % 62));
            codeBuilder.append(nextChar);
            number /= 62;
        }

        return codeBuilder.reverse().toString();
    }
}
