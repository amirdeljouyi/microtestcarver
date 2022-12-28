package test_generator;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import parser.Arg;
import parser.Clazz;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VelocityRunner {

    private HashMap<String, Clazz> clazzSet;
    private String sourceDirectory;
    VelocityEngine velocityEngine;
    Template template;

    public VelocityRunner(String sourceDirectory){
        clazzSet = new HashMap<>();
        this.sourceDirectory = sourceDirectory;

       velocityEngine = new VelocityEngine();

        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "class,file");
        velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        velocityEngine.init();
        template = velocityEngine.getTemplate("vtemplates/junit.vm");
    }

    public void write(){
        for (Map.Entry<String, Clazz> entry : getClazzSet().entrySet()) {
            String k = entry.getKey();
            Clazz v = entry.getValue();
            VelocityContext context = new VelocityContext();
            CombineClazz combineClazz = new CombineClazz(v, sourceDirectory);
            Set<Arg> initFields = v.initialFieldState();
            System.out.println("=========================");
            System.out.println("Clazz: " + v.fullName());
//            System.out.println("Methods based on Fields: ");
//            for (ClazzMethod item: v.methods){
//                System.out.println(item.clazzMethodsBasedOnFields());
//            }
//
//            System.out.println(combineClazz);

            context.put("className", v.clazzName);
            context.put("packageName", v.packageName);
            context.put("methods", v.testableMethods());
            context.put("args", v.args);
            context.put("params", v.mockableFields());
            context.put("combine", combineClazz);
            context.put("initialFields", initFields);
            context.put("testMethod", new TestMethodGenerator(combineClazz, initFields));

            try {
                Writer writer = new FileWriter(new File("./test-output/" + v.clazzName + "Test.java"));
                template.merge(context, writer);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void isTestable() {
    }

    public HashMap<String, Clazz> getClazzSet() {
        return clazzSet;
    }

    public void setClazzSet(HashMap<String, Clazz> clazzSet) {
        this.clazzSet = clazzSet;
    }
}
