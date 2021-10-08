package edu.escuelaing.arep;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
public class App {
    public static void main (String[]args){
        port(getPort());
        URLReader.main();
        secure("keystores/ecikeystore.p12", "123456" ,null,null);

        Gson gson = new Gson();
        Map<String,String> users = new HashMap<>();
        users.put("anamariasalazar", Hashing.sha256().hashString("ana", StandardCharsets.UTF_8).toString());
        staticFileLocation("/static");

        get("/", (req,res) -> {
            res.redirect("login.html");

            return "";
        });
        post("/acceso", (req,res) -> {
            req.session(true);
            User user = gson.fromJson(req.body(), User.class);
            if(Hashing.sha256().hashString(user.getContraseña(), StandardCharsets.UTF_8).toString().equals(users.get(user.getCorreo()))){
                req.session().attribute("User",user.getCorreo());
                req.session().attribute("Loged",true);
                return "si";
            }

            return "no";
        });
        get("/autorizado", (req,res) -> {
            String server = URLReader.readURL("https://"+((req.url().split("/")[2]).split(":")[0])+":4566/server");

            return server;
        });
    }
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
