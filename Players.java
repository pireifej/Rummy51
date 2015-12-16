package com.rummy51.ireifej.rummy51;

import java.util.ArrayList;

class Player
{
    /// <summary>
    /// The player's name
    /// </summary>
    private String m_playerName = "";

    /// <summary>
    /// The player's list of scores for the game
    /// </summary>
    private ArrayList<Score> m_scores = new ArrayList<Score>();

    /// <summary>
    /// Create a new player with a new name
    /// </summary>
    /// <param name="playerName">@param
    public Player(String playerName)
    {
        m_playerName = playerName;
    }

    /// <summary>
    /// Get the player's name
    /// </summary>
    public String PlayerName()
    {
        return m_playerName;
    }

    /// <summary>
    /// Get the scores
    /// </summary>
    public ArrayList<Score> Scores()
    {
        return m_scores;
    }

    /// <summary>
    /// Get the latest game's total score for this player
    /// </summary>
    public int getCurrentGameTotalScore()
    {
        if (m_scores.size() == 0)
        {
            return 0;
        }
        else
        {
            return m_scores.get(m_scores.size() - 1).getCurrentScore();
        }
    }

    /// <summary>
    /// Set the latest game's total score for this player
    /// </summary>
    public void setCurrentGameTotalScore(int value)
    {
        m_scores.get(m_scores.size() - 1).setCurrentScore(value);
    }

    /// <summary>
    /// Increment the current game total score by a given value
    /// </summary>
    /// <param name="score">@param
    public void IncrementCurrentGameTotalScore(int scoreToAdd)
    {
        int currentGameTotalScore = getCurrentGameTotalScore();
        currentGameTotalScore += scoreToAdd;

        m_scores.add(new Score(currentGameTotalScore, scoreToAdd));
    }

    /// <summary>
    /// Recalculate the game scores based on a new score to add
    /// </summary>
    /// <param name="gameNumber">@param
    /// <param name="newScoreToAdd">@param
    public void RecalculateGameScores(int gameNumber, int newScoreToAdd)
    {
        if (newScoreToAdd != 0)
        {
            // update this game's "score added" to the new value
            m_scores.get(gameNumber).setScoreAdded(newScoreToAdd);

            // go through all games after the game whose score added was just update and recalculate
            for (int gameIndex = gameNumber; gameIndex < m_scores.size(); gameIndex++)
            {
                if (gameIndex == 0)
                {
                    m_scores.get(gameIndex).setCurrentScore(m_scores.get(gameIndex).getScoreAdded());
                }
                else
                {
                    m_scores.get(gameIndex).setCurrentScore(m_scores.get(gameIndex - 1).getCurrentScore() + m_scores.get(gameIndex).getScoreAdded());
                }
            }
        }
    }
}

class Players
{
    /// <summary>
    /// The list of player names
    /// </summary>
    private ArrayList<Player>  m_players = null;

    /// <summary>
    /// The current game number
    /// </summary>
    private int m_currentGameNo = 1;

    /// <summary>
    /// The total number of games in this game
    /// </summary>
    private int m_totalGameNo = 7;

    /// <summary>
    /// The index of the player who shuffles this game
    /// </summary>
    private int m_shuffleIndex = 0;

    /// <summary>
    /// Constructor
    /// </summary>
    public Players()
    {
        m_players = new ArrayList<Player>();
    }

    /// <summary>
    /// Copy constructor
    /// </summary>
    /// <param name="players">@param
    public Players(Players players)
    {
        m_players = new ArrayList<Player>();

        for (Player player : m_players)
        {
            m_players.add(player);
        }
    }

    /// <summary>
    /// Get the list of players
    /// </summary>
    public ArrayList<Player> getListOfPlayers()
    {
        return m_players;
    }


    /// <summary>
    /// Get the current game number
    /// </summary>
    public int GetCurrentGameNo()
    {
        return m_currentGameNo;
    }

    /// <summary>
    /// Get the list of player names
    /// </summary>
    public ArrayList<String> getPlayerNames()
    {
        ArrayList<String> playerNames = new ArrayList<String>();

        for (Player playerName : m_players)
        {
            playerNames.add(playerName.PlayerName());
        }
        return playerNames;
    }

    /// <summary>
    /// Get the number of players
    /// </summary>
    public int PlayerCount()
    {
        return m_players.size();
    }

    /// <summary>
    /// Add the player to the list of players
    /// </summary>
    /// <param name="playerName">@param
    public void AddPlayerName (String playerName)
    {
        if (playerName != null && playerName.length() != 0)
        {
            Player newPlayer = new Player(playerName);
            m_players.add(newPlayer);
        }
    }

    /// <summary>
    /// Get the player with the minimum current game total score
    /// </summary>
    /// <returns></returns>
    public String GetWinnerName()
    {
        String playerName = "";
        int score = 999;

        // find maximum score
        for (Player player : m_players)
        {
            if (player.getCurrentGameTotalScore() < score)
            {
                score = player.getCurrentGameTotalScore();
                playerName = player.PlayerName();
            }
        }

        return playerName;
    }

    /// <summary>
    /// Get a list of the player names
    /// </summary>
    /// <returns></returns>
    public ArrayList<String> GetPlayerNames()
    {
        ArrayList<String> playerNames = new ArrayList<String>();

        for (Player player : m_players)
        {
            playerNames.add(player.PlayerName());
        }

        return playerNames;
    }

    /// <summary>
    /// Returns the player given a name
    /// </summary>
    /// <param name="playerName">@param
    /// <returns>Returns NULL if player does not exist</returns>
    public Player GetPlayerByName(String playerName)
    {
        Player playerToGet = null;

        for (Player player : m_players)
        {
            if (player.PlayerName() == playerName)
            {
                playerToGet = player;
            }
        }

        return playerToGet;
    }

    public Boolean IncrementCurrentGameNo()
    {
        m_shuffleIndex ++;
        if (m_shuffleIndex == m_players.size())
            m_shuffleIndex = 0;

        return (m_currentGameNo++ == m_totalGameNo);
    }

    public int GetTotalGameNo()
    {
        return(m_totalGameNo);
    }

    public String GetNextShuffler()
    {
        return(m_players.get(m_shuffleIndex).PlayerName());
    }

    public int GetShuffleIndex()
    {
        return(m_shuffleIndex);
    }
}

class Score
{
    /// <summary>
    /// The current score
    /// </summary>
    private int m_currentScore = 0;

    /// <summary>
    /// The score that was added to calculate the current score
    /// </summary>
    private int m_scoreAdded = 0;

    /// <summary>
    /// Get the current score
    /// </summary>
    public int getCurrentScore()
    {
        return m_currentScore;
    }

    /// <summary>
    /// Set the current score
    /// </summary>
    public void setCurrentScore(int value)
    {
        m_currentScore = value;
    }

    /// <summary>
    /// Get the score that was added
    /// </summary>
    public int getScoreAdded()
    {
        return m_scoreAdded;
    }

    public void setScoreAdded(int value)
    {
        m_scoreAdded = value;
    }

    /// <summary>
    /// Create a score given the current score and score that was added to calculate the current score
    /// </summary>
    /// <param name="currentScore">@param
    /// <param name="scoreAdded">@param
    public Score(int currentScore, int scoreAdded)
    {
        m_currentScore = currentScore;
        m_scoreAdded = scoreAdded;
    }

    /// <summary>
    /// Default constructor
    /// </summary>
    public Score()
    {
        m_currentScore = 0;
        m_scoreAdded = 0;
    }
}
