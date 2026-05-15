package app;

import static spark.Spark.*;
import service.FuncionarioService;

public class Aplicacao {
	
	private static FuncionarioService funcionarioService = new FuncionarioService();
	
    public static void main(String[] args) {
        port(4537);
        
        staticFiles.location("/public");
        
        post("/funcionario/insert", (request, response) -> funcionarioService.insert(request, response));

        get("/funcionario/:id", (request, response) -> funcionarioService.get(request, response));
        
        get("/funcionario/list/:orderby", (request, response) -> funcionarioService.getAll(request, response));

        get("/funcionario/update/:id", (request, response) -> funcionarioService.getToUpdate(request, response));
        
        post("/funcionario/update/:id", (request, response) -> funcionarioService.update(request, response));
           
        get("/funcionario/delete/:id", (request, response) -> funcionarioService.delete(request, response));
    }	
}
