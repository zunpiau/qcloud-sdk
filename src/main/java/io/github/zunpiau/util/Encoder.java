package io.github.zunpiau.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encoder {

    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode (s, StandardCharsets.UTF_8.name ());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException (e);
        }
    }

    public static String Base64Encode(byte[] bytes) {
        return Base64.getEncoder ().encodeToString (bytes);
    }

    public static String replaceUnderscores(String s) {
        return s.replace('_', '.');
    }

}
