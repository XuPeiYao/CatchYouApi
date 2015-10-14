package org.catchyou.api.models;

import org.json.serialization.JSONProperty;
import org.json.serialization.JSONSerializable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by XuPeiYao on 2015/10/14.
 */
@JSONSerializable
public class ChatUser {
    @JSONProperty
    public String sourceUId;

    @JSONProperty
    public String targetUId;

    @JSONProperty
    public UserInfo source;

    @JSONProperty
    public UserInfo target;

    @JSONProperty
    public long time;

    public String getTargetName(String Default) {
        if(this.target == null)return Default;
        return this.target.info.getName();
    }
    
    public String getSourceName(String Default) {
        if(this.source == null)return Default;
        return this.source.info.getName();
    }
    
    public Object getTargetPhotoSticker(Object Default){
        if(this.target ==null)return Default;
        return "https://graph.facebook.com/v2.3/"+ this.target.info.id +"/picture?height=256&width=256";
    }

    public Object getSourcePhotoSticker(Object Default){
        if(this.source ==null)return Default;
        return "https://graph.facebook.com/v2.3/"+ this.source.info.id +"/picture?height=256&width=256";
    }
    public String getTimeString() {
        Date time_ = new Date(time);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time_);
    }
}
