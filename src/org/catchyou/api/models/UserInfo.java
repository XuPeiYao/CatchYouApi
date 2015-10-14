package org.catchyou.api.models;

import org.json.serialization.JSONProperty;
import org.json.serialization.JSONSerializable;

@JSONSerializable
public class UserInfo {
    @JSONProperty
    public String uid;

    @JSONProperty
    public FbUserInfo info;

    @JSONProperty
    public String mac;
}
