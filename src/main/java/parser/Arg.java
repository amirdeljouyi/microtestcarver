package parser;

import java.util.Objects;
import java.util.Set;

public class Arg {
    public String key;
    public String value;
    public String type;

    public Boolean isPrimitive;
    public Boolean isInterface;
    private ArgType argType;

    public Set<Arg> params;

    public Arg(String key, String value){
        this.key = key;
        this.value = value;
        this.argType = ArgType.ARG;
    }

    public Arg(String key, String value, String type){
        this.key = key;
        this.value = value;
        this.type = type;
        this.argType = ArgType.ARG;
    }

    public Arg(String key, String value, String type, ArgType argType){
        this.key = key;
        this.value = value;
        this.type = type;
        this.argType = argType;
    }

    public Arg(String value){
        this.value = value;
    }

    public Arg(ArgType argType){
        this.argType = argType;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public ArgType getArgType() {
        return this.argType;
    }

    public Boolean isField() {
        return this.argType == ArgType.FIELD;
    }
    public Boolean isArg() {
        return this.argType == ArgType.ARG;
    }

    public Boolean isReturn() {
        return this.argType == ArgType.RETURN;
    }

    public String getType() {
        return type;
    }

    public String getShortType() {
        String[] names = type.split("\\.");
        return names[names.length - 1];
    }

    @Override
    public int hashCode(){
        return Objects.hash(key, value);
    }

    @Override
    public String toString()
        {
            return argType + ": Name<" + this.key + "> Type<" + this.type + "> Object<" + this.value + ">" ;
        }

    @Override
    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Arg arg = (Arg) obj;
        return Objects.equals(key, arg.key) && Objects.equals(value, arg.value);
    }

    enum ArgType {
        FIELD,
        ARG,
        RETURN
    }
}
