import React from 'react';
import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';

interface NavbarProps {
    isAuthenticated: boolean;
    handleLogout: () => void;
}

const Navbar: React.FC<NavbarProps> = ({ isAuthenticated, handleLogout }) => {
    const navigate = useNavigate();

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
                        <Box component={Link} to={isAuthenticated ? "/patients" : "/login"} onClick={handlePatientsClick} sx={{ textDecoration: 'none', color: 'white', mr: 2 }}>
                            Element2
                        </Box>
                        <Box component={Link} to={isAuthenticated ? "/patients" : "/login"} onClick={handlePatientsClick} sx={{ textDecoration: 'none', color: 'white', mr: 2 }}>
                            Element3
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