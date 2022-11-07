package commands;

import parser.Parser;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import test_generator.VelocityRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;

@Command(name = "parser")
public class ParserCommand {
    @Option(names = {"-f", "--file"}, description = "File Name")
    String file;

    @Option(names = {"-i", "--input"}, description = "Source Directory")
    String input;


    @Command
    void parse() {
        String fileDirectory = "/trace-output/" + file;
        InputStream inputStream = this.getClass().getResourceAsStream(fileDirectory);
        Parser parser = new Parser(inputStream);
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
}