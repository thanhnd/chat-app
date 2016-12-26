package com.chatapp.utils;

/**
 * Created by thanhnguyen on 12/27/16.
 */

public class SringUtil {
    public static String toASCII(String s) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            switch (chars[i]) {
                case 'â':
                case 'ấ':
                case 'ầ':
                case 'ậ':
                case 'ẩ':
                case 'ẫ':
                case 'ă':
                case 'ắ':
                case 'ằ':
                case 'ẳ':
                case 'ẵ':
                case 'ặ':
                case 'á':
                case 'à':
                case 'ạ':
                case 'ã':
                case 'ả':
                    chars[i] = 'a';
                    break;
                case 'ô':
                case 'ố':
                case 'ồ':
                case 'ộ':
                case 'ổ':
                case 'ó':
                case 'ò':
                case 'ỏ':
                case 'ọ':
                case 'õ':
                case 'ỗ':
                case 'ơ':
                case 'ở':
                case 'ờ':
                case 'ợ':
                case 'ỡ':
                case 'ớ':
                    chars[i] = 'o';
                    break;
                case 'ị':
                case 'ì':
                case 'í':
                case 'ỉ':
                case 'ĩ':
                    chars[i] = 'i';
                    break;
                case 'ú':
                case 'ù':
                case 'ụ':
                case 'ũ':
                case 'ủ':
                case 'ư':
                case 'ự':
                case 'ứ':
                case 'ừ':
                case 'ữ':
                case 'ử':
                    chars[i] = 'u';
                    break;
                case 'é':
                case 'è':
                case 'ẹ':
                case 'ẻ':
                case 'ẽ':
                case 'ê':
                case 'ề':
                case 'ệ':
                case 'ế':
                case 'ể':
                case 'ễ':
                    chars[i] = 'e';
                    break;
                case 'đ':
                    chars[i] = 'd';
                    break;
                case 'ý':
                case 'ỵ':
                case 'ỹ':
                case 'ỷ':
                case 'ỳ':
                    chars[i] = 'y';
                    break;
            }
        }
        return new String(chars);
    }
}
