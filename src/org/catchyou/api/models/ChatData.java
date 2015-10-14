package org.catchyou.api.models;

import org.json.serialization.JSONProperty;
import org.json.serialization.JSONSerializable;

import java.text.SimpleDateFormat;
import java.util.Date;

@JSONSerializable
public class ChatData {
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

    public String getTimeString() {
        Date time_ = new Date(time);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time_);
    }
}
