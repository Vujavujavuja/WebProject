package touristapp;

import static spark.Spark.*;

import spark.Service;
import spark.staticfiles.StaticFilesConfiguration;
import touristapp.controllers.*;
import touristapp.services.UserService;
import touristapp.util.DatabaseUtil;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        DatabaseUtil.initDatabase();
        UserService userService = new UserService();

        port(4343);

        StaticFilesConfiguration staticFiles = new StaticFilesConfiguration();
        staticFiles.configure("/static");

        before((req, res) -> System.out.println("Received api call: " + req.requestMethod() + " " + req.url()));

        before("/editor.html", (req, res) -> {
            String email = req.session().attribute("user");
            if (req.session().attribute("user") == null) {
                res.redirect("/index.html");
            }
            else if(userService.isAdmin(email)){
                res.redirect("/admin.html");
            }
        });

        before("/admin.html", (req, res) -> {
            System.out.println(Optional.ofNullable(req.session().attribute("user")));
            //check usertype by email
            String email = req.session().attribute("user");

            boolean isAdmin = userService.isAdmin(email);
            if (!isAdmin) {
                if(userService.isEditor(email)){
                    res.redirect("/editor.html");
                }
                else{
                    res.redirect("/index.html");
                }
            }

        });

        before((req, res) -> {
            staticFiles.consume(req.raw(), res.raw());
        });

        path("/api", () -> {
            UserController.setupRoutes();
            DestinationController.setupRoutes();
            ActivityController.setupRoutes();
            ArticleController.setupRoutes();
            CommentController.setupRoutes();
        });

        get("/", (req, res) -> {
            res.redirect("/index.html");
            return null;
        });

        System.out.println("Server started at: http://localhost:4343");
    }
}
