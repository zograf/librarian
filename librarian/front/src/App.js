import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { LoginPage } from './pages/login/LoginPage';
import { RegisterPage } from './pages/login/RegisterPage';

export default function App() {
    return(
        <main>
            <Router>
                <Routes>
                    <Route exact path='/' element={<LoginPage />} />
                    <Route exact path='/register' element={<RegisterPage/>} />
                </Routes>
            </Router>
        </main>
    )
}
