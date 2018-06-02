package hu.bets.dbaccess.filter;

import hu.bets.model.filter.Filter;
import hu.bets.model.filter.MultiEqualsFilter;

import java.util.stream.Collectors;

public class MultiEqualsFilterProcessor implements FilterProcessor {

    private DbFieldTranslator dbFieldTranslator;

    public MultiEqualsFilterProcessor(DbFieldTranslator dbFieldTranslator) {
        this.dbFieldTranslator = dbFieldTranslator;
    }

    @Override
    public boolean canHandle(Filter filter) {
        return filter instanceof MultiEqualsFilter;
    }

    @Override
    public String handle(Filter filter) {
        if (canHandle(filter)) {
            MultiEqualsFilter multiEqualsFilter = (MultiEqualsFilter)filter;

            String vals = multiEqualsFilter.getValues()
                    .stream()
                    .map(s-> "\""+s+"\"")
                    .collect(Collectors.joining(", "));

            return dbFieldTranslator.translate(multiEqualsFilter.getField()) + ": { $in: [ "+ vals +" ] }";
        }
        return "";
    }
}
