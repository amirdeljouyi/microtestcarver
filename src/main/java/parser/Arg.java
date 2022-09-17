package parser;

public class Arg {
    public String key;
    public String value;

    public Arg(String key, String value){
        this.key = key;
        this.value = value;
    }

    public Arg(String value){
        this.value = value;
    }
}
