import React from 'react';
import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../Auth/AuthContext';

const Navbar: React.FC = () => {
    const navigate = useNavigate();
    const { isAuthenticated, handleLogout } = useAuth();
    const handlePatientsClick = () => {
        if (!isAuthenticated) {
            navigate('/login');
        }
    };

    return (
        <AppBar position = "static" sx={{ height: 40 }}>
            <Toolbar sx={{ justifyContent: 'space-between', minHeight: '40px !important', px: 2 }}>
                <Box sx={{ display: 'flex', alignItems: 'center', flexGrow: 1}}>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        <Box component={Link} to={isAuthenticated ? "/patients" : "/login"} onClick={handlePatientsClick} sx={{ textDecoration: 'none', color: 'white', mr: 2 }}>
                            Patients
                        </Box>
                    </Typography>
                </Box>
                {isAuthenticated ? (
                    <Button color="inherit" onClick={handleLogout} component={Link} to="/login">Logout</Button>
                ) : (
                    <Button color="inherit" component={Link} to="/login">Login</Button>
                )}
            </Toolbar>
        </AppBar>
    )
}

export default Navbar;