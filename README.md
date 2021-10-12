### Build the project

- Open pom.xml and edit the system property **apigateway.version** based on API Gateway version.


```xml
        <!-- API Gateway August 2021 Release version -->
        <apigateway.version>7.7.0.20210830-2</apigateway.version>
```    

As API Gateway libraries are not available on public maven repositories, install the jar files to local maven repository (the version of jar vordel-trace-7.7.0.20210830-2.jar changes in each release).

```bash

 mvn install:install-file \
-Dfile=/home/axway/Axway-7.7.0-Aug2021/apigateway/system/lib/plugins/apigw-common-2.0.1.jar \
-DgroupId=apigw-common \
-DartifactId=apigw-common \
-Dversion=7.7.0.20210830-2 \
-Dpackaging=jar \
-DgeneratePom=true


mvn install:install-file \
-Dfile=/home/axway/Axway-7.7.0-Aug2021/apigateway/system/lib/plugins/vordel-trace-7.7.0.20210830-2.jar \
-DgroupId=vordel-trace \
-DartifactId=vordel-trace \
-Dversion=7.7.0.20210830-2 \
-Dpackaging=jar \
-DgeneratePom=true

mvn install:install-file \
-Dfile=/home/axway/Axway-7.7.0-Aug2021/apigateway/system/lib/vordel-apigateway-7.7.0.20210830-2.jar \
-DgroupId=vordel-apigateway \
-DartifactId=vordel-apigateway \
-Dversion=7.7.0.20210830-2 \
-Dpackaging=jar \
-DgeneratePom=true


mvn install:install-file \
-Dfile=/home/axway/Axway-7.7.0-Aug2021/apigateway/system/lib/vordel-core-runtime-7.7.0.20210830-2.jar \
-DgroupId=vordel-core-runtime \
-DartifactId=vordel-core-runtime \
-Dversion=7.7.0.20210830-2 \
-Dpackaging=jar \
-DgeneratePom=true


mvn install:install-file \
-Dfile=/home/axway/Axway-7.7.0-Aug2021/apigateway/system/lib/plugins/es-core-2.1.0.jar \
-DgroupId=es-core \
-DartifactId=es-core \
-Dversion=7.7.0.20210830-2 \
-Dpackaging=jar \
-DgeneratePom=true


mvn install:install-file \
-Dfile=/home/axway/Axway-7.7.0-Aug2021/apigateway/system/lib/plugins/vordel-config-7.7.0.20210830-2.jar \
-DgroupId=vordel-config \
-DartifactId=vordel-config \
-Dversion=7.7.0.20210830-2 \
-Dpackaging=jar \
-DgeneratePom=true

mvn install:install-file \
-Dfile=/home/axway/Axway-7.7.0-Aug2021/apigateway/system/lib/plugins/vordel-common-7.7.0.20210830-2.jar \
-DgroupId=vordel-common \
-DartifactId=vordel-common \
-Dversion=7.7.0.20210830-2 \
-Dpackaging=jar \
-DgeneratePom=true

mvn install:install-file \
-Dfile=/home/axway/Axway-7.7.0-Aug2021/apigateway/system/lib/plugins/vordel-system-7.7.0.20210830-2.jar \
-DgroupId=vordel-system \
-DartifactId=vordel-system \
-Dversion=7.7.0.20210830-2 \
-Dpackaging=jar \
-DgeneratePom=true
```

- Create jar file using maven
```bash
$mvn clean package
```

### Add Loadable Module to EMT Container

- Import Loadable Module
  Open Policystudio, Navigate to menu File -> Import -> Import Custom filters, select apim-policy-password-cert-env/src/main/resources/typeSet.xml. It will add Loadable module to entity store.

- Export fed file ( e.g container_env.fed) to build container

- Build a container merge directory ( **--merge-dir** ) option

    - The merge directory must be called apigateway and must have the same directory structure as in an API Gateway installation.
    - Copy the JAR file to a new directory /Users/axway/APIM/apigw-emt-scripts-2.1.0-SNAPSHOT/apigateway/ext/lib/ and specify /Users/axway/APIM/apigw-emt-scripts-2.1.0-SNAPSHOT/apigateway to the --merge-dir option.

```bash
./build_gw_image.py --license=/Users/axway/APIM/apigw-emt-scripts-2.1.0-SNAPSHOT/licenses/apim.lic --default-cert --parent-image=apigw-base --merge-dir=/Users/axway/APIM/apigw-emt-scripts-2.1.0-SNAPSHOT/apigateway --fed=container_env.fed --out-image=apim:latest
```
- Push it to docker registry if needed.

### Add Loadable Module to classic API Gateway


- Add Loadable module to running gateway using publish script or Import apim-policy-password-cert-env/src/main/resources/typeSet.xml via Policystudio using File -> Import -> Import Custom filters.
- Copy the micrometer-x.x.jar from project target folder to gateways instance folder $INSTALLDIR/apigateway/ext/lib
- Copy list of jars specified in a [file](jars.txt) to gateways instance folder $INSTALLDIR/apigateway/ext/lib