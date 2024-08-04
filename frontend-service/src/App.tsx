import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navigation/Navbar';
import LoginRegister from './components/Auth/LoginRegister';
import Patient from './components/Patient/Patient';
import './styles/App.css';
import { AuthProvider } from './components/Auth/AuthContext';

const App: React.FC = () => {
    return (
        <AuthProvider>
            <Router>
            <div className="App">
                <Navbar />

                <header className="App-header">
                    <h1>MEDILABO-SOLUTIONS</h1>
                    <h1>Application de gestion des patients</h1>
                </header>

                <main>
                    <Routes>
                        <Route path="/login" element={<LoginRegister />} />
                        <Route path="/patients" element={<Patient />} />
                        <Route path="/" element={<LoginRegister />} />
                    </Routes>
                </main>
                
            </div>
            </Router>
        </AuthProvider>
    );
}

export default App