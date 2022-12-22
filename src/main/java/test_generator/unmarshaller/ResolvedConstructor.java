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
        StringBuilder string = new StringBuilder();
        for(ResolvedParameter param : parameters){
            string.append(param.parameter + " <" + param.value + ">" + ", ");
        }
        return string.toString();
    }

    public String paramsToUnmarshal(){
        StringBuilder string = new StringBuilder();
        Iterator it = parameters.iterator();
        while (it.hasNext()) {
            ResolvedParameter param = (ResolvedParameter) it.next();
            UnmarshalledVariable uv = new UnmarshalledVariable(param.value, param.parameter.getType().getTypeDeclaration());
            String s = uv.getInlineOrVariable();
            string.append(s);

            if (it.hasNext()) {
                string.append(", ");
            }
        }
        return string.toString();
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
