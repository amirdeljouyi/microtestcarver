package test_generator.unmarshaller.types;

import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtFieldReference;
import test_generator.unmarshaller.utils.*;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class ReflectionUnmarshaller extends AbstractUnmarshaller{
    Set<String> variableNames;
    public ReflectionUnmarshaller(StringBuilder buf, Set<String> variableNames) {
        super(buf);
        this.variableNames = variableNames;
    }

    @Override
    public String instantiate(Object source, CtType staticClazz) {
        ReflectionSpoonUtil spoonUtil = new ReflectionSpoonUtil();
        ResolvedConstructor constructor = spoonUtil.resolveConstructor(staticClazz, source);

        if(constructor == null)
            return source.toString();

        if(constructor.unresolvedFields.size() > 0 && !source.getClass().getSimpleName().isEmpty()){
            return populate(source, staticClazz, constructor);
        } else {
            return constructor.toUnmarshal();
        }
    }

    @Override
    public String populate(Object source, CtType staticClazz) {
        return null;
    }

    public String populate(Object source, CtType staticClazz, ResolvedConstructor constructor) {
        ReflectionSpoonUtil spoonUtil = new ReflectionSpoonUtil();
        ReflectionUtil util = new ReflectionUtil();
        StringBuilder constructBuffer = new StringBuilder();

        mode = InitializeMode.MULTILINE;
        variableName = new NamingUtil(variableNames).variableName(source);
        constructBuffer.append(source.getClass().getSimpleName() + " " + variableName);
        constructBuffer.append(" = ");
        constructBuffer.append(constructor.toUnmarshal() + ";" + "\n");

        for(Map.Entry<CtFieldReference, Field> field: constructor.unresolvedFields.entrySet()){
            Field fieldValue = field.getValue();
//            System.out.println("FieldMap: " + field);
            Object fieldObject = util.getFieldValue(source, fieldValue);
//            System.out.println("fieldObject: " + fieldObject);
            CtFieldReference fieldReference = field.getKey();
//            System.out.println("fieldReference: " + fieldReference);;

            if(fieldReference == null)
                continue;

            StringBuilder sb = new StringBuilder();
            String setter = spoonUtil.getFieldSetter(staticClazz, fieldReference.getFieldDeclaration(), fieldValue.getName(), fieldObject, sb, variableNames);
            constructBuffer.append(variableName + "." + setter + ";" + "\n");
        }
        return constructBuffer.toString();
    }
}
