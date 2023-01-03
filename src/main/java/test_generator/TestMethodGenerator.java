package test_generator;

import parser.Arg;
import parser.BasicMethod;
import parser.ClazzMethod;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestMethodGenerator {

    CombineClazz clazz;

    Set<Arg> initialFields;

    public TestMethodGenerator(CombineClazz clazz, Set<Arg> initialFields){
        this.clazz = clazz;
        this.initialFields = initialFields;
    }

    private String setDifferentFields(ClazzMethod method, Set<Arg> initialFields, Set<String> variableNames){
        StringBuilder sb = new StringBuilder();

        for(Arg field: method.getFields()){
            // that argument didn't be initialized in the test fixture
            if(!initialFields.contains(field)){
                String initField = clazz.setSubjectField(field,variableNames);
                if(initField != null)
                    sb.append(initField);
            }
        }
        return sb.toString();
    }

    private String mockFields(ClazzMethod method, Set<String> variableNames){
        StringBuilder sb = new StringBuilder();
        Map<BasicMethod, Arg> mockableFieldsAndMethods = method.methodsBasedOnFields();

        for (Map.Entry<BasicMethod, Arg> entry: mockableFieldsAndMethods.entrySet()){
            String mockedField = mockField(entry.getKey(), entry.getValue(), variableNames);
            if(mockedField != null)
                sb.append(mockedField);
        }

        return sb.toString();
    }

    public String mockField(BasicMethod method, Arg field, Set<String> variableNames){
        StringBuilder buffer = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        sb.append("\t\tgiven(" + field.getKey() + "." + method.getMethodName() + "(");
        clazz.invokeMethod(buffer, sb, method.getArgs(), variableNames);
        sb.append(")).willReturn(");
        HashSet<Arg> returnField = new HashSet<>();
        returnField.add(method.getReturnField());
        clazz.invokeMethod(buffer, sb, returnField, variableNames);
        sb.append(");");
        buffer.append(sb);

        return buffer.toString();
    }

    public String invokeMUT(ClazzMethod method, Set<String> variableNames){
        StringBuilder buffer = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        sb.append("\t\t" + method.getReturnField().getShortType() + " " + method.methodName + " = subject." + method.methodName + "(");
        clazz.invokeMethod(buffer, sb, method.getArgs(), variableNames);
        sb.append(");");
        buffer.append(sb);

        return buffer.toString();
    }

    public String testMethod(ClazzMethod method) {
        Set<String> variableNames = new HashSet<>();

        AssertionGenerator assertionGenerator = new AssertionGenerator(clazz);
        StringBuilder sb = new StringBuilder();
        String differentFields = setDifferentFields(method, this.initialFields, variableNames);
        String mockFields = mockFields(method, variableNames);

        if(!differentFields.isEmpty()){
            sb.append(differentFields);
            sb.append("\n\n");
        }
        if(!mockFields.isEmpty()) {
            sb.append(mockFields);
            sb.append("\n\n");
        }
        sb.append(invokeMUT(method, variableNames));
        sb.append("\n\n");
        sb.append(assertionGenerator.assertion(method, variableNames));

        return sb.toString();
    }
}
