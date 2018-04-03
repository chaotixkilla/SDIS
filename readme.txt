Para compilar o projeto, pode correr o batch file dentro da pasta scripts com o nome 'windows_compile.bat', que cria os ficheiros '.class' numa pasta bin.

Para configurar o RMI, entre na pasta bin recentemente criada e no terminal corra o comando 'start rmiregistry' em Windows ou 'rmiregistry' em linux.

Para abrir servers, corra o batch file dentro da pasta scripts com o nome 'windows_create_servers.bat', introduzindo o número de servers que desejar.

Os ficheiros que são lidos para BACKUP têm que ser introduzidos na pasta principal do projecto (ao mesmo nível que as pastas bin, scripts, src).

Quando o backup de um ficheiro é efetuado, as suas chunks são guardadas numa nova pasta 'backup', sendo cada uma das suas subpastas o backup relativo 
a cada server (só haverá esta subpasta, caso o server tenha algum chunk stored).

Quando o restore de um ficheiro é efetuado, o ficheiro resultante será guardado na pasta principal, com o nome igual ao fileID, mas sem extensão.
Caso utilize um IDE de java e o ficheiro resultante estiver corrupto, deverá ser devido à codificação default utilizada pelo IDE. No nosso caso, o Eclipse 
utilizava a codificação default do Windows, o que trazia erros de codificação, mas após mudança nas propriedades do projeto, o ficheiro resultante era idêntico
ao original em 100% dos casos testados