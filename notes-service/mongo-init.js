db = connect("localhost:27018/admin")
db.auth("", "");

db = db.getSiblingDB('patient_notes');
db.createCollection('notes');