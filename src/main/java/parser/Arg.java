package parser;

import java.util.Objects;
import java.util.Set;

public class Arg {
    public String key;
    public String value;

    public String type;
    public Boolean isPrimitive;
    public Boolean isInterface;

    private Boolean isField;

    public Set<Arg> params;

    public Arg(String key, String value){
        this.key = key;
        this.value = value;
        this.isField = false;
    }

    public Arg(String key, String value, String type){
        this.key = key;
        this.value = value;
        this.type = type;
        this.isField = false;
    }

    public Arg(String key, String value, String type, Boolean isField){
        this.key = key;
        this.value = value;
        this.type = type;
        this.isField = isField;
    }

    public Arg(String value){
        this.value = value;
    }

    public Arg(Boolean isField){
        this.isField = isField;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public Boolean isField() {
        return isField;
    }

    public Boolean isArg() {
        return !isField;
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
            return "Name<" + this.key + "> Type<" + this.type + "> Object<" + this.type + ">" ;
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
}
