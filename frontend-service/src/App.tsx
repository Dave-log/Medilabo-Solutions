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

// import React from 'react';
// import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
// import Register from './Register';
// import Login from './Login';
// import PatientList from './PatientList';
// import PrivateRoute from './PrivateRoute';

// const App: React.FC = () => {
//     return (
//         <Router>
//             <Switch>
//                 <Route path="/register" component={Register} />
//                 <Route path="/login" component={Login} />
//                 <PrivateRoute path="/patients" component={PatientList} />
//                 <Route path="/" component={Login} /> {/* Default to login */}
//             </Switch>
//         </Router>
//     );
// };

// export default App;