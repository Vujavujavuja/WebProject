package touristapp.controllers;

import static spark.Spark.*;

import touristapp.enteties.User;
import touristapp.services.UserService;
import com.google.gson.Gson;

public class UserController {
    private static UserService userService = new UserService();
    private static Gson gson = new Gson();

    public static void setupRoutes() {
        get("/users", (req, res) -> {
            res.type("application/json");
            return gson.toJson(userService.getAllUsers());
        });

        //get user by id
        get("/users/:id", (req, res) -> {
            res.type("application/json");
            return gson.toJson(userService.getUserById(Integer.parseInt(req.params(":id"))));
        });

        post("/users", (req, res) -> {
            res.type("application/json");
            System.out.println(req.body());
            userService.addUser(gson.fromJson(req.body(), User.class));
            res.status(201);
            return "";
        });

        put("/users/:id", (req, res) -> {
            res.type("application/json");
            userService.updateUser(Integer.parseInt(req.params(":id")), gson.fromJson(req.body(), User.class));
            return "";
        });

        post("/login", (req, res) -> {
            res.type("application/json");
            String email = req.queryParams("email");
            String password = req.queryParams("password");

            boolean login = userService.verify(email, password);
            boolean active = userService.active(email);

            if (login) {
                if(active) {
                    req.session(true).attribute("user", email);
                    res.status(200);
                    return gson.toJson("Login successful");
                }
                res.status(401);
                return gson.toJson("Account is not active");
            } else {
                res.status(401);
                return gson.toJson("Wrong email or password");
            }
        });

        get("/logout", (req, res) -> {
            req.session().invalidate();
            res.status(200);
            return gson.toJson("Logged out");
        });
    }
}
