package hu.bets.dbaccess.filter;

import hu.bets.model.filter.Field;
import hu.bets.model.translator.DbFieldTranslator;

public class BetsDbFieldTranslator implements DbFieldTranslator {
    public String translate(Field field) {
        switch (field) {
            case MATCH_ID: return "matchId";
            case USER_ID: return "userId";
            case MATCH_DATE: return "matchDate";
        }

        return "unknown";
    }
}
