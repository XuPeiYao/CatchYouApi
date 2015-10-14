package org.catchyou.api.models;

import org.json.serialization.JSONProperty;
import org.json.serialization.JSONSerializable;

@JSONSerializable
public class Education {
    @JSONProperty
    public KeyName school;

    @JSONProperty
    public String type;

    @JSONProperty
    public KeyName year;

    @JSONProperty(key = "concentration")
    public KeyName[] concentrations;
}
