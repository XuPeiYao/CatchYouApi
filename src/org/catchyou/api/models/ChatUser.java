package org.catchyou.api.models;

import java.io.Serializable;
import org.json.serialization.JSONProperty;
import org.json.serialization.JSONSerializable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by XuPeiYao on 2015/10/14.
 */
@JSONSerializable
public class ChatUser implements Serializable{
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
        return this.target.info.getName(Default);
    }
    
    public String getSourceName(String Default) {
        if(this.source == null)return Default;
        return this.source.info.getName(Default);
    }
    
    public Object getTargetPhotoSticker(Object Default){
        if(this.target ==null)return Default;
        return this.target.info.getPhotoSticker(Default);
    }

    public Object getSourcePhotoSticker(Object Default){
        if(this.source == null)return Default;
        return this.source.info.getPhotoSticker(Default);
    }
    
    public String getTimeString() {
        Date time_ = new Date(time);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time_);
    }
}
