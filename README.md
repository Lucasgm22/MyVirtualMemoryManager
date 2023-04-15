# MyVirtualMemoryManager

Projeto para desenvolver a abstração de um Gestor de Memória Virtual, desenvolvido para a disciplina de Sistemas Operacionais da UFABC, ministrada pelo Prof. Dr. Joao Marcelo Borovina Josko.

- Tradução de endereço
- Gerenciamento de Page Fault
- Algoritmo de Page Replacement

## Como rodar?
Para rodar o programa execute em um terminal, para Linux:

`./gradlew run --args="[RAM_ALLOCATION_SIZE] [NUMBER_OF_REQUEST]"`


Para Windows:

`./gradlew.bat run --args="[RAM_ALLOCATION_SIZE] [NUMBER_OF_REQUEST]"`

Onde as váriavés são:
  - RAM_ALLOCATION_SIZE - Quantidade de RAM que será alocada;
  - NUMBER_OF_REQUEST - Quantidade de requisições de páginas que será feita;
  
## Recursos

### secondary_memory.json
Abstração de memória secundária, é utilizada para ser consultada pela classe Secondary Memory para consulta das páginas.

## Classes
### LRU
Classe responsável por implementar a estrutura de LRU (\textit{last recentry used}), que armazena os índeces das páginas requisitadas e guarda a informação da página utilizada por último.

#### Atributos
  - entries - Índices das páginas requisitadas;
  - frameCount - Quantidade de frames.

#### Métodos
  - addOnLRU(pageIndex)
  
Responsável por adicionar ou atualizar o índice da página na LRU, retorna a página requisitada por último.

```mermaid
stateDiagram-v2
    lruci: LRU Contains Index
    pgifru: Page Index is First Recently Used
    uiafru: Update index to First Recently Used
    piafru: Put index as First Recently Used
    rlru: Return Last Recently Used
    rmlru: Remove Last Recently Used
    hfa: Has frames available
    state if_state1 <<choice>>
    state if_state2 <<choice>>
    state if_state3 <<choice>>
    [*] --> lruci
    lruci --> if_state1
    if_state1 --> pgifru : true
    pgifru --> if_state2
    if_state2 --> rlru: true
    if_state2 --> uiafru: false
    uiafru --> rlru
    rlru --> [*]
    if_state1 --> hfa : false
    hfa --> if_state3
    if_state3 --> piafru: true
    if_state3 --> rmlru: false
    rmlru --> piafru
    piafru -->rlru
```
### PageTable
Classe responsável por implementar a estrutura de \textit{page table}.

#### Atributos
  - entries - Entradas da `page table`;
  - pageCount - Quantidade de páginas.

#### Métodos
  - removePageEntry(pageIndex)
  
  Responsável atualizar informação que indica que a página com dado índice foi removida da memória.
```mermaid
stateDiagram-v2
    s: Set invilid bit to false on page entrie by given index
    [*] --> s
    s --> [*]
```

  - addFrameOnPageEntry(pageIndex, frameIndex)
  
  Responsável atualizar informação que indica que a página com dado índice foi adicionada na memória, guardando o frame dado.
```mermaid
stateDiagram-v2
    f: Put frame index on page entrie by given page index
    s: Set invilid bit to true on page entrie by given page index
    [*] --> f
    f --> s
    s --> [*]
```

  - addFrameByPage(pageIndex)
  
  Responsável por retornar o frame em que a página está alocada em memória.
```mermaid
stateDiagram-v2
    f: Return frame by page index, throws exception if page not in memory
    [*] --> f
    f --> [*]
```

  - getFrameByPage(pageIndex)

Responsável por retornar o frame em que a página está alocada em memória.
```mermaid
stateDiagram-v2
    f: Return frame by page index, throws exception if page not in memory
    [*] --> f
    f --> [*]
```

  - getFrameByPage(pageIndex)

Retorna o `valid/invalid bit` da página dada (página está na memória ou não).
```mermaid
stateDiagram-v2
    f: Return valid/invalid bit of given page index
    [*] --> f
    f --> [*]
```

### RAM
Classe responsável por implementar a abstração de RAM.

#### Atributos
  - frames - Frames da RAM;
  - frameCount - Quantidade de frames.
  
#### Métodos
  - addPage(page, frameIndex)
  
Adiciona a página no frame indicado.
```mermaid
stateDiagram-v2
    f: Add page on given frame
    s: Return frame were page was added
    [*] --> f
    f --> s
    s --> [*]
```

### SecondaryMemory

Classe responsável por implementar a abstração de memóriia secundária.

#### Métodos
  - searchPage(pageIndex)
  
Busca no recurso \textit{secondary\_memory.json} a página e a retorna.
```mermaid
stateDiagram-v2
    f: Serch page on resource
    s: Return page
    [*] --> f
    f --> s
    s --> [*]
```
### MemoryManager
Classe principal do projeto, contêm a função \textit{main}, ponto inicial do programa, segue o diagrama da execução:

```mermaid
stateDiagram-v2
    irlp: Init RAM, LRU and PageTable
    fa: Frame Allocation
    pon: Page on RAM
    ulrgtlru: Update LRU and get Last Recently Used
    sposm: Seach page on Secondary Memory
    hfsor: Has free space o RAM
    rlrufr: Remove Last Recently Used from Ram
    ilrup: Invalidate Last Recently Used Page
    afor: Add page on RAM
    upt: Update Page Table with availble frame on page requested
    state if_state <<choice>>
    state if_state2 <<choice>>
    [*] --> irlp
    irlp --> fa
    fa --> GetPage: For each page request
    state GetPage {
        [*] --> ulrgtlru
        ulrgtlru --> pon
        pon --> if_state
        if_state --> [*]: true
        if_state --> sposm: false
        sposm --> hfsor
        hfsor --> if_state2
        if_state2 --> upt: true
        afor --> [*]
        if_state2 --> FrameReplacement: false
        state FrameReplacement {
            [*] --> rlrufr
            rlrufr --> ilrup
            ilrup --> [*]
        }
        FrameReplacement --> upt
        upt --> afor

    }

    GetPage --> [*]
```
