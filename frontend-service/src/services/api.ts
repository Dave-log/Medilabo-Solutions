import axios from 'axios';
import { Patient, Note } from '../types/types'
import { useNavigate } from 'react-router-dom';

const API_BASE_URL = 'http://localhost:8080';
const API_PATIENT_PATH = '/patient/api/v1/patients';
const API_NOTES_PATH = '/note/api/v1/notes';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json'
    }
});

// AUTH API

export const login = async (email: string, password: string) => {
    const response = await api.post('/auth/login', { email, password });
    const token = response.data.access_token;
    localStorage.setItem('token', token);
    return response;
};

export const register = async (email: string, password: string) => {
    return api.post('/auth/register', { email, password });
};

// PATIENTS API

export const getPatients = async () => {
    return api.get(API_PATIENT_PATH);
}

export const addPatient = async (patient: Partial<Patient>) => {
    return api.post(API_PATIENT_PATH, patient);
}

export const updatePatient = async (id: number, patient: Partial<Patient>) => {
    return api.put(`${API_PATIENT_PATH}/${id}`, patient)
}

export const deletePatient = async (id: number) => {
    return api.delete(`${API_PATIENT_PATH}/${id}`);
}

// NOTES API

export const getNotes = async () => {
    return api.get(API_NOTES_PATH);
}

export const getPatientNotes = async (patientId: string) => {
    return api.get(`${API_NOTES_PATH}/patient/${patientId}`);
}

export const addNote = async (note: Partial<Note>) => {
    return api.post(API_NOTES_PATH, note);
}

export const updateNote = async (id: string, note: Partial<Note>) => {
    return api.put(`${API_NOTES_PATH}/${id}`, note);
}

export const deleteNote = async (id: string) => {
    return api.delete(`${API_NOTES_PATH}/${id}`);
}

export const deletePatientNotes = async (patientId: string) => {
    return api.delete(`${API_NOTES_PATH}/patient/${patientId}`)
}

api.interceptors.request.use(
    config => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

api.interceptors.response.use(
    response => response,
    error => {
        const navigate = useNavigate();
        if (error.response && error.response.status === 401) {
            localStorage.removeItem('token');
            navigate('/login');
        }
        return Promise.reject(error);
    }
)

export default api;