public class LogoutController {

    public static String logout(Menus menu) {
        if (menu == Menus.MainMenu) return Response.userLogoutSuccessfully;
        return Response.notInMainMenu;
    }
}
