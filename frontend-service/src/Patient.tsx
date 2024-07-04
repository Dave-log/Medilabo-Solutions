import React, { useState} from "react";
import axios from "axios";

interface Patient {
    id: number;
    lastName: string;
    firstName: string;
    birthdate: string;
    gender: string;
    address: string;
    phone_number: string;
}

const PatientList: React.FC = () => {
    const [patients, setPatients] = useState<Patient[]>([]);
    const [error, setError] = useState<string | null>(null);

    const handleFetchPatients = async () => {
        try {
          const response = await axios.get('http://localhost:8080/patient/api/v1/patients');
          setPatients(response.data);
        } catch (error: any) {
            setError(error.message);
        }
    };

    return (
        <div>
            <h2>Liste des patients</h2>
            <button onClick={handleFetchPatients}>Charger tous les patients</button>
            {error && <p>Error: {error}</p>}
            <ul>
                {patients.map(patient => (
                    <li key={patient.id}>
                        {patient.firstName} {patient.lastName} - {patient.birthdate} - {patient.gender} - {patient.address} - {patient.phone_number}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default PatientList;