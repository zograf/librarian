import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { LoginPage } from './pages/login/LoginPage';

function App() {
    return(
        <main>
            <Router>
                <Routes>
                    <Route exact path='/' element={<LoginPage />} />
                </Routes>
            </Router>
        </main>
    )
}

export default App;
