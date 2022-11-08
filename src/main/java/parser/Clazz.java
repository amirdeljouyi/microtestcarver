package parser;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Clazz {
    public String clazzName;
    public String packageName;

    public String type;

    public Set<ClazzMethod> methods;
    public Set<Arg> args;
    public Set<Arg> params;

    public ClazzMethod initMethod;

    public Clazz(String packageName, String clazzName){
        this.packageName = packageName;
        this.clazzName = clazzName;

        methods = new HashSet<>();
        args = new HashSet<>();
        params = new HashSet<>();
    }

    public String fullName(){
        return packageName + "." + clazzName;
    }

    public void addMethod(ClazzMethod method){
        this.methods.add(method);
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setArgs(Set<Arg> args){
        this.args.addAll(args);
    }

    public void addArg(Arg arg){
        this.args.add(arg);
    }

    public Set<Arg> getArgs(){
        return args;
    }

    public void addParam(Arg arg){
        this.params.add(arg);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Arg> uniqueArgsByKey(){
        Set<Arg> set = new HashSet<>();
        for(Arg arg: args){
            set.add(new Arg(arg.key, null, arg.type, arg.getArgType()));
        }
        return set;
    }

    public Set<Arg> uniqueParamByKey(){
        Set<Arg> set = new HashSet<>();
        for(Arg arg: params){
            set.add(new Arg(arg.key, null, arg.type, arg.getArgType()));
        }
        return set;
    }

    @Override
    public int hashCode(){
        return Objects.hash(packageName, clazzName);
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Clazz clazz = (Clazz) obj;
        return Objects.equals(packageName, clazz.packageName) && Objects.equals(clazzName, clazz.clazzName);
    }

    @Override
    public String toString(){
        return "Package<" + this.packageName + "> Class<" + this.clazzName + ">";
    }
}
