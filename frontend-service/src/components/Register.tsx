import React, { useState } from 'react';
import axios from 'axios';
import { TextField, Button, Container, Typography, Box } from '@mui/material';

const Register: React.FC = () => {
    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [role, setRole] = useState<string>('');

    const handleRegister = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/auth/register', {
                email,
                password,
                role
            });
            console.log(response.data); // Optionally handle response
        } catch (error) {
            console.error('Error registering user:', error);
        }
    };

    return (
        <Container>
            <Typography variant="h4" gutterBottom>Register</Typography>
            <Box component="form" onSubmit={handleRegister} sx={{ mt: 1 }}>
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
                    name="role"
                    label="Role"
                    id="role"
                    value={role}
                    onChange={(e) => setRole(e.target.value)}
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
            </Box>
        </Container>
    );
};

export default Register;