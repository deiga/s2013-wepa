package wad.highfive;

import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.*;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import static org.hamcrest.Matchers.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import static org.junit.Assert.*;

public class HighFiveRestApiTest {
    private static String port;

    private final Random random = new Random();

    @BeforeClass
    public static void setup() {
        port = System.getProperty("jetty.port", "8090");
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = Integer.parseInt(port);
    }

    @Test
    @Points("W3E02.1")
    public void testPostGame() {
        String randomGameName = "game:" + UUID.randomUUID().toString();
        postGame(randomGameName);
    }

    @Test
    @Points("W3E02.1")
    public void testPostAndGetGame() {
        String randomGameName = "game:" + UUID.randomUUID().toString();
        postGame(randomGameName);
        getGame(randomGameName);
    }

    @Test
    @Points("W3E02.1")
    public void testPostAndGetGameAndListGames() {
        String randomGameName = "game:" + UUID.randomUUID().toString();
        postGame(randomGameName);
        Response response = getGame(randomGameName);
        Long id = response.body().jsonPath().getLong("id");
        expectToFindGameFromList(id, randomGameName);
    }

    @Test
    @Points("W3E02.1")
    public void testPostAndGetGameAndListGamesAndDeleteGame() {
        String randomGameName = "game:" + UUID.randomUUID().toString();
        postGame(randomGameName);
        Response response = getGame(randomGameName);
        Long id = response.body().jsonPath().getLong("id");
        expectToFindGameFromList(id, randomGameName);
        deleteGame(randomGameName);
        expectNotToFindGameFromList(id);
    }

    @Test
    @Points("W3E02.2")
    public void testPostGameAndACoupleOfScores() {
        String randomGameName = "game:" + UUID.randomUUID().toString();
        Response response = postGame(randomGameName);
        Long id = response.body().jsonPath().getLong("id");

        int[] points = new int[3];
        for (int i = 0; i < points.length; i++) {
            points[i] = random.nextInt(100000);
        }
        postScoresWithRandomNickname(randomGameName, points);
    }

    @Test
    @Points("W3E02.2")
    public void testPostGameAndACoupleOfScoresAndGetScores() {
        String randomGameName = "game:" + UUID.randomUUID().toString();
        postGame(randomGameName);

        int[] points = new int[5];
        String[] nicknames = new String[5];
        for (int i = 0; i < points.length; i++) {
            points[i] = random.nextInt(100000);
            nicknames[i] = "nick:" + UUID.randomUUID().toString();
        }

        long[] scoreIds = postScoresWithSpecifiedNickname(randomGameName, nicknames, points);

        for (int i = 0; i < points.length; i++) {
            getScore(randomGameName, scoreIds[i], nicknames[i], points[i]);
        }
    }

    @Test
    @Points("W3E02.2")
    public void testPostGameAndACoupleOfScoresAndListScores() {
        String randomGameName = "game:" + UUID.randomUUID().toString();
        postGame(randomGameName);

        int[] points = new int[5];
        for (int i = 0; i < points.length; i++) {
            points[i] = random.nextInt(100000);
        }
        long[] scoreIds = postScoresWithRandomNickname(randomGameName, points);

        getScores(randomGameName);
        expectToFindScoreFromList(randomGameName, scoreIds, points);
    }

    @Test
    @Points("W3E02.2")
    public void testPostGameAndACoupleOfScoresAndListScoresAndDeleteSpecificScores() {
        String randomGameName = "game:" + UUID.randomUUID().toString();
        postGame(randomGameName);

        int[] points = new int[7];
        for (int i = 0; i < points.length; i++) {
            points[i] = random.nextInt(100000);
        }
        long[] scoreIds = postScoresWithRandomNickname(randomGameName, points);

        getScores(randomGameName);
        expectToFindScoreFromList(randomGameName, scoreIds, points);

        deleteScore(randomGameName, scoreIds[0]);
        deleteScore(randomGameName, scoreIds[2]);
        deleteScore(randomGameName, scoreIds[5]);

        expectNotToFindScoreFromList(randomGameName, new long[] { scoreIds[0], scoreIds[2], scoreIds[5] });
    }

    public long[] postScoresWithSpecifiedNickname(String gameName, String[] nicknames, int[] points) {
        long[] ids = new long[points.length];
        for (int i = 0; i < points.length; i++) {
            Response response = postScore(gameName, nicknames[i], points[i]);
            ids[i] = response.body().jsonPath().getLong("id");
        }
        return ids;
    }

    public long[] postScoresWithRandomNickname(String gameName, int[] points) {
        long[] ids = new long[points.length];
        for (int i = 0; i < points.length; i++) {
            String randomNickname = "nickname:" + UUID.randomUUID().toString();
            Response response = postScore(gameName, randomNickname, points[i]);
            ids[i] = response.body().jsonPath().getLong("id");
        }
        return ids;
    }

    protected void expectToFindGameFromList(Long expectedId, String expectedName) {
        Response response = getGames();

        Map matchingGame = null;
        List<Map> gameList = response.body().jsonPath().getList("", Map.class);
        for (Map gameData : gameList) {
            Object id = gameData.get("id");
            if (String.valueOf(expectedId).equals(String.valueOf(id))) {
                matchingGame = gameData;
                break;
            }
        }

        if (matchingGame == null) {
            fail("Cannot find a stored game in the JSON array response of GET-request to /app/games");
            return;
        }

        assertEquals("GET-request to /app/games returns incorrect name for game",
                expectedName, matchingGame.get("name"));
    }

