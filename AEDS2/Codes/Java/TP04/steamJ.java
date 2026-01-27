// Importações necessárias
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;

// Classe que representa um jogo da Steam
class Game {
    // ========================
    // Atributos principais
    // ========================
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

    // ========================
    // Construtor padrão
    // ========================
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

    // ========================
    // Construtor completo
    // ========================
    public Game(int id, String name, String releaseDate, int estimatedOwners, float price,
            ArrayList<String> supportedLanguages, int metacriticScore, float userScore, int achievements,
            ArrayList<String> publishers, ArrayList<String> developers, ArrayList<String> categories,
            ArrayList<String> genres, ArrayList<String> tags) {
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

    // ========================
    // Getters e Setters
    // ========================
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
    public int getEstimatedOwners() { return estimatedOwners; }
    public void setEstimatedOwners(int estimatedOwners) { this.estimatedOwners = estimatedOwners; }
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    public ArrayList<String> getSupportedLanguages() { return supportedLanguages; }
    public void setSupportedLanguages(ArrayList<String> supportedLanguages) { this.supportedLanguages = supportedLanguages; }
    public int getMetacriticScore() { return metacriticScore; }
    public void setMetacriticScore(int metacriticScore) { this.metacriticScore = metacriticScore; }
    public float getUserScore() { return userScore; }
    public void setUserScore(float userScore) { this.userScore = userScore; }
    public int getAchievements() { return achievements; }
    public void setAchievements(int achievements) { this.achievements = achievements; }
    public ArrayList<String> getPublishers() { return publishers; }
    public void setPublishers(ArrayList<String> publishers) { this.publishers = publishers; }
    public ArrayList<String> getDevelopers() { return developers; }
    public void setDevelopers(ArrayList<String> developers) { this.developers = developers; }
    public ArrayList<String> getCategories() { return categories; }
    public void setCategories(ArrayList<String> categories) { this.categories = categories; }
    public ArrayList<String> getGenres() { return genres; }
    public void setGenres(ArrayList<String> genres) { this.genres = genres; }
    public ArrayList<String> getTags() { return tags; }
    public void setTags(ArrayList<String> tags) { this.tags = tags; }

    // ========================
    // Clonagem profunda (deep copy)
    // ========================
    @Override
    public Game clone() {
        return new Game(
            id,
            name,
            releaseDate,
            estimatedOwners,
            price,
            new ArrayList<>(supportedLanguages), // cria cópias das listas
            metacriticScore,
            userScore,
            achievements,
            new ArrayList<>(publishers),
            new ArrayList<>(developers),
            new ArrayList<>(categories),
            new ArrayList<>(genres),
            new ArrayList<>(tags)
        );
    }

    // ========================
    // Formata datas de vários formatos para dd/MM/yyyy
    // ========================
    private static String formatReleaseDate(String input) {
        String output = "";
        try {
            // Caso: "Jan 5, 2015"
            if (input.matches("[A-Za-z]{3} \\d{1,2}, \\d{4}")) {
                DateTimeFormatter inFmt = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
                LocalDate date = LocalDate.parse(input, inFmt);
                output = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
            // Caso: "Jan 2015"
            if (input.matches("[A-Za-z]{3} \\d{4}")) {
                DateTimeFormatter inFmt = DateTimeFormatter.ofPattern("MMM yyyy", Locale.ENGLISH);
                YearMonth ym = YearMonth.parse(input, inFmt);
                output = ym.atDay(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
            // Caso: "2015"
            if (input.matches("\\d{4}")) {
                int year = Integer.parseInt(input);
                LocalDate date = LocalDate.of(year, 1, 1);
                output = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
        } catch (Exception e) {
            System.err.println("Erro na entrada da data");
        }
        return output;
    }

    // ========================
    // Converte string de lista (ex: "['Action','Adventure']") em ArrayList
    // ========================
    private ArrayList<String> formatArrays(String input) {
        ArrayList<String> array = new ArrayList<>();
        if (input != null) {
            String splitted[] = input.split(",");
            for (int i = 0; i < splitted.length; i++) {
                String s = splitted[i].trim();
                // Remove aspas e colchetes
                s = s.replace("\"", "").replace("[", "").replace("]", "");
                if (s.startsWith("'")) s = s.substring(1);
                if (s.endsWith("'")) s = s.substring(0, s.length() - 1);
                if (!s.isEmpty()) array.add(s);
            }
        }
        return array;
    }

    // ========================
    // Lê e interpreta uma linha do CSV
    // ========================
    public void ler(String line) {
        String newLine = "";
        boolean insideQuotes = false;
        int tam = line.length();

        // Substitui vírgulas fora de aspas por ponto e vírgula (;)
        for (int i = 0; i < tam; i++) {
            char currentChar = line.charAt(i);
            if (currentChar == '"') {
                insideQuotes = !insideQuotes; // alterna estado dentro/fora de aspas
            }
            if (!insideQuotes) {
                if (currentChar == ',') {
                    newLine += ';'; // troca vírgula por ponto e vírgula
                } else if (currentChar != '"') {
                    newLine += currentChar;
                }
            } else {
                // Dentro das aspas, apenas ignora colchetes e aspas
                if (currentChar != '"' && currentChar != '[' && currentChar != ']') {
                    newLine += currentChar;
                }
            }
        }

        line = newLine;
        String splitted[] = line.split(";"); // separa os campos

        try {
            // Preenche os atributos do objeto
            setId(Integer.parseInt(splitted[0]));
            setName(splitted[1]);
            setReleaseDate(formatReleaseDate(splitted[2]));
            setEstimatedOwners(Integer.parseInt(splitted[3]));
            setPrice(Float.parseFloat(splitted[4]));
            setSupportedLanguages(formatArrays(splitted[5]));
            setMetacriticScore(Integer.parseInt(splitted[6]));
            setUserScore(Float.parseFloat(splitted[7]));
            setAchievements(Integer.parseInt(splitted[8]));
            // Campos opcionais (podem estar vazios)
            setPublishers(splitted[9].trim().isEmpty() ? new ArrayList<>() : formatArrays(splitted[9]));
            setDevelopers(splitted[10].trim().isEmpty() ? new ArrayList<>() : formatArrays(splitted[10]));
            setCategories(splitted[11].trim().isEmpty() ? new ArrayList<>() : formatArrays(splitted[11]));
            setGenres(splitted[12].trim().isEmpty() ? new ArrayList<>() : formatArrays(splitted[12]));
            setTags(splitted[13].trim().isEmpty() ? new ArrayList<>() : formatArrays(splitted[13]));
        } catch (Exception e) {
            // Ignora exceções — útil para CSVs incompletos
        }
    }

    // ========================
    // Lê o banco de dados completo do arquivo CSV
    // ========================
    public static Game[] readDb() {
        Game games[] = new Game[1850]; // tamanho fixo
        Scanner reader = null;

        try {
            reader = new Scanner(new FileReader("/tmp/games.csv"));
            reader.nextLine(); // pula o cabeçalho

            for (int i = 0; reader.hasNextLine(); i++) {
                String line = reader.nextLine();
                Game game = new Game();
                try {
                    game.ler(line); // processa linha
                    games[i] = game;
                } catch (Exception e) {
                    System.err.println("Erro ao processar linha " + (i + 1) + ": " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        } finally {
            if (reader != null) reader.close(); // fecha o arquivo
        }

        return games;
    }

    // ========================
    // Representação textual do jogo
    // ========================
    @Override
    public String toString() {
        return id + " ## " +
            name + " ## " +
            releaseDate + " ## " +
            estimatedOwners + " ## " +
            price + " ## " +
            supportedLanguages + " ## " +
            metacriticScore + " ## " +
            userScore + " ## " +
            achievements + " ## " +
            publishers + " ## " +
            developers + " ## " +
            categories + " ## " +
            genres + " ## " +
            tags + " ##";
    }

    // ========================
    // Imprime as informações formatadas
    // ========================
    public void mostrar() {
        System.out.println("=> " + this.toString());
    }
}

// ========================
// Classe principal do programa
// ========================
public class steamJ {
    // Banco de dados completo de jogos
    public static Game fullDB[] = new Game[1850];

    // Procura o índice do jogo pelo ID
    public static int findID(String input) {
        int id = Integer.parseInt(input);
        int pos = 0;
        // Busca linear até encontrar o ID correspondente
        while ((fullDB[pos].getId() != id)) {
            pos++;
        }
        return pos;
    }

    // Método principal
    public static void main(String[] args) {
        // Lê o banco de dados do CSV
        fullDB = Game.readDb();

        Scanner scanf = new Scanner(System.in);

        // Lê IDs até encontrar "FIM"
        for (String input = scanf.nextLine(); !input.equals("FIM"); input = scanf.nextLine()) {
            // Busca o jogo pelo ID e mostra informações
            fullDB[findID(input)].mostrar();
        }

        scanf.close();
    }
}
