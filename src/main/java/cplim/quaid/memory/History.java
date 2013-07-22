package cplim.quaid.memory;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Outcome> outcomes = new ArrayList<Outcome>();

    public void add(Outcome outcome) {
        outcomes.add(outcome);
    }
}
