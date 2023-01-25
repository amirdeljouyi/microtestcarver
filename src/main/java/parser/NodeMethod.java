package parser;

import java.util.*;

public class NodeMethod extends LeafMethod {

    public LeafMethod reference;
    public ArrayList<NodeMethod> childrenClazz;
    public ArrayList<LeafMethod> callee;

    public Boolean isInitMethod = false;

    public NodeMethod(Clazz clazz, String methodName) {
        super(clazz, methodName);
        childrenClazz = new ArrayList<>();
        callee = new ArrayList<>();
    }

    public void addChildren(NodeMethod clazz){
        this.childrenClazz.add(clazz);
    }

    public void addMethodCallee(LeafMethod callee){
        this.callee.add(callee);
    }

    public Map<LeafMethod, Arg> methodsBasedOnFields(){
        Map<LeafMethod, Arg> map = new HashMap<>();
        Collection<LeafMethod> calleeSet = calleeAndChildren().values();
        for (Arg param: this.clazz.fields){
            for(LeafMethod ce : calleeSet){
                if(param.value.isEmpty())
                    continue;
                if(param.value.equals(ce.instanceObject)){
                    map.put(ce, param);
                }
            }
        }
        return map;
    }

    public Map<String, LeafMethod> calleeAndChildren(){
        HashMap<String, LeafMethod> calleeSet = new HashMap<>();
        for(NodeMethod cm: childrenClazz){
            calleeSet.put(cm.fullName(), cm);
        }

        for(LeafMethod bm: callee){
            LeafMethod item = calleeSet.get(bm.fullName());
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
    public ArrayList<NodeMethod> getChildrenClazz() {
        return childrenClazz;
    }

    public ArrayList<LeafMethod> getCallee() {
        return callee;
    }

    public LeafMethod getReference() {
        return reference;
    }

    public void setReference(LeafMethod reference) {
        this.reference = reference;
    }
}
