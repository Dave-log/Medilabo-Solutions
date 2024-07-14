import React, {useState, useEffect } from "react";
import axios from "axios";
import { AgGridReact } from 'ag-grid-react';
import { ColDef } from 'ag-grid-community';
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-quartz.css";
import Button from '@mui/material/Button';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import TextField from "@mui/material/TextField";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";

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
    const [open, setOpen] = useState(false);
    const [newPatient, setNewPatient] = useState<Partial<Patient>>({
        firstName: '',
        lastName: '',
        birthdate: '',
        gender: '',
        address: '',
        phoneNumber: ''
    });

    useEffect(() => {
        fetchPatients();
    }, []);

    const fetchPatients = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/patient/api/v1/patients`);
          setPatients(response.data);
        } catch (error) {
            console.error('Error fetching patients:', error);
        }
    };

    const handleAddPatient = async () => {
        try {
            const response = await axios.post(`http://localhost:8080/patient/api/v1/patients`, newPatient);
            setPatients([...patients, response.data]);
            setOpen(false);
            setNewPatient({ firstName: '', lastName: '', birthdate: '', gender: '', address: '', phoneNumber: ''});
        } catch (error) {
            console.error('Error adding patient:', error);
        }
    };

    const handleUpdatePatient = async (updatedPatient: Patient) => {
        try {
            await axios.put(`http://localhost:8080/patient/api/v1/patients/${updatedPatient.id}`, updatedPatient);
            setPatients(patients.map(patient => patient.id === updatedPatient.id ? updatedPatient : patient));
        } catch (error) {
            console.error('Error updating patient:', error);
        }
    }

    const handleDeletePatient = async (id: number) => {
        try {
            await axios.delete(`http://localhost:8080/patient/api/v1/patients/${id}`);
            setPatients(patients.filter(patient => patient.id !== id));
        } catch (error) {
            console.error('Error deleting patient:', error);
        }
    }

    const renderActionsCell = (params: any) => (
        <div>
            <button onClick={() => handleUpdatePatient(params.data)}>Modifier</button>
            <button onClick={() => handleDeletePatient(params.data.id)}>Supprimer</button>
        </div>
    );

    const [colDefs] = useState<ColDef[]>([
        { field: "firstName", flex: 0.9, headerClass: 'center-header' },
        { field: "lastName", flex: 1, headerClass: 'center-header' },
        { field: "birthdate", flex: 0.9, headerClass: 'center-header' },
        { field: "gender", flex: 0.7, headerClass: 'center-header' },
        { field: "address", flex: 1, headerClass: 'center-header'},
        { field: "phoneNumber", flex: 1.1, headerClass: 'center-header'},
        {
            headerName: "Actions",
            cellRenderer: renderActionsCell,
            flex: 1.3,
            headerClass: 'center-header'
        }
    ]);

    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setNewPatient({ ...newPatient, [name]: value });
    };

    return (
        <Container>
            <Typography variant="h4" gutterBottom>Liste des patients</Typography>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', marginBottom: 2}}>
                <Button variant="contained" color="primary" onClick={fetchPatients}>Rafraîchir la liste</Button>
            </Box>
            
            <div className='ag-theme-quartz' style={{height: 300, width: '100%'}}>
                <AgGridReact rowData={patients} columnDefs={colDefs} />
            </div>

            <Box sx={{ marginTop: 2}}>
                <Button variant="contained" color="secondary" onClick={handleOpen}>Ajouter un patient</Button>
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
                    <Typography variant="h5" color={"black"} align={"center"} gutterBottom>Ajouter un patient</Typography>
                    <form noValidate autoComplete="off">
                        <TextField
                            required
                            label="Prénom"
                            name="firstName"
                            value={newPatient.firstName}
                            onChange={handleInputChange}
                            fullWidth
                            margin="normal"
                        />
                        <TextField
                            required
                            label="Nom"
                            name="lastName"
                            value={newPatient.lastName}
                            onChange={handleInputChange}
                            fullWidth
                            margin="normal"
                        />
                        <TextField
                            required
                            label="Date de naissance"
                            name="birthdate"
                            type="date"
                            value={newPatient.birthdate}
                            onChange={handleInputChange}
                            fullWidth
                            margin="normal"
                            InputLabelProps={{ shrink: true }}
                        />
                        <TextField
                            required
                            label="Genre"
                            name="gender"
                            value={newPatient.gender}
                            onChange={handleInputChange}
                            fullWidth
                            margin="normal"
                        />
                        <TextField
                            label="Adresse"
                            name="address"
                            value={newPatient.address}
                            onChange={handleInputChange}
                            fullWidth
                            margin="normal"
                        />
                        <TextField
                            label="Téléphone"
                            name="phoneNumber"
                            value={newPatient.phoneNumber}
                            onChange={handleInputChange}
                            fullWidth
                            margin="normal"
                        />
                        <Box sx={{display: "flex", justifyContent: "center", marginTop: 2}}>
                            <Button variant="contained" color="primary" onClick={handleAddPatient}>Ajouter</Button>
                        </Box>
                        
                    </form>
                </Box>
            </Modal>
        </Container>
    );
};

export default PatientList;