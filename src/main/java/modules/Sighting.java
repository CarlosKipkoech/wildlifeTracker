package modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.sql2o.*;

public class  Sighting implements DatabaseManagement {
    private String location;
    private String ranger_name;
    private int id;
    private int animal_id;

    public Sighting(String location,String ranger_name,int animal_id) {
       this.location = location;
       this.ranger_name = ranger_name;
       this.animal_id = animal_id;

    }

    public String getLocation() {
        return location;
    }

    public String getRanger() {
        return ranger_name;
    }

    public int getId() {
        return id;
    }

    public int getAnimal_id() {
        return animal_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sighting sighting = (Sighting) o;
        return Objects.equals(location, sighting.location) &&
                Objects.equals(ranger_name, sighting.ranger_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, ranger_name);
    }

    @Override
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO sightings (animal_id, location,ranger_name) VALUES (:animal_id, :location, :ranger_name);";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("animal_id",this.animal_id)
                    .addParameter("location", this.location)
                    .addParameter("ranger_name", this.ranger_name)
                    .throwOnMappingFailure(false)
                    .executeUpdate()
                    .getKey();

        }
    }
    public static List<Sighting> all() {
        String sql = "SELECT * FROM sightings";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).throwOnMappingFailure(false).executeAndFetch(Sighting.class);

        }
    }
    public static Sighting find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM sightings WHERE id=:id";
            Sighting sighting = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Sighting.class);
            return sighting;

        }
    }
    public List<Object> getAnimals() {
        List<Object> allAnimals = new ArrayList<Object>();

        try(Connection con = DB.sql2o.open()) {
            String sqlAnimal = "SELECT * FROM animals WHERE sightingId=:id ;";
            List<Animal> animal = con.createQuery(sqlAnimal)
                    .addParameter("id",this.id)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Animal.class);
            allAnimals.addAll(animal);

            String sqlEndangered = "SELECT * FROM endangered_animals WHERE sightingId=:id ;";
            List<Endangered> endangered = con.createQuery(sqlEndangered)
                    .addParameter("id", this.id)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Endangered.class);
            allAnimals.addAll(endangered);
        }
        return allAnimals;
    }

    @Override
    public void delete() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM sightings WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id", this.id)
                    .executeUpdate();
        }
    }


}