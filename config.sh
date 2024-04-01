#!/bin/bash 

if [ "$#" -ne 1 ]; then
 echo "Vous devez passer l'argument reset ou install au script"
fi

if [ ! -z "$1" ]
then 
    if [ "$1" == "reset" ]
    then 
        if [ ! -f  "./fr.erwan.babyphone.server/src/configuration/Constantes_gen.java.bak" ]
        then 
            echo "Vous ne pouvez pas réinitaliser si vous n'avez pas installé"
            exit 1
        fi
        echo "reset..."
        mv ./fr.erwan.babyphone.server/src/configuration/Constantes.java  ./fr.erwan.babyphone.server/src/configuration/Constantes.java.bak
        mv ./fr.erwan.babyphone.server/src/configuration/Constantes_gen.java.bak ./fr.erwan.babyphone.server/src/configuration/Constantes.java
    fi

    if [ "$1" == "install" ]
        then 
            if [ -f  "./fr.erwan.babyphone.server/src/configuration/Constantes_gen.java.bak" ]
            then 
                echo "Vous ne pouvez pas installer si vous n'avez pas réinitialsié"
                exit 1
            fi
            echo "installation..."
            mv ./fr.erwan.babyphone.server/src/configuration/Constantes.java ./fr.erwan.babyphone.server/src/configuration/Constantes_gen.java.bak
            mv ./fr.erwan.babyphone.server/src/configuration/Constantes.java.bak ./fr.erwan.babyphone.server/src/configuration/Constantes.java
    fi
    echo "Vérification : "
    tail -n5 ./fr.erwan.babyphone.server/src/configuration/Constantes.java 
    
    
fi;