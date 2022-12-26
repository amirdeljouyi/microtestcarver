package test_generator.unmarshaller;

import parser.Arg;
import spoon.reflect.declaration.CtType;
import test_generator.unmarshaller.types.EnumUnmarshaller;
import test_generator.unmarshaller.types.OptionalUnmarshaller;
import test_generator.unmarshaller.types.ReflectionUnmarshaller;

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
            settingCtType(this.root);
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
        settingCtType(this.root);
    }

    private void settingCtType(CtType root){
        CtType ctType = root.getFactory().Type().get(source.getClass().getName());
        if(ctType!=null)
            this.root = ctType;
    }

    public String unmarshal() {
        StringBuilder buf = new StringBuilder();
        return unmarshal(buf);
    }

    public String unmarshal(StringBuilder buf){
        if(mode.equals(UnmarshalMode.DESERIALIZE)){
            return unmarshalDeserialize(buf);
        } else if (mode.equals(UnmarshalMode.TO_STRING)){
            return unmarshalToString(buf);
        } else if (mode.equals(UnmarshalMode.GUESS)){
            return unmarshalGuess(buf);
        }
        return null;
    }

    private String unmarshalDeserialize(StringBuilder buf){

        if(source.getClass().equals(Optional.class)){
            OptionalUnmarshaller unmarshaller = new OptionalUnmarshaller(buf);
            return unmarshaller.unmarshalString(source, root);
        } if(source.getClass().isEnum()){
            EnumUnmarshaller unmarshaller = new EnumUnmarshaller(buf);
            return unmarshaller.unmarshalString(source, root);
        } else {
            ReflectionUnmarshaller unmarshaller = new ReflectionUnmarshaller(buf);
            return unmarshaller.unmarshalString(source, root);
        }
    }

    public String getInlineOrVariable(){
        StringBuilder buf = new StringBuilder();
        return getInlineOrVariable(buf);
    }
    public String getInlineOrVariable(StringBuilder buf){
        if(source == null)
            return null;

        System.out.println("Source: " + source);
        System.out.println("Source Class: " + source.getClass());
        System.out.println("isEnum: " + source.getClass().isEnum());
        System.out.println("Source Static: " + root);

        if(source.getClass().equals(String.class)) {
            return ("'" + source + "'");
        }
        if(source.getClass().isPrimitive()){
            return source.toString();
        }
        if(initMode.equals(InitializeMode.INLINE)){
            return unmarshal(buf);
        } else{
            unmarshal(buf);
            return variableName;
        }
    }

    private String unmarshalToString(StringBuilder buf){
        return null;
    }

    private String unmarshalGuess(StringBuilder buf){
        return null;
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
