package com.chatapp;

/**
 * Created by thanhnguyen on 12/17/16.
 */

public interface Config {
    String BASE_URL = "http://appname.wwwebapp.com";
    String URL_ABOUT = BASE_URL + "/home/about";
    String URL_TERM = BASE_URL + "/home/termsofuser";
    String URL_PRIVACY = BASE_URL + "/home/privacypolicy";

    int LIMIT_ADD_FRIEND_GREETING_CHARACTER = 50;
    int PASSWORD_MIN_LENGTH = 8;
}
