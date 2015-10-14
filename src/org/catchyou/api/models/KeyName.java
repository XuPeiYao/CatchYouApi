package org.catchyou.api.models;

import org.json.serialization.JSONProperty;
import org.json.serialization.JSONSerializable;

@JSONSerializable
public class KeyName {
    @JSONProperty
    public String id;

    @JSONProperty
    public String name;
}
