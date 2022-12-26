package test_generator.unmarshaller.types;

import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtFieldReference;
import test_generator.unmarshaller.utils.InitializeMode;
import test_generator.unmarshaller.utils.ReflectionSpoonUtil;
import test_generator.unmarshaller.utils.ReflectionUtil;
import test_generator.unmarshaller.utils.ResolvedConstructor;

import java.lang.reflect.Field;
import java.util.Map;

public class ReflectionUnmarshaller extends AbstractUnmarshaller{

    public ReflectionUnmarshaller(StringBuilder buf) {
        super(buf);
    }

    @Override
    public String instantiate(Object source, CtType staticClazz) {
        ReflectionSpoonUtil spoonUtil = new ReflectionSpoonUtil();
        ResolvedConstructor constructor = spoonUtil.resolveConstructor(staticClazz, source);

        if(constructor.unresolvedFields.size() > 0 && !source.getClass().getSimpleName().isEmpty()){
            return populate(source, staticClazz, constructor);
        } else {
            return constructor.toUnmarshal();
        }
    }

    @Override
    public String populate(Object source) {
        return null;
    }

    public String populate(Object source, CtType staticClazz, ResolvedConstructor constructor) {
        ReflectionSpoonUtil spoonUtil = new ReflectionSpoonUtil();
        ReflectionUtil util = new ReflectionUtil();
        StringBuilder constructBuffer = new StringBuilder();

        this.mode = InitializeMode.MULTILINE;
        this.variableName = source.getClass().getSimpleName().substring(0,1).toLowerCase() +
                source.getClass().getSimpleName().substring(1);
        constructBuffer.append(source.getClass().getSimpleName() + " " + variableName);
        constructBuffer.append(" = ");
        constructBuffer.append(constructor.toUnmarshal() + ";");

        for(Map.Entry<CtFieldReference, Field> field: constructor.unresolvedFields.entrySet()){
            Field fieldValue = field.getValue();
            System.out.println("FieldMap: " + field);
            Object fieldObject = util.getFieldValue(source, fieldValue);
            System.out.println("fieldObject: " + fieldObject);
            CtFieldReference fieldReference = field.getKey();
            System.out.println("fieldReference: " + fieldReference);;

            if(fieldReference == null)
                continue;

            String setter = spoonUtil.getFieldSetter(staticClazz, fieldReference.getFieldDeclaration(), fieldValue.getName(), fieldObject.toString());
            constructBuffer.append("\n" + this.variableName + "." + setter + ";");
        }
        return constructBuffer.toString();
    }
}