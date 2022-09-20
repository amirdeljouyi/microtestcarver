package test_generator;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import parser.Clazz;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class VelocityRunner {

    private HashMap<String, Clazz> clazzSet;
    VelocityEngine velocityEngine;
    Template template;

    public VelocityRunner(){
        clazzSet = new HashMap<>();

        VelocityEngine velocityEngine = new VelocityEngine();

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

            context.put("className", v.clazzName);
            context.put("packageName", v.packageName);
            context.put("methods", v.methods);
            context.put("args", v.args);

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

    public HashMap<String, Clazz> getClazzSet() {
        return clazzSet;
    }

    public void setClazzSet(HashMap<String, Clazz> clazzSet) {
        this.clazzSet = clazzSet;
    }
}
