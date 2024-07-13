import React, {useState, useEffect } from "react";
import axios from "axios";
import { AgGridReact } from 'ag-grid-react';
import { ColDef } from 'ag-grid-community';
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-quartz.css";
import '../styles/PatientTable.css';

interface Patient {
    id: number;
    lastName: string;
    firstName: string;
    birthdate: string;
    gender: string;
    address: string;
    phoneNumber: string;
}

const PatientList: React.FC = () => {

    const [patients, setPatients] = useState<Patient[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchPatients();
    }, []);

    const fetchPatients = async () => {
        try {
          const response = await axios.get('http://localhost:8080/patient/api/v1/patients');
          setPatients(response.data);
          setLoading(false);
        } catch (error) {
            console.error('Error fetching patients:', error);
            setLoading(false);
        }
    };

    const handleAddPatient = async (newPatient: Patient) => {
        try {
            const response = await axios.post('http://localhost:8080/patient/api/v1/patients', newPatient);
            setPatients([...patients, response.data]);
        } catch (error) {
            console.error('Error adding patient:', error);
        }
    };

    const handleUpdatePatient = async (updatedPatient: Patient) => {
        try {
            await axios.put('http://localhost:8080/patient/api/v1/patients/${updatedPatient.id}', updatedPatient);
            setPatients(patients.map(patient => patient.id === updatedPatient.id ? updatedPatient : patient));
        } catch (error) {
            console.error('Error updatingf patient:', error);
        }
    }

    const handleDeletePatient = async (id: number) => {
        try {
            await axios.delete('http://localhost:8080/patient/api/v1/patients/${id}');
            setPatients(patients.filter(patient => patient.id !== id));
        } catch (error) {
            console.error('Error deleting patient:', error);
        }
    }

    const [colDefs] = useState<ColDef[]>([
        { field: "firstName", flex: 1, headerClass: 'center-header' },
        { field: "lastName", flex: 1, headerClass: 'center-header' },
        { field: "birthdate", flex: 1, headerClass: 'center-header' },
        { field: "gender", flex: 0.8, headerClass: 'center-header' },
        { field: "address", flex: 1.2, headerClass: 'center-header'},
        { field: "phoneNumber", flex: 1.2, headerClass: 'center-header'},
        {
            headerName: "Actions",
            cellRenderer: (params: any) => (
                <div>
                    <button onClick={() => handleUpdatePatient(params.data)}>Edit</button>
                    <button onClick={() => handleDeletePatient(params.data.id)}>Delete</button>
                </div>
            ),
            flex: 1,
            headerClass: 'center-header'
        }
    ]);

    if (loading) {
        return <div>Chargement...</div>;
    }

    return (
        <div>
            <h2>Liste des patients</h2>
            <button onClick={fetchPatients}>Recharger les patients</button>
            <div className='ag-theme-quartz' style={{height: 500, width: '100%'}}>
                <AgGridReact rowData={patients} columnDefs={colDefs} />
            </div>
        </div>
    );
};

export default PatientList;