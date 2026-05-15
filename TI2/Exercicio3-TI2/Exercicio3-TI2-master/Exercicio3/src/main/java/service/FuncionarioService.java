package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;

import dao.FuncionarioDAO;
import model.Funcionario;
import spark.Request;
import spark.Response;

public class FuncionarioService {

    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private String form;
    private final int FORM_INSERT = 1;
    private final int FORM_DETAIL = 2;
    private final int FORM_UPDATE = 3;
    private final int FORM_ORDERBY_ID = 1;
    private final int FORM_ORDERBY_NOME = 2;
    private final int FORM_ORDERBY_IDADE = 3;

    public FuncionarioService() {
        makeForm();
    }

    public void makeForm() {
        makeForm(FORM_INSERT, new Funcionario(), FORM_ORDERBY_NOME);
    }

    public void makeForm(int orderBy) {
        makeForm(FORM_INSERT, new Funcionario(), orderBy);
    }

    public void makeForm(int tipo, Funcionario funcionario, int orderBy) {
        String nomeArquivo = "form.html";
        form = "";
        try {
        	Scanner entrada = new Scanner(getClass().getClassLoader().getResourceAsStream(nomeArquivo));
            while (entrada.hasNext()) {
                form += (entrada.nextLine() + "\n");
            }
            entrada.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String umFuncionario = "";
        if (tipo != FORM_INSERT) {
            umFuncionario += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umFuncionario += "\t\t<tr>";
            umFuncionario += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/funcionario/list/1\">Novo Funcionário</a></b></font></td>";
            umFuncionario += "\t\t</tr>";
            umFuncionario += "\t</table>";
            umFuncionario += "\t<br>";
        }

        if (tipo == FORM_INSERT || tipo == FORM_UPDATE) {
            String action = "/funcionario/";
            String name, buttonLabel, nome, idade, funcao, sexo;
            if (tipo == FORM_INSERT) {
                action += "insert";
                name = "Inserir Funcionário";
                nome = "";
                idade = "";
                funcao = "";
                sexo = "";
                buttonLabel = "Inserir";
            } else {
                action += "update/" + funcionario.getId();
                name = "Atualizar Funcionário (ID " + funcionario.getId() + ")";
                nome = funcionario.getNome();
                idade = String.valueOf(funcionario.getIdade());
                funcao = funcionario.getFuncao();
                sexo = funcionario.getSexo() == '\u0000' ? "" : String.valueOf(funcionario.getSexo());
                buttonLabel = "Atualizar";
            }
            umFuncionario += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
            umFuncionario += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umFuncionario += "\t\t<tr>";
            umFuncionario += "\t\t\t<td colspan=\"4\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
            umFuncionario += "\t\t</tr>";
            umFuncionario += "\t\t<tr><td colspan=\"4\">&nbsp;</td></tr>";
            umFuncionario += "\t\t<tr>";
            umFuncionario += "\t\t\t<td>&nbsp;Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\"" + nome + "\"></td>";
            umFuncionario += "\t\t\t<td>Idade: <input class=\"input--register\" type=\"number\" name=\"idade\" value=\"" + idade + "\"></td>";
            umFuncionario += "\t\t\t<td>Função: <input class=\"input--register\" type=\"text\" name=\"funcao\" value=\"" + funcao + "\"></td>";
            umFuncionario += "\t\t\t<td>Sexo: <input class=\"input--register\" type=\"text\" maxlength=\"1\" name=\"sexo\" value=\"" + sexo + "\"></td>";
            umFuncionario += "\t\t</tr>";
            umFuncionario += "\t\t<tr><td colspan=\"4\" align=\"center\"><input type=\"submit\" value=\"" + buttonLabel + "\" class=\"input--main__style input--button\"></td></tr>";
            umFuncionario += "\t</table>";
            umFuncionario += "\t</form>";
        } else if (tipo == FORM_DETAIL) {
            umFuncionario += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umFuncionario += "\t\t<tr>";
            umFuncionario += "\t\t\t<td colspan=\"4\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Funcionário (ID " + funcionario.getId() + ")</b></font></td>";
            umFuncionario += "\t\t</tr>";
            umFuncionario += "\t\t<tr><td colspan=\"4\">&nbsp;</td></tr>";
            umFuncionario += "\t\t<tr>";
            umFuncionario += "\t\t\t<td>&nbsp;Nome: " + funcionario.getNome() + "</td>";
            umFuncionario += "\t\t\t<td>Idade: " + funcionario.getIdade() + "</td>";
            umFuncionario += "\t\t\t<td>Função: " + funcionario.getFuncao() + "</td>";
            umFuncionario += "\t\t\t<td>Sexo: " + funcionario.getSexo() + "</td>";
            umFuncionario += "\t\t</tr>";
            umFuncionario += "\t</table>";
        } else {
            System.out.println("ERRO! Tipo não identificado " + tipo);
        }
        form = form.replaceFirst("<UM-FUNCIONARIO>", umFuncionario);

        // LISTA DE FUNCIONÁRIOS
        String list = "<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">";
        list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Funcionários</b></font></td></tr>\n" +
                "\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
                "\n<tr>\n" +
                "\t<td><a href=\"/funcionario/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
                "\t<td><a href=\"/funcionario/list/" + FORM_ORDERBY_NOME + "\"><b>Nome</b></a></td>\n" +
                "\t<td><a href=\"/funcionario/list/" + FORM_ORDERBY_IDADE + "\"><b>Idade</b></a></td>\n" +
                "\t<td><b>Função</b></td>\n" +
                "\t<td><b>Sexo</b></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Ações</b></td>\n" +
                "</tr>\n";

        List<Funcionario> funcionarios;
        if (orderBy == FORM_ORDERBY_ID) funcionarios = funcionarioDAO.getOrderByID();
        else if (orderBy == FORM_ORDERBY_NOME) funcionarios = funcionarioDAO.getOrderByNome();
        else if (orderBy == FORM_ORDERBY_IDADE) funcionarios = funcionarioDAO.getOrderByIdade();
        else funcionarios = funcionarioDAO.get();

        int i = 0;
        String bgcolor = "";
        for (Funcionario f : funcionarios) {
            bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
            list += "\n<tr bgcolor=\"" + bgcolor + "\">\n" +
                    "\t<td>" + f.getId() + "</td>\n" +
                    "\t<td>" + f.getNome() + "</td>\n" +
                    "\t<td>" + f.getIdade() + "</td>\n" +
                    "\t<td>" + f.getFuncao() + "</td>\n" +
                    "\t<td>" + f.getSexo() + "</td>\n" +
                    "\t<td align=\"center\">\n" +
                    "\t<a href=\"/funcionario/" + f.getId() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a>\n" +
                    "\t<a href=\"/funcionario/update/" + f.getId() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a>\n" +
                    "\t<a href=\"javascript:confirmarDeleteFuncionario('" + f.getId() + "', '" + f.getNome() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a>\n" +
                    "\t</td>\n" +
                    "</tr>\n";
        }
        list += "</table>";
        form = form.replaceFirst("<LISTAR-FUNCIONARIO>", list);
    }

    public Object insert(Request request, Response response) {
        String nome = request.queryParams("nome");
        int idade = Integer.parseInt(request.queryParams("idade"));
        String funcao = request.queryParams("funcao");
        String sexo = request.queryParams("sexo");

        Funcionario funcionario = new Funcionario(-1, nome, idade, funcao, sexo.isEmpty() ? '\u0000' : sexo.charAt(0));

        String resp;
        if (funcionarioDAO.insert(funcionario)) {
            resp = "Funcionário (" + nome + ") inserido!";
            response.status(201);
        } else {
            resp = "Funcionário (" + nome + ") não inserido!";
            response.status(404);
        }

        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
                "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }

    public Object get(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Funcionario funcionario = funcionarioDAO.get(id);

        if (funcionario != null) {
            response.status(200);
            makeForm(FORM_DETAIL, funcionario, FORM_ORDERBY_NOME);
        } else {
            response.status(404);
            String resp = "Funcionário " + id + " não encontrado.";
            makeForm();
            form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
                    "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
        }
        return form;
    }

    public Object getToUpdate(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Funcionario funcionario = funcionarioDAO.get(id);

        if (funcionario != null) {
            response.status(200);
            makeForm(FORM_UPDATE, funcionario, FORM_ORDERBY_NOME);
        } else {
            response.status(404);
            String resp = "Funcionário " + id + " não encontrado.";
            makeForm();
            form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
                    "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
        }
        return form;
    }

    public Object getAll(Request request, Response response) {
        int orderBy = Integer.parseInt(request.params(":orderby"));
        makeForm(orderBy);
        response.header("Content-Type", "text/html");
        response.header("Content-Encoding", "UTF-8");
        return form;
    }

    public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Funcionario funcionario = funcionarioDAO.get(id);
        String resp;

        if (funcionario != null) {
            funcionario.setNome(request.queryParams("nome"));
            funcionario.setIdade(Integer.parseInt(request.queryParams("idade")));
            funcionario.setFuncao(request.queryParams("funcao"));
            String sexo = request.queryParams("sexo");
            funcionario.setSexo(sexo.isEmpty() ? '\u0000' : sexo.charAt(0));

            funcionarioDAO.update(funcionario);
            response.status(200);
            resp = "Funcionário (ID " + funcionario.getId() + ") atualizado!";
        } else {
            response.status(404);
            resp = "Funcionário (" + id + ") não encontrado!";
        }

        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
                "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }

    public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Funcionario funcionario = funcionarioDAO.get(id);
        String resp;

        if (funcionario != null) {
            funcionarioDAO.delete(id);
            response.status(200);
            resp = "Funcionário (" + id + ") excluído!";
        } else {
            response.status(404);
            resp = "Funcionário (" + id + ") não encontrado!";
        }

        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
                "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }
}

