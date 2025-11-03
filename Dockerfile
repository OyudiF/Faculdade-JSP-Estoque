# Usamos uma imagem oficial do Maven (baseada em Java 11)
FROM maven:3.8-openjdk-11 AS build

# Dizemos ao Docker para trabalhar dentro da pasta /app
WORKDIR /app

# Copia o "mapa" do Maven primeiro (para cache)
COPY pom.xml .

# Copia o resto do código-fonte (o /src e /WebContent)
COPY src ./src
COPY WebContent ./WebContent

# Roda o Maven para baixar as dependências e "empacotar" o .war
# Isso cria o /app/target/ROOT.war
RUN mvn clean package -DskipTests


# --- ESTÁGIO 2: Preparar o Servidor (com Tomcat) ---
# Usamos uma imagem oficial e leve do Tomcat 9 com Java 11
FROM tomcat:9.0-jre11-slim

# Limpa a pasta webapps padrão do Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia o .war que criamos no Estágio 1 (build)
# para dentro da pasta webapps do Tomcat
COPY --from=build /app/target/ROOT.war /usr/local/tomcat/webapps/ROOT.war

# Expõe a porta 8080 (que o Tomcat usa)
EXPOSE 8080

# O comando para ligar o servidor
CMD ["catalina.sh", "run"]