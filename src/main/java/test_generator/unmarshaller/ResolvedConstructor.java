package test_generator.unmarshaller;

import spoon.reflect.declaration.CtConstructor;

import java.util.Iterator;
import java.util.List;

public class ResolvedConstructor {
    public CtConstructor constructor;
    public List<ResolvedParameter> parameters;
    public ResolvedConstructor(CtConstructor constructor, List<ResolvedParameter> parameters){
        this.constructor = constructor;
        this.parameters = parameters;
    }

    public String paramsToString(){
        StringBuilder buf = new StringBuilder();
        for(ResolvedParameter param : parameters){
            buf.append(param.parameter + " <" + param.value + ">" + ", ");
        }
        return buf.toString();
    }

    public String paramsToUnmarshal(){
        StringBuilder buf = new StringBuilder();
        Iterator it = parameters.iterator();
        while (it.hasNext()) {
            ResolvedParameter param = (ResolvedParameter) it.next();
            Boolean string = param.value.getClass().equals(String.class);
            if(string){
                buf.append("'");
            }
            buf.append(param.value);
            if(string){
                buf.append("'");
            }
            if (it.hasNext()) {
                buf.append(", ");
            }
        }
        return buf.toString();
    }

    @Override
    public String toString() {
        return "Constructor: " + constructor + "\n" +
                "Params: " + paramsToString();
    }

    public String toUnmarshal() {
        return "new " + constructor.getType().getSimpleName() + "(" + paramsToUnmarshal()+ ")";
    }
}
