import React, {useState, useEffect } from "react";
import { Patient } from "../../types/types";
import { AgGridReact } from 'ag-grid-react';
import { ColDef } from 'ag-grid-community';
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-quartz.css";
import { Button, Modal, Box, TextField, Container, Typography } from "@mui/material";
import { getPatients, addPatient, updatePatient, deletePatient } from '../../services/api';
import NoteModal from "./NoteModal";
import ReportCellRenderer from "./ReportCellRenderer";

const PatientComponent: React.FC = () => {
    const [patients, setPatients] = useState<Patient[]>([]);
    const [openPatientModal, setOpenPatientModal] = useState(false);
    const [newPatient, setNewPatient] = useState<Partial<Patient>>({
        firstName: '',
        lastName: '',
        birthdate: '',
        gender: '',
        address: '',
        phoneNumber: ''
    });
    const [currentPatient, setCurrentPatient] = useState<Partial<Patient> | null>(null);
    const [selectedPatient, setSelectedPatient] = useState<Patient | null>(null);

    useEffect(() => {
        fetchPatients();
    }, []);

    const fetchPatients = async () => {
        try {
          const response = await getPatients();
          setPatients(response.data);
        } catch (error) {
            console.error('Error fetching patients:', error);
        }
    };

    const handleAddPatient = async () => {
        try {
            const response = await addPatient(newPatient);
            setPatients([...patients, response.data]);
            setOpenPatientModal(false);
            setNewPatient({ firstName: '', lastName: '', birthdate: '', gender: '', address: '', phoneNumber: ''});
        } catch (error) {
            console.error('Error adding patient:', error);
        }
    };

    const handleUpdatePatient = async () => {
        if (currentPatient && currentPatient.id !== undefined) {
            try {
                const updatedPatient = { ...currentPatient } as Patient;
                await updatePatient(updatedPatient.id, updatedPatient);
                setPatients(patients.map(patient => patient.id === updatedPatient.id ? updatedPatient : patient));
                setOpenPatientModal(false);
                setCurrentPatient(null);
            } catch (error) {
                console.error('Error updating patient:', error);
            }
        }
    };

    const handleDeletePatient = async (id: number) => {
        try {
            await deletePatient(id);
            const updatedPatients = patients.filter(patient => patient.id !== id);
            setPatients(updatedPatients);
            fetchPatients();
        } catch (error) {
            console.error('Error deleting patient:', error);
        }
    };

    const handleOpenPatientModal = (patient: Partial<Patient> | null = null) => {
        if (patient) {
            setCurrentPatient(patient);
        } else {
            setCurrentPatient(null);
        }
        setNewPatient(patient || { firstName: '', lastName: '', birthdate: '', gender: '', address: '', phoneNumber: ''});
        setOpenPatientModal(true);
    };

    const handleOpenNotesModal = async (patient: Patient) => {
        try {
            setSelectedPatient(patient);
        } catch (error) {
            console.error('Error fetching notes:', error);
        }
    };

    const handleCloseModal = () => {
        setOpenPatientModal(false);
        setNewPatient({ firstName: '', lastName: '', birthdate: '', gender: '', address: '', phoneNumber: '' });
        setCurrentPatient(null);
        setSelectedPatient(null);
    };

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        if (currentPatient) {
            setCurrentPatient({ ...currentPatient, [name]: value });
        } else {
            setNewPatient({ ...newPatient, [name]: value });
        }
    };

    const handleSubmitPatient = () => {
        if (currentPatient) {
            handleUpdatePatient();
        } else {
            handleAddPatient();
        }
    };

    const renderNotesCell = (params: any) => (
        <div>
            <Button variant="contained" color="primary" sx={{width: 100, fontSize: '0.7em'}} onClick={() => handleOpenNotesModal(params.data)}>Voir les notes</Button>
        </div>
    );

    const renderActionsCell = (params: any) => (
        <div>
            <Button variant="contained" color="secondary" sx={{width: 50, fontSize:'0.7em', marginRight: 0.5}} onClick={() => handleOpenPatientModal(params.data)}>Modifier</Button>
            <Button variant="contained" color="secondary" sx={{width: 50, bgcolor:'#b82828', fontSize:'0.7em'}} onClick={() => handleDeletePatient(params.data.id)}>Supprimer</Button>
        </div>
    );

    const [colDefs] = useState<ColDef[]>([
        { field: "firstName", headerName: "Prénom", flex: 0.8, headerClass: 'center-header' },
        { field: "lastName", headerName: "Nom", flex: 1.1, headerClass: 'center-header' },
        { field: "birthdate", headerName: "Naissance", flex: 0.9, headerClass: 'center-header' },
        { field: "gender", headerName: "Genre", flex: 0.6, headerClass: 'center-header' },
        { field: "address", headerName: "Adresse", flex: 1, headerClass: 'center-header'},
        { field: "phoneNumber", headerName: "Téléphone", flex: 1.1, headerClass: 'center-header'},
        {
            headerName: "Notes",
            cellRenderer: renderNotesCell,
            flex: 1,
            headerClass: 'center-header'
        },
        {
            headerName: "Risque Diabète",
            cellRenderer: 'reportCellRenderer',
            flex: 1.4,
            headerClass: 'center-header'
        },
        {
            headerName: "Données Patient",
            cellRenderer: renderActionsCell,
            flex: 1.3,
            headerClass: 'center-header'
        }
    ]);

    return (
        <Container>
            <Typography variant="h4" gutterBottom>Liste des patients</Typography>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', marginBottom: 2}}>
                <Button variant="contained" color="primary" onClick={fetchPatients}>Rafraîchir la liste</Button>
            </Box>
            
            <div className='ag-theme-quartz' style={{ height: 300, width: '100%' }}>
                <AgGridReact 
                    rowData={patients} 
                    columnDefs={colDefs} 
                    components={{ reportCellRenderer: ReportCellRenderer}}
                />
            </div>

            <Box sx={{ marginTop: 2}}>
                <Button variant="contained" color="secondary" onClick={() => handleOpenPatientModal(null)}>Ajouter un patient</Button>
            </Box>

            <Modal open={openPatientModal} onClose={handleCloseModal}>
                <Box
                    sx={{
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                        width: 400,
                        backgroundColor: 'white',
                        boxShadow: 24,
                        p: 4,
                    }}
                >
                    <Typography variant="h5" color={"black"} align={"center"} gutterBottom>{currentPatient ? 'Modifier le patient' : 'Ajouter un patient'}</Typography>
                    <form noValidate autoComplete="off">
                        <TextField
                            required
                            label="Prénom"
                            name="firstName"
                            value={currentPatient?.firstName || newPatient.firstName}
                            onChange={handleInputChange}
                            fullWidth
                            margin="normal"
                        />
                        <TextField
                            required
                            label="Nom"
                            name="lastName"
                            value={currentPatient?.lastName || newPatient.lastName}
                            onChange={handleInputChange}
                            fullWidth
                            margin="normal"
                        />
                        <TextField
                            required
                            label="Date de naissance"
                            name="birthdate"
                            type="date"
                            value={currentPatient?.birthdate || newPatient.birthdate}
                            onChange={handleInputChange}
                            fullWidth
                            margin="normal"
                            InputLabelProps={{ shrink: true }}
                        />
                        <TextField
                            required
                            label="Genre"
                            name="gender"
                            value={currentPatient?.gender || newPatient.gender}
                            onChange={handleInputChange}
                            fullWidth
                            margin="normal"
                        />
                        <TextField
                            label="Adresse"
                            name="address"
                            value={currentPatient?.address || newPatient.address}
                            onChange={handleInputChange}
                            fullWidth
                            margin="normal"
                        />
                        <TextField
                            label="Téléphone"
                            name="phoneNumber"
                            value={currentPatient?.phoneNumber || newPatient.phoneNumber}
                            onChange={handleInputChange}
                            fullWidth
                            margin="normal"
                        />
                        <Box sx={{display: "flex", justifyContent: "center", marginTop: 2}}>
                            <Button variant="contained" color="primary" onClick={handleSubmitPatient}>{currentPatient ? 'Modifier' : 'Ajouter'}</Button>
                        </Box>
                        
                    </form>
                </Box>
            </Modal>

            {selectedPatient && (
                <NoteModal patient={selectedPatient} onClose={handleCloseModal} />
            )}

        </Container>
    );
};

export default PatientComponent;