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

    public String getName(String Default) {
        if(name_format == null)return Default;
        return name_format.replaceAll("\\{last\\}", safeString(last_name)).replaceAll("\\{first\\}", safeString(first_name)).replaceAll("\\{middle\\}", safeString(middle_name));
    }

    public Object getPhotoSticker(Object Default){
        if(id==null)return Default;
        return "https://graph.facebook.com/v2.3/"+ id +"/picture?height=256&width=256";
    }
    
    private String safeString(String Value) {
        return Value != null ? Value : "";
    }
}
