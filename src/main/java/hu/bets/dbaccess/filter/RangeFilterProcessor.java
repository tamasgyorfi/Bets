package hu.bets.dbaccess.filter;

import hu.bets.model.filter.Filter;
import hu.bets.model.filter.RangeFilter;

public class RangeFilterProcessor implements FilterProcessor {

    private DbFieldTranslator dbFieldTranslator;

    public RangeFilterProcessor(DbFieldTranslator dbFieldTranslator){
        this.dbFieldTranslator = dbFieldTranslator;
    }

    @Override
    public boolean canHandle(Filter filter) {
        return filter instanceof RangeFilter;
    }

    @Override
    public String handle(Filter filter) {
        if (canHandle(filter)) {
            RangeFilter rangeFilter = (RangeFilter)filter;
            return dbFieldTranslator.translate(rangeFilter.getField()) + ": { $gte: \"" + rangeFilter.getLower() + "\", $lte: \"" + rangeFilter.getUpper() + "\" }";
        }

        return "";
    }
}
