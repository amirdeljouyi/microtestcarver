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
            printDetailedArray("Args", args);
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
            location = @Location(value = Kind.CALL, clazz = "/.*/", method = "/.*/", where= Where.BEFORE))
    public static void onMethodCall(@Self Object self, @TargetInstance Object instance,
                                    @TargetMethodOrField String method, AnyType[] args) {
        if (instance != null) {
            print(name(classOf(instance)) + "." + method + "()");

            printDetailedArray("Args", args);
        }
    }

    @OnMethod(
            clazz = "${packageName}\\..*/",
            method = "${methodName}/",
            location = @Location(value = Kind.CALL, clazz = "/.*/", method = "/.*/", where = Where.AFTER)
    )
    public static void onMethodAfterCall(@Return AnyType callbackData, AnyType inputData) {
        printDetailedObject("Callback", callbackData);
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
        printDetailedObject("Return", callbackData);
    }
}
