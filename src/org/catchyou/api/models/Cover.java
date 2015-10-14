/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.catchyou.api.models;

import org.json.serialization.JSONProperty;
import org.json.serialization.JSONSerializable;

@JSONSerializable
public class Cover {
    @JSONProperty(key = "id")
    public String id;

    @JSONProperty(key = "offset_y")
    public int offset_y;

    @JSONProperty(key = "source")
    public String source;
}
