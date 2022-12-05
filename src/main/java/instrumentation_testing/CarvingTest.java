package instrumentation_testing;

import org.openjdk.btrace.core.BTraceUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;

public class CarvingTest {
    private static RuntimeException translate(Exception exp) {
        if (exp instanceof RuntimeException) {
            return (RuntimeException) exp;
        } else {
            return new RuntimeException(exp);
        }
    }

    public static Field[] getAllFields(Class<?> clazz) {
        return AccessController.doPrivileged(
                (PrivilegedAction<Field[]>)
                        () -> {
                            try {
                                Field[] fields = clazz.getDeclaredFields();
                                for (Field f : fields) {
                                    f.setAccessible(true);
                                }
                                return fields;
                            } catch (Exception exp) {
                                throw translate(exp);
                            }
                        });
    }

    public static void main(String[] args) {
        ATest aTest = new ATest();
        aTest.a.add(10);
        aTest.s = "amir";
        String s = "sd";
        Field[] fields = getAllFields(aTest.getClass());
        for (Field f: fields){
            System.out.println(f.getGenericType().getTypeName());
            try {
                FieldDetailed fieldDetailed = new FieldDetailed(f.getName(), f.getGenericType().getTypeName(), f.getType().isPrimitive(), f.get(aTest));
                System.out.println(fieldDetailed);
            } catch (IllegalAccessException e) {
                throw translate(e);
            }

        }
    }

    public static class ATest{
        public ArrayList<Integer> a;
        public String s;
        public String s2;
        ATest(){
             a = new ArrayList<>();

        }
    }

    public static void addFieldValues(
            StringBuilder buf, Object obj, Class<?> clazz, boolean classNamePrefix, boolean typeNamePrefix) {
        Field[] fields = getAllFields(clazz);
        for (Field f : fields) {
            int modifiers = f.getModifiers();
            if (!Modifier.isStatic(modifiers)) {
                if (classNamePrefix) {
                    buf.append(f.getDeclaringClass().getName());
                    buf.append('.');
                }
                if (typeNamePrefix) {
                    buf.append(f.getType() + " ");
                }
                buf.append(f.getName());
                buf.append('=');
                try {
                    buf.append(BTraceUtils.Strings.str(f.get(obj)));
                } catch (Exception exp) {
                    throw translate(exp);
                }
                buf.append(", ");
            }
        }
        Class<?> sc = clazz.getSuperclass();
        if (sc != null) {
            addFieldValues(buf, obj, sc, classNamePrefix, typeNamePrefix);
        }
    }

    public static class FieldDetailed {

        String name;
        String type;
        Boolean isInterface;
        Boolean isPrimitive;
        Object object;
        String fields;

        public FieldDetailed(String name, String type, Boolean isPrimitive, Object object) {
            this.name = name;
            this.type = type;
            this.isPrimitive = isPrimitive;
            this.isInterface = !isPrimitive;
            if (object != null) {
                this.object = object;
                if (isString(type)) {
                    this.object = '"' + object.toString() + '"';
                }
                if (!isPrimitiveTypes(type)) {
                    StringBuilder buffer = new StringBuilder();
                    addFieldValues(buffer, object, object.getClass(), false, true);
                    this.fields = buffer.toString();
                } else {
                    this.fields = "";
                }
            } else{
                this.fields = "";
            }
        }

        private boolean isString(String type) {
            if (type == "java.lang.String")
                return true;
            return false;
        }

        private boolean isPrimitiveTypes(String type) {
            if (type == null || type == "" || type == "null")
                return true;
            if (type == "java.lang.String")
                return true;
            if (type == "java.lang.Integer")
                return true;
            if (type == "java.lang.Long")
                return true;
            if (type == "java.lang.Short")
                return true;
            if (type == "java.lang.Float")
                return true;
            if (type == "java.lang.Double")
                return true;
            if (type == "java.lang.Byte")
                return true;
            if (type == "java.lang.AtomicLong")
                return true;
            if (type == "java.lang.AtomicInteger")
                return true;
            return false;
        }

        @Override
        public String toString() {
            return "{\n"
                    + "    name: " + name + ",\n"
                    + "    type: " + type + ",\n"
                    + "    isPrimitive: " + isPrimitive + ",\n"
                    + "    isInterface: " + isInterface + ",\n"
                    + "    object: " + object + ",\n"
                    + "    fields: [" + fields + "],\n"
                    + "}";
        }
    }
}
