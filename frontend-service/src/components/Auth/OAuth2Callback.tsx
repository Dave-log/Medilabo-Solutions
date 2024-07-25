import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const OAuth2Callback: React.FC = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const fetchToken = async () => {
            try {
                const response = await axios.get('http://localhost:8080/auth/oauth2');
                const { access_token } = response.data;
                localStorage.setItem('token', access_token);
                navigate('/patients');
            } catch (error) {
                console.error('OAuth2 callback error:', error);
                navigate('/login');
            }
        };

        fetchToken();
    }, [navigate]);

    return <div>Loading...</div>;
};

export default OAuth2Callback;