package write;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fileio.Input;

import java.io.IOException;
import java.nio.file.Paths;

public final class Writer {

    private final String path;

    public Writer(final String path) throws IOException {
        this.path = path;
    }
    /**
     * Writes in output file with JSON format
     *
     * @param input
     */
    public void writeFile(final Input input) {
        try {
            ToWrite toWrite = new ToWrite();
            toWrite.transformToWrite(input);
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter objectWriter = mapper.writer(new DefaultPrettyPrinter());
            objectWriter.writeValue(Paths.get(path).toFile(), toWrite);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
