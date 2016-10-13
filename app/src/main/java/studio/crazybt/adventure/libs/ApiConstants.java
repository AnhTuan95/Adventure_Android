package studio.crazybt.adventure.libs;

import android.net.Uri;


/**
 * Created by Brucelee Thanh on 07/10/2016.
 */

public class ApiConstants {

    public Uri.Builder builder;

    // Request code & response code
    public static final int RESPONSE_CODE_SUCCESS = 200;

    // API Urls
    public static final String API_ROOT = "161.202.24.102:22896/api";
    public static final String API_SCHEME = "http";
    public static final String API_NORMAL_SIGNUP = "user/sign_up";
    public static final String API_NORMAL_LOGIN = "user/login";
    public static final String API_UPLOAD_IMAGE = "file/upload_image";
    public static final String API_CREATE_STATUS = "status/create";

    // Default params
    public static final String DEF_CODE = "code";
    public static final String DEF_MSG = "message";
    public static final String DEF_DATA = "data";

    // Key params

    // user params
    public static final String KEY_PHONE_NUMBER_EMAIL = "phone_number_email";
    public static final String KEY_PHONE_NUMBER = "phone_number";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_FILE = "file";
    public static final String KEY_LINK = "link";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_IMAGE_DESCRIPTION = "image_description";
    public static final String KEY_TYPE = "type";
    public static final String KEY_URL = "url";
    public static final String KEY_DESCRIPTION = "description";

    public ApiConstants() {
    }

    public void setBaseUrl(){
        builder = new Uri.Builder();
        builder.scheme(API_SCHEME).encodedAuthority(API_ROOT);
    }

    public Uri.Builder getBaseApi(){
        this.setBaseUrl();
        return builder;
    }

    public Uri.Builder getApi(String path){
        this.setBaseUrl();
        if(path.contains("/")){
            builder.appendEncodedPath(path);
        }else{
            builder.appendPath(path);
        }
        return builder;
    }

}
