package commands;

import parser.Parser;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import test_generator.VelocityRunner;

import java.io.*;

@Command(name = "parser")
public class ParserCommand {
    @Option(names = {"-f", "--file"}, description = "File Name, Required", required = true)
    String file;


    @Command
    void parse() {
        String fileDirectory = "/trace-output/" + file;
        InputStream inputStream = this.getClass().getResourceAsStream(fileDirectory);
        Parser parser = new Parser(inputStream);
        parser.readLines();

        VelocityRunner vRunner = new VelocityRunner();
        vRunner.setClazzSet(parser.getClazzSet());
        vRunner.write();
    }
}