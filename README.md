# APLICACIÓN DISTRIBUIDA SEGURA EN TODOS SUS FRENTES
En las aplicaciones distribuidas se van a aplicar en cada una de las aplicacion el manejo de certificados ```http + ssl``` 


### Arquitectura
Esta se puede ver de las siguientes manera:

**Localhost:** Esta arquitectura tiene dos servicios diferentes que por medio del protocolo https se comunican entre ellos, el localhost tiene el login entra desde el browser por por medio del protrocol https.

**AWS:** Es similar a la del localhost, la diferencia es que los servicios estan dentro de una máquina EC2 de AWS.


### Requisitos previos

* Tener maven instalado
* Tener git instalado
* Versión de Java 7 o Java 8
* Tener Docker instalado
* Tener un protocolo SSH instalado y habilitado


### Instalación

1. Para hacer uso de este proyecto debe clonarlo de este repositorio a su computadora desde cmd usando el siguiente comando:
   
   ```
   git clone https://github.com/anamariasalazar/TALLER-DE-DE-MODULARIZACI-N-CON-VIRTUALIZACI-N-E-INTRODUCCI-N-A-DOCKER-Y-A-AWS.git
   ```

## Despliegue en localhost
### Creación llaves y certificados de seguridad

> 1. Se crea llave de confianza, llave de seguridad y certificado, se ubican en la carpeta keystores del servicio LOGIN.

*COMANDOS*
>
> Llave de seguridad:
```
keytool -genkeypair -alias ecikeypair -keyalg RSA -keysize 2048-storetype PKCS12 -keystore ecikeystore.p12 -validity 3650
```
>  En el nombre se le pone localhost, y en los demás los que le corresponda.

> Certificado:
```
keytool -export -keystore ./ecikeystore.p12 -alias ecikeypair -file ecicert.cer
```

>Llave de confianza
```
keytool -import -file ./ecicert.cer -alias firstCA -keystore myTrustStore
```

> Estos 3 archivos creados se copian y pega en la carpeta keystores del servicio CLIENTE. 

### Construcción

> Se usan los siguientes comandos en orden en la raiz del proyecto.
```
cd LOGIN

mvn clean build

mvn package

cd ..

cd CLIENTE

mvn clean build

mvn package
```

### Despliegue

> En la terminal de la carpeta LOGIN se introduce este comando:
```
java -cp "target/classes:target/dependency/*" edu.escuelaing.arep.App
```

> En la terminal de la carpeta CLIENTE se introduce este comando:
```
java -cp "target/classes:target/dependency/*" edu.escuelaing.arep.App
```

## Despliegue en AWS

### Adquirir Servicios

>Se  comprime la carpeta que contiene ambos proyecto y ese archivo comprimido se lleva a la carpeta donde están claves de conexión  a la máquina EC2
> 
> ![](/Imagenes/1.PNG)
> 
> Por linea de comandos en este caso es la dirección de conexión via ssh que da AWS para acceder a la maquina EC2, se reemplazando ```ssh``` por ```sftp```
> 
> ![](/Imagenes/2.PNG)
> 
> Se pasa  el archivo hacia la maquina con el comando ```put```  
> 
> ![](/Imagenes/3.PNG)
> 
> Ahora se  ingresa a la maquina con el protocolo ```ssh```
> 
> ![](/Imagenes/4.PNG)
> 
> Se descomprime el archivo que fue transferido con el comando ```unzip``` y el nombre del archivo

> 
### Creación llaves y certificados de seguridad

> Se ingresan  los siguientes comandos para eliminar las llaves existentes, en orden:
> 
```
cd AREPAPPSEGURA

cd LOGIN

rm -rf keystores

cd ..

cd CLIENTE

rm -rf keystores

cd ..
```

> Se crea unas nuevas por lo cual se accede a la ubicación LOGIN
> 
```
cd LOGIN
```

> Crearemos un nuevo directorio keystores

```
mkdir keystores
```
```
cd keystores
```

> Creación de las llaves 
```
keytool -genkeypair -alias ecikeypair -keyalg RSA -keysize 2048-storetype PKCS12 -keystore ecikeystore.p12 -validity 3650
```
> En el nombre se le pone el nombre de la maquina en la que se estara corriendo el servicio, en este caso debe ser el dominio de la maquina EC2, y en los demás los que le corresponda

> Creación certificado
```
keytool -export -keystore ./ecikeystore.p12 -alias ecikeypair -file ecicert.cer
```

> Creación llave de confianza
```
keytool -import -file ./ecicert.cer -alias firstCA -keystore myTrustStore
```

> Despues de haber creado los 3 archivos se copiay pega en la carpeta CLIENTE, con los siguientes comando:
> 
```
cd ..

cp -r keystores/ ~/AREPAPPSEGURA/CLIENTE/
```

### Despliegue

>En dos terminales diferentes ubicadas una en la carpeta CLIENTE y la otra en la carpeta LOGIN, se ingresa el siguiente comando en ambas:

```
java -cp "target/classes:target/dependency/*" edu.escuelaing.arep.App
```
### Prueba

> Se ingresa a el  browser y consultar la dirección:
> 
```
https://+ruta del servicio amazon+:4567
```

En la pagina del login e introduciremos como credenciales

```
Correo: anamariasalazar
Contraseña: ana
```
> 
> ![](/Imagenes/5.PNG)
>
> Al ingresar aparece la siguiente pestaña que muestra que el ingreso fue exitoso
> 
> ![](/Imagenes/6.PNG)
> 
## Video explicación despliegue en AWS

Podemos abrir este enlace y descargarlo

[Descargar video](https://github.com/anamariasalazar/APLICACI-N-DISTRIBUIDA-SEGURA/tree/main/Ana%20Salazar%20Video%20de%20experimento%20en%20AWS)


## Construido con

* [Java](https://www.oracle.com/java/) : Tecnología que se usa para el desarrollo de aplicaciones que convierten a la Web en un elemento más interesante y útil.
* [IntelliJ](https://es.wikipedia.org/wiki/IntelliJ_IDEA): Es un entorno de desarrollo integrado (IDE) para el desarrollo de programas informáticos.
* [Git](https://es.wikipedia.org/wiki/Git): Herramienta que realiza una función del control de versiones de código de forma distribuida
* [Maven](https://es.wikipedia.org/wiki/Maven): Maven es una herramienta de software para la gestión y construcción de proyectos Java creada por Jason van Zyl, de Sonatype, en 2002. 
* [JavaScript](https://es.wikipedia.org/wiki/JavaScript): Es el lenguaje de programación encargado de dotar de mayor interactividad y dinamismo a las páginas web.
* [SSH](https://es.wikipedia.org/wiki/Secure_Shell): Es el nombre de un protocolo y del programa que lo implementa cuya principal función es el acceso remoto a un servidor por medio de un canal seguro en el que toda la información está cifrada.
* [AWS](https://aws.amazon.com/es/): s una colección de servicios de computación en la nube pública que en conjunto forman una plataforma de computación en la nube, ofrecidas a través de Internet por Amazon.com

## Autor

* [Ana Maria Salazar Bohorquez](https://github.com/anamariasalazar)

## Licencia

**©️** Ana Maria Salazar Bohorquez etudiante de Ingeniería de Sistemas de la Escuela Colombiana de Ingeniería Julio Garavito

Licencia bajo la [GNU General Public License](/LICENSE.txt)
