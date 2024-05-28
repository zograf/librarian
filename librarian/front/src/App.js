import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { LoginPage } from './pages/login/LoginPage';
import { RegisterPage } from './pages/login/RegisterPage';
import LibraryPage from './pages/home/Library';
import UserRootLayout from './pages/root/UserRootLayout';
import Preferences from './pages/home/preferences/Preferences';
import Recommend from './pages/home/Recommend';
import SearchBooks from './pages/home/books/SearchBooks';
import Trending from './pages/home/Trending';
import AddEditBook from './pages/crud/AddEditBook';

export default function App() {
    const isAdmin = localStorage.getItem("isAdmin")
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
                        <Route exact path='/books/search' element={<SearchBooks/>} />
                        <Route exact path='/trending' element={<Trending/>} />
                        <Route exact path='/admin/book/new' element={<AddEditBook/>}/>
                    </Routes>
                </UserRootLayout>
            </Router>
        </main>
    )
}
