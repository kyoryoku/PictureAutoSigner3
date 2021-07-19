import jdk.dynalink.StandardOperation;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;

public class VersionControl {

    private static long version;

    public static void enable() throws IOException {
        version = 0L;
        File versionFile = new File("version.txt");
        if (versionFile.exists()){
            String content = Files.readString(versionFile.toPath(), StandardCharsets.US_ASCII);
            version = Long.parseLong(content);
            version++;
        } else {
            versionFile.createNewFile();
        }
        Files.writeString(versionFile.toPath(), Long.toString(version), StandardOpenOption.WRITE);
        System.out.println("Version: " + version);
    }

}
