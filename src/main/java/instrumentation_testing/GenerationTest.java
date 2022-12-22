package instrumentation_testing;

import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import test_generator.unmarshaller.UnmarshalledVariable;

import java.util.Optional;

public class GenerationTest {

    public static void main(String[] args) throws IllegalAccessException {
        ATest aTest = new ATest(new BTest("Clouds", "few clouds"));
        Optional optional = Optional.of(aTest);
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
        UnmarshalledVariable uv = new UnmarshalledVariable(optional, aClass);
        System.out.println(uv.getInlineOrVariable());
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
