package parser;

import java.util.*;

public class LeafMethod {

    public String methodName;

    public Set<Arg> args;
    public Set<Arg> fields;
    public Set<String> argTypes;

    public Clazz clazz;

    public Arg returnField;
    public String returnType;
    public String instanceObject;

    public LeafMethod(Clazz clazz, String methodName) {
        this.clazz = clazz;
        this.methodName = methodName;

        this.args = new HashSet<>();
        this.argTypes = new HashSet<>();
        this.fields = new HashSet<>();
    }

    public LeafMethod(Clazz clazz, String methodName, String returnType, String[] argTypes, String instanceObject) {
        this.clazz = clazz;
        this.methodName = methodName;

        this.returnType = returnType;
        this.argTypes = new HashSet<>();
        if(argTypes != null)
            this.argTypes.addAll(Arrays.asList(argTypes));
        this.instanceObject = instanceObject;

        this.args = new HashSet<>();
        this.fields = new HashSet<>();
    }

    public String fullName(){
        return clazz.fullName() + "." + methodName;
    }

    public String getMethodName(){
        return methodName;
    }

    public void addArg(Arg arg){
        this.args.add(arg);
    }

    public void setArgs(Set<Arg> args){
        this.args.addAll(args);
    }

    public Set<Arg> getArgs(){
        return args;
    }

    public void addField(Arg arg){
        this.fields.add(arg);
    }

    public void setFields(Set<Arg> args){
        this.fields.addAll(args);
    }

    public Set<Arg> getFields(){
        return fields;
    }

    public Boolean hasReturn(){
        if(returnField!= null){
            return true;
        }
        return false;
    }

    public Arg getReturnField() {
        return returnField;
    }

    @Override
    public String toString(){
        String string = this.clazz.toString() + " Method<" + this.methodName + ">";
        if(hasReturn())
            string = string.concat(" Return<" + returnField.value + ">");
        if(instanceObject != null && !instanceObject.isEmpty())
            string = string.concat(" Instance Object<"+ instanceObject + ">");
        return string;
    }

    @Override
    public int hashCode(){
        return Objects.hash(methodName, clazz);
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LeafMethod bm = ((LeafMethod) obj);
        return Objects.equals(methodName, bm.methodName) &&
                Objects.equals(clazz, bm.clazz) &&
                Objects.equals(instanceObject, bm.instanceObject) &&
                Objects.equals(args, bm.args) &&
                Objects.equals(returnType, bm.returnType);
//                Objects.equals(returnField, bm.returnField);
    }

}
