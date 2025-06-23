public class ThemeManager {
    private static Theme currentTheme = Theme.CLASSIC;

    public static void setTheme(Theme theme) {
        currentTheme = theme;
    }

    public static Theme getTheme() {
        return currentTheme;
    }
}
