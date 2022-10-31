package parser;

import java.util.*;

public class ClazzMethod extends BasicMethod {

    public BasicMethod reference;
    public ArrayList<ClazzMethod> childrenClazz;
    public ArrayList<BasicMethod> callee;

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

    public Map<BasicMethod, Arg> clazzMethodsBasedOnFields(){
        Map<BasicMethod, Arg> map = new HashMap<>();
        for (Arg param: this.clazz.params){
            Collection<BasicMethod> calleeSet = CalleeAndChildren().values();
            for(BasicMethod ce : calleeSet){
                if(param.value.isEmpty())
                    continue;
                if(param.value.equals(ce.instanceObject)){
                    map.put(ce, param);
                }
            }
        }
        return map;
    }

    public Map<String, BasicMethod> CalleeAndChildren(){
        HashMap<String, BasicMethod> calleeSet = new HashMap<>();
        for(ClazzMethod cm: childrenClazz){
            calleeSet.put(cm.fullName(), cm);
        }

        for(BasicMethod bm: callee){
            BasicMethod item = calleeSet.get(bm.fullName());
            if (item == null){
                calleeSet.put(bm.fullName(), bm);
            } else {
                if (item.argTypes == null)
                    item.argTypes = bm.argTypes;
                if (item.instanceObject == null)
                    item.instanceObject = bm.instanceObject;
                if (item.returnType == null)
                    item.returnType = bm.returnType;
                calleeSet.put(item.fullName(), item);
            }
        }

        return calleeSet;
    }
    public ArrayList<ClazzMethod> getChildrenClazz() {
        return childrenClazz;
    }

    public ArrayList<BasicMethod> getCallee() {
        return callee;
    }

    public BasicMethod getReference() {
        return reference;
    }

    public void setReference(BasicMethod reference) {
        this.reference = reference;
    }
}
