import axios from 'axios';
import { Patient } from '../types/types'

const API_BASE_URL = 'http://localhost:8080';
const API_PATIENT_PATH = '/patient/api/v1/patients';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json'
    }
});

export const login = async (email: string, password: string) => {
    const response = await api.post('/auth/login', { email, password });
    const token = response.data;
    localStorage.setItem('token', token);
    return response;
};

export const register = async (email: string, password: string) => {
    return api.post('/auth/register', { email, password });
};

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

api.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export default api;