package com.rummy51.ireifej.rummy51;

public class Settings
{
    /// <summary>
    /// The font size for the application
    /// </summary>
    private static int m_fontSize = 12;

    /// <summary>
    /// The font size for the continue portion of the application (bottom panel)
    /// </summary>
    private static int m_continueFontSize = 12;

    /// <summary>
    /// The list of players for this instance
    /// </summary>
    private static Players m_players = new Players();

    /// <summary>
    /// Get the font size
    /// </summary>
    public static int getFontSize()
    {
        return m_fontSize;
    }

    /// <summary>
    /// Set the font size
    /// </summary>
    public static void setFontSize(int value)
    {
        m_fontSize = value;
    }

    /// <summary>
    /// Get the continue font size (bottom panel)
    /// </summary>
    public static int getContinueFontSize()
    {
        return m_continueFontSize;
    }

    /// <summary>
    /// Set the continue font size (bottom panel)
    /// </summary>
    public static void setContinueFontSize(int value)
    {
        m_continueFontSize = value;
    }

    /// <summary>
    /// Get the current instance players
    /// </summary>
    public static Players getPlayers()
    {
        return m_players;
    }

    /// <summary>
    /// Set the current instance players
    /// </summary>
    public static void setPlayers(Players value)
    {
        m_players = value;
    }
}
