package test_generator.unmarshaller;

import spoon.reflect.declaration.CtType;

public abstract class AbstractUnmarshaller {

    StringBuilder buf;

    AbstractUnmarshaller(StringBuilder buf){
        this.buf = buf;
    }

    public StringBuilder unmarshalString(Object source,  CtType staticClazz){
        buf.append(instantiate(source, staticClazz));
        buf.append("\n");
        buf.append(populate(source));
        return buf;
    }

    public abstract StringBuilder instantiate(Object source, CtType staticClazz);

    public abstract String populate(Object source);
    // canUnmarshal
}
