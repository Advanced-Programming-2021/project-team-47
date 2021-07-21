public class LogoutController {

    public static String logout(Menus menu , String token) {
        if (menu == Menus.MainMenu) {
            LoginController.getAllLoggedInPlayers().remove(token);
            return Response.userLogoutSuccessfully;
        }
        return Response.notInMainMenu;
    }
}
