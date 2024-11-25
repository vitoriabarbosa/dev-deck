# 🎮 DEV DECK  🃏

## 🕹 Seja bem-vindo(a) ao game! 🥳
_Dev Deck, um jogo de dev pra dev!_ 🧑‍💻

## 💡 Como Jogar
### Objetivo: 
O objetivo do jogo é organizar todas as cartas de cada naipe (Java, Python, C, C++) em ordem crescente, começando do Ás até o 7, dentro de seus respectivos conjuntos (sem alternar cores).

### Naipes:
   - Java (cor da carta: Vermelha)
   - Python (cor da carta: Amarelo)
   - C (cor da carta: Cinza)
   - C++ (cor da carta: Azul)

### Regras:
1. **Montagem**: As cartas devem ser organizadas em sequência crescente dentro do mesmo naipe. Exemplo: Ás de Java, seguido do 2 de Java, e assim por diante até o 7 de Java. O mesmo vale para os outros naipes.<br><br>
2. **Movimentação**: Somente a última carta de uma sequência pode ser movida para uma nova pilha, contanto que a carta no topo da nova pilha seja a próxima em ordem dentro do mesmo naipe.<br><br>
3. **Distribuição Inicial**: As cartas são distribuídas em pilhas aleatórias no início do jogo, e o jogador deve reorganizá-las de acordo com as regras mencionadas.<br><br>
4. **Condições de Vitória**: O jogo é vencido quando todas as cartas forem organizadas em pilhas de seus respectivos naipes, com a sequência completa de Ás a 7.<br><br>


## 🔢 Assuntos Envolvidos (Matemática)
### Teoria dos Conjuntos: 
A organização das cartas por naipe (Java, Python, C, C++) e a sequência de valores pode ser vista como uma aplicação da teoria de conjuntos. Cada naipe forma um conjunto de elementos, e o jogador precisa organizar as cartas de forma que cada subconjunto (ou sequência) siga uma ordem crescente específica.

### Permutação e Combinação: 
A maneira como as cartas são distribuídas inicialmente envolve permutações, pois as cartas podem ser rearranjadas em várias ordens. Durante o jogo, o jogador também lida com a combinação de cartas ao movê-las entre as pilhas.

### Lógica Booleana: 
O processo de tomada de decisões ao mover cartas pode ser modelado por lógica booleana, onde o jogador precisa verificar condições específicas (como se a carta que está movendo é a próxima na sequência e se está no mesmo naipe).

### Teoria dos Grafos: 
O jogo pode ser representado como um grafo, onde cada carta ou pilha é um nó, e as conexões entre as cartas (possibilidades de movimento) formam as arestas. O jogador precisa "navegar" por esse grafo de maneira eficiente para encontrar a solução.


## 🛠️ Implementação
- **Linguagem de Programação:** Java
- **Interface Gráfica:** Swing
- **Gerente de Dependência:** Maven
- **Controle de Versão:** Git
- **Protótipo do Design:** Figma e Canva


## 🚀 Começando
### Pré-requisitos
Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
- [JDK 17 ou superior (Oracle JDK)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/)
- [Git](https://git-scm.com/)

Software util:
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) ou uma IDE de sua preferência

### Instalação
1. #### Clone o repositório
   ```bash
   git clone https://github.com/vitoriabarbosa/dev-deck.git

2. #### Navegar até o Diretório do Projeto:
   ```bash
   cd ~/dev-deck

3. #### Instalar o JDK 17 ou Superior.
   - Verifique se você tem o JDK 17 ou superior instalado.

4. #### Instalar o Maven:
   - Instale o Maven a partir do site oficial.
   - Adicione o Maven ao seu PATH conforme as instruções de instalação do site.

5. #### Compilar e Executar a Aplicação Principal:
   - Compilar e executar a aplicação principal.
   - Certifique-se de que todas as dependências estão no classpath.

      No terminal, execute:
      ```bash
       mvn compile exec:java -Dexec.mainClass="devdeck.InicialFrame"
      ```

## 🤝 Equipe
<table>
   <tr>
      <td align="center">
          <a href="https://github.com/carlosklg" title="Github de Carlos Candido">
            <img src="https://avatars.githubusercontent.com/u/139086058?v=4" width="100px;" alt="foto"/><br>
            <sub>
              <b>Carlos Candido</b>
            </sub>
          </a>
      </td>
      <td align="center">
          <a href="https://github.com/Caze-69" title="Github de Carlos Eduardo">
            <img src="https://avatars.githubusercontent.com/u/176598075?v=4" width="100px;" alt="foto"/><br>
            <sub>
              <b>Carlos Eduardo</b>
            </sub>
          </a>
      </td>
      <td align="center">
          <a href="https://github.com/vitoriabarbosa" title="Github de Vitória">
            <img src="https://avatars.githubusercontent.com/u/93888309?v=4" width="100px;" alt="foto"/><br>
            <sub>
              <b>Vitória Barbosa</b>
            </sub>
          </a>
     </td>
      <td align="center">
          <a href="https://github.com/CordeiroGab" title="Github de Gabriel">
            <img src="https://avatars.githubusercontent.com/u/158838184?v=4" width="100px;" alt="foto"/><br>
            <sub>
              <b>Gabriel Neves</b>
            </sub>
          </a>
      </td>
      <td align="center">
          <a href="https://github.com/YcaruNunesDev" title="Github de Ycaru">
            <img src="https://avatars.githubusercontent.com/u/136641821?v=4" width="100px;" alt="foto"/><br>
            <sub>
              <b>Ycaru Nunes</b>
            </sub>
          </a>
      </td>
   </tr>
</table><br>

## 📝 Licença
Este projeto é licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.
