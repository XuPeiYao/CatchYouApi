package org.catchyou.api.converters;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.serialization.DeserializeException;
import org.json.serialization.IJSONConverter;
import org.json.serialization.SerializeException;

public class FbIdConverter implements IJSONConverter{

    @Override
    public Object serialize(Object obj) throws SerializeException {
        String str = (String)obj;
        byte[] strBytes = str.getBytes();
        for(int i = 0 ; i < strBytes.length ;i++)strBytes[i] = (byte) ~strBytes[i];
        return Base64.encode(strBytes);
    }

    @Override
    public <T> T deserialize(Class<T> type, Object obj) throws DeserializeException {
        try {
            String str = (String)obj;
            byte[] strBytes = Base64.decode(str);
            for(int i = 0 ; i < strBytes.length ;i++)strBytes[i] = (byte) ~strBytes[i];
            return (T) new String(strBytes);
        } catch (Base64DecodingException ex) {
            Logger.getLogger(FbIdConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void main(String... args) throws DeserializeException, SerializeException{
        System.out.println(new FbIdConverter().serialize("1085727241444815"));
    }
}
