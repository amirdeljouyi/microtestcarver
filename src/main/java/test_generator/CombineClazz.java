package test_generator;

import parser.Arg;
import parser.Clazz;
import spoon.Launcher;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtFieldReference;
import test_generator.unmarshaller.UnmarshalledVariable;
import test_generator.unmarshaller.utils.ReflectionSpoonUtil;

public class CombineClazz {

    Clazz dynamicClazz;
    CtType staticClazz;

    private ReflectionSpoonUtil spoonUtil;

    public CombineClazz(Clazz dynamicClazz, CtType staticClazz){
        this.dynamicClazz = dynamicClazz;
        this.staticClazz = staticClazz;
        this.spoonUtil = new ReflectionSpoonUtil();
    }

    public CombineClazz(Clazz dynamicClazz, String sourceDirectory){
        final Launcher launcher = new Launcher();
        launcher.addInputResource(sourceDirectory);
        launcher.buildModel();
        launcher.getEnvironment().setComplianceLevel(11);
        Factory spoon = launcher.getFactory();
//        CtModel model = spoon.getModel();
        CtType<?> mainClass = spoon.Type().get(dynamicClazz.fullName());
//        System.out.println(mainClass.getAllMethods());

        this.spoonUtil = new ReflectionSpoonUtil();
        this.dynamicClazz = dynamicClazz;
        this.staticClazz = mainClass;
    }

    public String setDeclarationField(Arg field){
        String fieldName = field.getKey();

//        System.out.println("FieldName :" + fieldName);
        CtFieldReference stReferenceField = this.staticClazz.getDeclaredOrInheritedField(fieldName);
//        System.out.println("FieldReference: "+ stReferenceField);
        if(stReferenceField == null)
            return null;
        CtField stField =  stReferenceField.getFieldDeclaration();
//        System.out.println("FieldDeclaration: " + stField);
        return spoonUtil.getFieldSetter(this.staticClazz, stField, fieldName, field.getValue());
    }

    public String setField(Arg field){
        String fieldName = field.getKey();
        System.out.println("FieldName: " + fieldName);
        CtField stField = this.staticClazz.getField(fieldName);
        System.out.println("Field: " + stField);
        System.out.println("FieldValue: " + field.getValue());
        if(stField == null)
            return null;

        return spoonUtil.getFieldSetter(this.staticClazz, stField, fieldName, field.getValue());
    }

    public String revealObject(Arg arg, StringBuilder buf){
        if(arg == null || arg.getValue() == null)
            return null;

        if(arg.isPrimitiveType())
            return arg.getValue();

        UnmarshalledVariable uv = new UnmarshalledVariable(arg, staticClazz);
        String revealedObject = uv.getInlineOrVariable(buf);
//        if(uv.isMultiline()){
//            System.out.println("NotNull!!!");
//            System.out.println("Buf" + buf);
//        }
//
//        System.out.println("revealedObject: " + revealedObject);

        if(revealedObject == null)
            return arg.getValue();

        return revealedObject;
    }

    public String setFields(Arg field){
        String string = setField(field);
        if(string == null)
            return setDeclarationField(field);
        return string;
    }


}
