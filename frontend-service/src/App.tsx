import Patient from './components/Patient';

import './styles/App.css';

function App() {
  return (
    <div className="App">

      <header className="App-header">
        <h1>MEDILABO-SOLUTIONS</h1>
        <h1>Application de gestion des patients</h1>
      </header>

      <main>
        <Patient />
      </main>
      
    </div>

  );
}

export default App
