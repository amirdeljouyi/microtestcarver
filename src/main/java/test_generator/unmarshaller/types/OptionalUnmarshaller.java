package test_generator.unmarshaller.types;

import spoon.reflect.declaration.CtType;
import test_generator.unmarshaller.utils.ReflectionSpoonUtil;
import test_generator.unmarshaller.UnmarshalledVariable;

import java.util.Optional;

public class OptionalUnmarshaller extends AbstractUnmarshaller{

    public OptionalUnmarshaller(StringBuilder buf) {
        super(buf);
    }

    @Override
    public String instantiate(Object source, CtType staticClazz) {
        Optional typedSource = (Optional) source;
        if(typedSource.isEmpty()){
            return ("Optional.empty();");
        } else {
            ReflectionSpoonUtil spoonUtil = new ReflectionSpoonUtil();
            UnmarshalledVariable uv = spoonUtil.instantiateRelateToType(typedSource.get(), staticClazz);
            return ("Optional.of(" + uv.getInlineOrVariable(buf) + ")");
        }
    }

    @Override
    public String populate(Object source, CtType staticClazz) {
        return null;
    }
}
