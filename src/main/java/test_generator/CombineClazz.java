package test_generator;

import parser.Arg;
import parser.Clazz;
import spoon.Launcher;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtFieldReference;

import java.util.Set;

public class CombineClazz {

    Clazz dynamicClazz;
    CtType staticClazz;

    public CombineClazz(Clazz dynamicClazz, CtType staticClazz){
        this.dynamicClazz = dynamicClazz;
        this.staticClazz = staticClazz;
    }

    public CombineClazz(Clazz dynamicClazz, String sourceDirectory){
        final Launcher launcher = new Launcher();
        launcher.addInputResource(sourceDirectory);
        launcher.buildModel();
        launcher.getEnvironment().setComplianceLevel(11);
        Factory spoon = launcher.getFactory();
//        CtModel model = spoon.getModel();
        CtType<?> mainClass = spoon.Type().get(dynamicClazz.fullName());
        System.out.println(mainClass.getAllMethods());

        this.dynamicClazz = dynamicClazz;
        this.staticClazz = mainClass;
    }

    public String setField(Arg field){
        String fieldName = field.getKey();
        System.out.println("FieldName :" + fieldName);
        CtFieldReference stReferenceField = this.staticClazz.getDeclaredOrInheritedField(fieldName);
        System.out.println("FieldReference: "+ stReferenceField);
        if(stReferenceField == null)
            return null;
        CtField stField =  stReferenceField.getFieldDeclaration();
        System.out.println("FieldDeclaration: " + stField);
        if(stField.isPublic()){
            return fieldName + " = " + field.getValue();
        } else{
            String setterName = "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1).toLowerCase();

            System.out.println("setterName: "+ setterName);
            Set<CtMethod> allMethods = staticClazz.getAllMethods();
            CtMethod stMethod = null;
            for(CtMethod method: allMethods){
                if (method.getSimpleName().equals(setterName)) {
                    stMethod = method;
                    break;
                }
            }
            System.out.println("StMethod: " + stMethod);
            if(stMethod != null && !stMethod.isPrivate())
                return setterName + "(" + field.value + ")";
//            }
        }
        return null;
    }
}