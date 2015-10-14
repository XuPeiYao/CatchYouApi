package org.catchyou.api.models;

import org.json.serialization.JSONProperty;
import org.json.serialization.JSONSerializable;

@JSONSerializable
public class KeyName {
    @JSONProperty(key = "id")
    public String id;

    @JSONProperty(key = "name")
    public String name;
}
