package hu.bets.dbaccess.filter;

import hu.bets.model.filter.Filter;

public interface FilterProcessor {

    boolean canHandle(Filter filter);

    String handle(Filter filter);
}
