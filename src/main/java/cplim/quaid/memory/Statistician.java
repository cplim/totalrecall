package cplim.quaid.memory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.io.File;
import java.io.IOException;

public class Statistician {
    private final File file;
    private final ObjectMapper mapper;
    private final History history;

    public Statistician(File file) throws IOException {
        this.file = file;
        this.mapper = new ObjectMapper();
        this.mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        final String content = FileUtils.readFileToString(file);
        if(StringUtils.isEmpty(content)) {
            history = new History();
        } else {
            history = mapper.readValue(file, History.class);
        }
    }

    public void record(Outcome outcome) throws IOException {
        history.add(outcome);
        mapper.writeValue(file, history);
    }
}
