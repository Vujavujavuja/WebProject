package touristapp.controllers;

import static spark.Spark.*;

import com.google.gson.Gson;
import touristapp.services.ActivityService;
import touristapp.enteties.Activity;

public class ActivityController {
    private static ActivityService activityService = new ActivityService();
    private static Gson gson = new Gson();

    public static void setupRoutes() {
        get("/activities", (req, res) -> {
            res.type("application/json");
            return gson.toJson(activityService.getAllActivities());
        });

        post("/activities", (req, res) -> {
            res.type("application/json");
            System.out.println(req.body());
            System.out.println(gson.fromJson(req.body(), Activity.class));
            Activity activity = gson.fromJson(req.body(), Activity.class);
            activityService.addActivity(activity);
            res.status(201);
            //return activities id
            System.out.println(activity.getId());
            System.out.println(gson.toJson(activity.getId()));
            return gson.toJson(activity.getId());
        });

        get("/activities/:id", (req, res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params(":id"));
            return gson.toJson(activityService.getActivityById(id));
        });

    }
}
