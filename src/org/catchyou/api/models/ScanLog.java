package org.catchyou.api.models;

import java.io.Serializable;
import org.json.serialization.JSONProperty;
import org.json.serialization.JSONSerializable;

import java.text.SimpleDateFormat;
import java.util.Date;

@JSONSerializable
public class ScanLog implements Serializable {
    @JSONProperty
    public String id;

    @JSONProperty
    public UserInfo source;

    @JSONProperty
    public UserInfo target;

    @JSONProperty
    public long time;

    public String getTimeString(){return getTimeString("yyyy-MM-dd HH:mm:ss");}
    public String getTimeString(String format) {
        Date time_ = new Date(time);
        return new SimpleDateFormat(format).format(time_);
    }
}
