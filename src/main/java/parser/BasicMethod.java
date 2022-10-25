package parser;

import java.util.ArrayList;

public class BasicMethod {

    public String methodName;

    public ArrayList<Arg> args;

    public Clazz clazz;

    public Arg returnField;

    public BasicMethod(Clazz clazz, String methodName) {
        this.clazz = clazz;
        this.methodName = methodName;

        this.args = new ArrayList<>();
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
            return string +  " Return<" + returnField.value + ">";
        return string;
    }
}
