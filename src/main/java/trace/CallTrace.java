package trace;

import org.openjdk.btrace.core.BTraceUtils;
import org.openjdk.btrace.core.annotations.*;
import org.openjdk.btrace.core.types.AnyType;

import static org.openjdk.btrace.core.BTraceUtils.*;

@BTrace
public class CallTrace {

    @OnMethod(
            clazz = "${packageName}\\..*/",
            method = "${methodName}/",
            location = @Location(Kind.ENTRY)
    )
    public static void onMethodEntry(@Self Object o, @ProbeClassName String pcn, @ProbeMethodName String pmn) {
        if (o != null) {
            print(BTraceUtils.Strings.strcat(BTraceUtils.Strings.strcat(pcn, "."), pmn) + ":{");
            printFields(o);
        }
    }

    @OnMethod(
            clazz = "${packageName}\\..*/",
            method = "${methodName}/",
            location = @Location(value = Kind.CALL, clazz = "/.*/", method = "/.*/"))
    public static void onMethodCall(@Self Object self, @TargetInstance Object instance,
                                    @TargetMethodOrField String method, AnyType[] args) { // all calls to the methods with signature "(String)"
        if (instance != null) {
            print(name(classOf(instance)) + "." + method + "()");
            printArray(args);
        }
    }

    @OnMethod(
            clazz = "${packageName}\\..*/",
            method = "${methodName}/",
            location = @Location(Kind.RETURN)
    )
    public static void onMethodReturn(@ProbeClassName String pcn, @ProbeMethodName String pmn, @Duration long d) {
        println("}");
    }
}
