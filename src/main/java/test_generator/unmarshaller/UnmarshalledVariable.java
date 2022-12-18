package test_generator.unmarshaller;

import parser.Arg;

import java.util.Optional;

public class UnmarshalledVariable {

    Object source;
    Arg arg;
    UnmarshalMode mode;
    InitializeMode initMode;

    String variableName;


    UnmarshalledVariable(Arg arg){
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

    UnmarshalledVariable(Object source){
        this.source = source;
        this.mode = UnmarshalMode.DESERIALIZE;
    }

    public void unmarshal() {
        StringBuilder buf = new StringBuilder();
        unmarshal(buf);
    }

    public void unmarshal(StringBuilder buf){
        if(mode.equals(UnmarshalMode.DESERIALIZE)){
            unmarshalDeserialize();
        } else if (mode.equals(UnmarshalMode.TO_STRING)){
            unmarshalToString();
        } else if (mode.equals(UnmarshalMode.GUESS)){
            unmarshalGuess();
        }
    }

    private void unmarshalDeserialize(){
        if(source.getClass().equals(Optional.class)){

        }
    }

    public String getInlineOrVariable(){
        if(initMode.equals(InitializeMode.INLINE)){
            // TODO
            return null;
        } else{
            return variableName;
        }
    }

    private void unmarshalToString(){

    }

    private void unmarshalGuess(){

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
