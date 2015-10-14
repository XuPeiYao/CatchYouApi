package org.catchyou.api.converters;

import java.util.Date;
import org.json.serialization.DeserializeException;
import org.json.serialization.IJSONConverter;
import org.json.serialization.SerializeException;

public class DateConverter implements IJSONConverter{

    @Override
    public Object serialize(Object obj) throws SerializeException {
        Date Obj = (Date)obj;
        return Obj.getTime();
    }

    @Override
    public <T> T deserialize(Class<T> type, Object obj) throws DeserializeException {
        return (T)new Date((long)obj);
    }
    
}
