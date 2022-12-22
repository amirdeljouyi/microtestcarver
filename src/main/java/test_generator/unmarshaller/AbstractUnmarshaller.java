package test_generator.unmarshaller;

import spoon.reflect.declaration.CtType;

public abstract class AbstractUnmarshaller {

    StringBuilder buf;

    AbstractUnmarshaller(StringBuilder buf){
        this.buf = buf;
    }

    public StringBuilder unmarshalString(Object source,  CtType staticClazz){
        String instantiate = instantiate(source, staticClazz);
        System.out.println("instantiate: " + instantiate);
        buf.append(instantiate);
//        buf.append("\n");
//        buf.append(populate(source));
        return buf;
    }

    public abstract String instantiate(Object source, CtType staticClazz);

    public abstract String populate(Object source);
    // canUnmarshal
}
