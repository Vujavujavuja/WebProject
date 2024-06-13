package touristapp.controllers;

import static spark.Spark.*;

import com.google.gson.Gson;
import touristapp.services.CommentService;
import touristapp.enteties.Comment;

public class CommentController {
    private static CommentService commentService = new CommentService();
    private static Gson gson = new Gson();

    public static void setupRoutes() {
        get("/comments/", (req, res) -> {
            res.type("application/json");
            return gson.toJson(commentService.getAllComments());
        });

        post("/comments/", (req, res) -> {
            res.type("application/json");
            Comment comment = gson.fromJson(req.body(), Comment.class);
            commentService.addComment(comment);
            res.status(201);
            return "";
        });

        get("/comments/:id", (req, res) -> {
            res.type("application/json");
            return gson.toJson(commentService.getCommentsByArticleId(Integer.parseInt(req.params(":id"))));
        });
    }
}
