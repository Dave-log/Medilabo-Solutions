import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navigation/Navbar';
import Login from './components/Auth/Login';
import Register from './components/Auth/Register';
import Patient from './components/Patient/Patient';
import './styles/App.css';
import OAuth2Callback from './components/Auth/OAuth2Callback';

const App: React.FC = () => {
    const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);

    const handleLogout = () => {
      localStorage.removeItem('token');
      setIsAuthenticated(false);
    };

    return (
        <Router>
            <div className="App">
                <Navbar isAuthenticated={isAuthenticated} handleLogout={handleLogout} />

                <header className="App-header">
                    <h1>MEDILABO-SOLUTIONS</h1>
                    <h1>Application de gestion des patients</h1>
                </header>

                <main>
                    <Routes>
                        <Route path="/login" element={<Login setIsAuthenticated={setIsAuthenticated} />} />
                        <Route path="/register" element={<Register />} />
                        <Route path="/patients" element={<Patient />} />
                        <Route path="/" element={<Login setIsAuthenticated={setIsAuthenticated} />} />
                        <Route path="auth/oauth2" element={<OAuth2Callback />} />
                    </Routes>
                </main>
                
            </div>
        </Router>

    );
}

export default App