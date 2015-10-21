package org.catchyou.api.models;

import java.io.Serializable;
import org.json.serialization.JSONProperty;
import org.json.serialization.JSONSerializable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.json.serialization.DeserializeException;
import org.json.serialization.JSONConvert;

@JSONSerializable
public class ChatData implements Serializable {
    @JSONProperty
    public ChatType type;

    @JSONProperty
    public Object content;

    @JSONProperty
    public String sourceUId;

    @JSONProperty
    public String targetUId;

    @JSONProperty
    public long time;

    public UserInfo getRequestInfoContent(){
        try {
            return JSONConvert.deserialize(UserInfo.class, content);
        } catch (DeserializeException ex) {
            Logger.getLogger(ChatData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean isNullObject(){
        return this.content.equals(JSONObject.NULL);
    }
    
    public String getTimeString(){return getTimeString("yyyy-MM-dd HH:mm:ss");}
    public String getTimeString(String format) {
        Date time_ = new Date(time);
        return new SimpleDateFormat(format).format(time_);
    }
}
