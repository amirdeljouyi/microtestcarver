package test_generator.unmarshaller;

import spoon.reflect.declaration.CtType;

import java.util.Optional;

public class OptionalUnmarshaller extends AbstractUnmarshaller{

    OptionalUnmarshaller(StringBuilder buf) {
        super(buf);
    }

    @Override
    public String instantiate(Object source, CtType staticClazz) {
        Optional typedSource = (Optional) source;
        if(typedSource.isEmpty()){
            return ("Optional.empty();");
        } else {
            UnmarshalledVariable uv = new UnmarshalledVariable(typedSource.get(), staticClazz);
            uv.unmarshal(buf);
            return ("Optional.of(" + uv.getInlineOrVariable(buf) + ")");
        }
    }

    @Override
    public String populate(Object source) {
        return null;
    }
}
