package integration;

public class Constants {

    public static final String POST_JSON = "{\n" +
            "  \"bets\": [\n" +
            "    {\n" +
            "      \"competitionId\": \"aa\",\n" +
            "      \"matchId\": \"aa\",\n" +
            "      \"matchDate\": \"2018/03/19 13:00:00\",\n" +
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

    public static final String ADD_BET_ENDPOINT = "/bets/football/v1/%s/bets";
    public static final String QUERY_BETS_ENDPOINT = "/bets/football/v1/userBets";
    public static final String INFO_ENDPOINT = "/bets/football/v1/info";
    public static final String FILTER_QUERY_ENDPOINT = "/bets/football/v1/%s/user-bets";

    public static final String PROTOCOL = "http";

    public static final String INVALID_POST_JSON = "{\n" +
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

    public static String getBet(String matchId) {
        return "{\n" +
                "  \"bets\": [\n" +
                "    {\n" +
                "      \"competitionId\": \"aa\",\n" +
                "      \"matchId\": \"" + matchId + "\",\n" +
                "      \"matchDate\": \"2018/03/19 13:00:00\",\n" +
                "      \"homeTeamId\": \"aa\",\n" +
                "      \"awayTeamId\": \"aa\",\n" +
                "      \"homeTeamGoals\": 1,\n" +
                "      \"awayTeamGoals\": 0\n" +
                "    }],\n" +
                "  \"token\": \"djskal\"\n" +
                "}";
    }

    public static String getBet(String matchId, String matchDate) {
        return "{\n" +
                "  \"bets\": [\n" +
                "    {\n" +
                "      \"competitionId\": \"aa\",\n" +
                "      \"matchId\": \"" + matchId + "\",\n" +
                "      \"matchDate\": \"" + matchDate + "\",\n" +
                "      \"homeTeamId\": \"aa\",\n" +
                "      \"awayTeamId\": \"aa\",\n" +
                "      \"homeTeamGoals\": 1,\n" +
                "      \"awayTeamGoals\": 0\n" +
                "    }],\n" +
                "  \"token\": \"djskal\"\n" +
                "}";
    }


    public static String getBet(String matchId, String betId, int homeGoals, int awayGoals) {
        return "{\n" +
                "  \"bets\": [\n" +
                "    {\n" +
                "      \"competitionId\": \"aa\",\n" +
                "      \"matchId\": \"" + matchId + "\",\n" +
                "      \"matchDate\": \"2018/03/19 13:00:00\",\n" +
                "      \"betId\": \"" + betId + "\",\n" +
                "      \"homeTeamId\": \"aa\",\n" +
                "      \"awayTeamId\": \"aa\",\n" +
                "      \"homeTeamGoals\": " + homeGoals + ",\n" +
                "      \"awayTeamGoals\": " + awayGoals + "\n" +
                "    }],\n" +
                "  \"token\": \"djskal\"\n" +
                "}";
    }

}
