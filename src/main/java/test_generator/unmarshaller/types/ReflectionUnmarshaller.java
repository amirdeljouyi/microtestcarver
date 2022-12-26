package test_generator.unmarshaller.types;

import spoon.reflect.declaration.CtType;
import test_generator.unmarshaller.utils.ReflectionSpoonUtil;
import test_generator.unmarshaller.utils.ResolvedConstructor;

public class ReflectionUnmarshaller extends AbstractUnmarshaller{

    public ReflectionUnmarshaller(StringBuilder buf) {
        super(buf);
    }

    @Override
    public String instantiate(Object source, CtType staticClazz) {
        ReflectionSpoonUtil spoonUtil = new ReflectionSpoonUtil();
        ResolvedConstructor constructor = spoonUtil.resolveConstructor(staticClazz, source);
        return constructor.toUnmarshal();
    }

    @Override
    public String populate(Object source) {
        return null;
    }
}
