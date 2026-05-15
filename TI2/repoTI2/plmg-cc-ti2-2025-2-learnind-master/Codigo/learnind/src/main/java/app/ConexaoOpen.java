package app; 

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatRequestMessage; 
import com.azure.ai.openai.models.ChatRequestSystemMessage; 
import com.azure.ai.openai.models.ChatRequestUserMessage; 
import com.azure.core.credential.AzureKeyCredential;

import java.util.ArrayList;
import java.util.List;

public class ConexaoOpen {

    public static void main(String[] args) {
        
        // --- CONFIGURAÇÃO ---
        // CHAVE da API (Substitua pela sua chave real)
        String azureOpenAiKey = "COLE-SUA-CHAVE-AQUI"; // Essa é a chave ANTIGA, já foi regenerada.
        
        // PONTO DE EXTREMIDADE (Endpoint)
        String azureOpenAiEndpoint = "https://learnind-resource.services.ai.azure.com/"; // Verifique se seu endpoint tem /api... no final ou não. Geralmente é só o domínio base.
        
        // NOME DA IMPLANTAÇÃO (Deployment Name)
        String deploymentName = "gerador-perguntas"; 
        // --------------------

        // Validar se preencheu os dados
        if (azureOpenAiKey.equals("COLE_SUA_CHAVE_AQUI")) {
            System.err.println("ERRO: Você esqueceu de colocar a chave da API no código.");
            return; 
        }

        System.out.println("--- Iniciando Teste de Conexão com Azure OpenAI ---");

        try {
            // 1. Cria o cliente de conexão
            OpenAIClient client = new OpenAIClientBuilder()
                .endpoint(azureOpenAiEndpoint)
                .credential(new AzureKeyCredential(azureOpenAiKey))
                .buildClient();

            // 2. Prepara a mensagem (Código Atualizado para versão beta.12+)
            List<ChatRequestMessage> chatMessages = new ArrayList<>();
            chatMessages.add(new ChatRequestSystemMessage("Você é um mentor para o Ensino Superior"));
            chatMessages.add(new ChatRequestUserMessage("Crie perguntas acadêmicas sobre o meu tema"));

            // 3. Envia a mensagem
            System.out.println("\n[Enviando prompt para a IA... aguarde...]");
            ChatCompletions chatCompletions = client.getChatCompletions(deploymentName, new ChatCompletionsOptions(chatMessages));

            // 4. Exibe a resposta
            String resposta = chatCompletions.getChoices().get(0).getMessage().getContent();
            
            System.out.println("\n-------------------------------------------");
            System.out.println("RESPOSTA DA IA: " + resposta);
            System.out.println("-------------------------------------------");
            System.out.println("\n>>> SUCESSO! A Parte 1 (Cérebro) está conectada. <<<");

        } catch (Exception e) {
            System.err.println("\nXXX FALHA NA CONEXÃO XXX");
            e.printStackTrace();
        }
    }
}