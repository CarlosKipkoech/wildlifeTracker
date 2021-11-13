import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modules.Animal;
import modules.Endangered;
import modules.Sighting;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        ProcessBuilder process = new ProcessBuilder();
        int port;


        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);

        staticFileLocation("/public");


        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("animals", Animal.all());
            model.put("endangered", Endangered.all());
            model.put("sightings",Sighting.all());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        post("/endangered_sighting", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String rangerName = request.queryParams("rangeName");
            int animalIdSelected = Integer.parseInt(request.queryParams("endangeredAnimalSelected"));
            String location = request.queryParams("location");
            Sighting sighting = new Sighting(location,rangerName,animalIdSelected );
            sighting.save();
            model.put("sighting", sighting);
            model.put("endangered", Endangered.all());
            String animal = Endangered.find(animalIdSelected).getName();
            model.put("animal", animal);
            return new ModelAndView(model,"success.hbs");
        }, new HandlebarsTemplateEngine());

        post("/sighting", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String rangerName = request.queryParams("rangerName");
            int animalIdSelected = Integer.parseInt(request.queryParams("animalSelected"));
            String location = request.queryParams("location");
            Sighting sighting = new Sighting(location, rangerName, animalIdSelected);
            sighting.save();
            model.put("sighting", sighting);
            model.put("animals", Animal.all());
            String animal = Animal.find(animalIdSelected).getName();
            model.put("animal", animal);

            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());


        get("/animal/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("HEALTH_HEALTHY", Endangered.HEALTH_HEALTHY);
            model.put("HEALTH_OK", Endangered.HEALTH_OK);
            model.put("HEALTH_ILL", Endangered.HEALTH_ILL);
            model.put("AGE_BABY", Endangered.AGE_BABY);
            model.put("AGE_YOUNG", Endangered.AGE_YOUNG);
            model.put("AGE_ADULT", Endangered.AGE_ADULT);
            model.put("animals", Animal.all());
            model.put("endangered", Endangered.all());
            return new ModelAndView(model, "animal-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/animal/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            boolean endangered = request.queryParams("endangered")!=null;
            if (endangered) {
                String name = request.queryParams("name");
                String health = request.queryParams("health");
                String age = request.queryParams("age");
                Endangered endangeredAnimal = new Endangered(name, health, age);
                endangeredAnimal.save();
                model.put("animals", Animal.all());
                model.put("endangered", Endangered.all());
            } else {
                String name = request.queryParams("name");
                Animal animal = new Animal(name);
                animal.save();
                model.put("animals", Animal.all());
                model.put("endangered", Endangered.all());
            }
            response.redirect("/");
            return null;
        });

        get("/animal/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Animal animal = Animal.find(Integer.parseInt(request.params("id")));
            model.put("animal", animal);
            return new ModelAndView(model, "animal-details.hbs");
        }, new HandlebarsTemplateEngine());


        get("/endangered_animal/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Endangered endangered = Endangered.find(Integer.parseInt(request.params("id")));
            model.put("endangered", endangered);
            return new ModelAndView(model, "endangered-details.hbs");
        }, new HandlebarsTemplateEngine());

    }

}
