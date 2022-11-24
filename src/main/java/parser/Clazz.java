package parser;

import java.util.*;

public class Clazz {
    public String clazzName;
    public String packageName;

    public String type;

    public Set<ClazzMethod> methods;
    public Set<Arg> args;
    public Set<Arg> fields;

    public ClazzMethod initMethod;

    public Clazz(String packageName, String clazzName){
        this.packageName = packageName;
        this.clazzName = clazzName;

        methods = new HashSet<>();
        args = new HashSet<>();
        fields = new HashSet<>();
    }

    public String fullName(){
        return packageName + "." + clazzName;
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

    public void setArgs(Set<Arg> args){
        this.args.addAll(args);
    }

    public void addArg(Arg arg){
        this.args.add(arg);
    }

    public Set<Arg> getArgs(){
        return args;
    }

    public void addParam(Arg arg){
        this.fields.add(arg);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Arg> uniqueArgsByKey(){
        Set<Arg> set = new HashSet<>();
        for(Arg arg: args){
            set.add(new Arg(arg.key, null, arg.type, arg.getArgType()));
        }
        return set;
    }

    public Set<Arg> uniqueParamByKey(){
        Set<Arg> set = new HashSet<>();
        for(Arg arg: fields){
            set.add(new Arg(arg.key, null, arg.type, arg.getArgType()));
        }
        return set;
    }

    public Set<Arg> complexUniqueParams(){
        Set<Arg> params = uniqueParamByKey();
        Set<Arg> set = new HashSet<>();
        for(Arg arg: fields){
            if(!arg.isPrimitiveType())
                set.add(arg);
        }
        return set;
    }

    public Set<Arg> mockableFields(){
        Set<Arg> params = complexUniqueParams();
        Set<Arg> mockableFields = new HashSet<>();
        for(ClazzMethod method: methods){
            Collection<BasicMethod> calleeSet = method.calleeAndChildren().values();
            for (Arg param: params){
                for(BasicMethod ce : calleeSet){
                    if(param.value.isEmpty())
                        continue;
                    if(param.value.equals(ce.instanceObject)){
                        mockableFields.add(param);
                    }
                }
            }
        }
        return mockableFields;
    }

    public Set<Arg> uniqueFieldsNotMockable(){
        Set<Arg> mockableFields = mockableFields();
        Set<Arg> uniqueFields = uniqueParamByKey();
        Set<Arg> set = new HashSet<>();

        for (Arg field :uniqueFields){
            if(!mockableFields.contains(field))
                set.add(field);
        }

        return set;
    }

    public Set<Arg> initialFieldState(){
        Set<Arg> uniqueFields = uniqueFieldsNotMockable();

        // init
        Map<String, NavigableMap> priorityMap = new HashMap<>();
        for(Arg field: uniqueFields){
            NavigableMap<String, ArgRepetition> maxHeap = new TreeMap<>();
            priorityMap.put(field.getKey(), maxHeap);
        }

        for(ClazzMethod method: methods){
            for(Arg field: method.getFields()){
                addOrIncreaseMapItem(priorityMap, field);
            }
        }
        for(Arg field: fields){
            addOrIncreaseMapItem(priorityMap, field);
        }

        Set<Arg> set = new HashSet<>();
        for(NavigableMap map: priorityMap.values()){
            Map.Entry<String, ArgRepetition> entry = map.firstEntry();
            System.out.println("ARG: " + entry.getValue().getArg() + " REPETITION: "+ entry.getValue().getRepetition());
            set.add(entry.getValue().getArg());
        }
        return set;
    }

    private void addOrIncreaseMapItem(Map<String, NavigableMap> priorityMap, Arg field) {
        NavigableMap<String, ArgRepetition> map = priorityMap.get(field.getKey());
        if (map == null)
            return;

        if(!map.containsKey(field.getValue())) {
            map.put(field.getValue(), new ArgRepetition(field));
        } else{
            ArgRepetition arg = map.get(field.getValue());
            arg.increaseRepetition();
        }

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
