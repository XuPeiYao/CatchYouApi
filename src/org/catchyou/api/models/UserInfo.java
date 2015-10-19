package org.catchyou.api.models;

import java.io.Serializable;
import org.json.serialization.JSONProperty;
import org.json.serialization.JSONSerializable;

@JSONSerializable
public class UserInfo implements Serializable {
    @JSONProperty
    public String uid;

    @JSONProperty
    public FbUserInfo info;

    @JSONProperty
    public String mac;
}
