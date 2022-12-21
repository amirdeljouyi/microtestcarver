package test_generator.unmarshaller;

import spoon.reflect.declaration.CtType;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectionUnmarshaller extends AbstractUnmarshaller{

    ReflectionUnmarshaller(StringBuilder buf) {
        super(buf);
    }

    @Override
    public StringBuilder instantiate(Object source, CtType staticClazz) {
//        source.
//        Set<CtConstructor> cons = ((CtClass)staticClazz).getConstructors();
//        for(CtConstructor con: cons){
//            CtBlock ctBlock = con.getBody();
//            List<CtStatement> cl= ctBlock.getStatements();
//            for(CtStatement cli: cl){
////                cli.getElements();
//            }
//            CtParameter ct = (CtParameter) con.getParameters().get(0);
////            ct.++
//        }
        Constructor[] constructors = source.getClass().getDeclaredConstructors();
        for (int i = 0; i < constructors.length; i++) {
            final Constructor constructor = constructors[i];

            System.out.println("constructor: " + constructor);
            if (constructor.getParameterTypes().length == 0) {
                if (!constructor.isAccessible()) {
                    constructor.setAccessible(true);
                }
                try {
                    constructor.newInstance(new Object[0]);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return buf;
    }

    @Override
    public String populate(Object source) {
        return null;
    }
}
