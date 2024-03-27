import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Sidebar from './components/Sidebar';
import Home from './components/Home';
import About from './components/Page';
import Vacancies from './components/Vacancy';
import Applicants from './components/Page';
import Documents from './components/Page';
import Reports from './components/Page';

function App() {
    const [pageTitle, setPageTitle] = React.useState("Главная");

    return (
        <Router>
            <div>
                <header>
                    <button className="menu-button" onClick={() => document.querySelector('.sidebar').classList.toggle('open')}>Меню</button>
                    <h1>{pageTitle}</h1>
                </header>
                <Sidebar />
                <div className="content">
                    <Routes>
                        <Route path="/" element={<Home setPageTitle={setPageTitle} />} />
                        <Route path="/about" element={<About setPageTitle={setPageTitle} title="О сайте" />} />
                        <Route path="/vacancies" element={<Vacancies setPageTitle={setPageTitle} title="Вакансии" />} />
                        <Route path="/applicants" element={<Applicants setPageTitle={setPageTitle} title="Соискатели" />} />
                        <Route path="/documents" element={<Documents setPageTitle={setPageTitle} title="Документы" />} />
                        <Route path="/reports" element={<Reports setPageTitle={setPageTitle} title="Отчеты" />} />
                        <Route path="*" element={<Home setPageTitle={setPageTitle} />} />
                    </Routes>
                </div>
            </div>
        </Router>
    );
}

export default App;

