package hu.bets.dbaccess.filter;

import hu.bets.model.filter.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FilterHandlerTest {

    @Test
    public void processAllShouldCreateCorrectQuery() {

        DbFieldTranslator dbFieldTranslator = new DbFieldTranslator();

        List<Filter> filters = Arrays.asList(
                new EqualsFilter(Field.MATCH_ID, "B115"),
                new MultiEqualsFilter(Field.USER_ID, Arrays.asList("B115", "C116")),
                new RangeFilter(Field.MATCH_ID, "1", "100"));

        FilterHandler filterHandler = new FilterHandler(Arrays.asList(
                new RangeFilterProcessor(dbFieldTranslator),
                new EqualsFilterProcessor(dbFieldTranslator),
                new MultiEqualsFilterProcessor(dbFieldTranslator)
        ));

        String result = filterHandler.processAll(filters);

        assertEquals("{matchId: \"B115\", userId: { $in: [ \"B115\", \"C116\" ] }, matchId: { &gte: \"1\", &lte: \"100\" }}", result);
    }
}