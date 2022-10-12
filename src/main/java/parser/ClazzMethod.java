package parser;

import java.util.ArrayList;

public class ClazzMethod extends BasicMethod {
    public ArrayList<ClazzMethod> childrenClazz;
    public ArrayList<BasicMethod> callee;

    public Arg returnField;

    public ClazzMethod(Clazz clazz, String methodName) {
        super(clazz, methodName);
        childrenClazz = new ArrayList<>();
        callee = new ArrayList<>();
    }

    public void addChildren(ClazzMethod clazz){
        this.childrenClazz.add(clazz);
    }

    public void addMethodCallee(BasicMethod callee){
        this.callee.add(callee);
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
        if(hasReturn())
            return super.toString() + " Return<" + returnField.value + ">";
        else
            return super.toString();
    }
}
