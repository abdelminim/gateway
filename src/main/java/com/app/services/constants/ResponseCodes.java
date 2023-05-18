/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.services.constants;

import com.app.services.util.Value;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 *
 * @author Saeed Fathi
 */
public class ResponseCodes {

    private ResponseCodes() {
    }

    @Value(Value = "success")
    public static final int success = 200;

    @Value(Value = "OriginatingChannel is Required field")
    public static final int originatingChannelIsNull = 301;

    @Value(Value = "حدث خطأ أثناء التنفيذ")
    public static final int unHandeledException = 500;

    @Value(Value = "خطأ فى رقم المحمول أو كلمة المرور")
    public static final int badCredentials = 900;

    @Value(Value = "المستخدم غير مفعل")
    public static final int disabledCredentials = 901;

    @Value(Value = "لا يوجد خدمة متاحة على الرابط")
    public static final int pathNotFound = 902;

    @Value(Value = "رمز الدخول غير صحيح")
    public static final int invalidToken = 903;

    @Value(Value = "صلاحيات غير كافية")
    public static final int notEnoughPrivileges = 904;

    @Value(Value = "لا يمكن تمديد فترة أنتهاء رمز الدخول")
    public static final int cantRefreshToken = 905;

    @Value(Value = "رقم المحمول غير صحيح")
    public static final String usernameIncorrect = "906";

    @Value(Value = "كلمة المرور غير صحيحة")
    public static final String passwordIncorrect = "907";

    @Value(Value = "كلمة المرور الجديدة غير صحيحة")
    public static final String newPasswordIncorrect = "908";

    @Value(Value = "كلمة المرور القديمة غير صحيحة")
    public static final String oldPasswordIncorrect = "909";

    @Value(Value = "نوع التطبيق غير صحيح")
    public static final String appTypeIncorrect = "910";

    @Value(Value = "غير مسموح بتغيير كلمة المرور")
    public static final int authorityNotAllowChangePassword = 911;

    @Value(Value = "غير مسموح بعرض بيانات المستخدم")
    public static final int authorityNotAllowUserData = 912;

    public static String getDesc(int constantVlaue) {
        String Desc = "";
        Field[] interfaceFields = ResponseCodes.class.getFields();
        for (Field f : interfaceFields) {
            try {
                if (Integer.valueOf(f.get(null).toString()) == constantVlaue) {
                    Annotation annotation = f.getAnnotation(Value.class);
                    if (annotation instanceof Value) {
                        Value objValue = (Value) annotation;
                        Desc = objValue.Value();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Desc;

    }

}
