import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

export const login = (email: string, password: string) => {
    return axios.post(`${API_BASE_URL}/auth/login`, { email, password });
};

export const register = (email: string, password: string) => {
    return axios.post(`${API_BASE_URL}/auth/register`, { email, password });
};