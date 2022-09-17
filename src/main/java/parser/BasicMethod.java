package parser;

import java.util.ArrayList;

public class BasicMethod {
    public String clazzName;
    public String packageName;
    public String methodName;

    public ArrayList<Arg> args;

    public BasicMethod(String clazzName, String packageName, String methodName) {
        this.clazzName = clazzName;
        this.packageName = packageName;
        this.methodName = methodName;

        this.args = new ArrayList<>();
    }

    public void setArgs(ArrayList<Arg> args){
        this.args.addAll(args);
    }

    public String toString(){
        return "Package<" + this.packageName + "> Class<" + this.clazzName + "> Method<" + this.methodName + ">";
    }
}
