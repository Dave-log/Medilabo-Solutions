import React from 'react';
import Patient from './Patient';

import './App.css';

function App() {

  return (
    <div className="App">

      <header className="App-header">
        <h1>Application de gestion des patients</h1>
      </header>

      <main>
        <Patient />
      </main>
      
    </div>
  );

}

export default App;
