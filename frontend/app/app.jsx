import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Sidebar from './components/Sidebar';
import Home from './components/Home';
import About from './components/Page';
import OpenVacancies from './components/OpenVacancies'; // Импорт нового компонента для открытых вакансий
import WorkingVacancies from './components/WorkingVacancies'; // Импорт нового компонента для вакансий в работе
import Applicants from './components/CandidatePage';
import Documents from './components/Page';
import Reports from './components/Page';
import CandidatePage from "./components/CandidatePage";

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
                        <Route path="/open-vacancies" element={<OpenVacancies setPageTitle={setPageTitle} title="Открытые вакансии" />} /> {/* Новый маршрут для открытых вакансий */}
                       <Route path="/working-vacancies" element={<WorkingVacancies setPageTitle={setPageTitle} title="Вакансии в работе" />} />  Новый маршрут для вакансий в работе
                        <Route path="/candidates" element={<Applicants setPageTitle={setPageTitle} title="Соискатели" />} />
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
