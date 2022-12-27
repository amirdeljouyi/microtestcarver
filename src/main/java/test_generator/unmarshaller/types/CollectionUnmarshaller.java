package test_generator.unmarshaller.types;

import spoon.reflect.declaration.CtType;
import test_generator.unmarshaller.UnmarshalledVariable;
import test_generator.unmarshaller.utils.InitializeMode;

import java.util.Collection;

public class CollectionUnmarshaller extends AbstractUnmarshaller {

    public CollectionUnmarshaller(StringBuilder buf) {
        super(buf);
    }

    @Override
    public String instantiate(Object source, CtType staticClazz) {
        Collection collection = (Collection) source;
        if (collection.size() == 0) {
            return "new " + collection.getClass().getSimpleName() + "<>()";
        } else {
            this.mode = InitializeMode.MULTILINE;
            String objectsType = collection.toArray()[0].getClass().getSimpleName();
            this.variableName = objectsType.substring(0, 1).toLowerCase() +
                    objectsType.substring(1) + "s";
            String instantiate = collection.getClass().getSimpleName() + "<" + objectsType + ">" + " " +
                    this.variableName + " = " + "new " + collection.getClass().getSimpleName() + "<>();";
            return instantiate + "\n" + populate(collection, staticClazz);
        }
    }

    @Override
    public String populate(Object source, CtType staticClazz) {
        Collection collection = (Collection) source;
        StringBuilder sb = new StringBuilder();
        for (Object item: collection){
            UnmarshalledVariable uv = new UnmarshalledVariable(item, staticClazz);
            sb.append(this.variableName + ".add(" + uv.getInlineOrVariable() + ");");
        }
        return sb.toString();
    }
}
