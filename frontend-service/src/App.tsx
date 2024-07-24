import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/Auth/Login';
import Register from './components/Auth/Register';
import Patient from './components/Patient/Patient';
import './styles/App.css';

function App() {
    return (
        <Router>
            <div className="App">

                <header className="App-header">
                    <h1>MEDILABO-SOLUTIONS</h1>
                    <h1>Application de gestion des patients</h1>
                </header>

                <main>
                    <Routes>
                        <Route path="/login" element={<Login />} />
                        <Route path="/register" element={<Register />} />
                        <Route path="/patients" element={<Patient />} />
                    </Routes>
                </main>

            </div>
        </Router>

    );
}

export default App