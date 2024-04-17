import React from 'react';
import {BrowserRouter as Router, NavLink, Route, Routes} from 'react-router-dom';
import Sidebar from './components/Sidebar';
import Home from './components/Home';
import About from './components/Page';
import Documents from './components/Page';
import OpenVacancies from './components/OpenVacancies';
import WorkingVacancies from './components/WorkingVacancies';
import Applicants from './components/CandidatePage';
import Reports from './components/Reports';
import RecruitmentReport from './components/RecruitmentSummaryPage';
import LoginPage from "./components/LoginPage";
import VacancyStatusReport from "./components/VacancyStatusReport";

function App() {
    const [pageTitle, setPageTitle] = React.useState("Главная");
    const [pageLinks, setPageLinks] = React.useState();

    return (
        <Router>
            <div>
                <header>
                    <button className="menu-button" onClick={() => document.querySelector('.sidebar').classList.toggle('open')}>Меню</button>
                    <div>{pageLinks}</div>
                    <h1>{pageTitle}</h1>
                </header>
                <Sidebar />
                <div className="content">
                    <Routes>
                        <Route path="/" element={<Home setPageTitle={setPageTitle} />} />
                        <Route path="/about" element={<About setPageTitle={setPageTitle} title="О сайте" />} />
                        <Route path="/open-vacancies" element={<OpenVacancies setPageTitle={setPageTitle} title="Открытые вакансии" />} />
                        <Route path="/working-vacancies" element={<WorkingVacancies setPageTitle={setPageTitle} title="Вакансии в работе" />} />
                        <Route path="/candidates" element={<Applicants setPageTitle={setPageTitle} title="Соискатели" />} />
                        <Route path="/documents" element={<Documents setPageTitle={setPageTitle} title="Документы" />} />
                        <Route path="/reports" element={<Reports setPageTitle={setPageTitle} setPageLinks={setPageLinks} title="Отчеты" />} />
                        <Route path="/reports/recruitment" element={<RecruitmentReport />} />
                        <Route path="/reports/vacancy-status" element={<VacancyStatusReport />} />
                        <Route path="/reports/svod" element={<RecruitmentReport />} />
                        <Route path="/login" element={<LoginPage />} />
                        <Route path="*" element={<Home setPageTitle={setPageTitle} />} />
                    </Routes>
                </div>
            </div>
        </Router>
    );
}

export default App;
