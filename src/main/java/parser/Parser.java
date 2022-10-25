package parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Parser {

    public InputStream inputStream;
    private ArrayList<ClazzMethod> clazzMethods;
    private BasicMethod lastMethodClazz;
    private Stack<ClazzMethod> stackClazz;

    private Arg lastArg;
    private HashMap<String, Clazz> clazzSet;

    public String time;

    public Parser(InputStream inputStream) {
        this.inputStream = inputStream;
        this.clazzMethods = new ArrayList<>();
        this.stackClazz = new Stack<>();
        this.clazzSet = new HashMap<>();
    }

    public void readLines() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(inputStream));
            String line = in.readLine();
            tokenStart(line);

            while ((line = in.readLine()) != null) {
                if (line.contains(":{")) {
                    tokenClazzMethod(line);
                } else if (line.contains("()")) {
                    tokenMethod(line);
                } else if (line.contains("Args: [{")) {
                    tokenArg(line);
                } else if (line.contains("Fields: [{")) {
                    tokenField(line);
                } else if (line.contains("Return: {")) {
                    tokenReturn(line);
                } else if (line.contains("Callback: {")) {
                    tokenCallback(line);
                } else if (line.contains("}, ]")) {
                    tokenEndArg(line);
                } else if (line.contains("}, {")) {
                    tokenEndNewArg(line);
                } else if (line.contains("}:")) {
                    tokenEndClazzMethod(line);
                } else if (line.contains("name: ")) {
                    tokenName(line);
                } else if (line.contains("type: ")) {
                    tokenType(line);
                } else if (line.contains("isPrimitive: ")) {
                    tokenIsPrimitive(line);
                } else if (line.contains("isInterface: ")) {
                    tokenIsInterface(line);
                } else if (line.contains("object: ")) {
                    tokenObject(line);
                } else if (line.contains("fields: ")) {
                    tokenFields(line);
                } else if (line.equals("}")) {
                    tokenEndRet(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(clazzMethods);
    }

    private void tokenFields(String line) {
        line = line.substring(12);
        String[] argStrings = splitArgs(line);
        Set<Arg> args = new HashSet<>();
        for (String argString : argStrings) {
//            System.out.println(argString);
            if (!argString.isEmpty() && argString != null) {
                String[] dual = argString.split("=");
                String[] detailedType = dual[0].split(" ");
                String type = detailedType[1];
                String key = detailedType[2];
                String value = dual[1];
                args.add(new Arg(key, value, type));
            }
        }
        lastArg.params = args;
    }

    private void tokenObject(String line) {
        line = line.substring(12, line.length()-1);
        lastArg.value = line;
    }

    private void tokenIsInterface(String line) {
        line = line.substring(17, line.length()-1);
        lastArg.isInterface =  Boolean.parseBoolean(line);
    }

    private void tokenName(String line) {
        line = line.substring(10, line.length()-1);
        lastArg.key =  line;
    }
    private void tokenType(String line) {
        line = line.substring(10, line.length()-1);
        lastArg.type =  line;
    }

    private void tokenIsPrimitive(String line) {
        line = line.substring(16, line.length()-1);
        lastArg.isPrimitive =  Boolean.parseBoolean(line);
    }

    private void tokenEndNewArg(String line) {
        Arg.ArgType argType = lastArg.getArgType();

        if (lastArg.isField() &&  (lastMethodClazz instanceof ClazzMethod)){
            lastMethodClazz.clazz.addParam(lastArg);
            clazzSet.put(lastMethodClazz.clazz.fullName(), lastMethodClazz.clazz);
        } else {
            lastMethodClazz.addArg(lastArg);
        }
        System.out.println(lastArg);


        Arg arg = new Arg(argType);
        this.lastArg = arg;
    }

    private void tokenEndArg(String line) {

        if (lastArg.isField() &&  (lastMethodClazz instanceof ClazzMethod)){
            lastMethodClazz.clazz.addParam(lastArg);
            clazzSet.put(lastMethodClazz.clazz.fullName(), lastMethodClazz.clazz);
        } else if(lastArg.isArg()) {
            lastMethodClazz.addArg(lastArg);
        }

        if (!(lastMethodClazz instanceof ClazzMethod)) {
            stackClazz.peek().addMethodCallee(lastMethodClazz);
        }

        System.out.println(lastArg);

        lastArg = null;

    }

    private void tokenEndRet(String line) {
        ClazzMethod clazzMethod = stackClazz.peek();
        if (lastArg.isReturn()){
            clazzMethod.returnField = lastArg;
        } else if (lastArg.isCallback()){
            lastMethodClazz.returnField = lastArg;
        }

        System.out.println(lastArg);
        lastArg = null;
    }

    private void tokenStart(String line) {
        this.time = line.split("### BTrace Log: ")[0];
    }

    private void tokenClazzMethod(String line) {
        line = line.split(":")[0];

        ClazzMethod cm = (ClazzMethod) methodParser(line, true);
        lastMethodClazz = cm;
        stackClazz.push(cm);
        System.out.println("class parsed: " + cm);
    }

    private void tokenMethod(String line) {
        line = line.substring(0, line.length() - 2);
        System.out.println(line);
        BasicMethod cm = methodParser(line, false);
        lastMethodClazz = cm;
        System.out.println("method parsed: " + cm);
    }

//    private void tokenStartClazzMethod(String line){
//
//    }

    private void tokenEndClazzMethod(String line) {
        ClazzMethod clazz = stackClazz.pop();
        if(!stackClazz.isEmpty())
            stackClazz.peek().addChildren(clazz);
        clazzMethods.add(clazz);
    }

    private void tokenField(String line) {
        Arg arg = new Arg(Arg.ArgType.FIELD);
        lastArg = arg;
    }

    private void tokenArg(String line) {
        Arg arg = new Arg(Arg.ArgType.ARG);
        lastArg = arg;

//        if (lastMethodClazz instanceof ClazzMethod) {
//            if (!stackClazz.isEmpty())
//                stackClazz.peek().addChildren((ClazzMethod) lastMethodClazz);
//        } else {
//            stackClazz.peek().addMethodCallee(lastMethodClazz);
//        }
    }

    private void tokenReturn(String line) {
        Arg arg = new Arg(Arg.ArgType.RETURN);
        lastArg = arg;
    }

    private void tokenCallback(String line) {
        Arg arg = new Arg(Arg.ArgType.CALLBACK);
        lastArg = arg;
    }

    private String[] splitArgs(String line) {
        line = line.substring(1, line.length() - 2);
        return line.split("([a-zA-Z])+ ([a-zA-Z]|\\.|\\@|\\$|\\d)+ ([a-zA-Z])+=(\\[([a-zA-Z]|\\.|\\@|\\$|\\d|\\s|,)*\\]|([a-zA-Z]|\\.|\\@|\\$|\\d)*), ");
    }

    private BasicMethod methodParser(String line, boolean clazzMethod) {
        String[] names = line.split("\\.");
//        System.out.println(line);
        String methodName = names[names.length - 1];
        String clazzName = names[names.length - 2];
        String packageName = String.join(".", Arrays.copyOfRange(names, 0, names.length - 2));
        String clazzKey = packageName + "." + clazzName;

        Clazz item = clazzSet.get(clazzKey);
        if (item == null) {
            item = new Clazz(packageName, clazzName);
        }

        BasicMethod method;
        if (clazzMethod) {
            method = new ClazzMethod(item, methodName);
            item.addMethod((ClazzMethod) method);
            clazzSet.put(clazzKey, item);
        } else {
            method = new BasicMethod(item, methodName);
        }

        return method;
    }

    public HashMap<String, Clazz> getClazzSet() {
        return clazzSet;
    }
}
