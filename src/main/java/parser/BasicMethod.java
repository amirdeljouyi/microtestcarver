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

    @Override
    public String toString(){
        return this.clazz.toString() + " Method<" + this.methodName + ">";
    }
}
