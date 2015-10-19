/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.catchyou.api.models;

import java.io.Serializable;
import org.json.serialization.JSONProperty;
import org.json.serialization.JSONSerializable;

@JSONSerializable
public class Cover  implements Serializable{
    @JSONProperty
    public String id;

    @JSONProperty
    public int offset_y;

    @JSONProperty
    public String source;
}
