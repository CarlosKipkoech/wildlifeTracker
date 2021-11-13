package modules;

import java.sql.Timestamp;
import org.sql2o.*;

public abstract class Animals{
    public String name;
    public Timestamp createdAt;


    public String getName() {
        return name;
    }
    public Timestamp getCreatedAt(){
        return createdAt;
    }







}