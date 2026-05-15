package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class videoIndexer {

    // --- CONFIGURAÇÕES DO VIDEO INDEXER (SEUS DADOS) ---
    private static final String VI_ACCOUNT_ID = "7b12729d-f138-41c7-8189-603c1d857cf8"; 
    private static final String VI_LOCATION = "eastus2"; 
    // ---------------------------------------------------

    public static void main(String[] args) {
        System.out.println("--- Integrando Banco (Tabela 'video') com Azure ---");

        try {
            // 1. BUSCAR A URL NA TABELA 'video'
            System.out.println("[1/3] Buscando URL no banco de dados...");
            String videoUrl = buscarUrlNoBanco(); 

            if (videoUrl == null || videoUrl.isEmpty()) {
                System.err.println("XXX ERRO: Nenhuma URL encontrada na tabela 'video'.");
                return; 
            }
            
            System.out.println(">>> Vídeo Encontrado: " + videoUrl);
            String nomeVideo = "Video_BD_" + System.currentTimeMillis(); 

            // 2. TOKEN DE ACESSO (MANUAL)
            // Gere um novo no terminal com o comando 'az video-indexer access-token...' e cole abaixo:
            String accessToken = "COLE_O_TOKEN_GIGANTE_AQUI"; 
            
            if (accessToken.contains("COLE_O_TOKEN")) {
                 System.err.println("XXX ERRO: Você precisa gerar o token no terminal e colar no código.");
                 return;
            }

            // 3. FAZER UPLOAD
            System.out.println("[3/3] Enviando para o Azure Video Indexer...");
            uploadVideo(accessToken, videoUrl, nomeVideo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- MÉTODO ATUALIZADO COM SUA TABELA ---
    private static String buscarUrlNoBanco() {
        ConnectionFactory factory = new ConnectionFactory();
        
        // SEU SQL AQUI: Tabela 'video', Coluna 'url'
        // Pegamos o último inserido (assumindo que existe uma coluna 'id' para ordenar)
        String sql = "SELECT url FROM video ORDER BY id DESC LIMIT 1"; 
        
        try (Connection con = factory.recuperaConexao();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getString("url"); // <--- Pega da coluna 'url'
            }
        } catch (Exception e) {
            System.err.println("Erro SQL: " + e.getMessage());
        }
        return null; 
    }

    private static void uploadVideo(String token, String videoUrl, String videoName) throws Exception {
        // Monta a URL de upload (note que removemos espaços da URL só por segurança)
        String apiUrl = String.format(
            "https://api.videoindexer.ai/%s/Accounts/%s/Videos?name=%s&videoUrl=%s&accessToken=%s",
            VI_LOCATION, VI_ACCOUNT_ID, videoName, videoUrl.replace(" ", "%20"), token
        );

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl))
            .POST(HttpRequest.BodyPublishers.noBody()) 
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println("\n>>> SUCESSO! O vídeo foi enviado.");
            System.out.println("ID do Vídeo: " + response.body());
        } else {
            System.err.println("Falha no upload (" + response.statusCode() + "): " + response.body());
        }
    }
}