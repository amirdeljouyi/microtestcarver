package parser;

import java.util.ArrayList;

public class ClazzMethod extends BasicMethod {
    public ArrayList<ClazzMethod> childrenClazz;
    public ArrayList<BasicMethod> callee;

    public ClazzMethod(String clazzName, String packageName, String methodName) {
        super(clazzName, packageName, methodName);
        childrenClazz = new ArrayList<>();
        callee = new ArrayList<>();
    }

    public void addChildren(ClazzMethod clazz){
        this.childrenClazz.add(clazz);
    }

    public void addMethodCallee(BasicMethod callee){
        this.callee.add(callee);
    }
}
