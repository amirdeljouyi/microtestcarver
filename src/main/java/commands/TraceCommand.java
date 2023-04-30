package commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Paths;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Option;

@Command(name = "trace")
public class TraceCommand implements Runnable {
    @Option(names = {"-v", "--verbose"}, description = "print verbose output.")
    boolean verbose;

    @Option(names = {"-cp", "--package"}, description = "Class path, Required", required = true)
    String classPath;

    @Option(names = {"-p", "--port"}, description = "Port, Required", required = true)
    String port;

    @Option(names = {"-l", "--log"}, description = "The log that is displayed on Shell, Default: false")
    boolean log = false;

    @Option(names = {"-m", "--method"}, description = "Method Name, Default: *")
    String method = "^(?!lambda\\$*)(.*)";


    @Override
    public void run() {
        try {
//            String traceClassPath = this.getClass().getClassLoader().getResource("trace/CallTrace.class").getPath();
//            System.out.println(traceClassPath);
            String directory = "./src/main/resources";
            String filename = String.format("%s-%s.btrace", classPath, java.time.LocalDate.now());
            URL resource = getClass().getClassLoader().getResource("trace-execution.sh");
            String extPath = Paths.get(resource.toURI()).toFile().toString();
            System.out.println(extPath);
            String[] commandOptions = new String[]{extPath, port, classPath, method, String.valueOf(log), filename};
            runProcess(commandOptions, directory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void printLines(String cmd, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(cmd + " " + line);
        }
    }

    private void runProcess(String[] commands, String directory) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.directory(new File(directory));
        Process pro = pb.start();
        printLines(String.join(" ", commands) + " stdout:", pro.getInputStream());
        printLines(String.join(" ", commands) + " stderr:", pro.getErrorStream());
        pro.waitFor();
        System.out.println(String.join(" ", commands) + " exitValue() " + pro.exitValue());
    }
}