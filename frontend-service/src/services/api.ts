import axios from 'axios';
import { Patient, Note } from '../types/types'

const API_BASE_URL = 'http://localhost:8080';
const API_PATIENT_PATH = '/patient/api/v1/patients';
const API_NOTES_PATH = '/note/api/v1/notes';
const API_REPORT_PATH = '/diabetes-report/api/v1/reports';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json'
    }
});

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

// REPORT API

export const getDiabetesReport = async (id: number) => {
    return api.get(`${API_REPORT_PATH}/${id}`);
}

// TOKEN EXPIRATION MANAGEMENT

const isTokenExpired = (token: string) : boolean => {
    const decoded = JSON.parse(atob(token.split('.')[1])); // Decode JWT payload
    const now = Date.now().valueOf() / 1000; // Conversion to seconds
    return decoded.exp < now; // Compare to expiration
}

const handleExpiredToken = () => {
    localStorage.removeItem('token');
    window.location.href = '/login';
};

// INTERCEPTORS

api.interceptors.request.use(
    config => {
        const token = localStorage.getItem('token');
        if (token) {
            if (isTokenExpired(token)) {
                handleExpiredToken();
                return Promise.reject(new Error('Token expired'));
            }
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

api.interceptors.response.use(
    response => response,
    error => {
        if (error.response && error.response.status === 401) {
            localStorage.removeItem('token');
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
)

export default api;