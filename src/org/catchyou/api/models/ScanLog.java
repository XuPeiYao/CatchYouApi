package org.catchyou.api.models;

import org.json.serialization.JSONProperty;
import org.json.serialization.JSONSerializable;

import java.text.SimpleDateFormat;
import java.util.Date;

@JSONSerializable
public class ScanLog {
    @JSONProperty
    public String id;

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
