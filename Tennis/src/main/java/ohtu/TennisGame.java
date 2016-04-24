package ohtu;

public class TennisGame {

    private int player1Score = 0;
    private int player2Score = 0;
    private String player1Name;
    private String player2Name;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        switch (playerName) {
            case "player1":
                player1Score += 1;
                break;
            case "player2":
                player2Score += 1;
                break;
        }
    }

    public String getScore() {
        String score = "";
        if (player1Score == player2Score) {
            score = scoreWhenEven(score);
        } else if (player1Score >= 4 || player2Score >= 4) {
            score = switchForMinusResult(score);
        } else {
            score = parseUnevenScore(score, player1Score) + "-" + parseUnevenScore(score, player2Score);
        }
        return score;
    }

    public String parseUnevenScore(String score, int tempScore) {
        switch (tempScore) {
            case 0:
                score += "Love";
                break;
            case 1:
                score += "Fifteen";
                break;
            case 2:
                score += "Thirty";
                break;
            case 3:
                score += "Forty";
                break;
        }
        return score;
    }

    public String switchForMinusResult(String score) {
        if ((player1Score - player2Score) == 1) {
            score = "Advantage player1";
        } else if ((player1Score - player2Score) == -1) {
            score = "Advantage player2";
        } else if ((player1Score - player2Score) > 1) {
            score = "Win for player1";
        } else if ((player1Score - player2Score) < -1) {
            score = "Win for player2";
        }
        return score;
    }

    public String scoreWhenEven(String score) {
        switch (player1Score) {
            case 0:
                score = "Love-All";
                break;
            case 1:
                score = "Fifteen-All";
                break;
            case 2:
                score = "Thirty-All";
                break;
            case 3:
                score = "Forty-All";
                break;
            default:
                score = "Deuce";
                break;

        }
        return score;
    }
}
