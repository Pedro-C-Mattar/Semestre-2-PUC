# SAP-1 Turbo
Este projeto visa a melhoria e expansão funcional da arquitetura do processador SAP-1, no contexto da disciplina de Arquitetura de Computadores 1.

O trabalho é uma evolução de um projeto anterior desenvolvido pelos alunos:
- André Santos Alves
- Nathan de Araújo Cunha Lisboa
- Pedro Malta Boscatti
- Sophia Damasceno Satuf

O projeto foi disponibilizado pelo professor Cláudio Dias Campos.

> [!WARNING]
> Compatibilidade: Este projeto é compatível exclusivamente com o simulador [Logisim](http://www.cburch.com/logisim/). Não é garantido que funcione no [Logisim Evolution](https://github.com/logisim-evolution/logisim-evolution).

## Melhoria Proposta
A arquitetura original do SAP-1 utiliza uma matriz de controle no controlador-sequenciador para decodificar as instruções, gerando as microinstruções a cada ciclo de clock por meio de portas lógicas. O objetivo deste projeto é substituir essa abordagem por microprogramação, simplificando e aprimorando o design do controlador sequenciador.

### O que é Microprogramação?
A microprogramação consiste em armazenar as microinstruções em uma ROM (Read-Only Memory), ao invés de gerá-las dinamicamente a partir de uma matriz de portas lógicas. Com isso, o controlador se torna mais modular e flexível, permitindo maior facilidade no design e expansão futura.

## Vídeo Demonstrativo
<p align="center">
	<a href="https://www.youtube.com/watch?v=bnCJoAbZbGE">
		<img src="https://img.youtube.com/vi/bnCJoAbZbGE/maxresdefault.jpg" width="500" alt="SAP-1 Turbo - AC1">
	</a>
</p>


## Como Executar
O sistema utiliza duas ROMs no contador-sequenciador: uma para endereços e outra para o controle. Caso as ROMs não contenham o conteúdo correto, você pode carregá-las a partir de arquivos de texto.

Arquivos de ROM
Os arquivos necessários para as ROMs estão disponíveis na pasta [`/roms_content`](/roms_content/).