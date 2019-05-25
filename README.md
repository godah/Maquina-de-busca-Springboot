# Maquina-de-busca-Springboot
Maquina de busca Projeto Final


HOST: https://maquina-de-busca.herokuapp.com



==================================================================
Login
==================================================================

#Rota para login
POST https://maquina-de-busca.herokuapp.com/login/



==================================================================
Coletor
==================================================================
#Executar coleta
GET https://maquina-de-busca.herokuapp.com/coletor/iniciar



==================================================================
Documento
==================================================================
#Listar Documentos
GET https://maquina-de-busca.herokuapp.com/documento/

#Listar Documento por id
GET https://maquina-de-busca.herokuapp.com/documento/{id}

#Deletar Documento (apenas ADMIN)
DELETE https://maquina-de-busca.herokuapp.com/documento/{id}
DELETE https://maquina-de-busca.herokuapp.com/documento/ (passando json do documento no body)

#Encontrar Documento pela url
GET https://maquina-de-busca.herokuapp.com/documento/encontrar/{url}



==================================================================
Host
==================================================================
#Listar Hosts
GET https://maquina-de-busca.herokuapp.com/host/

#Listar Hosts em ordem Alfabetica
GET https://maquina-de-busca.herokuapp.com/host/ordemAlfabetica

#Listar Host por id 
GET https://maquina-de-busca.herokuapp.com/host/{id}

#Deletar Documento (apenas ADMIN)
DELETE https://maquina-de-busca.herokuapp.com/host/{id}
DELETE https://maquina-de-busca.herokuapp.com/host/ (passando json do host no body)

#Encontrar Documento pela url
GET https://maquina-de-busca.herokuapp.com/host/encontrar/{url}


==================================================================
Link
==================================================================
#Listar Links
GET https://maquina-de-busca.herokuapp.com/link/

#Listar Links em ordem Alfabetica
GET https://maquina-de-busca.herokuapp.com/link/ordemAlfabetica

#Listar Link por id 
GET https://maquina-de-busca.herokuapp.com/link/{id}

#Encontrar Link pela url
GET https://maquina-de-busca.herokuapp.com/link/encontrar/{url}

#Listar Sementes
GET https://maquina-de-busca.herokuapp.com/link/sementes

#Deletar Documento (apenas ADMIN)
DELETE https://maquina-de-busca.herokuapp.com/link/{id}
DELETE https://maquina-de-busca.herokuapp.com/link/ (passando json do link no body)

#Atualiza Link
PUT https://maquina-de-busca.herokuapp.com/link/ (passando json do link no body)

#Atualiza ultimaColeta(Data) (apenas ADMIN)
PUT https://maquina-de-busca.herokuapp.com/link/atualizaUltimaColetaSementes (passando a data como json no body)

#Inserir Link (apenas ADMIN)
POST https://maquina-de-busca.herokuapp.com/link/ (passando json do link no body)

#Listar pagina
GET https://maquina-de-busca.herokuapp.com/link/pagina

#Listar pagina por numero
GET https://maquina-de-busca.herokuapp.com/link/pagina/{pageFlag}

#Listar Link por intervalo
GET https://maquina-de-busca.herokuapp.com/link/intervalo/{id1}/{id2}

#Contar Link por intervalo
GET https://maquina-de-busca.herokuapp.com/link/intervalo/contar/{id1}/{id2}

#Inserir url sementes (apenas ADMIN)
POST https://maquina-de-busca.herokuapp.com/link/urlsSementes (passando json do link no body)

#Listar Sementes por Host
GET https://maquina-de-busca.herokuapp.com/link/encontrarSemente/{link}

#Listar Sementes por intervalo de data
GET https://maquina-de-busca.herokuapp.com/link/sementes/datas/{id1}/{id2}

#Atualizar ultima coleta Sementes (apenas ADMIN)
PUT https://maquina-de-busca.herokuapp.com/link/ultima/coleta/{link}/{data}


==================================================================
Usuario
==================================================================
#Listar Usuarios
GET https://maquina-de-busca.herokuapp.com/usuario/

#Listar Usuarios por id
GET https://maquina-de-busca.herokuapp.com/usuario/{id}

#Listar Administradores (apenas ADMIN)
GET https://maquina-de-busca.herokuapp.com/usuario/adminisrador

#Listar Administradores por id (apenas ADMIN)
GET https://maquina-de-busca.herokuapp.com/usuario/administrador/{id}

(obs: Usuario visualiza apenas usuario)
#Listar Usuarios/Administradores pelo nome
GET https://maquina-de-busca.herokuapp.com/usuario/encontrar/{nome}

(obs: Usuario visualiza apenas usuario)
#Listar Usuarios/Administradores por nome em ordem alfabetica
GET https://maquina-de-busca.herokuapp.com/usuario/ordemAlfabetica

#Inserir Usuario
POST https://maquina-de-busca.herokuapp.com/usuario/

#Inserir Administrador (apenas ADMIN)
POST https://maquina-de-busca.herokuapp.com/usuario/administrador

obs: DELETE com rota "/remove" pois deu conflito com o GET
#Deletar Usuario (apenas ADMIN)
DELETE https://maquina-de-busca.herokuapp.com/usuario/remove

(obs: DELETE com rota "/remove" pois deu conflito com o GET)
#Deletar Usuario/Administrador (apenas ADMIN)
DELETE https://maquina-de-busca.herokuapp.com/usuario/remove (passando json do Usuario no body)

#Deletar Usuario/Administrador (apenas ADMIN remove Administrador)
DELETE https://maquina-de-busca.herokuapp.com/usuario/remove/{id}



==================================================================
Indexador
==================================================================

#Cria Indice
POST https://maquina-de-busca.herokuapp.com/indexador/indice

#Retorna documentos indexados
GET https://maquina-de-busca.herokuapp.com/indexador/documento



==================================================================
Indexador
==================================================================
 
#Processador de consulta
GET https://maquina-de-busca.herokuapp.com/processador/consulta/{consultaDoUsuario}

#Retorna o ranking
GET https://maquina-de-busca.herokuapp.com/processador/ranking

#Retorna o ranking com Similaridade normalizada = 1, semi-normalizada = 2 (Apenas ADMIN)
GET https://maquina-de-busca.herokuapp.com/processador/ranking/{tipo}




