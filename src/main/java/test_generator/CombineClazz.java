package test_generator;

import parser.Arg;
import parser.Clazz;
import parser.ClazzMethod;
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

    public String setDeclarationField(Arg field, StringBuilder buf){
        String fieldName = field.getKey();

//        System.out.println("FieldName :" + fieldName);
        CtFieldReference stReferenceField = this.staticClazz.getDeclaredOrInheritedField(fieldName);
//        System.out.println("FieldReference: "+ stReferenceField);
        if(stReferenceField == null)
            return null;
        CtField stField =  stReferenceField.getFieldDeclaration();
//        System.out.println("FieldDeclaration: " + stField);
        return spoonUtil.getFieldSetter(this.staticClazz, stField, fieldName, field.getActualValue(), buf);
    }

    public String setField(Arg field, StringBuilder buf){
        String fieldName = field.getKey();
//        System.out.println("FieldName: " + fieldName);
        CtField stField = this.staticClazz.getField(fieldName);
//        System.out.println("Field: " + stField);
//        System.out.println("FieldValue: " + field.getValue());
        if(stField == null)
            return null;

        return spoonUtil.getFieldSetter(this.staticClazz, stField, fieldName, field.getActualValue(), buf);
    }

    public String revealObject(Arg arg, StringBuilder buf){
        if(arg == null || arg.getValue() == null)
            return null;

        if(arg.isPrimitiveType())
            return arg.getValue();

        UnmarshalledVariable uv = new UnmarshalledVariable(arg, staticClazz);
        String revealedObject = uv.getInlineOrVariable(buf);
//        System.out.println("revealedObject: " + revealedObject);

        if(revealedObject == null)
            return arg.getValue();

        return revealedObject;
    }

    public String setFieldHierarchy(Arg field, StringBuilder buf){
        String string = setField(field, buf);
        if(string == null)
            return setDeclarationField(field, buf);
        return string;
    }

    public String setSubjectFields(Set<Arg> fields){
        StringBuilder sb = new StringBuilder();
        for(Arg field: fields){
            String setter = setSubjectField(field);
            if(setter != null)
                sb.append(setter);
        }
        return sb.toString();
    }

    public String setSubjectField(Arg field){
        StringBuilder subjectBuf = new StringBuilder();
        StringBuilder populationBuf = new StringBuilder();
        String fieldSetter = setFieldHierarchy(field, populationBuf);
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

    public String callTestMethod(ClazzMethod method){
//        ${method.getReturnField().getShortType()} ${method.methodName} = subject.${method.methodName}(#foreach($arg in $method.args)${arg.getValue()}#if( $foreach.hasNext ), #end#end);
        StringBuilder buffer = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        sb.append("\t\t" + method.getReturnField().getShortType() + " " + method.methodName + " = subject." + method.methodName + "(");
        Iterator<Arg> it = method.args.iterator();
        while (it.hasNext()){
            Arg arg = it.next();

            StringBuilder populationBuf = new StringBuilder();
            String argValue = revealObject(arg, populationBuf);

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
        sb.append(")");

        buffer.append(sb);

        return buffer.toString();
    }

}
