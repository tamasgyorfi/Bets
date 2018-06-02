package integration;

public class Constants {

    public static final String POST_JSON = "{\n" +
            "  \"userId\": \"aa\",\n" +
            "  \"bets\": [\n" +
            "    {\n" +
            "      \"competitionId\": \"aa\",\n" +
            "      \"matchId\": \"aa\",\n" +
            "      \"homeTeamId\": \"aa\",\n" +
            "      \"awayTeamId\": \"aa\",\n" +
            "      \"homeTeamGoals\": 1,\n" +
            "      \"awayTeamGoals\": 0\n" +
            "    }\n" +
            "  ],\n" +
            "  \"token\": \"djskal\"\n" +
            "}";

    public static final String HOST = "localhost";
    public static final String PORT = "10000";

    public static final String ADD_BET_ENDPOINT = "/bets/football/v1/bet";
    public static final String QUERY_BETS_ENDPOINT = "/bets/football/v1/userBets";
    public static final String INFO_ENDPOINT = "/bets/football/v1/info";
    public static final String FILTER_QUERY_ENDPOINT = "/bets/football/v1/user-bets";

    public static final String PROTOCOL = "http";

    public static final String INVALID_POST_JSON = "{\n" +
            "  \"userId\": \"aa\",\n" +
            "  \"bets\": [\n" +
            "    {\n" +
            "      \"competitionId\": \"aa\",\n" +
            "      \"matchId\": \"aa\",\n" +
            "      \"homeTeamId\": \"aa\",\n" +
            "      \"awayTeamId\": \"aa\",\n" +
            "      \"homeTeamGoals\": 1,\n" +
            "      \"awayTeamGoals\": 0\n" +
            "    }]" +
            "}";

    public static String getBet(String userId, String matchId) {
        return "{\n" +
                "  \"userId\":\"" + userId + "\",\n" +
                "  \"bets\": [\n" +
                "    {\n" +
                "      \"competitionId\": \"aa\",\n" +
                "      \"matchId\": \"" + matchId + "\",\n" +
                "      \"homeTeamId\": \"aa\",\n" +
                "      \"awayTeamId\": \"aa\",\n" +
                "      \"homeTeamGoals\": 1,\n" +
                "      \"awayTeamGoals\": 0\n" +
                "    }],\n" +
                "  \"token\": \"djskal\"\n" +
                "}";
    }

    public static String getBet(String userId, String matchId, String betId, int homeGoals, int awayGoals) {
        return "{\n" +
                "  \"userId\":\"" + userId + "\",\n" +
                "  \"bets\": [\n" +
                "    {\n" +
                "      \"competitionId\": \"aa\",\n" +
                "      \"matchId\": \"" + matchId + "\",\n" +
                "      \"betId\": \"" + betId + "\",\n" +
                "      \"homeTeamId\": \"aa\",\n" +
                "      \"awayTeamId\": \"aa\",\n" +
                "      \"homeTeamGoals\": "+homeGoals+",\n" +
                "      \"awayTeamGoals\": "+awayGoals+"\n" +
                "    }],\n" +
                "  \"token\": \"djskal\"\n" +
                "}";
    }

}
