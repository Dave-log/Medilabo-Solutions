import React, {useState, useEffect } from "react";
import axios from "axios";
import { Patient } from "../../types/types";
import { AgGridReact } from 'ag-grid-react';
import { ColDef } from 'ag-grid-community';
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-quartz.css";
import { Button, Modal, Box, TextField, Container, Typography } from "@mui/material";
import { getPatients, addPatient, updatePatient, deletePatient } from '../../services/api';

const PatientList: React.FC = () => {
    const [patients, setPatients] = useState<Patient[]>([]);
    const [open, setOpen] = useState(false);
    const [newPatient, setNewPatient] = useState<Partial<Patient>>({
        firstName: '',
        lastName: '',
        birthdate: '',
        gender: '',
        address: '',
        phoneNumber: ''
    });
    const [currentPatient, setCurrentPatient] = useState<Partial<Patient> | null>(null);

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
            setOpen(false);
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
                setOpen(false);
                setCurrentPatient(null);
            } catch (error) {
                console.error('Error updating patient:', error);
            }
        }
    }

    const handleDeletePatient = async (id: number) => {
        try {
            await deletePatient(id);
            const updatedPatients = patients.filter(patient => patient.id !== id);
            setPatients(updatedPatients);
            fetchPatients();
        } catch (error) {
            console.error('Error deleting patient:', error);
        }
    }

    const renderActionsCell = (params: any) => (
        <div>
            <Button variant="contained" color="secondary" sx={{width: 50, fontSize:'0.7em', marginRight: 0.5}} onClick={() => handleOpen(params.data)}>Modifier</Button>
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
            headerName: "Actions",
            cellRenderer: renderActionsCell,
            flex: 1.3,
            headerClass: 'center-header'
        }
    ]);

    const handleOpen = (patient: Partial<Patient> | null = null) => {
        if (patient) {
            setCurrentPatient(patient);
        } else {
            setCurrentPatient(null);
        }
        setNewPatient(patient || { firstName: '', lastName: '', birthdate: '', gender: '', address: '', phoneNumber: ''});
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
        setNewPatient({ firstName: '', lastName: '', birthdate: '', gender: '', address: '', phoneNumber: '' });
        setCurrentPatient(null);
    }

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        if (currentPatient) {
            setCurrentPatient({ ...currentPatient, [name]: value });
        } else {
            setNewPatient({ ...newPatient, [name]: value });
        }
    };

    const handleSubmit = () => {
        if (currentPatient) {
            handleUpdatePatient();
        } else {
            handleAddPatient();
        }
    }

    return (
        <Container>
            <Typography variant="h4" gutterBottom>Liste des patients</Typography>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', marginBottom: 2}}>
                <Button variant="contained" color="primary" onClick={fetchPatients}>Rafraîchir la liste</Button>
            </Box>
            
            <div className='ag-theme-quartz' style={{ height: 300, width: '100%' }}>
                <AgGridReact rowData={patients} columnDefs={colDefs} />
            </div>

            <Box sx={{ marginTop: 2}}>
                <Button variant="contained" color="secondary" onClick={() => handleOpen(null)}>Ajouter un patient</Button>
            </Box>

            <Modal open={open} onClose={handleClose}>
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
                            <Button variant="contained" color="primary" onClick={handleSubmit}>{currentPatient ? 'Modifier' : 'Ajouter'}</Button>
                        </Box>
                        
                    </form>
                </Box>
            </Modal>
        </Container>
    );
};

export default PatientList;