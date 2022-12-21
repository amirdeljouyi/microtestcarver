package instrumentation_testing;

import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import test_generator.unmarshaller.ReflectionSpoonUtil;
import test_generator.unmarshaller.ResolvedConstructor;

public class GenerationTest {

    public static void main(String[] args) throws IllegalAccessException {
        ATest aTest = new ATest(new BTest("Clouds", "few clouds"));
        Object object = aTest;
        System.out.println(aTest);
        final Launcher launcher = new Launcher();
        System.out.println(System.getProperty("user.dir"));
        launcher.addInputResource(System.getProperty("user.dir") + "/src/main/java/instrumentation_testing");
        launcher.buildModel();
        launcher.getEnvironment().setComplianceLevel(11);
        Factory spoon = launcher.getFactory();
        CtModel model = spoon.getModel();
        CtType<?> aClass = spoon.Type().get(ATest.class.getName());
        ReflectionSpoonUtil spoonUtil = new ReflectionSpoonUtil();
        ResolvedConstructor constructor = spoonUtil.resolveConstructor(aClass, aTest);
        System.out.println(constructor.toUnmarshal());


//        Constructor[] constructors = aTest.getClass().getDeclaredConstructors();
//
//        for (int i = 0; i < constructors.length; i++) {
//            final Constructor constructor = constructors[i];
//            System.out.println("constructor: " + constructor);
//
//            if (constructor.getParameterTypes().length == 0) {
//                if (!constructor.isAccessible()) {
//                    constructor.setAccessible(true);
//                }
//                try {
//                    constructor.newInstance(new Object[0]);
//                } catch (InstantiationException e) {
//                    throw new RuntimeException(e);
//                } catch (IllegalAccessException e) {
//                    throw new RuntimeException(e);
//                } catch (InvocationTargetException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
    }

    public static class ATest {
        private BTest bTest;

        ATest() {
        }

        ATest(BTest btest) {
            this.bTest = btest;
        }

//        ATest(String a, String b) {
//            this.bTest = new BTest(a, b);
//        }

        @Override
        public String toString() {
            return "ATest{" + "bTest=" + bTest + '}';
        }
    }

    public static class BTest {
        public String a;
        public String b;

        BTest(String a, String b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public String toString() {
            return "BTest{" + "a='" + a + '\'' + ", b='" + b + '\'' + '}';
        }
    }
}
