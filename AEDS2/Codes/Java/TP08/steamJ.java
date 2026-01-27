import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;

// ======================================
// Classe Game 
// ======================================
class Game {
    private int id;
    private String name;
    private String releaseDate;
    private int estimatedOwners;
    private float price;
    private ArrayList<String> supportedLanguages;
    private int metacriticScore;
    private float userScore;
    private int achievements;
    private ArrayList<String> publishers;
    private ArrayList<String> developers;
    private ArrayList<String> categories;
    private ArrayList<String> genres;
    private ArrayList<String> tags;

    public Game() {
        this.id = 0;
        this.name = "";
        this.releaseDate = "";
        this.estimatedOwners = 0;
        this.price = 0;
        this.supportedLanguages = new ArrayList<>();
        this.metacriticScore = 0;
        this.userScore = 0;
        this.achievements = 0;
        this.publishers = new ArrayList<>();
        this.developers = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.genres = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getEstimatedOwners() {
        return estimatedOwners;
    }

    public float getPrice() {
        return price;
    }

    public ArrayList<String> getSupportedLanguages() {
        return supportedLanguages;
    }

    public int getMetacriticScore() {
        return metacriticScore;
    }

    public float getUserScore() {
        return userScore;
    }

    public int getAchievements() {
        return achievements;
    }

    public ArrayList<String> getPublishers() {
        return publishers;
    }

    public ArrayList<String> getDevelopers() {
        return developers;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setEstimatedOwners(int estimatedOwners) {
        this.estimatedOwners = estimatedOwners;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setSupportedLanguages(ArrayList<String> supportedLanguages) {
        this.supportedLanguages = supportedLanguages;
    }

    public void setMetacriticScore(int metacriticScore) {
        this.metacriticScore = metacriticScore;
    }

    public void setUserScore(float userScore) {
        this.userScore = userScore;
    }

    public void setAchievements(int achievements) {
        this.achievements = achievements;
    }

    public void setPublishers(ArrayList<String> publishers) {
        this.publishers = publishers;
    }

    public void setDevelopers(ArrayList<String> developers) {
        this.developers = developers;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    @Override
    public Game clone() {
        return new Game(
                id, name, releaseDate, estimatedOwners, price,
                new ArrayList<>(supportedLanguages), metacriticScore, userScore,
                achievements, new ArrayList<>(publishers),
                new ArrayList<>(developers), new ArrayList<>(categories),
                new ArrayList<>(genres), new ArrayList<>(tags));
    }

    public Game(int id, String name, String releaseDate, int estimatedOwners, float price,
            ArrayList<String> supportedLanguages, int metacriticScore, float userScore,
            int achievements, ArrayList<String> publishers, ArrayList<String> developers,
            ArrayList<String> categories, ArrayList<String> genres, ArrayList<String> tags) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.estimatedOwners = estimatedOwners;
        this.price = price;
        this.supportedLanguages = supportedLanguages;
        this.metacriticScore = metacriticScore;
        this.userScore = userScore;
        this.achievements = achievements;
        this.publishers = publishers;
        this.developers = developers;
        this.categories = categories;
        this.genres = genres;
        this.tags = tags;
    }

    // Formata a data de lançamento
    private static String formatReleaseDate(String input) {
        String output = "";
        try {
            if (input.matches("[A-Za-z]{3} \\d{1,2}, \\d{4}")) {
                DateTimeFormatter inFmt = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
                LocalDate date = LocalDate.parse(input, inFmt);
                output = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } else if (input.matches("[A-Za-z]{3} \\d{4}")) {
                DateTimeFormatter inFmt = DateTimeFormatter.ofPattern("MMM yyyy", Locale.ENGLISH);
                YearMonth ym = YearMonth.parse(input, inFmt);
                output = ym.atDay(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } else if (input.matches("\\d{4}")) {
                int year = Integer.parseInt(input);
                LocalDate date = LocalDate.of(year, 1, 1);
                output = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
        } catch (Exception e) {
        }
        return output;
    }

    private ArrayList<String> formatArrays(String input) {
        ArrayList<String> array = new ArrayList<>();
        if (input != null) {
            String splitted[] = input.split(",");
            for (String s : splitted) {
                s = s.trim().replace("\"", "").replace("[", "").replace("]", "");
                if (s.startsWith("'"))
                    s = s.substring(1);
                if (s.endsWith("'"))
                    s = s.substring(0, s.length() - 1);
                if (!s.isEmpty())
                    array.add(s);
            }
        }
        return array;
    }

    // Lê uma linha do CSV
    public void ler(String line) {
        String newLine = "";
        boolean insideQuotes = false;
        int tam = line.length();

        for (int i = 0; i < tam; i++) {
            char currentChar = line.charAt(i);
            if (currentChar == '"')
                insideQuotes = !insideQuotes;
            if (!insideQuotes) {
                newLine += (currentChar == ',' ? ';' : currentChar);
            } else if (currentChar != '"' && currentChar != '[' && currentChar != ']') {
                newLine += currentChar;
            }
        }

        line = newLine;
        String splitted[] = line.split(";");

        try {
            setId(Integer.parseInt(splitted[0]));
            setName(splitted[1]);
            setReleaseDate(formatReleaseDate(splitted[2]));
            setEstimatedOwners(Integer.parseInt(splitted[3]));
            setPrice(Float.parseFloat(splitted[4]));
            setSupportedLanguages(formatArrays(splitted[5]));
            setMetacriticScore(Integer.parseInt(splitted[6]));
            setUserScore(Float.parseFloat(splitted[7]));
            setAchievements(Integer.parseInt(splitted[8]));
            setPublishers(splitted[9].trim().isEmpty() ? new ArrayList<>() : formatArrays(splitted[9]));
            setDevelopers(splitted[10].trim().isEmpty() ? new ArrayList<>() : formatArrays(splitted[10]));
            setCategories(splitted[11].trim().isEmpty() ? new ArrayList<>() : formatArrays(splitted[11]));
            setGenres(splitted[12].trim().isEmpty() ? new ArrayList<>() : formatArrays(splitted[12]));
            setTags(splitted[13].trim().isEmpty() ? new ArrayList<>() : formatArrays(splitted[13]));
        } catch (Exception e) {
        }
    }

    // Lê o CSV
    public static Game[] readDb() {
        ArrayList<Game> temp = new ArrayList<>();
        try (Scanner reader = new Scanner(new FileReader("/tmp/games.csv"))) {
            if (reader.hasNextLine())
                reader.nextLine();
            while (reader.hasNextLine()) {
                Game g = new Game();
                g.ler(reader.nextLine());
                temp.add(g);
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler CSV: " + e.getMessage());
        }
        return temp.toArray(new Game[0]);
    }

    public String toString() {
        return id + " ## " + name + " ## " + releaseDate + " ## " + estimatedOwners + " ## " + price + " ## "
                + supportedLanguages + " ## " + metacriticScore + " ## " + userScore + " ## " + achievements + " ## "
                + publishers + " ## " + developers + " ## " + categories + " ## " + genres + " ## " + tags + " ##";
    }
}

// ======================================
// Nó da fila encadeada
// ======================================
class Celula {
    public Game elemento;
    public Celula prox;

    public Celula(Game elemento) {
        this.elemento = elemento;
        this.prox = null;
    }
}

// ======================================
// Fila dinâmica (encadeada)
// ======================================
class FilaFlex {
    private Celula primeiro, ultimo;

    public FilaFlex() {
        primeiro = new Celula(null);
        ultimo = primeiro;
    }

    public void inserir(Game x) {
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }

    public Game remover() throws Exception {
        if (primeiro == ultimo)
            throw new Exception("Erro: fila vazia");
        Celula tmp = primeiro.prox;
        primeiro.prox = tmp.prox;
        if (primeiro.prox == null)
            ultimo = primeiro;
        return tmp.elemento;
    }

    public void mostrar() {
        Celula i = primeiro.prox;
        int pos = 0;
        while (i != null) {
            System.out.println("[" + pos + "] " + i.elemento.toString());
            i = i.prox;
            pos++;
        }
    }
}

// ===================
// No Arvore
// ===================
class No {
    public Game elemento;
    public No esq, dir;

    public No(Game x) {
        this.elemento = x;
        esq = dir = null;
    }
}

// ==================
// Arvore Bi
// ==================
class ArvoreBinaria {
    private No raiz;
    public int comparacoes = 0;

    public void inserir(Game x) {
        raiz = inserir(x, raiz);
    }

    private No inserir(Game x, No i) {
        if (i == null) {
            return new No(x);
        }

        int cmp = x.getName().compareTo(i.elemento.getName());
        if (cmp < 0) {
            i.esq = inserir(x, i.esq);
        } else if (cmp > 0) {
            i.dir = inserir(x, i.dir);
        }
        // se cmp == 0 → já existe → não insere
        return i;
    }

    // =================
    // Pesquisa
    // =================
    public boolean pesquisarComCaminho(String nome) {
        MyIO.print(nome + ": =>raiz ");
        return pesquisarComCaminho(nome, raiz);
    }

    private boolean pesquisarComCaminho(String nome, No i) {
        if (i == null) {
            System.out.println("NAO");
            return false;
        }

        comparacoes++;
        if (nome.compareTo(i.elemento.getName()) == 0) {
            System.out.println("SIM");
            return true;
        }

        comparacoes++;
        if (nome.compareTo(i.elemento.getName()) < 0) {
            System.out.print("esq ");
            return pesquisarComCaminho(nome, i.esq);
        } else {
            System.out.print("dir ");
            return pesquisarComCaminho(nome, i.dir);
        }
    }

}

// ====================================
// No Alvinegra
// ====================================
class NoAN {
    public Game elemento;
    public NoAN esq, dir;
    public boolean cor;

    // constructor default: BLACK (seguindo a lógica original que criava nós
    // inicialmente com cor false)
    public NoAN(Game elemento) {
        this(elemento, false);
    }

    public NoAN(Game elemento, boolean cor) {
        this.elemento = elemento;
        this.cor = cor;
        this.esq = this.dir = null;
    }
}

// ====================================
// Avore Alvinegra
// ====================================
class ArvoreAlvinegra {
    public NoAN raiz;
    public int comparacoes = 0;

    // Inserção pública
    public void inserir(Game x) {
        raiz = inserir(x, raiz);
        if (raiz != null)
            raiz.cor = false; // raiz sempre BLACK
    }

    // Inserção principal
    private NoAN inserir(Game x, NoAN i) {
        if (i == null) {
            i = new NoAN(x);
        } else if (i.esq == null && i.dir == null) { // 0 filhos
            if (x.getName().compareTo(i.elemento.getName()) < 0) {
                i.esq = new NoAN(x);
            } else {
                i.dir = new NoAN(x);
            }

        } else if (i.esq == null) { // apenas filho à direita
            if (x.getName().compareTo(i.elemento.getName()) < 0) {
                i.esq = new NoAN(x);

            } else if (x.getName().compareTo(i.dir.elemento.getName()) < 0) {
                // inserir entre i e i.dir
                i.esq = new NoAN(i.elemento.clone());
                i.elemento = x;
            } else {
                // shift: i.esq = antigo i, i.nome = i.dir.nome, i.dir.nome = x
                i.esq = new NoAN(i.elemento.clone());
                Game temp = i.dir.elemento;
                i.elemento = temp;
                i.dir.elemento = x;
            }
            // filhos ficam BLACK (como no original: i.esq.cor = i.dir.cor = false)
            if (i.esq != null)
                i.esq.cor = false;
            if (i.dir != null)
                i.dir.cor = false;

        } else if (i.dir == null) { // apenas filho à esquerda
            if (x.getName().compareTo(i.elemento.getName()) > 0) {
                i.dir = new NoAN(x);

            } else if (x.getName().compareTo(i.esq.elemento.getName()) > 0) {
                i.dir = new NoAN(i.elemento.clone());
                i.elemento = x;

            } else {
                i.dir = new NoAN(i.elemento.clone());
                Game temp = i.esq.elemento;
                i.elemento = temp;
                i.esq.elemento = x;
            }
            if (i.esq != null)
                i.esq.cor = false;
            if (i.dir != null)
                i.dir.cor = false;

        } else {
            // caso com dois filhos: chama a versão com bisavo/avo/pai para inserir em nível
            // mais profundo e balancear
            inserir(x, null, null, i, i);
        }

        if (raiz != null)
            raiz.cor = false;
        return i;
    }

    // Balanceamento explícito (adaptação do original)
    private void balancear(NoAN bisavo, NoAN avo, NoAN pai, NoAN i) {
        if (pai.cor == true) {
            // decide tipo de rotação comparando nomes (strings)
            if (pai.elemento.getName().compareTo(avo.elemento.getName()) > 0) { // pai é à direita do avô -> dir dir ou
                                                                                // dir esq
                if (i.elemento.getName().compareTo(pai.elemento.getName()) > 0) {
                    avo = rotacaoEsq(avo);
                } else {
                    avo = rotacaoDirEsq(avo);
                }
            } else { // pai é à esquerda do avô -> esq esq ou esq dir
                if (i.elemento.getName().compareTo(pai.elemento.getName()) < 0) {
                    avo = rotacaoDir(avo);
                } else {
                    avo = rotacaoEsqDir(avo);
                }
            }

            // ajustar ponteiro no bisavô
            if (bisavo == null) {
                raiz = avo;
            } else if (avo.elemento.getName().compareTo(bisavo.elemento.getName()) < 0) {
                bisavo.esq = avo;
            } else {
                bisavo.dir = avo;
            }

            // após rotação: avo vira BLACK; filhos do avo viram RED
            avo.cor = false;
            if (avo.esq != null)
                avo.esq.cor = true;
            if (avo.dir != null)
                avo.dir.cor = true;
        }
    }

    // Inserção recursiva com parâmetros para balanceamento
    private void inserir(Game x, NoAN bisavo, NoAN avo, NoAN pai, NoAN i) {
        if (i == null) {
            // insere novo nó RED como filho do pai
            if (x.getName().compareTo(pai.elemento.getName()) < 0) {
                i = pai.esq = new NoAN(x, true);
            } else {
                i = pai.dir = new NoAN(x, true);
            }

            if (pai.cor == true) {
                balancear(bisavo, avo, pai, i);
            }

        } else {
            // aplica recolor se ambos filhos são RED
            if (i.esq != null && i.dir != null && i.esq.cor && i.dir.cor) {
                i.cor = true;
                i.esq.cor = false;
                i.dir.cor = false;

                if (i == raiz) { // raiz deve ficar BLACK
                    i.cor = false;
                } else if (pai.cor == true) {
                    balancear(bisavo, avo, pai, i);
                }
            }

            // desce na árvore
            if (x.getName().compareTo(i.elemento.getName()) < 0) {
                inserir(x, avo, pai, i, i.esq); // esquerda
            } else if (x.getName().compareTo(i.elemento.getName()) > 0) {
                inserir(x, avo, pai, i, i.dir); // direita
            } // se igual, não insere (evitar duplicatas)
        }
    }

    // =========================
    // Rotações
    // =========================
    private NoAN rotacaoDir(NoAN no) {
        NoAN noEsq = no.esq;
        NoAN noEsqDir = noEsq.dir;

        noEsq.dir = no;
        no.esq = noEsqDir;

        return noEsq;
    }

    private NoAN rotacaoEsq(NoAN no) {
        NoAN noDir = no.dir;
        NoAN noDirEsq = noDir.esq;

        noDir.esq = no;
        no.dir = noDirEsq;

        return noDir;
    }

    private NoAN rotacaoDirEsq(NoAN no) {
        no.dir = rotacaoDir(no.dir);
        return rotacaoEsq(no);
    }

    private NoAN rotacaoEsqDir(NoAN no) {
        no.esq = rotacaoEsq(no.esq);
        return rotacaoDir(no);
    }

    // =========================
    // Pesquisa com caminho
    // =========================
    public boolean pesquisarComCaminho(String nome) {
        MyIO.print(nome + ": =>raiz ");
        return pesquisarComCaminho(nome, raiz);
    }

    private boolean pesquisarComCaminho(String nome, NoAN i) {
        if (i == null) {
            System.out.println("NAO");
            return false;
        }

        comparacoes++;
        if (nome.compareTo(i.elemento.getName()) == 0) {
            System.out.println("SIM");
            return true;
        }

        comparacoes++;
        if (nome.compareTo(i.elemento.getName()) < 0) {
            System.out.print("esq ");
            return pesquisarComCaminho(nome, i.esq);
        } else {
            System.out.print("dir ");
            return pesquisarComCaminho(nome, i.dir);
        }
    }
}

// ==========================
// ArvoreDeAvore
// ==========================

// Nó da PRIMEIRA árvore
class No1 {
    int chave;
    No1 esq, dir;
    No2 raizSecundaria;

    No1(int chave) {
        this.chave = chave;
        this.esq = this.dir = null;
        this.raizSecundaria = null;
    }
}

// Nó da SEGUNDA árvore
class No2 {
    String name;
    No2 esq, dir;

    No2(String name) {
        this.name = name;
        this.esq = this.dir = null;
    }
}

class ArvoreDeArvore {
    private No1 raiz;
    public int comparacoes = 0;

    // constrói a primeira árvore com ordem fixa
    public ArvoreDeArvore() {
        int[] ordem = { 7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14 };
        for (int k : ordem) {
            raiz = inserirPrimeira(k, raiz);
        }
    }

    private No1 inserirPrimeira(int chave, No1 i) {
        if (i == null) {
            return new No1(chave);
        } else if (chave < i.chave) {
            i.esq = inserirPrimeira(chave, i.esq);
        } else if (chave > i.chave) {
            i.dir = inserirPrimeira(chave, i.dir);
        }
        return i;
    }

    // localizar nó da primeira árvore pela chave
    private No1 buscarNo1(int chave, No1 i) {
        if (i == null)
            return null;
        if (chave == i.chave)
            return i;
        if (chave < i.chave)
            return buscarNo1(chave, i.esq);
        return buscarNo1(chave, i.dir);
    }

    // inserir um Game: calcula chave e insere o nome na árvore secundária
    // correspondente
    public void inserirGame(Game g) {
        int chave = Math.floorMod(g.getEstimatedOwners(), 15); // ensure non-negative
        No1 no = buscarNo1(chave, raiz);
        if (no != null) {
            no.raizSecundaria = inserirSegunda(g.getName(), no.raizSecundaria);
        } else {
            // normalmente não acontece pois a primeira árvore foi criada com 0..14
            System.err.println("Chave da primeira árvore não encontrada: " + chave);
        }
    }

    // inserção na segunda árvore
    private No2 inserirSegunda(String name, No2 i) {
        if (i == null)
            return new No2(name);
        if (name.compareTo(i.name) < 0)
            i.esq = inserirSegunda(name, i.esq);
        else if (name.compareTo(i.name) > 0)
            i.dir = inserirSegunda(name, i.dir);
        return i;
    }

    // pesquisa que imprime caminhos
    public boolean pesquisarComCaminho(String nome) {
        System.out.print("=> " + nome + " => raiz ");
        boolean found = pesquisarPrimeiraComCaminho(nome, raiz);
        if (!found)
            System.out.println("NAO");
        return found;
    }

    // percorre a primeira árvore
    private boolean pesquisarPrimeiraComCaminho(String nome, No1 i) {
        if (i == null)
            return false;

        // primeiro, checar a segunda árvore neste nó
        if (i.raizSecundaria != null) {
            // pesquisa na segunda árvore, ela imprimirá os passos que fizer
            if (pesquisarSegundaComPrint(nome, i.raizSecundaria)) {
                System.out.println(" SIM");
                return true;
            }
        }
        // não achou neste nó
        if (i.esq != null) {
            System.out.print("ESQ ");
            if (pesquisarPrimeiraComCaminho(nome, i.esq))
                return true;
        }
        if (i.dir != null) {
            System.out.print("DIR ");
            if (pesquisarPrimeiraComCaminho(nome, i.dir))
                return true;
        }

        return false;
    }

    // pesquisa na segunda árvore
    private boolean pesquisarSegundaComPrint(String nome, No2 i) {
        No2 atual = i;
        while (atual != null) {
            comparacoes++;
            if (nome.compareTo(atual.name) == 0) {
                return true;
            }

            comparacoes++;
            if (nome.compareTo(atual.name) < 0) {
                System.out.print("esq ");
                atual = atual.esq;
            } else {
                System.out.print("dir ");
                atual = atual.dir;
            }
        }
        return false;
    }
}

// ===========================
// HASH COM REHASH
// ===========================
class Hash {
    String tabela[];
    int m;
    final String NULO = null;
    public int comparacoes = 0;

    public Hash() {
        this(21);
    }

    public Hash(int m) {
        this.m = m;
        this.tabela = new String[this.m];
        for (int i = 0; i < m; i++) {
            tabela[i] = NULO;
        }
    }

    public int transformar(String nome) {
        int soma = 0;
        for (int i = 0; i < nome.length(); i++) {
            soma += nome.charAt(i);
        }
        return soma;
    }

    public int h(String elemento) {
        return transformar(elemento) % m;
    }

    public int reh(int pos) {
        return (pos + 1) % m;
    }

    public boolean inserir(String elemento) {
        boolean resp = false;
        if (elemento != NULO) {

            int pos = h(elemento);

            if (tabela[pos] == NULO) {
                tabela[pos] = elemento;
                resp = true;

            } else {
                pos = reh(pos);

                if (tabela[pos] == NULO) {
                    tabela[pos] = elemento;
                    resp = true;
                }
            }
        }
        return resp;
    }

    public boolean pesquisar(String elemento) {
        boolean resp = false;
        int pos = h(elemento);
        comparacoes++;
        if (tabela[pos] != null) {
            comparacoes++;
            if (tabela[pos].equals(elemento)) {
                resp = true;
            } else {
                pos = reh(pos);
                comparacoes++;
                if (tabela[pos] != null) {
                    comparacoes++;
                    if (tabela[pos].equals(elemento)) {
                        resp = true;
                    }
                }
            }
        }
        return resp;
    }
}

class Hash2 {
    String tabela[];
    int m1, m2, m, reserva;
    final String NULO = null;

    public int comparacoes = 0;

    public Hash2() {
        this(21, 9);
    }

    public Hash2(int m1, int m2) {
        this.m1 = m1;
        this.m2 = m2;
        this.m = m1 + m2;
        this.tabela = new String[m];
        for (int i = 0; i < m; i++)
            tabela[i] = NULO;
        reserva = 0;
    }

    public int h(String nome) {
        int soma = 0;
        for (int i = 0; i < nome.length(); i++)
            soma += nome.charAt(i);
        return soma % m1;
    }

    public boolean inserir(String nome) {
        int pos = h(nome);

        if (tabela[pos] == null) {
            tabela[pos] = nome;
            return true;
        }

        if (reserva < m2) {
            tabela[m1 + reserva] = nome;
            reserva++;
            return true;
        }

        return false;
    }

    public boolean pesquisar(String nome) {
        int pos = h(nome);

        // Comparação na tabela principal
        comparacoes++;
        if (tabela[pos] != null) {
            comparacoes++;
            if (tabela[pos].equals(nome)) {
                return true;
            }
        }

        // Procurar na reserva
        for (int i = 0; i < reserva; i++) {
            int posReserva = m1 + i;

            comparacoes++;
            if (tabela[posReserva] != null) {
                comparacoes++;
                if (tabela[posReserva].equals(nome)) {
                    return true;
                }
            }
        }

        return false;
    }

}

// =================
// Principal
// =================
public class steamJ {
    public static Game[] fullDB;

    public static int findID(String input) {
        int id = Integer.parseInt(input);
        for (int i = 0; i < fullDB.length; i++)
            if (fullDB[i].getId() == id)
                return i;
        return -1;
    }

    public static void main(String[] args) throws Exception {

        long inicio = System.currentTimeMillis();
        Game[] gameDb = Game.readDb();
        Scanner sc = new Scanner(System.in);
        String entrada;

        Hash2 hash = new Hash2();

        // INSERIR

        while (!(entrada = sc.nextLine()).equals("FIM")) {
            int idBuscado = Integer.parseInt(entrada);

            // buscar ID no DB
            for (Game g : gameDb) {
                if (g.getId() == idBuscado) {
                    hash.inserir(g.getName());
                    break;
                }
            }
        }

        // PESQUISAR

        String nome;
        while (!(nome = sc.nextLine()).equals("FIM")) {
            if (nome.length() == 0)
                continue;

            int pos = hash.h(nome);  

            System.out.print(nome + ":  (Posicao: " + pos + ") ");

            boolean resp = hash.pesquisar(nome);
            System.out.println(resp ? "SIM" : "NAO");
        }

        long fim = System.currentTimeMillis();
        long tempo = fim - inicio;

        // SALVAR LOG

        FileWriter fw = new FileWriter("888302_hashReserva.txt");
        fw.write("888302\t" + tempo + "\t" + hash.comparacoes);
        fw.close();

        sc.close();
    }
}