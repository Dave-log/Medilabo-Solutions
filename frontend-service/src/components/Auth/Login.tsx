import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { TextField, Button, Container, Typography, Box, Link } from '@mui/material';
import { login } from '../../services/api';

interface LoginProps {
    setIsAuthenticated: (isAuthenticated: boolean) => void;
}

const Login: React.FC<LoginProps> = ({ setIsAuthenticated }) => {
    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const navigate = useNavigate();

    const handleLogin = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        try {
            await login(email, password);
            setIsAuthenticated(true);
            navigate('/patients');
            console.log('Login successful');
        } catch (error) {
            console.error('Login failed:', error);
        }
    };

    return (
        <Container
            sx={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '50vh'
          }}
          >
            <Box component="form" onSubmit={handleLogin} sx={
                { 
                    display: 'flex', 
                    flexDirection: 'column',
                    alignItems: 'center',
                    width: 400,
                    backgroundColor: 'white',
                    boxShadow: 24,
                    p: 4,
                    borderRadius: 2
                }}
            >
                <Typography variant="h4" color={"black"} gutterBottom>Login</Typography>
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="email"
                    label="Email"
                    name="email"
                    autoComplete="email"
                    autoFocus
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    name="password"
                    label="Password"
                    type="password"
                    id="password"
                    autoComplete="current-password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    color="primary"
                    sx={{ mt: 3, mb: 2 }}
                >
                    Login
                </Button>

                <Link href="/register" variant="body2" sx={{ mt: 2 }}>
                    Register
                </Link>

            </Box>
        </Container>
    );
};

export default Login;