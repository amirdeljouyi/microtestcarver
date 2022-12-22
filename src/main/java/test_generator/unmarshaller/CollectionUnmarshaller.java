package test_generator.unmarshaller;

import spoon.reflect.declaration.CtType;

public class CollectionUnmarshaller extends AbstractUnmarshaller{

    CollectionUnmarshaller(StringBuilder buf) {
        super(buf);
    }

    @Override
    public String instantiate(Object source, CtType staticClazz) {
        return null;
    }

    @Override
    public String populate(Object source) {
        return null;
    }
}
