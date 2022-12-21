package test_generator.unmarshaller;

import spoon.reflect.declaration.CtParameter;

public class ResolvedParameter {
    CtParameter parameter;
    Object value;

    int order;

    ResolvedParameter(CtParameter parameter, Object value){
        this.parameter = parameter;
        this.value = value;
    }
}
