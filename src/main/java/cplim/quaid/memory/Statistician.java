package cplim.quaid.memory;

import cplim.quaid.Approach;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.type.TypeFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Collections.sort;

public class Statistician {
    private static final int LEGACY_LIMIT = 3;
    private static final int NUMBER_OF_RECORDS = 4;
    private final File file;
    private final ObjectMapper mapper;
    private final List<Outcome> outcomes;
    private final Random random;

    public Statistician(File file) throws IOException {
        this.file = file;
        this.mapper = new ObjectMapper();
        this.random = new Random(System.currentTimeMillis());
        this.mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        if(!file.exists()) {
            file.createNewFile();
        }
        final String content = FileUtils.readFileToString(file);
        if(StringUtils.isEmpty(content)) {
            outcomes = new ArrayList<Outcome>();
        } else {
            outcomes = mapper.readValue(file, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, Outcome.class));
        }
    }

    public void record(Outcome outcome) throws IOException {
        outcomes.add(outcome);
        mapper.writeValue(file, outcomes);
    }

    public Outcome bestOutcomeSoFar() {
        sort(outcomes);
        return outcomes.get(0);
    }

    public Approach recommendApproachFromStatistics() {
        // pick top LEGACY_LIMIT outcomes
        List<Outcome> topOutcomes = new ArrayList<Outcome>();
        sort(outcomes);
        if(outcomes.size() > LEGACY_LIMIT) {
            topOutcomes.addAll(outcomes.subList(0, LEGACY_LIMIT));
        } else {
            topOutcomes.addAll(outcomes);
        }

        // ensure we have at least NUMBER_OF_RECORDS when determining the best approach
        float firstPickTotal = 0.0f;
        float secondPickTotal = 0.0f;
        for(int i=0; i < NUMBER_OF_RECORDS; i++) {
            if(!topOutcomes.isEmpty()) {
                final Outcome topOutcome = topOutcomes.remove(0);
                // we have history, use it
                firstPickTotal += topOutcome.getFirstPickBias();
                secondPickTotal += topOutcome.getSecondPickBias();
            } else {
                // we will generate a random bias
                firstPickTotal += random.nextFloat();
                secondPickTotal += random.nextFloat();
            }
        }

        return new Approach(firstPickTotal/NUMBER_OF_RECORDS, secondPickTotal/NUMBER_OF_RECORDS);
    }
}
