package org.catchyou.api.models;

import org.json.serialization.JSONProperty;
import org.json.serialization.JSONSerializable;


@JSONSerializable
public class FbUserInfo {
    @JSONProperty
    public String id;

    @JSONProperty
    public String gender;

    @JSONProperty
    private String first_name;
    @JSONProperty
    private String middle_name;
    @JSONProperty
    private String last_name;
    @JSONProperty
    private String name_format;

    @JSONProperty
    public KeyName hometown;

    @JSONProperty(key = "education")
    public Education[] educations;

    @JSONProperty(key = "work")
    public Work[] works;

    @JSONProperty(key = "cover")
    public Cover cover;

    public String GetName() {
        return name_format.replaceAll("\\{last\\}", SafeString(last_name)).replaceAll("\\{first\\}", SafeString(first_name)).replaceAll("\\{middle\\}", SafeString(middle_name));
    }

    private String SafeString(String Value) {
        return Value != null ? Value : "";
    }
}
