package com.sda.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HelpfulUtils {

    public static final String SOMETHING_WENT_WRONG = "Something went wrong";
    public static final String INVALID_DATA = "Invalid Data";

    public static final String UNAUTHORIZED_ACCESS = "Unauthorized Access";

    private HelpfulUtils(){

    }
    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"message\":\""+responseMessage+"\"}", httpStatus);
    }

    //TODO
    public static String getUUID(){
        Date date = new Date();
        long time = date.getTime();
        return "BILL-"+time;
    }

    public static JsonNode getJsonNodeFromString(String data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(data);
    }

    public static Map<String, Object> setMapFromJson(String data) {
        if (data != null && !data.isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {});
            } catch (IOException e) {
            }
        }
        return new HashMap<>();
    }

    public static Boolean doesFileExist(String path) {
        try {
            File file = new File(path);
            return (file !=null && file.exists()) ? Boolean.TRUE:Boolean.FALSE;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
