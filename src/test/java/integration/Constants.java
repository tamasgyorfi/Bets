package integration;

public class Constants {

    public static final String POST_JSON = "{  \n" +
            "   \"userId\":\"aa\",\n" +
            "   \"competitionId\":\"aa\",\n" +
            "   \"matchId\":\"aa\",\n" +
            "   \"homeTeamId\":\"aa\",\n" +
            "   \"awayTeamId\":\"aa\",\n" +
            "   \"homeTeamGoals\":1,\n" +
            "   \"awayTeamGoals\":0,\n" +
            "   \"token\": \"securityToken\"\n" +
            "}";

    public static final String HOST = "localhost";
    public static final String PORT = "10000";

    public static final String ADD_BET_ENDPOINT = "/bets/football/v1/bet";
    public static final String INFO_ENDPOINT = "/bets/football/v1/info";

    public static final String PROTOCOL = "http";

    public static final String INVALID_POST_JSON = "{  \n" +
            "   \"userId\":\"aa\",\n" +
            "   \"competitionId\":\"aa\",\n" +
            "   \"matchId\":\"aa\",\n" +
            "   \"homeTeamId\":\"aa\",\n" +
            "   \"awayTeamId\":\"aa\",\n" +
            "   \"homeTeamGoals\":1,\n" +
            "   \"awayTeamGoals\":0\n" +
            "}";
}
