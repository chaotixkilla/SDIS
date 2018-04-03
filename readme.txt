Para compilar o projeto, pode correr o batch file dentro da pasta scripts com o nome 'windows_compile.bat', que cria os ficheiros '.class' numa pasta bin.

Para configurar o RMI, entre na pasta bin recentemente criada e no terminal corra o comando 'start rmiregistry' em Windows ou 'rmiregistry' em linux.

Para abrir servers, corra o batch file dentro da pasta scripts com o nome 'windows_create_servers.bat', introduzindo o n�mero de servers que desejar.

Os ficheiros que s�o lidos para BACKUP t�m que ser introduzidos na pasta principal do projecto (ao mesmo n�vel que as pastas bin, scripts, src).

Quando o backup de um ficheiro � efetuado, as suas chunks s�o guardadas numa nova pasta 'backup', sendo cada uma das suas subpastas o backup relativo 
a cada server (s� haver� esta subpasta, caso o server tenha algum chunk stored).

Quando o restore de um ficheiro � efetuado, o ficheiro resultante ser� guardado na pasta principal, com o nome igual ao fileID, mas sem extens�o.
Caso utilize um IDE de java e o ficheiro resultante estiver corrupto, dever� ser devido � codifica��o default utilizada pelo IDE. No nosso caso, o Eclipse 
utilizava a codifica��o default do Windows, o que trazia erros de codifica��o, mas ap�s mudan�a nas propriedades do projeto, o ficheiro resultante era id�ntico
ao original em 100% dos casos testados