package test_generator.unmarshaller.types;

import spoon.reflect.declaration.CtType;

public abstract class AbstractUnmarshaller {

    StringBuilder buf;

    AbstractUnmarshaller(StringBuilder buf){
        this.buf = buf;
    }

    public String unmarshalString(Object source,  CtType staticClazz){
        String instantiate = instantiate(source, staticClazz);
//        buf.append("\n");
//        buf.append(populate(source));
        return instantiate;
    }

    public abstract String instantiate(Object source, CtType staticClazz);

    public abstract String populate(Object source);
    // canUnmarshal
}
