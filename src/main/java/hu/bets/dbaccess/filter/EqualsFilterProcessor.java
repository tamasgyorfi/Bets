package hu.bets.dbaccess.filter;

import hu.bets.model.filter.EqualsFilter;
import hu.bets.model.filter.Filter;

public class EqualsFilterProcessor implements FilterProcessor {

    private DbFieldTranslator dbFieldTranslator;

    public EqualsFilterProcessor(DbFieldTranslator dbFieldTranslator) {
        this.dbFieldTranslator = dbFieldTranslator;
    }

    @Override
    public boolean canHandle(Filter filter) {
        return (filter instanceof EqualsFilter);
    }

    @Override
    public String handle(Filter filter) {
        if (canHandle(filter)) {
            EqualsFilter equalsFilter = (EqualsFilter)filter;
            return dbFieldTranslator.translate(equalsFilter.getField()) + ": "+"\""+equalsFilter.getValue()+"\"";
        }

        return "";
    }
}
