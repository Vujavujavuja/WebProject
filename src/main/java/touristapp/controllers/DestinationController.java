package touristapp.controllers;

import static spark.Spark.*;

import com.google.gson.Gson;
import touristapp.services.DestinationService;
import touristapp.enteties.Destination;

public class DestinationController {
    private static DestinationService destinationService = new DestinationService();
    private static Gson gson = new Gson();

    public static void setupRoutes() {
        get("/destinations", (req, res) -> {
            res.type("application/json");
            return gson.toJson(destinationService.getAllDestinations());
        });

        post("/destinations", (req, res) -> {
            res.type("application/json");
            Destination destination = gson.fromJson(req.body(), Destination.class);
            destinationService.addDestination(destination);
            res.status(201);
            return gson.toJson("Destination added successfully");
        });

        delete("/destinations/:id", (req, res) -> {
            res.type("application/json");
            destinationService.removeDestination(Integer.parseInt(req.params(":id")));
            res.status(200);
            return gson.toJson("Destination updated successfully");
        });

        get("/destinations/:id", (req, res) -> {
            res.type("application/json");
            return gson.toJson(destinationService.getDestinationById(Integer.parseInt(req.params(":id"))));
        });

        //edit destination by id
        put("/destinations/:id", (req, res) -> {
            res.type("application/json");
            Destination destination = gson.fromJson(req.body(), Destination.class);
            destinationService.editDestination(Integer.parseInt(req.params(":id")), destination);
            res.status(200);
            return "";
        });

        //get destination by name
        get("/destinations/name/:name", (req, res) -> {
            res.type("application/json");
            return gson.toJson(destinationService.getDestinationByName(req.params(":name")));
        });

    }
}
