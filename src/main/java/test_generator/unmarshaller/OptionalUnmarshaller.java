package test_generator.unmarshaller;

import spoon.reflect.declaration.CtType;

import java.util.Optional;

public class OptionalUnmarshaller extends AbstractUnmarshaller{

    OptionalUnmarshaller(StringBuilder buf) {
        super(buf);
    }

    @Override
    public StringBuilder instantiate(Object source, CtType staticClazz) {
        Optional typedSource = (Optional) source;
        if(typedSource.isEmpty()){
            buf.append("Optional.empty();");
        } else {
            UnmarshalledVariable uv = new UnmarshalledVariable(typedSource.get());
            uv.unmarshal(buf);
            buf.append("Optional.of(" + uv.getInlineOrVariable(buf) + ")");
        }
        return buf;
    }

    @Override
    public String populate(Object source) {
        return null;
    }
}
