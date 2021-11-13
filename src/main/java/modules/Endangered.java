package modules;

import org.sql2o.Connection;

import java.util.List;
import java.util.Objects;

public class Endangered extends Animals implements DatabaseManagement{
    private String health;
    private String age;
    private boolean endangered;
    private int id;


    public static final String HEALTH_ILL = "ill";
    public static final String HEALTH_HEALTHY = "healthy";
    public static final String HEALTH_OK = "ok";

    public static final String AGE_BABY = "newborn";
    public static final String AGE_YOUNG = "young";
    public static final String AGE_ADULT = "adult";


    public Endangered(String name,String health,String age) {
        this.name = name;
        this.health = health;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endangered that = (Endangered) o;
        return Objects.equals(health, that.health) &&
                Objects.equals(age, that.age)&&
                Objects.equals(name,that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(health, age, name);
    }

    @Override
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO endangered_animals (name, health, age) VALUES (:name, :health, :age);";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("health",this.health)
                    .addParameter("age",this.age)
                    .executeUpdate()
                    .getKey();
        }
    }

    public int getId() {
        return id;
    }

    public static List<Endangered> all() {
        String sql = "SELECT * FROM endangered_animals";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Endangered.class);
        }
    }
    public static Endangered find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM endangered_animals WHERE id=:id";
            Endangered endangeredAnimal = con.createQuery(sql)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Endangered.class);
            return endangeredAnimal;
        }
    }

    public List<Sighting> getSightings() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM sightings WHERE animal_id=:id;";
            List<Sighting> sightings = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetch(Sighting.class);
            return sightings;
        }
    }

    @Override
    public void delete() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM endangered_animals WHERE id=:id;";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    public String getHealth() {
        return health;
    }

    public String getAge() {
        return age;
    }


}
