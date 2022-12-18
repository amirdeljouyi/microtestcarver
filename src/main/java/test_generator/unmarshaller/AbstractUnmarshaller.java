package test_generator.unmarshaller;

public abstract class AbstractUnmarshaller {

    StringBuilder buf;

    AbstractUnmarshaller(StringBuilder buf){
        this.buf = buf;
    }

    public StringBuilder unmarshalString(Object source){
        buf.append(instantiate(source));
        buf.append("\n");
        buf.append(populate(source));
        return buf;
    }

    public abstract String instantiate(Object source);

    public abstract String populate(Object source);
    // canUnmarshal
}
