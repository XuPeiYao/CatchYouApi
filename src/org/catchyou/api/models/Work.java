package org.catchyou.api.models;

import org.json.serialization.JSONProperty;
import org.json.serialization.JSONSerializable;

@JSONSerializable
public class Work {
    @JSONProperty(key = "employer")
    public KeyName employer;

    @JSONProperty(key = "position")
    public KeyName position;

    @JSONProperty(key = "start_date")
    public String startDate;

    @JSONProperty(key = "end_date")
    public String endDate;
}
