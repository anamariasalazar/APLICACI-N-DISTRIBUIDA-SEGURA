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
