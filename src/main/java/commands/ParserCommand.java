package commands;

import parser.Parser;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import test_generator.VelocityRunner;

import java.io.*;
import java.lang.reflect.Field;

@Command(name = "parser")
public class ParserCommand {
    @Option(names = {"-f", "--file"}, description = "File Name")
    String file;

    @Option(names = {"-i", "--input"}, description = "Source Directory")
    String input;

    @Option(names = {"-p", "--pool"}, description = "Pool Folder Directory")
    String poolDir;


    @Command
    void parse() {
        String fileDirectory = "/trace-output/" + file;
        poolDir = "/trace-output/" + poolDir;
        InputStream inputStream = this.getClass().getResourceAsStream(fileDirectory);
        Parser parser = new Parser(inputStream, poolDir);
        parser.readLines();

        VelocityRunner vRunner = new VelocityRunner(input);
        vRunner.setClazzSet(parser.getClazzSet());
        vRunner.write();
    }

    @Command
    void deserialize(){
        String fileDirectory = "/trace-serialized-objects/" + file;
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(fileDirectory);
            ObjectInputStream in = new ObjectInputStream(inputStream);
            Object obj = in.readObject();
            System.out.println(obj);
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true); // You might want to set modifier to public first.
                Object value = field.get(obj);
                if (value != null) {
                    System.out.println(field.getName() + "=" + value);
                }
            }
            System.out.println(obj);
        } catch (IOException | ClassNotFoundException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Command
    void fix(){
        String fileDirectory = "/trace-output/" + file;
        String fileCleanDirectory = "./trace-fixed-output/" + file;

        try {
            File file = new File(fileCleanDirectory);
//            file.getParentFile().mkdirs();
            file.createNewFile();
            InputStream inputStream = this.getClass().getResourceAsStream(fileDirectory);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(inputStream));
            FileWriter fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw);
            String line;

            while ((line = in.readLine()) != null) {
                if(line.contains("! ERROR") || line.contains("java.lang.reflect.InaccessibleObjectException: ") || line.contains("\tat ")){
                    // Think about it
                } else {
                    pw.println(line);
                }
            }
            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}