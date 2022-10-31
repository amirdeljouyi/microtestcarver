package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class BasicMethod {

    public String methodName;

    public ArrayList<Arg> args;
    public ArrayList<String> argTypes;

    public Clazz clazz;

    public Arg returnField;
    public String returnType;
    public String instanceObject;

    public BasicMethod(Clazz clazz, String methodName) {
        this.clazz = clazz;
        this.methodName = methodName;

        this.args = new ArrayList<>();
        this.argTypes = new ArrayList<>();
    }

    public BasicMethod(Clazz clazz, String methodName, String returnType, String[] argTypes, String instanceObject) {
        this.clazz = clazz;
        this.methodName = methodName;

        this.returnType = returnType;
        this.argTypes = new ArrayList<>();
        if(argTypes != null)
            this.argTypes.addAll(Arrays.asList(argTypes));
        this.instanceObject = instanceObject;

        this.args = new ArrayList<>();
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

    public void setArgs(ArrayList<Arg> args){
        this.args.addAll(args);
    }

    public ArrayList<Arg> getArgs(){
        return args;
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
        BasicMethod bm = ((BasicMethod) obj);
        return Objects.equals(methodName, bm.methodName) && Objects.equals(bm.clazz, clazz);
    }

}