    protected void expectToFindScoreFromList(String gameName, long[] expectedIds, int[] expectedPoints) {
        Response response = getScores(gameName);

        List<Map> scoreList = response.body().jsonPath().getList("", Map.class);
        for (Map scoreData : scoreList) {
            Object id = scoreData.get("id");
            boolean foundId = false;
            for (int i = 0; i < expectedIds.length; i++) {
                long expectedId = expectedIds[i];
                int expectedPnts = expectedPoints[i];
                if (String.valueOf(expectedId).equals(String.valueOf(id))) {
                    foundId = true;
                    assertEquals("GET-request to /app/games/" + gameName + "/scores returns incorrect number of points for a score",
                            expectedPnts, scoreData.get("points"));
                    break;
                }
            }
            if (!foundId) {
                fail("Cannot find a stored score in the JSON array response of GET-request to /app/games/" + gameName + "/scores");
            }
        }
    }

    protected void expectNotToFindGameFromList(Long expectedId) {
        Response response = getGames();

        Map matchingGame = null;
        List<Map> gameList = response.body().jsonPath().getList("", Map.class);
        for (Map gameData : gameList) {
            Object id = gameData.get("id");
            if (String.valueOf(expectedId).equals(String.valueOf(id))) {
                matchingGame = gameData;
                break;
            }
        }

        if (matchingGame != null) {
            fail("Expected not to find a game after deletion in the JSON array response of GET-request to /app/games");
            return;
        }
    }

    protected void expectNotToFindScoreFromList(String gameName, long[] expectedIds) {
        Response response = getScores(gameName);

        List<Map> scoreList = response.body().jsonPath().getList("", Map.class);
        for (Map scoreData : scoreList) {
            Object id = scoreData.get("id");
            boolean foundId = false;
            for (int i = 0; i < expectedIds.length; i++) {
                long expectedId = expectedIds[i];
                if (String.valueOf(expectedId).equals(String.valueOf(id))) {
                    foundId = true;
                    break;
                }
            }
            if (foundId) {
                fail("Expected not to find a score after deletion in the JSON array response of GET-request to /app/games/" + gameName + "/scores");
            }
        }
    }

    protected Response postGame(String name) {
        Map<String, Object> request = new HashMap<String, Object>();
        request.put("name", name);
        return given().request().
                header("Accept", ContentType.JSON.toString()).
                contentType(ContentType.JSON).
                body(request).
                log().all().
            expect().response().
                statusCode(HttpStatus.OK.value()).
                body("id", notNullValue(Long.class)).
                body("name", equalTo(name)).
            when().post("/app/games");
    }

    protected Response getGame(String name) {
        return given().request().
                header("Accept", ContentType.JSON.toString()).
                log().all().
            expect().response().statusCode(HttpStatus.OK.value()).
                body("id", notNullValue(Long.class)).
                body("name", equalTo(name)).
            when().get("/app/games/" + name);
    }

    protected Response deleteGame(String name) {
        return given().request().
                log().all().
            expect().response().statusCode(HttpStatus.OK.value()).
            when().delete("/app/games/" + name);
    }

    protected Response getGames() {
        return given().request().
                header("Accept", ContentType.JSON.toString()).
                log().all().
            expect().response().statusCode(HttpStatus.OK.value()).
            when().get("/app/games");
    }

    protected Response postScore(String gameName,
            String nickname, int points) {
        Map<String, Object> request = new HashMap<String, Object>();
        request.put("nickname", nickname);
        request.put("points", points);
        return given().request().
                header("Accept", ContentType.JSON.toString()).
                contentType(ContentType.JSON).
                body(request).
                log().all().
            expect().response().
                statusCode(HttpStatus.OK.value()).
                body("id", notNullValue(Long.class)).
                body("nickname", equalTo(nickname)).
                body("points", equalTo(points)).
                body("timestamp", notNullValue()).
                body("game", nullValue()). // game should not be included in score
            when().post("/app/games/" + gameName + "/scores");
    }

    protected Response getScore(String gameName, Long scoreId,
            String expectedNickname, int expectedPoints) {
        return given().request().
                header("Accept", ContentType.JSON.toString()).
                log().all().
            expect().response().statusCode(HttpStatus.OK.value()).
                body("id", notNullValue(Long.class)).
                body("nickname", equalTo(expectedNickname)).
                body("points", equalTo(expectedPoints)).
                body("timestamp", notNullValue()).
                body("game", nullValue()). // game should not be included in score
            when().get("/app/games/" + gameName + "/scores/" + scoreId);
    }

    protected Response deleteScore(String gameName, Long scoreId) {
        return given().request().
                log().all().
            expect().response().statusCode(HttpStatus.OK.value()).
            when().delete("/app/games/" + gameName + "/scores/" + scoreId);
    }

    protected Response getScores(String gameName) {
        return given().request().
                header("Accept", ContentType.JSON.toString()).
                log().all().
            expect().response().statusCode(HttpStatus.OK.value()).
            when().get("/app/games/" + gameName + "/scores");
    }
}
