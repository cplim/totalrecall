package cplim.quaid.memory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Statistician {
    private final File file;
    private final ObjectMapper mapper;
    private final List<Outcome> outcomes;

    public Statistician(File file) throws IOException {
        this.file = file;
        this.mapper = new ObjectMapper();
        this.mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        final String content = FileUtils.readFileToString(file);
        if(StringUtils.isEmpty(content)) {
            outcomes = new ArrayList<Outcome>();
        } else {
            outcomes = mapper.readValue(file, List.class);
        }
    }

    public void record(Outcome outcome) throws IOException {
        outcomes.add(outcome);
        mapper.writeValue(file, outcomes);
    }
}
