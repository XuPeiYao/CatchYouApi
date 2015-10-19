package org.catchyou.api.converters;

import android.util.Base64;
import org.json.serialization.DeserializeException;
import org.json.serialization.IJSONConverter;
import org.json.serialization.SerializeException;

public class FbIdConverter implements IJSONConverter{

    @Override
    public Object serialize(Object obj) throws SerializeException {
        String str = (String)obj;
        byte[] strBytes = str.getBytes();
        for(int i = 0 ; i < strBytes.length ;i++)strBytes[i] = (byte) ~strBytes[i];
        return Base64.encode(strBytes,Base64.DEFAULT);
    }

    @Override
    public <T> T deserialize(Class<T> type, Object obj) throws DeserializeException {
        String str = (String)obj;
        byte[] strBytes = Base64.decode(str,Base64.DEFAULT);
        for(int i = 0 ; i < strBytes.length ;i++)strBytes[i] = (byte) ~strBytes[i];
        return (T) new String(strBytes);
    }
    
    public static void main(String... args) throws DeserializeException, SerializeException{
        System.out.println(new FbIdConverter().serialize("1085727241444815"));
    }
}
