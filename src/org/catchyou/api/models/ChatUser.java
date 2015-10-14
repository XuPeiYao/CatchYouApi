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

    public String getTimeString() {
        Date time_ = new Date(time);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time_);
    }
}
