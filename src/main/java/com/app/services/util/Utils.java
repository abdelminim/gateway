/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.services.util;

import com.app.services.entity.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.validation.Validation;
import javax.validation.Validator;
import org.apache.commons.io.IOUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

/**
 *
 * @author A.shokier
 */
public class Utils {

    private Utils() {
    }

    private static final Validator javaxValidator = Validation.buildDefaultValidatorFactory().getValidator();
    private static final SpringValidatorAdapter validator = new SpringValidatorAdapter(javaxValidator);

    public static String mapObject(Object object) {
        String value;
        ObjectMapper mapper = new ObjectMapper();
        try {
            value = mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            value = "";
        }
        return value;
    }

    public static JsonNode mapJson(String value) throws JsonProcessingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(value);
        return actualObj;
    }

    public static Response getResponse(String value) throws JsonProcessingException, IOException {
        JsonNode jsonNode = mapJson(value);
        return new Response(jsonNode.get("responseCode").asInt(), jsonNode.get("responseMsg").asText());
    }

    public static boolean checkCardCode(String card) {
        if (card == null) {
            return false;
        }

        if (card.length() != 12) {
            return false;
        }

        for (char ca : card.toCharArray()) {
            if (!Character.isDigit(ca)) {
                return false;
            }
        }

        return true;
    }

    public static boolean checkNid(String nid) {
        if (nid == null) {
            return false;
        }

        if (nid.length() != 14) {
            return false;
        }

        for (char ca : nid.toCharArray()) {
            if (!Character.isDigit(ca)) {
                return false;
            }
        }

        return true;
    }

    public static boolean checkMobileNo(String mob) {
        if (mob == null) {
            return false;
        }

        if (mob.length() != 11) {
            return false;
        }

        for (char c : mob.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        if (!mob.startsWith("010") && !mob.startsWith("011") && !mob.startsWith("012") && !mob.startsWith("015")) {
            return false;
        }

        return true;

    }

    public static boolean checkName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        String s = name.trim().replaceAll("\\s+", "");

        for (int i = 0; i < s.length();) {
            int c = s.codePointAt(i);
            //range of arabic chars/symbols is from 0x0600 to 0x06ff
            //the arabic letter '��' is special case having the range from 0xFE70 to 0xFEFF
            if (c >= 0x0600 && c <= 0x06FF || (c >= 0xFE70 && c <= 0xFEFF)) {
                i += Character.charCount(c);
            } else {
                return false;
            }

        }

        String[] arr = s.trim().split(" ");

        for (String ss : arr) {

            char[] c = ss.toCharArray();

            for (int i = 1; i < c.length - 1; i++) {
                char current = c[i];
                char after = c[i + 1];
                char before = c[i - 1];
                if ((current == before) && (current == after)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int validate(Object entry) {
        int response = 200;
        Errors errors = new BeanPropertyBindingResult(entry, entry.getClass().getName());
        validator.validate(entry, errors);
        if (!errors.getAllErrors().isEmpty()) {
            response = Integer.valueOf(errors.getAllErrors().get(0).getDefaultMessage());
        }
        return response;
    }

    public static String extractOriginatingChannel(InputStream postBody) {

        String originatingChannel = "";
        try {
            JsonNode jsonNode = mapJson(new String(IOUtils.toByteArray(postBody), StandardCharsets.UTF_8));
            originatingChannel = jsonNode.get("Message").get("Header").get("originatingChannel").asText();
        } catch (Exception e) {
        }
        return originatingChannel;
    }

}
