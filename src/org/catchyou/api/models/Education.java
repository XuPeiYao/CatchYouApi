package org.catchyou.api.models;

import org.json.serialization.JSONProperty;
import org.json.serialization.JSONSerializable;

@JSONSerializable
public class Education {
    @JSONProperty(key = "school")
    public KeyName school;

    @JSONProperty(key = "type")
    public String type;

    @JSONProperty(key = "year")
    public KeyName year;

    @JSONProperty(key = "concentration")
    public KeyName[] concentrations;
}
