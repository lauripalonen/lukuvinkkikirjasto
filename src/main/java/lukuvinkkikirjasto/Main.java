package lukuvinkkikirjasto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import lukuvinkkikirjasto.UI.UserInterface;
import lukuvinkkikirjasto.domain.Library;
import lukuvinkkikirjasto.domain.Note;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.velocity.VelocityTemplateEngine;

public class Main {
    
    static String portFromEnv = new ProcessBuilder().environment().get("PORT");
    static String layout = "templates/layout.html";
    
    static void setEnvPort(String port){
        portFromEnv = port;
    }
    
    static int findOutPort() {
        if ( portFromEnv!=null ) {
            return Integer.parseInt(portFromEnv);
        }
        
        return 3001;
    }

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        //Library library = new Library(); //in memory
        Library library = new Library("links.db"); //in sqlite database
//        UserInterface ui = new UserInterface(library, scanner);
//
//        ui.startLibrary();
        
        staticFiles.location("/public");
        port(findOutPort());
        
        get("/", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/index.html");
            ArrayList<Note> notes = library.listAll();
            model.put("noteList", notes);
            return new VelocityTemplateEngine().render(
                new ModelAndView(model, layout)
            );
        });
        
        get("/newlink", (request, response) -> {
            HashMap<String, Object> model = new HashMap();
            model.put("template", "templates/newlink.html");
            return new VelocityTemplateEngine().render(
                new ModelAndView(model, layout)
            );
        });
        
        post("/newlink", (request, response) -> {
            String header = request.queryParams("header");
            String url = request.queryParams("url");
            
            library.addLink(header, url);
            
            response.redirect("/");
            return null;
        });
        
        get("/newbook", (request, response) -> {
            HashMap<String, Object> model = new HashMap();
            model.put("template", "templates/newbook.html");
            return new VelocityTemplateEngine().render(
                new ModelAndView(model, layout)
            );
        });
        
        post("/newbook", (request, response) -> {
            String header = request.queryParams("header");
            String url = request.queryParams("url");
            String author = request.queryParams("author");
            String isbn = request.queryParams("isbn");
            
            library.addBook(header, url, author, isbn);
            
            response.redirect("/");
            return null;
        });
        
        post("/changetype", (request, response) -> {
            int noteType = Integer.parseInt(request.queryParams("noteType"));
            if (noteType == 1) {
                response.redirect("/newlink");
            } else if (noteType == 2) {
                response.redirect("/newbook");
            }
            return null;
        });
        
    }

}
