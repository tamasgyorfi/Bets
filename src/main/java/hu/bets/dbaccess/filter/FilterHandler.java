package hu.bets.dbaccess.filter;

import hu.bets.model.filter.Filter;

import java.util.List;
import java.util.stream.Collectors;

public class FilterHandler {
    private List<FilterProcessor> processors;

    public FilterHandler(List<FilterProcessor> processors) {
        this.processors = processors;
    }

    private String process(Filter filter) {
        for (FilterProcessor processor : processors) {
            if (processor.canHandle(filter)) {
                return processor.handle(filter);
            }
        }

        return "";
    }

    public String processAll(List<Filter> filters) {
        return "{" + filters.stream().map(filter -> process(filter)).collect(Collectors.joining(", ")) + "}";
    }
}
