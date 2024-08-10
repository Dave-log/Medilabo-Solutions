import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { GoogleOAuthProvider, GoogleLogin, CredentialResponse } from '@react-oauth/google';
import { Container, Typography, Box } from '@mui/material';
import { useAuth } from './AuthContext';

const LoginRegister: React.FC = () => {
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();
    const { setIsAuthenticated } = useAuth();

    const handleLoginSuccess = async (credentialResponse: CredentialResponse) => {
        if (credentialResponse.credential) {
            try {
                localStorage.setItem('token', credentialResponse.credential);
                setIsAuthenticated(true);

                fetch('http://localhost:8080/api/auth/google', {
                    headers: new Headers({
                        'Authorization': `Bearer ${credentialResponse.credential}`
                    })
                })

                navigate('/patients');
            } catch (error) {
                console.error('Login error:', error);
                setError('Login failed. Please try again.');
            }
        }  
    }

    const handleError = () => {
        console.error('Authentication error:', error);
        setError('Authentication failed. Please try again.');
    };

    return (
        <GoogleOAuthProvider clientId={"648209968703-25m2abibg15g7v1sbjbetgp6dq72fdvo.apps.googleusercontent.com"}>
            <Container maxWidth="xs" sx={{ marginTop: 8 }}>
                <Typography variant="h4" gutterBottom align="center">
                    Bienvenue chez Medilabo Solutions
                </Typography>

                {/* Affichage de l'erreur */}
                {error && (
                    <Typography variant="body1" color="error" align="center">
                        {error}
                    </Typography>
                )}

                <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                    {/* Bouton de connexion */}
                    <GoogleLogin
                        onSuccess={handleLoginSuccess}
                        onError={handleError}
                    />
                </Box>
            </Container>
        </GoogleOAuthProvider>
    );
};

export default LoginRegister;
