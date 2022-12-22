package test_generator.unmarshaller;

import spoon.reflect.declaration.CtType;

public class ReflectionUnmarshaller extends AbstractUnmarshaller{

    ReflectionUnmarshaller(StringBuilder buf) {
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
