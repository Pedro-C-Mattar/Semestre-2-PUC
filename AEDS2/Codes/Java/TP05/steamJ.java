
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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getEstimatedOwners() {
        return estimatedOwners;
    }

    public void setEstimatedOwners(int estimatedOwners) {
        this.estimatedOwners = estimatedOwners;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ArrayList<String> getSupportedLanguages() {
        return supportedLanguages;
    }

    public void setSupportedLanguages(ArrayList<String> supportedLanguages) {
        this.supportedLanguages = supportedLanguages;
    }

    public int getMetacriticScore() {
        return metacriticScore;
    }

    public void setMetacriticScore(int metacriticScore) {
        this.metacriticScore = metacriticScore;
    }

    public float getUserScore() {
        return userScore;
    }

    public void setUserScore(float userScore) {
        this.userScore = userScore;
    }

    public int getAchievements() {
        return achievements;
    }

    public void setAchievements(int achievements) {
        this.achievements = achievements;
    }

    public ArrayList<String> getPublishers() {
        return publishers;
    }

    public void setPublishers(ArrayList<String> publishers) {
        this.publishers = publishers;
    }

    public ArrayList<String> getDevelopers() {
        return developers;
    }

    public void setDevelopers(ArrayList<String> developers) {
        this.developers = developers;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

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
                new ArrayList<>(tags));
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
        ArrayList<Game> temp = new ArrayList<>();
        Scanner reader = null;

        try {
            // Usa o arquivo games.csv na pasta corrente
            reader = new Scanner(new FileReader("/tmp/games.csv"));
            if (reader.hasNextLine())
                reader.nextLine(); // pula o cabeçalho

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                Game game = new Game();
                try {
                    game.ler(line); // processa linha
                    temp.add(game);
                } catch (Exception e) {
                    System.err.println("Erro ao processar linha: " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        } finally {
            if (reader != null)
                reader.close(); // fecha o arquivo
        }

        // Converte para vetor
        Game games[] = new Game[temp.size()];
        for (int i = 0; i < temp.size(); i++)
            games[i] = temp.get(i);
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

    // Contadores para MergeSort
    public static long comparisons = 0; // comparações entre elementos
    public static long movements = 0; // movimentações entre elementos

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

        // Lê IDs até encontrar "FIM" e armazena os jogos em uma lista temporária
        ArrayList<Game> tempList = new ArrayList<>();
        for (String input = scanf.nextLine(); !input.equals("FIM"); input = scanf.nextLine()) {
            int pos = findID(input);
            // Armazena uma cópia (clone) do jogo encontrado
            tempList.add(fullDB[pos].clone());
        }
        scanf.close();

        // Cria vetor com os jogos selecionados
        Game selected[] = new Game[tempList.size()];
        for (int i = 0; i < tempList.size(); i++)
            selected[i] = tempList.get(i);

        comparisons = 0;
        movements = 0;
        long startTime = System.currentTimeMillis();
        if (selected.length > 0) {
            heapSort(selected);
            // mergeSort(selected);
        }
        long endTime = System.currentTimeMillis();
        long elapsed = endTime - startTime; // em ms
        // Exibe os 5 mais caros e os 5 mais (chamada antiga comentada)
        // printExtremes(selected);
        // Exibe todos os registros ordenados por Estimated_owners
        printAllByOwners(selected);

        // Gera arquivo de log na pasta corrente
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("888302_heapsort.txt"))) {
            String matricula = "888302";
            bw.write(matricula + "\t" + comparisons + "\t" + movements + "\t" + elapsed);
        } catch (IOException e) {
            System.err.println("Erro ao escrever o arquivo de log: " + e.getMessage());
        }

        /*
         * try (BufferedWriter bw = new BufferedWriter(new FileWriter("888302_mergesort.txt"))) {
         *    String matricula = "888302";
         *    bw.write(matricula + "\t" + comparisons + "\t" + movements + "\t" + elapsed);
         * } catch (IOException e) {
         *    System.err.println("Erro ao escrever o arquivo de log: " + e.getMessage());
         * }
         */
    }

    // Imprime os 5 jogos mais caros e os 5 mais baratos (formato solicitado)
    private static void printExtremes(Game[] selected) {
        // Top 5 mais caros
        MyIO.println("| 5 preços mais caros |");
        int top = Math.min(5, selected.length);
        for (int i = selected.length - 1; i >= selected.length - top; i--) {
            if (i < 0)
                break;
            selected[i].mostrar();
            System.out.println();
        }

        // Bottom 5 mais baratos
        MyIO.println("| 5 preços mais baratos |");
        int bottom = Math.min(5, selected.length);
        for (int i = 0; i < bottom; i++) {
            selected[i].mostrar();
            System.out.println();
        }
    }

    // compara dois jogos conforme regra
    private static int compareGames(Game a, Game b) {
        // Compara price
        comparisons++;
        if (a.getPrice() < b.getPrice())
            return -1;
        if (a.getPrice() > b.getPrice())
            return 1;
        // Preço igual - desempata por id
        comparisons++;
        if (a.getId() < b.getId())
            return -1;
        if (a.getId() > b.getId())
            return 1;
        return 0;
    }

    // Implementação de MergeSort usando vetores
    public static void mergeSort(Game[] arr) {
        Game[] aux = new Game[arr.length];
        mergeSortRec(arr, aux, 0, arr.length - 1);
    }

    private static void mergeSortRec(Game[] arr, Game[] aux, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortRec(arr, aux, left, mid);
            mergeSortRec(arr, aux, mid + 1, right);
            merge(arr, aux, left, mid, right);
        }
    }

    private static void merge(Game[] arr, Game[] aux, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            int cmp = compareGames(arr[i], arr[j]);
            if (cmp <= 0) {
                aux[k++] = arr[i++];
                movements++;
            } else {
                aux[k++] = arr[j++];
                movements++;
            }
        }

        while (i <= mid) {
            aux[k++] = arr[i++];
            movements++;
        }

        while (j <= right) {
            aux[k++] = arr[j++];
            movements++;
        }

        // Copia de volta para arr
        for (k = left; k <= right; k++) {
            arr[k] = aux[k];
            movements++;
        }
    }

    // Heapsort ordena por estimatedOwners. Empate, desempata por id.
    public static void heapSort(Game[] arr) {
        int n = arr.length;
        // Constroi heap máximo
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Extrai elementos do heap um a um
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i); // move maior para o fim
            heapify(arr, i, 0); // heapifica o restante
        }
    }

    private static void heapify(Game[] arr, int heapSize, int root) {
        int largest = root;
        int left = 2 * root + 1;
        int right = 2 * root + 2;

        if (left < heapSize) {
            if (compareByOwners(arr[left], arr[largest]) > 0) {
                largest = left;
            }
        }

        if (right < heapSize) {
            if (compareByOwners(arr[right], arr[largest]) > 0) {
                largest = right;
            }
        }

        if (largest != root) {
            swap(arr, root, largest);
            heapify(arr, heapSize, largest);
        }
    }

    private static void swap(Game[] arr, int i, int j) {
        Game tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        movements += 3; // conta 3 atribuições como 3 movimentações
    }

    // Compara por estimatedOwners asc; desempata por id asc
    private static int compareByOwners(Game a, Game b) {
        comparisons++;
        if (a.getEstimatedOwners() < b.getEstimatedOwners())
            return -1;
        if (a.getEstimatedOwners() > b.getEstimatedOwners())
            return 1;
        // empate em estimatedOwners: desempata por id
        comparisons++;
        if (a.getId() < b.getId())
            return -1;
        if (a.getId() > b.getId())
            return 1;
        return 0;
    }

    // Imprime TODOS os registros (full data) na ordem atual do vetor (ordenado por
    // estimatedOwners)
    private static void printAllByOwners(Game[] selected) {
        for (int i = 0; i < selected.length; i++) {
            selected[i].mostrar();
        }
    }
}
