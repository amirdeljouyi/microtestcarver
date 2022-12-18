package test_generator.unmarshaller;

import java.util.Optional;

public class OptionalUnmarshaller extends AbstractUnmarshaller{

    OptionalUnmarshaller(StringBuilder buf) {
        super(buf);
    }

    @Override
    public String instantiate(Object source) {
        Optional typedSource = (Optional) source;
        if(typedSource.isEmpty()){
            return "Optional.empty();";
        } else {
            UnmarshalledVariable uv = new UnmarshalledVariable(typedSource.get());
            uv.unmarshal(buf);
            return "Optional.of(" + uv.getInlineOrVariable() + ")";
        }
    }

    @Override
    public String populate(Object source) {
        return null;
    }
}
