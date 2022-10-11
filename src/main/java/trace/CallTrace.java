package trace;

import org.openjdk.btrace.core.annotations.*;
import org.openjdk.btrace.core.types.AnyType;

import static org.openjdk.btrace.core.BTraceUtils.*;

@BTrace(trusted = true)
public class CallTrace {

    @OnMethod(
            clazz = "${packageName}\\..*/",
            method = "${methodName}/",
            location = @Location(Kind.ENTRY)
    )
    public static void onMethodEntry(@Self Object o, @ProbeClassName String pcn, @ProbeMethodName String pmn, AnyType[] args) throws IllegalAccessException {
        if (o != null) {
            print(Strings.strcat(Strings.strcat(pcn, "."), pmn) + ":{");
            printDetailedArgs(args);
            printDetailedFields(o);
        }
//        Serialization
//        if (o != null) {
//            String filename = strcat(strcat(pcn, "."), pmn);
//            writeXML(o, filename + ".xml");
//            writeDOT(o, filename + ".dot");
//            serialize((Serializable) o,  + filename + ".ser");
//        }
    }

    @OnMethod(
            clazz = "${packageName}\\..*/",
            method = "${methodName}/",
            location = @Location(value = Kind.CALL, clazz = "/.*/", method = "/.*/"))
    public static void onMethodCall(@Self Object self, @TargetInstance Object instance,
                                    @TargetMethodOrField String method, AnyType[] args) {
        if (instance != null) {
            print(name(classOf(instance)) + "." + method + "()");

            printDetailedArgs(args);
        }
    }

    @OnMethod(
            clazz = "${packageName}\\..*/",
            method = "${methodName}/",
            location = @Location(Kind.RETURN)
    )
    public static void onMethodReturn(@Self Object o, @ProbeClassName String pcn, @ProbeMethodName String pmn, @Duration long d) {
        print("}:");
    }

    @OnMethod(
            clazz = "${packageName}\\..*/",
            method = "${methodName}/",
            location = @Location(Kind.RETURN)
    )
    public static void onMethodReturn(@ProbeClassName String pcn, @ProbeMethodName String pmn, @Return AnyType callbackData) {
        printDetailedRet(callbackData);
    }
}
