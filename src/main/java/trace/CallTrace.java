package trace;

import org.openjdk.btrace.core.annotations.*;
import org.openjdk.btrace.core.types.AnyType;

import static org.openjdk.btrace.core.BTraceUtils.*;

@BTrace(trusted = true)
public class CallTrace {

    final static String SERIALIZE_TYPE = "json";
    @OnMethod(
            clazz = "${packageName}\\..*/",
            method = "${methodName}/",
            location = @Location(Kind.ENTRY)
    )
    public static void onMethodEntry(@Self Object o, @ProbeClassName String pcn, @ProbeMethodName String pmn, AnyType[] args) throws IllegalAccessException {
        print(Strings.strcat(Strings.strcat(pcn, "."), pmn) + ":{");
        printDetailedArray("Args", SERIALIZE_TYPE, args);
        if (o != null) {
            printDetailedFields(o, SERIALIZE_TYPE);
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
            location = @Location(value = Kind.CALL, clazz = "/.*/", method = "/.*/", where = Where.BEFORE)
    )
    public static void onMethodCall(@Self Object self, @TargetInstance Object instance, @TargetMethodOrField(fqn = true) String method, AnyType[] args) {
        if (instance != null) {
            print(method + "[" + instance.toString() + "]");
            printDetailedArray("Args", SERIALIZE_TYPE, args);
        }
    }

    @OnMethod(
            clazz = "${packageName}\\..*/",
            method = "${methodName}/",
            location = @Location(value = Kind.CALL, clazz = "/.*/", method = "/.*/", where = Where.AFTER)
    )
    public static void onMethodAfterCall(@TargetMethodOrField(fqn = true) String method, @Return AnyType callbackData, @Duration long d, AnyType arg) {
//        String regex = Sys.$("packageName").substring(1) + ".*";
//        String methodName = method.split("#")[0].split(" ")[2];
//        Boolean inPackage = methodName.matches(regex);
        printDetailedObject("Callback", SERIALIZE_TYPE, callbackData);
//        if (!inPackage) printDetailedObject("Callback", callbackData);
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
    public static void onMethodReturnWithCallback(@Return AnyType callbackData) {
        printDetailedObject("Return", SERIALIZE_TYPE, callbackData);
    }
}
