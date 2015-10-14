package org.catchyou.api.models;

import org.json.serialization.JSONProperty;
import org.json.serialization.JSONSerializable;

@JSONSerializable
public class Work {
    @JSONProperty
    public KeyName employer;

    @JSONProperty
    public KeyName position;

    @JSONProperty(key = "start_date")
    public String startDate;

    @JSONProperty(key = "end_date")
    public String endDate;
}
