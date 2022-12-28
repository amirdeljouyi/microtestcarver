package test_generator.unmarshaller.utils;

import java.util.Collection;
import java.util.Set;

public class NamingUtil {

    Set<String> variableNames;

    public NamingUtil(Set<String> variableNames){
        this.variableNames = variableNames;
    }

    public String collectionName(Collection<?> collection){
        String objectsType = collection.toArray()[0].getClass().getSimpleName();
        String proposedName = objectsType.substring(0, 1).toLowerCase() +
                objectsType.substring(1) + "s";
        String approvedName = uniqueNameWithNumbers(proposedName);
        variableNames.add(approvedName);
        return approvedName;
    }

    public String variableName(Object source){
        String proposedName = source.getClass().getSimpleName().substring(0,1).toLowerCase() +
                source.getClass().getSimpleName().substring(1);
        String approvedName = uniqueNameWithNumbers(proposedName);
        variableNames.add(approvedName);
        return approvedName;
    }

    public String testName(){
        return null;
    }

    private String uniqueNameWithNumbers(String name){
        String approvedName = name;
        int i = 1;
        while(checkDuplication(approvedName)){
            approvedName = name + "_" + i;
            i++;
        }
        return approvedName;
    }

    private Boolean checkDuplication(String name){
        return variableNames.contains(name);
    }
}
