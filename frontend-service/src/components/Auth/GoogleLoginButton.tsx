import React from 'react';
import Button from '@mui/material/Button';

const GoogleLoginButton: React.FC = () => {
    const handleLogin = () => {
        window.location.href = 'http://localhost:8080/oauth2/authorization/google';
    };

    return (
        <Button
            variant="contained"
            color="primary"
            onClick={handleLogin}
        >
            Login with Google
        </Button>
    );
};

export default GoogleLoginButton;