import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { TextField, Button, Container, Typography, Box, Link } from '@mui/material';
import { register } from '../../services/api';

const Register: React.FC = () => {
    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [confirmPassword, setConfirmPassword] = useState<string>('');
    const navigate = useNavigate();

    const handleRegister = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        if (password !== confirmPassword) {
            alert('Passwords do not match');
            return;
        }

        try {
            await register(email, password);
            navigate('/login');
        } catch (error) {
            console.error('Registration failed:', error);
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
            <Box component="form" onSubmit={handleRegister} sx={
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
                <Typography variant="h4" color={"black"} gutterBottom>Register</Typography>
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
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    name="confirmPassword"
                    label="Confirm Password"
                    id="confirmPassword"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                />
                <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    color="primary"
                    sx={{ mt: 3, mb: 2 }}
                >
                    Register
                </Button>

                <Link href="/login" variant="body2" sx={{ marginTop: 2 }}>
                    Déjà enregistré ? Login
                </Link>

            </Box>
        </Container>
    );
};

export default Register;