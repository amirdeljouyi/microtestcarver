package test_generator.unmarshaller;

import parser.Arg;
import spoon.reflect.declaration.CtType;

import java.util.Optional;

public class UnmarshalledVariable {

    CtType root;
    Object source;
    Arg arg;
    UnmarshalMode mode;
    InitializeMode initMode = InitializeMode.INLINE;

    String variableName;


    public UnmarshalledVariable(Arg arg, CtType root){
        this.root = root;
        this.arg = arg;

        if(arg.serialized){
            this.source = this.arg.serializedValue;
            this.mode = UnmarshalMode.DESERIALIZE;
        } else {
            if(arg.value.contains("@")){
                this.mode = UnmarshalMode.GUESS;
            } else {
                this.mode = UnmarshalMode.TO_STRING;
            }
        }
    }

    public UnmarshalledVariable(Object source, CtType root){
        this.root = root;
        this.source = source;
        this.mode = UnmarshalMode.DESERIALIZE;
    }

    public void unmarshal() {
        StringBuilder buf = new StringBuilder();
        unmarshal(buf);
    }

    public void unmarshal(StringBuilder buf){
        if(mode.equals(UnmarshalMode.DESERIALIZE)){
            unmarshalDeserialize(buf);
        } else if (mode.equals(UnmarshalMode.TO_STRING)){
            unmarshalToString(buf);
        } else if (mode.equals(UnmarshalMode.GUESS)){
            unmarshalGuess(buf);
        }
    }

    private void unmarshalDeserialize(StringBuilder buf){
        if(source == null)
            return;

        if(source.getClass().equals(Optional.class)){
            OptionalUnmarshaller unmarshaller = new OptionalUnmarshaller(buf);
            unmarshaller.unmarshalString(source, root);
        } else {
            ReflectionUnmarshaller unmarshaller = new ReflectionUnmarshaller(buf);
            unmarshaller.unmarshalString(source, root);
        }
    }

    public String getInlineOrVariable(){
        StringBuilder buf = new StringBuilder();
        return getInlineOrVariable(buf);
    }
    public String getInlineOrVariable(StringBuilder buf){
        if(source.getClass().equals(String.class)) {
            return ("'" + source + "'");
        }
        if(source.getClass().isPrimitive()){
            return source.toString();
        }
        if(initMode.equals(InitializeMode.INLINE)){
            unmarshal(buf);
            System.out.println("Buffer " + buf);
            return buf.toString();
        } else{
            unmarshal(buf);
            return variableName;
        }
    }

    private void unmarshalToString(StringBuilder buf){

    }

    private void unmarshalGuess(StringBuilder buf){

    }

    enum UnmarshalMode{
        DESERIALIZE,
        TO_STRING,
        GUESS
    }

    enum InitializeMode{
        INLINE,
        MULTILINE
    }
}
