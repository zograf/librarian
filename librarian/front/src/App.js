import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { LoginPage } from './pages/login/LoginPage';
import { RegisterPage } from './pages/login/RegisterPage';
import LibraryPage from './pages/home/Library';
import UserRootLayout from './pages/root/UserRootLayout';
import Preferences from './pages/home/preferences/Preferences';
import Recommend from './pages/home/Recommend';

export default function App() {
    return(
        <main>
            <Router>
                <Routes>
                    <Route exact path='/' element={<LoginPage />} />
                    <Route exact path='/register' element={<RegisterPage/>} />
                </Routes>
                <UserRootLayout>
                    <Routes>
                        <Route exact path='/library' element={<LibraryPage/>} />
                        <Route exact path='/preferences' element={<Preferences/>} />
                        <Route exact path='/recommend' element={<Recommend/>} />
                    </Routes>
                </UserRootLayout>
            </Router>
        </main>
    )
}
