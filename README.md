# KeePassJ

Il gruppo si pone come obiettivo quello di realizzare un'applicazione di gestione delle password in stile KeePass.

Un problema comune degli utenti di internet è quello della gestione dei propri account.

La maggior parte delle persone è abituata a segnarsi le proprie password su supporti cartacei, tenerle a mente o ancora peggio a memorizzarle in chiaro sul proprio pc (operazione rischiosissima dal punto di vista della sicurezza informatica).

KeePassJ ha l'obiettivo di facilitare la gestione dei propri dati personali in maniera efficace e sicura, salvando tutto in un database locale criptato con un algoritmo di encryption a scelta (es. AES-256-CBC con HMAC-SHA256).


Funzionalità obbligatorie:

*    Gestione account con possibilità di suddividere gli account in gruppi/categorie
*    Funzione per generare password/nickname a random
*    Import ed export del database in XML/KDBX
*    Controllo robustezza password al momento dell'inserimento
*    Salvataggio dei dati in un database locale criptato con algoritmi a scelta
*    Sezione Statistics che mostra le statistiche relative al proprio database (Es. il numero di account salvati)


Funzionalità opzionali:

*   Export del database automatizzato
*   Import/export del database in diversi formati
*   Possibilità di cifrare archivi
*   Funzione Sort e Find per ordinare gli account e per cercarne uno in particolare
*   Funzione Expire che ti avvisa di cambiare una determinata password dopo un certo lasso di tempo


Challenge principali:

*   Rispettare le funzionalità specifiche di un password manager
*   Lavorare con algoritmi crittografici nel modo più efficace possibile
*   Garantire la sicurezza e la riservatezza dei dati forniti
*   Fornire un'interfaccia user friendly per semplificare all'utente finale la gestione dei propri dati personali


Suddivisione del lavoro (non definitiva):

*    Francesco Ercolani: creazione dell'interfaccia; gestione suddivisione account per categoria
*    Giovanni Di Santi: gestione della parte crittografica
*    Davide Cellot: gestione sezione Statistics; gestione import/export dei dati su database locale
*    Massimiliano Conti: gestione generazione password random e controllo robustezza password tramite apposita GUI


test_master
