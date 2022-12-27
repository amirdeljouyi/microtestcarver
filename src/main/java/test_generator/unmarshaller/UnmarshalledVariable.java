package test_generator.unmarshaller;

import parser.Arg;
import spoon.reflect.declaration.CtType;
import test_generator.unmarshaller.types.*;
import test_generator.unmarshaller.utils.InitializeMode;
import test_generator.unmarshaller.utils.ReflectionUtil;

import java.time.LocalDate;
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
        ReflectionUtil util = new ReflectionUtil();

        if(source instanceof Optional){
            OptionalUnmarshaller unmarshaller = new OptionalUnmarshaller(buf);
            return unmarshaller.unmarshalString(source, root);
        } else if(source.getClass().isEnum()){
            EnumUnmarshaller unmarshaller = new EnumUnmarshaller(buf);
            return unmarshaller.unmarshalString(source, root);
        } else if(source instanceof LocalDate){
            DateUnmarshaller unmarshaller = new DateUnmarshaller(buf);
            return unmarshaller.unmarshalString(source, root);
        } else {
            ReflectionUnmarshaller unmarshaller = new ReflectionUnmarshaller(buf);
            String unmarshalString = unmarshaller.unmarshalString(source, root);
            if(unmarshaller.isMultiline()){
                initMode = InitializeMode.MULTILINE;
                variableName = unmarshaller.getVariableName();
            }
            return unmarshalString;
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

        if(source.getClass().equals(String.class)) {
            return ("'" + source + "'");
        }
        if(source.getClass().isPrimitive()){
            return source.toString();
        }

        String unmarshalValue = unmarshal(buf);

        if(initMode.equals(InitializeMode.INLINE)){
            return unmarshalValue;
        } else{
            buf.append(unmarshalValue);
            return variableName;
        }
    }

    private String unmarshalToString(StringBuilder buf){
        return null;
    }

    private String unmarshalGuess(StringBuilder buf){
        return null;
    }

    public Boolean isMultiline(){
        return this.initMode.equals(InitializeMode.MULTILINE);
    }

    enum UnmarshalMode{
        DESERIALIZE,
        TO_STRING,
        GUESS
    }
}
