package touristapp.controllers;

import static spark.Spark.*;

import com.google.gson.Gson;
import touristapp.services.ArticleService;
import touristapp.enteties.Article;
import touristapp.enteties.User;
import touristapp.services.DestinationService;
import touristapp.services.UserService;
import touristapp.util.GsonUtil;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

public class ArticleController {
    private static ArticleService articleService = new ArticleService();
    private static DestinationService destinationService = new DestinationService();
    private static Gson gson = GsonUtil.getGson();

    public static void setupRoutes() {
        get("/articles", (req, res) -> {
            res.type("application/json");
            return gson.toJson(articleService.getAllArticles());
        });

        post("/articles", (req, res) -> {
            res.type("application/json");
            String email = req.session().attribute("user");
            UserService userService = new UserService();
            System.out.println(email);

            User user = userService.getUserByEmail(email);
            if (user == null) {
                res.status(401);
                return "Unauthorized";
            }
            System.out.println("Pre artikle = " + req.body());
            Article article = gson.fromJson(req.body(), Article.class);
            article.setTime(LocalDateTime.now());
            article.setAuthorId(user.getId());
            System.out.println(article);

            System.out.println("Destination ID: " + article.getDestinationId());
            System.out.println("Activity ID: " + article.getActivityId());

            if (!destinationService.doesDestinationExist(article.getDestinationId())) {
                res.status(400);
                return gson.toJson("Destination ID does not exist");
            }

            articleService.addArticle(article);
            res.status(201);
            return "";
        });


        get("/articles/:id", (req, res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params("id"));
            return gson.toJson(articleService.getArticle(id));
        });

        delete("/articles/:id", (req, res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params("id"));
            articleService.deleteArticle(id);
            res.status(204);
            return "";
        });

        put("/articles/:id", (req, res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params("id"));
            Article article = gson.fromJson(req.body(), Article.class);
            articleService.updateArticle(id, article);
            res.status(200);
            return "";
        });

        get("/articlesplaginized/:pageNumber", (req, res) -> {
            res.type("application/json");
            int pageNumber = Integer.parseInt(req.params("pageNumber"));
            return gson.toJson(articleService.getPlaginizedArticles(pageNumber, 4));
        });

        get("/articlesviewed/", (req, res) -> {
            res.type("application/json");
            return gson.toJson(articleService.getMostViewedArticles());
        });

        get("/articlestotal", (req, res) -> {
            res.type("application/json");
            int totalArticles = articleService.getTotalArticles();
            return gson.toJson(Collections.singletonMap("totalArticles", totalArticles));
        });

        post("/articlesaddview/:id", (req, res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params("id"));
            articleService.addArticleView(id);
            res.status(200);
            return "";
        });

        get("/articledest/:id", (req, res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params("id"));
            return gson.toJson(articleService.getArticleDestination(id));
        });

        get("/articleactivity/:id", (req, res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params("id"));
            return gson.toJson(articleService.getArticleActivity(id));
        });

        get("/articleacc/:id", (req, res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params("id"));
            return gson.toJson(articleService.getActivityId(id));
        });

    }
}
