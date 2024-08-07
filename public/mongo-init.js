//db = connect("localhost:27018/admin")
//db.auth("", "");

//db = db.getSiblingDB('patient_notes');
//db.createCollection('notes');
db = db.getSiblingDB('patient_notes');
db.createCollection('notes');

db.notes.insertMany([
    {
        "patientId": "1",
        "patientName": "TestNone",
        "note": "Le patient déclare qu'il 'se sent très bien'. Poids égal ou inférieur au poids recommandé."
    },
    {
        "patientId": "2",
        "patientName": "TestBorderline",
        "note": "Le patient déclare qu'il ressent beaucoup de stress au travail. Il se plaint également que son audition est anormale dernièrement."
    },
    {
        "patientId": "2",
        "patientName": "TestBorderline",
        "note": "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois. Il remarque également que son audition continue d'être anormale."
    },
    {
        "patientId": "3",
        "patientName": "TestInDanger",
        "note": "Le patient déclare qu'il fume depuis peu."
    },
    {
        "patientId": "3",
        "patientName": "TestInDanger",
        "note": "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière. Il se plaint également de crises d’apnée respiratoire anormales. Tests de laboratoire indiquant un taux de cholestérol LDL élevé."
    },
    {
        "patientId": "4",
        "patientName": "TestEarlyOnset",
        "note": "Le patient déclare qu'il lui est devenu difficile de monter les escaliers. Il se plaint également d’être essoufflé. Tests de laboratoire indiquant que les anticorps sont élevés. Réaction aux médicaments."
    },
    {
        "patientId": "4",
        "patientName": "TestEarlyOnset",
        "note": "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps."
    },
    {
        "patientId": "4",
        "patientName": "TestEarlyOnset",
        "note": "Le patient déclare avoir commencé à fumer depuis peu. Hémoglobine A1C supérieure au niveau recommandé."
    },
    {
        "patientId": "4",
        "patientName": "TestEarlyOnset",
        "note": "Taille, Poids, Cholestérol, Vertige et Réaction."
    }
]);