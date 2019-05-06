# Maquina-de-busca-Springboot
Maquina de busca Projeto Final


HOST: https://maquina-de-busca-springboot.herokuapp.com


==================================================================
Coletor
==================================================================
#Executar coleta
GET https://maquina-de-busca-springboot.herokuapp.com/coletor/iniciar



==================================================================
Documento
==================================================================
#Listar Documentos
GET https://maquina-de-busca-springboot.herokuapp.com/documento/

#Listar Documento por id
GET https://maquina-de-busca-springboot.herokuapp.com/documento/{id}

#Deletar Documento (apenas ADMIN)
DELETE https://maquina-de-busca-springboot.herokuapp.com/documento/{id}
DELETE https://maquina-de-busca-springboot.herokuapp.com/documento/ (passando json do documento no body)

#Encontrar Documento pela url
GET https://maquina-de-busca-springboot.herokuapp.com/documento/encontrar/{url}



==================================================================
Host
==================================================================
#Listar Hosts
GET https://maquina-de-busca-springboot.herokuapp.com/host/

#Listar Hosts em ordem Alfabetica
GET https://maquina-de-busca-springboot.herokuapp.com/host/ordemAlfabetica

#Listar Host por id 
GET https://maquina-de-busca-springboot.herokuapp.com/host/{id}

#Deletar Documento (apenas ADMIN)
DELETE https://maquina-de-busca-springboot.herokuapp.com/host/{id}
DELETE https://maquina-de-busca-springboot.herokuapp.com/host/ (passando json do host no body)

#Encontrar Documento pela url
GET https://maquina-de-busca-springboot.herokuapp.com/host/encontrar/{url}


==================================================================
Link
==================================================================
#Listar Links
GET https://maquina-de-busca-springboot.herokuapp.com/link/

#Listar Links em ordem Alfabetica
GET https://maquina-de-busca-springboot.herokuapp.com/link/ordemAlfabetica

#Listar Link por id 
GET https://maquina-de-busca-springboot.herokuapp.com/link/{id}

#Encontrar Link pela url
GET https://maquina-de-busca-springboot.herokuapp.com/link/encontrar/{url}

#Listar Sementes
GET https://maquina-de-busca-springboot.herokuapp.com/link/sementes

#Deletar Documento (apenas ADMIN)
DELETE https://maquina-de-busca-springboot.herokuapp.com/link/{id}
DELETE https://maquina-de-busca-springboot.herokuapp.com/link/ (passando json do link no body)

#Atualiza Link
PUT https://maquina-de-busca-springboot.herokuapp.com/link/ (passando json do link no body)

#Atualiza ultimaColeta(Data) (apenas ADMIN)
PUT https://maquina-de-busca-springboot.herokuapp.com/link/atualizaUltimaColetaSementes (passando a data como json no body)

#Inserir Link
POST https://maquina-de-busca-springboot.herokuapp.com/link/ (passando json do link no body)



==================================================================
Usuario
==================================================================
#Listar Usuarios
GET https://maquina-de-busca-springboot.herokuapp.com/usuario/

#Listar Usuarios por id
GET https://maquina-de-busca-springboot.herokuapp.com/usuario/{id}

#Listar Administradores (apenas ADMIN)
GET https://maquina-de-busca-springboot.herokuapp.com/usuario/

#Listar Administradores por id (apenas ADMIN)
GET https://maquina-de-busca-springboot.herokuapp.com/usuario/{id}

(obs: Usuario visualiza apenas usuario)
#Listar Usuarios/Administradores pelo nome
GET https://maquina-de-busca-springboot.herokuapp.com/usuario/encontrar/{nome}

(obs: Usuario visualiza apenas usuario)
#Listar Usuarios/Administradores por nome em ordem alfabetica
GET https://maquina-de-busca-springboot.herokuapp.com/usuario/ordemAlfabetica

#Inserir Usuario
POST https://maquina-de-busca-springboot.herokuapp.com/usuario/

#Inserir Administrador (apenas ADMIN)
POST https://maquina-de-busca-springboot.herokuapp.com/usuario/administrador

#Deletar Usuario (obs: DELETE com rota "/remove" pois deu conflito com o GET)
DELETE https://maquina-de-busca-springboot.herokuapp.com/usuario/remove

(obs: DELETE com rota "/remove" pois deu conflito com o GET)
#Deletar Usuario/Administrador (apenas ADMIN remove Administrador)
DELETE https://maquina-de-busca-springboot.herokuapp.com/usuario/remove (passando json do Usuario no body)

#Deletar Usuario/Administrador (apenas ADMIN remove Administrador)
DELETE https://maquina-de-busca-springboot.herokuapp.com/usuario/remove/{id}




