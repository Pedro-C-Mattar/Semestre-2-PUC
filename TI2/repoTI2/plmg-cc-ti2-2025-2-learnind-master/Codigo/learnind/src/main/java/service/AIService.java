package service;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.*;
import com.azure.core.credential.AzureKeyCredential;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AIService {

    // ==================================================================================
    // CONFIGURAÇÕES - ATENÇÃO: O Token do VI expira em 1 hora!
    // ==================================================================================
    private static final String VI_ACCESS_TOKEN = "Token VIDEO INDEXER"; 
    private static final String OPENAI_KEY = "Chave FIXA"; 
    
    // Configurações Fixas
    private static final String VI_ACCOUNT_ID = "7b12729d-f138-41c7-8189-603c1d857cf8";
    private static final String VI_LOCATION = "eastus2";
    
    private static final String OPENAI_ENDPOINT = "https://learnind-resource.openai.azure.com/";
    private static final String OPENAI_DEPLOYMENT = "gerador-perguntas";
    // ==================================================================================

    public String gerarPerguntas(String videoUrl) throws Exception {
        
        // Validação básica para evitar dor de cabeça
        if (VI_ACCESS_TOKEN.contains("COLE_SEU_TOKEN") || VI_ACCESS_TOKEN.length() < 20) {
            return "ERRO DE CONFIGURAÇÃO: Atualize o VI_ACCESS_TOKEN no arquivo AIService.java";
        }

        System.out.println(">>> [IA] 1. Iniciando processo para o vídeo: " + videoUrl);
        String nomeVideo = "Aula_" + System.currentTimeMillis();
        
        try {
            // 1. Upload do Vídeo
            String videoId = uploadVideo(videoUrl, nomeVideo);
            System.out.println(">>> [IA] Upload realizado. ID do Indexer: " + videoId);
            
            // 2. Aguardar Processamento (Loop)
            System.out.println(">>> [IA] 2. Aguardando a IA assistir o vídeo (Isso pode demorar)...");
            esperarProcessamento(videoId);
            
            // 3. Baixar Transcrição (Legenda)
            System.out.println(">>> [IA] 3. Vídeo processado! Baixando o texto...");
            String transcricao = pegarTranscricao(videoId);
            
            if (transcricao.trim().isEmpty()) {
                return "A IA assistiu ao vídeo, mas não conseguiu entender nenhuma fala (áudio mudo ou ruído).";
            }

            // 4. Enviar para o GPT
            System.out.println(">>> [IA] 4. Enviando texto para o GPT criar as questões...");
            String resultado = chamarGPT(transcricao);
            System.out.println(">>> [IA] Processo concluído com sucesso!");
            
            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro no processo de IA: " + e.getMessage();
        }
    }

    // --- MÉTODOS AUXILIARES ---

    private String uploadVideo(String url, String name) throws Exception {
        // 1. Codificação da URL (Mantém o que já estava funcionando para gerar a URL correta)
        String videoUrlEncoded = java.net.URLEncoder.encode(url, java.nio.charset.StandardCharsets.UTF_8);
        String nameEncoded = java.net.URLEncoder.encode(name, java.nio.charset.StandardCharsets.UTF_8);

        String apiUrl = String.format(
            "https://api.videoindexer.ai/%s/Accounts/%s/Videos?name=%s&videoUrl=%s&accessToken=%s&privacy=Private",
            VI_LOCATION, VI_ACCOUNT_ID, nameEncoded, videoUrlEncoded, VI_ACCESS_TOKEN
        );

        // 2. AQUI ESTÁ A CORREÇÃO DO ERRO 411:
        // Forçamos o cliente a usar HTTP 1.1. O Azure Video Indexer as vezes rejeita HTTP/2 em uploads via URL.
        HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1) 
            .build();
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl))
            // Removemos o header "Content-Type" que pode estar confundindo o servidor
            .POST(HttpRequest.BodyPublishers.ofString("")) // Envia corpo vazio simples
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String json = response.body();
            if (json.contains("\"id\":")) {
                return json.split("\"id\":\"")[1].split("\"")[0];
            }
            return json.replace("\"", "").trim();
        }
        
        // Logs para Debug no Terminal
        System.err.println("ERRO NO UPLOAD. Status: " + response.statusCode());
        System.err.println("Resposta Azure: " + response.body());
        
        throw new RuntimeException("Falha no Upload (" + response.statusCode() + "): " + response.body());
    }

    private void esperarProcessamento(String videoId) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String urlStatus = String.format("https://api.videoindexer.ai/%s/Accounts/%s/Videos/%s/Index?accessToken=%s",
                VI_LOCATION, VI_ACCOUNT_ID, videoId, VI_ACCESS_TOKEN);

        System.out.println(">>> Aguardando processamento do ID: " + videoId);

        int tentativas = 0;
        while (tentativas < 40) { 
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urlStatus)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            // SUCESSO
            if (json.contains("\"state\":\"Processed\"")) {
                System.out.println("\n>>> Processamento Concluído!");
                return; 
            }
            
            // FALHA (AGORA VAI MOSTRAR O MOTIVO NA TELA DE ERRO)
            if (json.contains("\"state\":\"Failed\"")) {
                // Aqui está o segredo: Jogamos o JSON inteiro dentro do erro
                throw new RuntimeException("O AZURE REPROVOU. MOTIVO: " + json);
            }

            System.out.print("."); 
            TimeUnit.SECONDS.sleep(10); 
            tentativas++;
        }
        throw new RuntimeException("Tempo limite excedido.");
    }
    private String pegarTranscricao(String videoId) throws Exception {
        String urlCaption = String.format("https://api.videoindexer.ai/%s/Accounts/%s/Videos/%s/Captions?format=Txt&accessToken=%s",
                VI_LOCATION, VI_ACCOUNT_ID, videoId, VI_ACCESS_TOKEN);
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urlCaption)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            // Se der 404, pode ser que o vídeo não tenha áudio detectável
            if(response.statusCode() == 404) return "";
            throw new RuntimeException("Erro ao baixar legendas: " + response.body());
        }
        return response.body();
    }

    private String chamarGPT(String texto) {
        // Limita o texto para não estourar o limite de tokens do GPT (aprox 15 mil caracteres é seguro)
        if (texto.length() > 15000) {
            texto = texto.substring(0, 15000) + "... [texto cortado]";
        }

        OpenAIClient client = new OpenAIClientBuilder()
            .endpoint(OPENAI_ENDPOINT)
            .credential(new AzureKeyCredential(OPENAI_KEY))
            .buildClient();

        List<ChatRequestMessage> chatMessages = new ArrayList<>();
        
        // Prompt do Sistema (Cérebro do Professor)
        chatMessages.add(new ChatRequestSystemMessage(
            "Você é um professor universitário. " +
            "Sua tarefa: Ler a transcrição da aula abaixo e criar 3 perguntas de múltipla escolha. " +
            "Formato de saída: Apenas as perguntas, alternativas e a resposta correta indicada. " +
            "Não use formatação Markdown complexa, apenas texto limpo."
        ));
        
        // Prompt do Usuário (O Conteúdo)
        chatMessages.add(new ChatRequestUserMessage("Transcrição da Aula:\n" + texto));

        ChatCompletions chatCompletions = client.getChatCompletions(OPENAI_DEPLOYMENT, new ChatCompletionsOptions(chatMessages));
        
        return chatCompletions.getChoices().get(0).getMessage().getContent();
    }
}