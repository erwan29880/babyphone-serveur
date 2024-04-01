# Babyphone : serveur  

Voir https://github.com/erwan29880/babyphone-client pour d'avantage d'information.

Simple serveur de streaming audio via l'entrée micro du serveur par socket, pas d'interface graphique pour le serveur.

Le serveur a été testé sous ubuntu serveur 22LTS sous raspberryPi 4 et sous windows 11, avec java 17 (opendjdk-17-jre), d'après un exécutable jar de ce programme.

Certains éléments sont à renseigner dans le fichier configuration.Constantes. Les données de formatage audio doivent être identiques à celles du client.    

config.sh : destiné à mon usage pour le développement et le test. Les tests dépendent de la configuration et ne sont pas fournis.
