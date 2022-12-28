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

import java.util.Iterator;
import java.util.Set;

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

    public String setDeclarationField(Arg field, StringBuilder buf, Set<String> variableNames){
        String fieldName = field.getKey();

//        System.out.println("FieldName :" + fieldName);
        CtFieldReference stReferenceField = this.staticClazz.getDeclaredOrInheritedField(fieldName);
//        System.out.println("FieldReference: "+ stReferenceField);
        if(stReferenceField == null)
            return null;
        CtField stField =  stReferenceField.getFieldDeclaration();
//        System.out.println("FieldDeclaration: " + stField);
        return spoonUtil.getFieldSetter(this.staticClazz, stField, fieldName, field.getActualValue(), buf, variableNames);
    }

    public String setField(Arg field, StringBuilder buf, Set<String> variableNames){
        String fieldName = field.getKey();
//        System.out.println("FieldName: " + fieldName);
        CtField stField = this.staticClazz.getField(fieldName);
//        System.out.println("Field: " + stField);
//        System.out.println("FieldValue: " + field.getValue());
        if(stField == null)
            return null;

        return spoonUtil.getFieldSetter(this.staticClazz, stField, fieldName, field.getActualValue(), buf, variableNames);
    }

    public String revealObject(Arg arg, StringBuilder buf, Set<String> variableNames){
        if(arg == null || arg.getValue() == null)
            return null;

        if(arg.isPrimitiveType())
            return arg.getValue();

        UnmarshalledVariable uv = new UnmarshalledVariable(arg, staticClazz);
        String revealedObject = uv.getInlineOrVariable(buf, variableNames);
//        System.out.println("revealedObject: " + revealedObject);

        if(revealedObject == null)
            return arg.getValue();

        return revealedObject;
    }

    public String setFieldHierarchy(Arg field, StringBuilder buf, Set<String> variableNames){
        String string = setField(field, buf, variableNames);
        if(string == null)
            return setDeclarationField(field, buf, variableNames);
        return string;
    }

    public String setSubjectFields(Set<Arg> fields, Set<String> variableNames){
        StringBuilder sb = new StringBuilder();
        for(Arg field: fields){
            String setter = setSubjectField(field, variableNames);
            if(setter != null)
                sb.append(setter);
        }
        return sb.toString();
    }

    public String setSubjectField(Arg field, Set<String> variableNames){
        StringBuilder subjectBuf = new StringBuilder();
        StringBuilder populationBuf = new StringBuilder();
        String fieldSetter = setFieldHierarchy(field, populationBuf, variableNames);
        if(fieldSetter == null)
            return null;

        if(!populationBuf.toString().isEmpty()){

            String[] lines = populationBuf.toString().split("\\n");
            for(String s: lines){
                subjectBuf.append("\t\t" + s);
                subjectBuf.append("\n");
            }
            subjectBuf.append("\n");
        }
        subjectBuf.append("\t\t" + "subject." + fieldSetter + ";\n");
        return subjectBuf.toString();
    }

    public void invokeMethod(StringBuilder buffer, StringBuilder sb, Set<Arg> args, Set<String> variableNames) {
        Iterator<Arg> it = args.iterator();
        while (it.hasNext()){
            Arg arg = it.next();

            StringBuilder populationBuf = new StringBuilder();
            String argValue = revealObject(arg, populationBuf, variableNames);

            if(!populationBuf.toString().isEmpty()){
                String[] lines = populationBuf.toString().split("\\n");
                for(String s: lines){
                    buffer.append("\t\t" + s);
                    buffer.append("\n");
                }
                buffer.append("\n");
            }

            sb.append(argValue);

            if(it.hasNext()){
                sb.append(", ");
            }
        }
    }

}
