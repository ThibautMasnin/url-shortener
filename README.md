# URL Shortener

Projet de raccourcissement d’URL.
*Développé dans le cadre d'un test technique pour **Portage CyberTech***

Ce projet permet de :
- Générer une URL raccourcie à partir d’une URL complète.
- Retrouver l’URL d’origine à partir de l'URL raccourcie.
- Faire persister les données grâce à une base H2.
- Consulter et tester les endpoints via Swagger UI.

---

## Prérequis
- **Java 21** ou supérieur

---

## Lancer l’application

A l'aide d'un terminal:

### Cloner le projet
```bash
git clone https://github.com/ThibautMasnin/url-shortener.git
cd url-shortener
```

### (Facultatif) Compiler et générer le JAR
```bash
mvn clean package
```
Cela créera un fichier dans le dossier `target`

### Lancer à partir du fichier `.jar`

Ouvrez un terminal dans le dossier contenant le `.jar`, puis exécutez :
```bash
java -jar urlshortener-1.0.0.jar
```

---

## Accéder l’application

### Via Swagger UI

[http://localhost:8080/docs](http://localhost:8080/docs)


**Exemple de création d'URL raccourcie:**
Méthode: `POST`
Chemin: `/api/urls`
Request body: `https://www.portagecybertech.com/fr/a-propos/carrieres#postes`

Réponse: `http://localhost:8080/A3xZ9q`



**Exemple de réccupération d'URL originale:**
Méthode : `GET`
Chemin: `/api/urls`
Paramètres: 
&emsp;*shortenedUrl* : `https://www.portagecybertech.com/A3xZ9q`

Réponse: `https://www.portagecybertech.com/fr/a-propos/carrieres#postes`

### Via la console H2

[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

- **URL JDBC :** `jdbc:h2:file:./data/urlshortener`
- **Utilisateur :** `sa`
- **Mot de passe :** *(vide)*

### Via la description technique JSON
[http://localhost:8080/api-docs](http://localhost:8080/api-docs)

---

## Auteur
**Thibaut Masnin**  
Développeur Fullstack Java
[thibautmasnin@gmail.com](mailto:thibautmasnin@gmail.com)