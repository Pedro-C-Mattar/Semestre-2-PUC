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

    public int getId() { return id; }
    public String getName() { return name; }
    public String getReleaseDate() { return releaseDate; }
    public int getEstimatedOwners() { return estimatedOwners; }
    public float getPrice() { return price; }
    public ArrayList<String> getSupportedLanguages() { return supportedLanguages; }
    public int getMetacriticScore() { return metacriticScore; }
    public float getUserScore() { return userScore; }
    public int getAchievements() { return achievements; }
    public ArrayList<String> getPublishers() { return publishers; }
    public ArrayList<String> getDevelopers() { return developers; }
    public ArrayList<String> getCategories() { return categories; }
    public ArrayList<String> getGenres() { return genres; }
    public ArrayList<String> getTags() { return tags; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
    public void setEstimatedOwners(int estimatedOwners) { this.estimatedOwners = estimatedOwners; }
    public void setPrice(float price) { this.price = price; }
    public void setSupportedLanguages(ArrayList<String> supportedLanguages) { this.supportedLanguages = supportedLanguages; }
    public void setMetacriticScore(int metacriticScore) { this.metacriticScore = metacriticScore; }
    public void setUserScore(float userScore) { this.userScore = userScore; }
    public void setAchievements(int achievements) { this.achievements = achievements; }
    public void setPublishers(ArrayList<String> publishers) { this.publishers = publishers; }
    public void setDevelopers(ArrayList<String> developers) { this.developers = developers; }
    public void setCategories(ArrayList<String> categories) { this.categories = categories; }
    public void setGenres(ArrayList<String> genres) { this.genres = genres; }
    public void setTags(ArrayList<String> tags) { this.tags = tags; }

    @Override
    public Game clone() {
        return new Game(
            id, name, releaseDate, estimatedOwners, price,
            new ArrayList<>(supportedLanguages), metacriticScore, userScore,
            achievements, new ArrayList<>(publishers),
            new ArrayList<>(developers), new ArrayList<>(categories),
            new ArrayList<>(genres), new ArrayList<>(tags)
        );
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
        } catch (Exception e) {}
        return output;
    }

    private ArrayList<String> formatArrays(String input) {
        ArrayList<String> array = new ArrayList<>();
        if (input != null) {
            String splitted[] = input.split(",");
            for (String s : splitted) {
                s = s.trim().replace("\"", "").replace("[", "").replace("]", "");
                if (s.startsWith("'")) s = s.substring(1);
                if (s.endsWith("'")) s = s.substring(0, s.length() - 1);
                if (!s.isEmpty()) array.add(s);
            }
        }
        return array;
    }

    public void ler(String line) {
        String newLine = "";
        boolean insideQuotes = false;
        int tam = line.length();

        for (int i = 0; i < tam; i++) {
            char currentChar = line.charAt(i);
            if (currentChar == '"') insideQuotes = !insideQuotes;
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
        } catch (Exception e) {}
    }

    public static Game[] readDb() {
        ArrayList<Game> temp = new ArrayList<>();
        try (Scanner reader = new Scanner(new FileReader("/tmp/games.csv"))) {
            if (reader.hasNextLine()) reader.nextLine();
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
        if (primeiro == ultimo) throw new Exception("Erro: fila vazia");
        Celula tmp = primeiro.prox;
        primeiro.prox = tmp.prox;
        if (primeiro.prox == null) ultimo = primeiro;
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

// ======================================
// Classe principal
// ======================================
public class steamJ {
    public static Game[] fullDB;

    public static int findID(String input) {
        int id = Integer.parseInt(input);
        for (int i = 0; i < fullDB.length; i++)
            if (fullDB[i].getId() == id) return i;
        return -1;
    }

    public static void main(String[] args) {
    fullDB = Game.readDb();
    Scanner sc = new Scanner(System.in);

    FilaFlex fila = new FilaFlex();

    // ======= Leitura inicial dos IDs =======
    String line = sc.nextLine();
    while (!line.equals("FIM")) {
        int pos = findID(line);
        if (pos != -1) {
            fila.inserir(fullDB[pos].clone());
        }
        line = sc.nextLine();
    }

    // ======= Leitura do número de comandos =======
    int n = Integer.parseInt(sc.nextLine());

    // ======= Processa comandos =======
    for (int i = 0; i < n; i++) {
        String cmd = sc.next();
        if (cmd.equals("I")) {
            String idStr = sc.next();
            int pos = findID(idStr);
            if (pos != -1) {
                fila.inserir(fullDB[pos].clone());
            }
            // garante consumir a linha restante se houver
            // (não necessário aqui porque usamos next() para tokens)
        } else if (cmd.equals("R")) {
            try {
                Game removido = fila.remover();
                System.out.println("(R) " + removido.getName());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    // ======= Imprime fila final =======
    fila.mostrar();
    sc.close();
}

}
