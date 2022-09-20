package parser;

import java.util.ArrayList;

public class BasicMethod {

    public String methodName;

    public ArrayList<Arg> args;

    public Clazz clazz;

    public BasicMethod(Clazz clazz, String methodName) {
        this.clazz = clazz;
        this.methodName = methodName;

        this.args = new ArrayList<>();
    }

    public void setArgs(ArrayList<Arg> args){
        this.args.addAll(args);
    }

    public String getMethodName(){
        return methodName;
    }

    public ArrayList<Arg> getArgs(){
        return args;
    }

    public String toString(){
        return this.clazz.toString() + " Method<" + this.methodName + ">";
    }
}
