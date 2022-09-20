package parser;

import java.util.ArrayList;
import java.util.Objects;

public class Clazz {
    public String clazzName;
    public String packageName;

    public ArrayList<ClazzMethod> methods;
    public ArrayList<Arg> args;
    public ArrayList<Arg> params;

    public Clazz(String packageName, String clazzName){
        this.packageName = packageName;
        this.clazzName = clazzName;

        methods = new ArrayList<>();
        args = new ArrayList<>();
        params = new ArrayList<>();
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
