Url para ver las propiedades del configServer que tenemos configurado en git:

http://localhost:8888/limits-service/default

* Para configurar git cloud config: configuramos un proyecto con https://start.spring.io/ 
y añadimos dependencias spring devtools y config server -> spring-cloud-config-server

* Despues creamos un repositorio git que añadimos al proyecto  spring-cloud-config-server, donde añadimos el 
fichero properties

** Con esto configuramos configServer a git
** Luego nuestro proyecto de limits-service lo configuramos a configServer

Al crear el currency-exchange-services creamos dos, arrancando uno en 8000 y otro en 8001, para 
ello en run configuration duplicamos el creado y en arguments ponemos:

-Dserver.port=8001

Realizamos un proxy para hacer peticiones a diferente microservicios, para ello nos ayudamos de @FeignClient creando un proxy al servicio 
y un metodo para el punto de acceso. Ademas en la aplicaciona habilitamos el @EnableFeignClients

Para el balanceo de carga entre las diferentes maquinas utilizamos netflix-ribbon 

Incluimos dependencia, en el proxy incluimos y cambiamos el @FeignClient a currency-exchange-service. en las properties indicamos la 
lista de maquinas -> currency-exchange-service.ribbon.listOfServers=http://localhost:8000,http://localhost:8001

Pero cada vez que se crea un nuevo servidor habria que cambiar configuracion, queremos que se cambie dinamicamente para ello utilizamos eureka
que es un registro de nombres. Cada vez queremos hablar con un servicio hablamos con servidor de nombres y le pregunta que instancias se estan 
ejecutando. Creamos un proyecto con eureka naming server, importando eureka, config client, actuator y devtools

Habilitamos eureka en la app y el las propiedades:
spring.application.name=netflix-eureka-naming-server
server.port=8761

eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false


En el servicio que queremos usarlo qutamos en las propiedades currency-exchange-service.ribbon.listOfServers y 
ya usarmos eureka.client.service-url.default-zone 