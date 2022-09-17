package parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Parser {

    public InputStream inputStream;
    private ArrayList<ClazzMethod> clazzMethods;
    private BasicMethod lastMethodClazz;
    private Stack<ClazzMethod> stackClazz;

    public String time;

    public Parser(InputStream inputStream) {
        this.inputStream = inputStream;
        this.clazzMethods = new ArrayList<>();
        this.stackClazz = new Stack<>();
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
                } else if (line.contains("{")) {
                    tokenClazzArg(line);
                } else if (line.contains("[")) {
                    tokenMethodArg(line);
                } else if (line.contains("}")) {
                    tokenEndClazzMethod(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(clazzMethods);
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
        clazzMethods.add(stackClazz.pop());
    }

    private void tokenClazzArg(String line) {
        String[] argStrings = splitArgs(line);
        ArrayList<Arg> args = new ArrayList<>();
        for (String argString : argStrings) {
            String[] dual = argString.split("=");
            String key = dual[0];
            String value = dual[1];
            args.add(new Arg(key, value));
        }
        lastMethodClazz.setArgs(args);
        if(!stackClazz.isEmpty())
            stackClazz.peek().addChildren((ClazzMethod) lastMethodClazz);
    }

    private void tokenMethodArg(String line) {
        String[] argStrings = splitArgs(line);
        ArrayList<Arg> args = new ArrayList<>();
        for (String argString : argStrings) {
            args.add(new Arg(argString));
        }
        lastMethodClazz.setArgs(args);
        System.out.println("Stack " + stackClazz.toArray().length);
        stackClazz.peek().addMethodCallee(lastMethodClazz);
    }

    private String[] splitArgs(String line) {
        line = line.substring(1, line.length() - 1);
        return line.split(", ");
    }

    private BasicMethod methodParser(String line, boolean clazz) {
        String[] names = line.split("\\.");
//        System.out.println(line);
        String methodName = names[names.length - 1];
        String clazzName = names[names.length - 2];
        String packageName = String.join(".", Arrays.copyOfRange(names, 0, names.length - 2));

        if (clazz) {
            return new ClazzMethod(clazzName, packageName, methodName);
        }

        return new BasicMethod(clazzName, packageName, methodName);
    }

}
