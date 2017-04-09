package integration;

public class Constants {

    public static final String POST_JSON = "{  \n" +
            "   \"userId\":\"aa\",\n" +
            "   \"competitionId\":\"aa\",\n" +
            "   \"matchId\":\"aa\",\n" +
            "   \"homeTeamId\":\"aa\",\n" +
            "   \"awayTeamId\":\"aa\",\n" +
            "   \"homeTeamGoals\":1,\n" +
            "   \"awayTeamGoals\":0\n" +
            "}";

    public static final String HOST = "localhost";
    public static final String PORT = "10000";

    public static final String ENDPOINT = "/bets/football/v1/result";
    public static final String PROTOCOL = "http";

}
