package org.catchyou.api.models;

import java.io.Serializable;
import org.json.serialization.JSONProperty;
import org.json.serialization.JSONSerializable;

@JSONSerializable
public class KeyName implements Serializable {
    @JSONProperty
    public String id;

    @JSONProperty
    public String name;
}
